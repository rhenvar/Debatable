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
public class DebateEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listDebate")
	public CollectionResponse<Debate> listDebate(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<Debate> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(Debate.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<Debate>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Debate obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Debate> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getDebate")
	public Debate getDebate(@Named("id") String id) {
		PersistenceManager mgr = getPersistenceManager();
		Debate debate = null;
		try {
			debate = mgr.getObjectById(Debate.class, id);
		} finally {
			mgr.close();
		}
		return debate;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param debate the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertDebate")
	public Debate insertDebate(Debate debate) {
		PersistenceManager mgr = getPersistenceManager();
		User prop1User = new User();
		prop1User.setEmail(debate.getProp1Email());
		prop1User.setFirstName(debate.getProp1SpeakerFirstName());
		prop1User.setLastName(debate.getProp1SpeakerLastName());
		prop1User = prop1User.getUserByEmail(prop1User.getEmail(), prop1User.getFirstName(), prop1User.getLastName());
		debate.setProp1Id(prop1User.getId());

		User prop2User = new User();
		prop2User.setEmail(debate.getProp2Email());
		prop2User.setFirstName(debate.getProp2SpeakerFirstName());
		prop2User.setLastName(debate.getProp2SpeakerLastName());
		prop2User = prop2User.getUserByEmail(prop2User.getEmail(), prop2User.getFirstName(), prop2User.getLastName());
		debate.setProp2Id(prop2User.getId());

		User opp1User = new User();
		opp1User.setEmail(debate.getOpp1Email());
		opp1User.setFirstName(debate.getOpp1SpeakerFirstName());
		opp1User.setLastName(debate.getOpp1SpeakerLastName());
		opp1User = opp1User.getUserByEmail(opp1User.getEmail(), opp1User.getFirstName(), opp1User.getLastName());
		debate.setOpp1Id(opp1User.getId());

		User opp2User = new User();
		opp2User.setEmail(debate.getOpp2Email());
		opp2User.setFirstName(debate.getOpp2SpeakerFirstName());
		opp2User.setLastName(debate.getOpp2SpeakerLastName());
		opp2User = opp2User.getUserByEmail(opp2User.getEmail(), opp2User.getFirstName(), opp2User.getLastName());
		debate.setOpp2Id(opp2User.getId());

		User judgeUser = new User();
		judgeUser.setEmail(debate.getJudgeEmail());
		judgeUser.setFirstName(debate.getJudgeSpeakerFirstName());
		judgeUser.setLastName(debate.getJudgeSpeakerLastName());
		judgeUser = judgeUser.getUserByEmail(judgeUser.getEmail(), judgeUser.getFirstName(), judgeUser.getLastName());
		debate.setJudgeId(judgeUser.getId());

		User ownerUser = new User();
		UserEndpoint getOwner = new UserEndpoint();
		ownerUser = getOwner.getUser(debate.getCreatedBy());

		ContactList contactList = new ContactList();
		
		contactList.createNewContact(prop1User, prop2User);
		contactList.createNewContact(prop1User, opp1User);
		contactList.createNewContact(prop1User, opp2User);
		contactList.createNewContact(prop1User, judgeUser);
		contactList.createNewContact(prop1User, ownerUser);

		contactList.createNewContact(prop2User, prop1User);
		contactList.createNewContact(prop2User, opp1User);
		contactList.createNewContact(prop2User, opp2User);
		contactList.createNewContact(prop2User, judgeUser);
		contactList.createNewContact(prop2User, ownerUser);

		contactList.createNewContact(opp1User, prop1User);
		contactList.createNewContact(opp1User, prop2User);
		contactList.createNewContact(opp1User, opp2User);
		contactList.createNewContact(opp1User, judgeUser);
		contactList.createNewContact(opp1User, ownerUser);

		contactList.createNewContact(opp2User, prop1User);
		contactList.createNewContact(opp2User, prop2User);
		contactList.createNewContact(opp2User, opp1User);
		contactList.createNewContact(opp2User, judgeUser);
		contactList.createNewContact(opp2User, ownerUser);

		contactList.createNewContact(judgeUser, prop1User);
		contactList.createNewContact(judgeUser, prop2User);
		contactList.createNewContact(judgeUser, opp1User);
		contactList.createNewContact(judgeUser, opp2User);
		contactList.createNewContact(judgeUser, ownerUser);

		contactList.createNewContact(ownerUser, prop1User);
		contactList.createNewContact(ownerUser, prop2User);
		contactList.createNewContact(ownerUser, opp1User);
		contactList.createNewContact(ownerUser, opp2User);
		contactList.createNewContact(ownerUser, judgeUser);

		try {
			if (containsDebate(debate)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.makePersistent(debate);
		} finally {
			mgr.close();
		}
		return debate;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param debate the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateDebate")
	public Debate updateDebate(Debate debate) {
		PersistenceManager mgr = getPersistenceManager();

		Debate oDebate = new Debate();
		oDebate = mgr.getObjectById(Debate.class, debate.getId());
		
		try {
			if (!containsDebate(debate)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			
			if (debate.getSessionId() != null) { oDebate.setSessionId(debate.getSessionId()); }
			if (debate.getDebateName() != null) { oDebate.setDebateName(debate.getDebateName()); }
			if (debate.getDebateFormat() != null) { oDebate.setDebateFormat(debate.getDebateFormat()); }
			if (debate.getProp1SpeakerFirstName() != null) { oDebate.setProp1SpeakerFirstName(debate.getProp1SpeakerFirstName()); }
			if (debate.getProp1SpeakerLastName() != null) { oDebate.setProp1SpeakerLastName(debate.getProp1SpeakerLastName()); }
			if (debate.getProp1Email() != null) { oDebate.setProp1Email(debate.getProp1Email()); }
			if (debate.getProp1Id() != null) { oDebate.setProp1Id(debate.getProp1Id()); }
			if (debate.getProp1Code() != null) { oDebate.setProp1Code(debate.getProp1Code()); }
			if (debate.getProp1Token() != null) { oDebate.setProp1Token(debate.getProp1Token()); }
			if (debate.getProp2SpeakerFirstName() != null) { oDebate.setProp2SpeakerFirstName(debate.getProp2SpeakerFirstName()); }
			if (debate.getProp2SpeakerLastName() != null) { oDebate.setProp2SpeakerLastName(debate.getProp2SpeakerLastName()); }
			if (debate.getProp2Email() != null) { oDebate.setProp2Email(debate.getProp2Email()); }
			if (debate.getProp2Id() != null) { oDebate.setProp2Id(debate.getProp2Id()); }
			if (debate.getProp2Code() != null) { oDebate.setProp2Code(debate.getProp2Code()); }
			if (debate.getProp2Token() != null) { oDebate.setProp2Token(debate.getProp2Token()); }
			if (debate.getOpp1SpeakerFirstName() != null) { oDebate.setOpp1SpeakerFirstName(debate.getOpp1SpeakerFirstName()); }
			if (debate.getOpp1SpeakerLastName() != null) { oDebate.setOpp1SpeakerLastName(debate.getOpp1SpeakerLastName()); }
			if (debate.getOpp1Email() != null) { oDebate.setOpp1Email(debate.getOpp1Email()); }
			if (debate.getOpp1Id() != null) { oDebate.setOpp1Id(debate.getOpp1Id()); }
			if (debate.getOpp1Code() != null) { oDebate.setOpp1Code(debate.getOpp1Code()); }
			if (debate.getOpp1Token() != null) { oDebate.setOpp1Token(debate.getOpp1Token()); }
			if (debate.getOpp2SpeakerFirstName() != null) { oDebate.setOpp2SpeakerFirstName(debate.getOpp2SpeakerFirstName()); }
			if (debate.getOpp2SpeakerLastName() != null) { oDebate.setOpp2SpeakerLastName(debate.getOpp2SpeakerLastName()); }
			if (debate.getOpp2Email() != null) { oDebate.setOpp2Email(debate.getOpp2Email()); }
			if (debate.getOpp2Id() != null) { oDebate.setOpp2Id(debate.getOpp2Id()); }
			if (debate.getOpp2Code() != null) { oDebate.setOpp2Code(debate.getOpp2Code()); }
			if (debate.getOpp2Token() != null) { oDebate.setOpp2Token(debate.getOpp2Token()); }
			if (debate.getJudgeSpeakerFirstName() != null) { oDebate.setJudgeSpeakerFirstName(debate.getJudgeSpeakerFirstName()); }
			if (debate.getJudgeSpeakerLastName() != null) { oDebate.setJudgeSpeakerLastName(debate.getJudgeSpeakerLastName()); }
			if (debate.getJudgeEmail() != null) { oDebate.setJudgeEmail(debate.getJudgeEmail()); }
			if (debate.getJudgeId() != null) { oDebate.setJudgeId(debate.getJudgeId()); }
			if (debate.getJudgeCode() != null) { oDebate.setJudgeCode(debate.getJudgeCode()); }
			if (debate.getJudgeToken() != null) { oDebate.setJudgeToken(debate.getJudgeToken()); }
			if (debate.getTimerFormat() != null) { oDebate.setTimerFormat(debate.getTimerFormat()); }
			if (debate.getGuestCode() != null) { oDebate.setGuestCode(debate.getGuestCode()); }
			if (debate.getGuestToken() != null) { oDebate.setGuestToken(debate.getGuestToken()); }
			mgr.makePersistent(oDebate);

		} finally {
			mgr.close();
		}
		return debate;
		
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeDebate")
	public void removeDebate(@Named("id") String id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			Debate debate = mgr.getObjectById(Debate.class, id);
			mgr.deletePersistent(debate);
		} finally {
			mgr.close();
		}
	}

	private boolean containsDebate(Debate debate) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(Debate.class, debate.getId());
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
