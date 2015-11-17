package co.debatable;

import co.debatable.PMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

import co.debatable.EmailTemplate;
import co.debatable.User;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.mail.Session;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@Api(name = "debatable", namespace = @ApiNamespace(ownerDomain = "debatable.co", ownerName = "debatable.co", packagePath = ""))
public class UserEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listUser")
	public CollectionResponse<User> listUser(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<User> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(User.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<User>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (User obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<User> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getUser")
	public User getUser(@Named("id") String id) {
		PersistenceManager mgr = getPersistenceManager();
		User user = null;
		try {
			user = mgr.getObjectById(User.class, id);
		} finally {
			mgr.close();
		}
		return user;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param user the entity to be inserted.
	 * @return The inserted entity.
	 * @throws UnsupportedEncodingException 
	 */
	@ApiMethod(name = "insertUser")
	public User insertUser(User user) throws UnsupportedEncodingException {
		PersistenceManager mgr = getPersistenceManager();

		System.out.println("Trying to setup new user...");
		
		EmailEngine email = new EmailEngine();
		email.sendMail(1, user.getEmail(), null, null, null);

		String validationCode = Integer.toString(100000 + (int)(Math.random() * ((999999 - 100000) + 1)));
		user.setValidationCode(validationCode);
		user.setValidated("No");
		user.setToken(UUID.randomUUID().toString());
		
		// Check if email is already in use by another user.
		User checkUser = new User();
		checkUser = checkUser.checkUserByEmail(user.getEmail());
		if (checkUser.getId() != "empty") {
			user.setId("Error");
			user.setEmail("200");
			user.setFirstName("Email address already in use.");
			return user;
		} else {
			try {
				if (containsUser(user)) {
					throw new EntityExistsException("Object already exists");
				}
				
				mgr.makePersistent(user);
				
			} finally {
				mgr.close();
			}
			return user;			
		}

	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param user the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateUser", path = "user")
	public User updateUser(User user) {
		PersistenceManager mgr = getPersistenceManager();
		
		User oUser = new User();
		oUser = mgr.getObjectById(User.class, user.getId());
		
		try {
			if (!containsUser(user)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			
			if (user.getEmail() != null) { oUser.setEmail(user.getEmail()); }
			if (user.getPasswordHashed() != null) { oUser.setPasswordHashed(user.getPasswordHashed()); }
			if (user.getToken() != null) { oUser.setToken(user.getToken()); }
			if (user.getValidationCode() != null) { oUser.setValidationCode(user.getValidationCode()); }
			if (user.getValidated() != null) { oUser.setValidated(user.getValidated()); }
			if (user.getFirstName() != null) { oUser.setFirstName(user.getFirstName()); }
			if (user.getLastName() != null) { oUser.setLastName(user.getLastName()); }
			if (user.getSchool() != null) { oUser.setSchool(user.getSchool()); }
			if (user.getDebateFormats() != null) { oUser.setDebateFormats(user.getDebateFormats()); }
			if (user.getCreatedDate() != null) { oUser.setCreatedDate(user.getCreatedDate()); }
			if (user.getModifiedDate() != null) { oUser.setModifiedDate(user.getModifiedDate()); }
			if (user.getCreatedBy() != null) { oUser.setCreatedBy(user.getCreatedBy()); }
			if (user.getModifiedBy() != null) { oUser.setModifiedBy(user.getModifiedBy()); }
			if (user.getPaypalConfirmation() != null) { oUser.setPaypalConfirmation(user.getPaypalConfirmation()); }
			oUser.setToken(UUID.randomUUID().toString());

			mgr.makePersistent(oUser);

		} finally {
			mgr.close();
		}
		
		oUser.setPasswordHashed(null);
		return oUser;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeUser")
	public void removeUser(@Named("id") String id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			User user = mgr.getObjectById(User.class, id);
			mgr.deletePersistent(user);
		} finally {
			mgr.close();
		}
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "authUser", httpMethod="GET", path = "auth/{email}/{password}")
	public User authUser(
			@Named("email") String email,
			@Named("password") String password) {
		PersistenceManager mgr = getPersistenceManager();
		User user = null;
		
			Query q = mgr.newQuery(User.class);
			q.setFilter("email == emailParam && passwordHashed == passwordParam");
			q.declareParameters("String emailParam, String passwordParam");
			List <User> result = (List<User>) q.execute(email, password);
			for (User u : result) {
				user = u;
			}
			
			if (user == null) {
				user = new User();
				user.setId("Error");
			} else {
				String token = UUID.randomUUID().toString();
				user.setToken(token);
				mgr.makePersistent(user);
			}
				
		return user;
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "authUserByToken", httpMethod="GET", path = "authbytoken/{token}")
	public User authUserByToken(
			@Named("token") String token) {
		PersistenceManager mgr = getPersistenceManager();
		User user = null;
		
		try {
			Query q = mgr.newQuery(User.class);
			q.setFilter("token == tokenParam");
			q.declareParameters("String tokenParam");
			List <User> result = (List<User>) q.execute(token);
			for (User u : result) {
				user = u;
				System.out.println("user matched token: " + user.getEmail());
			}
			
			if (user != null) {
				// TODO LOG THIS LOGIN
			} else {
				user = new User();
				user.setId("Error");
				System.out.println("authUserByToken is null user.");
			}
			
		} finally {
			mgr.close();
		}
		user.setPasswordHashed(null);
		user.setToken(null);
		return user;
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "isUserValidated", httpMethod="GET", path = "isuservalidated/{userId}")
	public User isUserValidated(@Named("userId") String userId) {
		PersistenceManager mgr = getPersistenceManager();
		User user = null;
		User returnUser = new User();

		try {
			user = mgr.getObjectById(User.class, userId);
			returnUser.setValidated(user.getValidated());
			returnUser.setFirstName(user.getFirstName());
			returnUser.setLastName(user.getLastName());
			returnUser.setEmail(user.getEmail());
		} finally {
			mgr.close();
		}

		return returnUser;
		
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 * @throws UnsupportedEncodingException 
	 */
	@ApiMethod(name = "resetPassword", httpMethod="GET", path = "resetpassword/{email}")
	public void resetPassword(
			@Named("email") String email) throws UnsupportedEncodingException {
		EmailEngine emailMsg = new EmailEngine();
		emailMsg.sendMail(6, email, null, null, null);
	}

	private boolean containsUser(User user) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(User.class, user.getId());
		} catch (javax.jdo.JDOObjectNotFoundException ex) {
			contains = false;
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}
