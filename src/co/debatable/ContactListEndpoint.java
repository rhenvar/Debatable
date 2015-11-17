package co.debatable;

import co.debatable.PMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@Api(name = "debatable", namespace = @ApiNamespace(ownerDomain = "debatable.co", ownerName = "debatable.co", packagePath = ""))
public class ContactListEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listContactList")
	public CollectionResponse<ContactList> listContactList(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<ContactList> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(ContactList.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<ContactList>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (ContactList obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<ContactList> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getContactList")
	public ContactList getContactList(@Named("id") String id) {
		PersistenceManager mgr = getPersistenceManager();
		ContactList contactlist = null;
		try {
			contactlist = mgr.getObjectById(ContactList.class, id);
		} finally {
			mgr.close();
		}
		return contactlist;
	}

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */

	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "getContactsByUserId", httpMethod="GET", path = "getContactsByUserId/{userId}")
	public List<ContactList> getContactsByUserId(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit,
			@Named("userId") String userId) {

		PersistenceManager mgr = null;
		List<ContactList> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(ContactList.class);
			query.setFilter("ownerId == userIdParam");
			query.declareParameters("String userIdParam");
			execute = (List<ContactList>) query.execute(userId);
		} finally {
			mgr.close();
		}

		return execute;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param contactlist the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertContactList")
	public ContactList insertContactList(ContactList contactlist) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (containsContactList(contactlist)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.makePersistent(contactlist);
		} finally {
			mgr.close();
		}
		return contactlist;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param contactlist the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateContactList")
	public ContactList updateContactList(ContactList contactlist) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (!containsContactList(contactlist)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.makePersistent(contactlist);
		} finally {
			mgr.close();
		}
		return contactlist;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeContactList")
	public void removeContactList(@Named("id") String id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			ContactList contactlist = mgr.getObjectById(ContactList.class, id);
			mgr.deletePersistent(contactlist);
		} finally {
			mgr.close();
		}
	}

	private boolean containsContactList(ContactList contactlist) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(ContactList.class, contactlist.getId());
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
