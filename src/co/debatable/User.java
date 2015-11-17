package co.debatable;
import java.util.List;
import java.util.UUID;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

import co.debatable.PMF;
import co.debatable.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class User {

	@PrimaryKey
	private String id;
	private String email;
	private String passwordHashed;
	private String token;
	private String validationCode;
	private String validated;
	private String firstName;
	private String lastName;
	private String school;
	private String debateFormats;
	private Long createdDate;
	private Long modifiedDate;
	private String createdBy;
	private String modifiedBy;
	private String paypalConfirmation;
		
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswordHashed() {
		return passwordHashed;
	}
	public void setPasswordHashed(String passwordHashed) {
		this.passwordHashed = passwordHashed;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getValidationCode() {
		return validationCode;
	}
	public void setValidationCode(String validationCode) {
		this.validationCode = validationCode;
	}
	public String getValidated() {
		return validated;
	}
	public void setValidated(String validated) {
		this.validated = validated;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getDebateFormats() {
		return debateFormats;
	}
	public void setDebateFormats(String debateFormats) {
		this.debateFormats = debateFormats;
	}
	public Long getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
	}
	public Long getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Long modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public String getPaypalConfirmation() {
		return paypalConfirmation;
	}
	public void setPaypalConfirmation(String paypalConfirmation) {
		this.paypalConfirmation = paypalConfirmation;
	}

	public User checkUserByEmail(String email) {
		PersistenceManager mgr = getPersistenceManager();
		User user = new User();
		user.setId("empty");
		try {
			Query q = mgr.newQuery(User.class);
			q.setFilter("email == emailParam");
			q.declareParameters("String emailParam");
			List <User> result = (List<User>) q.execute(email);
			for (User u : result) {
				user = u;
			}
		} finally {
			mgr.close();
		}
		return user;
	}

	public User getUserByEmail(String email, String firstName, String lastName) {
		PersistenceManager mgr = getPersistenceManager();
		User user = new User();
		user.setId("empty");
		try {
			Query q = mgr.newQuery(User.class);
			q.setFilter("email == emailParam");
			q.declareParameters("String emailParam");
			List <User> result = (List<User>) q.execute(email);
			for (User u : result) {
				user = u;
			}
		} finally {
			mgr.close();
		}
		if (user.getId() == "empty") {
			user = createUser(email, firstName, lastName);
		}
		return user;
	}

	public User createUser(String email, String firstName, String lastName) {
		PersistenceManager mgr = getPersistenceManager();
		User user = new User();
		user.setId("empty");
		try {
			Query q = mgr.newQuery(User.class);
			q.setFilter("email == emailParam");
			q.declareParameters("String emailParam");
			List <User> result = (List<User>) q.execute(email);
			for (User u : result) {
				user = u;
			}
		} finally {
			if (user.getId() == "empty") {
				user.setId(UUID.randomUUID().toString());
				user.setEmail(email);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setValidated("No");
				String token = UUID.randomUUID().toString();
				user.setToken(token);
				mgr.makePersistent(user);
			}
			mgr.close();
		}
		return user;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}
