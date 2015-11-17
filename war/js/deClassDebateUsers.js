/*
 * Java Class Attributes
	private String id;
	private String debateId;
	private String userId;
	private String userEmail;
	private String role;
	private String code;
	private String token;
*/

function createDebateUser(debateUser, callback) {

	$.ajax({
		url: "/_ah/api/debatable/v1/debateusers",
		cache: false,
		type: "POST",
		data: JSON.stringify(debateUser),
		dataType: "json",
		contentType: "application/json"
	}).done(function(response) {
		console.log(response);
		callback(response);
	});
	
}

function getDebatesByUserId(userId, callback) {

	$.ajax({
		url: "/_ah/api/debatable/v1/getDebatesByUserId/" + userId,
		cache: false,
		type: "GET",
		dataType: "json"
	}).done(function(response) {
		console.log("Server Response: " + response);
		callback(response);
	});
	
}