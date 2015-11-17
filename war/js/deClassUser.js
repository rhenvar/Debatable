/*
 * Java Class Attributes
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
*/

function createUser(user, callback) {

	$.ajax({
		url: "/_ah/api/debatable/v1/user",
		cache: false,
		type: "POST",
		data: JSON.stringify(user),
		dataType: "json",
		contentType: "application/json"
	}).done(function(response) {
		console.log(response);
		callback(response);
	});
	
}

function authUserByToken(token, callback) {

	$.ajax({
		url: "/_ah/api/debatable/v1/authbytoken/" + token,
		cache: false,
		type: "GET",
		dataType: "json"
	}).done(function(response) {
		callback(response);
	});
	
}

function isUserValidated(userId, callback) {

	$.ajax({
		url: "/_ah/api/debatable/v1/isuservalidated/" + userId,
		cache: false,
		type: "GET",
		dataType: "json"
	}).done(function(response) {
		callback(response);
	});
	
}

function resetPassword(email) {

	$.ajax({
		url: "/_ah/api/debatable/v1/resetpassword/" + email,
		cache: false,
		type: "GET",
		dataType: "json"
	});
	
}

function authByEmailPassword(email, password, callback) {

	$.ajax({
		url: "/_ah/api/debatable/v1/auth/" + email + "/" + password,
		cache: false,
		type: "GET",
		dataType: "json"
	}).done(function(response) {
		callback(response);
	});
	
}

function updateUser(user, callback) {

	console.log("updating user with:");
	console.log(user);
	
	$.ajax({
		url: "/_ah/api/debatable/v1/user",
		cache: false,
		type: "PUT",
		data: JSON.stringify(user),
		dataType: "json",
    	contentType: "application/json"
	}).done(function(response) {
		callback(response);
	});
	
}