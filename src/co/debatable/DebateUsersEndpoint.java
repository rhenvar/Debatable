package co.debatable;

import co.debatable.PMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@Api(name = "debatable", namespace = @ApiNamespace(ownerDomain = "debatable.co", ownerName = "debatable.co", packagePath = ""))
public class DebateUsersEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listDebateUsers")
	public CollectionResponse<DebateUsers> listDebateUsers(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<DebateUsers> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(DebateUsers.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<DebateUsers>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (DebateUsers obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<DebateUsers> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getDebateUsers")
	public DebateUsers getDebateUsers(@Named("id") String id) {
		PersistenceManager mgr = getPersistenceManager();
		DebateUsers debateusers = null;
		try {
			debateusers = mgr.getObjectById(DebateUsers.class, id);
		} finally {
			mgr.close();
		}
		return debateusers;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param debateusers the entity to be inserted.
	 * @return The inserted entity.
	 * @throws UnsupportedEncodingException 
	 */
	@ApiMethod(name = "insertDebateUsers")
	public DebateUsers insertDebateUsers(DebateUsers debateusers) throws UnsupportedEncodingException {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (containsDebateUsers(debateusers)) {
				throw new EntityExistsException("Object already exists");
			}
			DebateEndpoint debateEndpoint = new DebateEndpoint();
			Debate debate = debateEndpoint.getDebate(debateusers.getDebateId());
			UserEndpoint userEndpoint = new UserEndpoint();
			User createdByUser = userEndpoint.getUser(debate.getCreatedBy());
			
			if (debate.getCreatedBy() != debateusers.getUserId()) {
				debateusers.setIsOwner("No");
				if (debateusers.getRole() == "judge") {
					EmailEngine email = new EmailEngine();
					email.sendMail(4, debateusers.getUserEmail(), "https://debatable-app.appspot.com/link.html?a=2&d=" + debateusers.getDebateId() + "&u=" + debateusers.getUserId(), debate.getDebateName(), createdByUser.getFirstName() + " " + createdByUser.getLastName());					
				} else {
					EmailEngine email = new EmailEngine();
					email.sendMail(3, debateusers.getUserEmail(), "https://debatable-app.appspot.com/link.html?a=2&d=" + debateusers.getDebateId() + "&u=" + debateusers.getUserId(), debate.getDebateName(), createdByUser.getFirstName() + " " + createdByUser.getLastName());					
				}
			} else {
				debateusers.setIsOwner("Yes");
			}
			mgr.makePersistent(debateusers);
		} finally {
			mgr.close();
		}
		return debateusers;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param debateusers the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateDebateUsers")
	public DebateUsers updateDebateUsers(DebateUsers debateusers) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (!containsDebateUsers(debateusers)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.makePersistent(debateusers);
		} finally {
			mgr.close();
		}
		return debateusers;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeDebateUsers")
	public void removeDebateUsers(@Named("id") String id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			DebateUsers debateusers = mgr.getObjectById(DebateUsers.class, id);
			mgr.deletePersistent(debateusers);
		} finally {
			mgr.close();
		}
	}

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */

	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "getDebatesByUserId", httpMethod="GET", path = "getDebatesByUserId/{userId}")
	public List<DebateUsers> getDebatesByUserId(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit,
			@Named("userId") String userId) {

		PersistenceManager mgr = null;
		List<DebateUsers> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(DebateUsers.class);
			query.setFilter("userId == userIdParam");
			query.declareParameters("String userIdParam");
			System.out.println("Trying userId: " + userId);
			execute = (List<DebateUsers>) query.execute(userId);
		} finally {
			mgr.close();
		}

		return execute;
	}

	private boolean containsDebateUsers(DebateUsers debateusers) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(DebateUsers.class, debateusers.getId());
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
