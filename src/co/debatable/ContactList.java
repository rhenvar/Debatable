package co.debatable;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ContactList {

	@PrimaryKey
	private String id; // using formst ownerId-contactId
	private String ownerId;
	private String contactId;
	private String contactFirstName;
	private String contactLastName;
	private String contactEmail;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getContactFirstName() {
		return contactFirstName;
	}
	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}
	public String getContactLastName() {
		return contactLastName;
	}
	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public void createNewContact(User ownerUser, User contactUser) {
		PersistenceManager mgr = getPersistenceManager();

		ContactList newContact = new ContactList();
		newContact.setId(ownerUser.getId() + "-" + contactUser.getId());
		newContact.setOwnerId(ownerUser.getId());
		newContact.setContactId(contactUser.getId());
		newContact.setContactFirstName(contactUser.getFirstName());
		newContact.setContactLastName(contactUser.getLastName());
		newContact.setContactEmail(contactUser.getEmail());
		mgr.makePersistent(newContact);
		mgr.close();
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}
