/*
 * Java Class Attributes
	private String id; // using formst ownerId-contactId
	private String ownerId;
	private String contactId;
	private String contactFirstName;
	private String contactLastName;
	private String contactEmail;
*/

function getContactsByUserId(userId, callback) {

	$.ajax({
		url: "/_ah/api/debatable/v1/getContactsByUserId/" + userId,
		cache: false,
		type: "GET",
		dataType: "json"
	}).done(function(response) {
		console.log("Server Response: " + response);
		callback(response);
	});
	
}