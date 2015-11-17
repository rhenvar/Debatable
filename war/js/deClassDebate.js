/*
 * Java Class Attributes
	private String id;
	private String debateName;
	private String debateFormat;
	private String propSpeakerName;
	private String propEmail;
	private String propId;
	private String oppSpeakerName;
	private String oppEmail;
	private String oppId;
	private String judgeSpeakerName;
	private String judgeEmail;
	private String judgeId;
	private String timerFormat;
	private String guestCode;
*/

function createDebate(debate, callback) {

	$.ajax({
		url: "/_ah/api/debatable/v1/debate",
		cache: false,
		type: "POST",
		data: JSON.stringify(debate),
		dataType: "json",
		contentType: "application/json"
	}).done(function(response) {
		console.log(response);
		callback(response);
	});
	
}

function updateDebate(debate, callback) {
	
	$.ajax({
		url: "/_ah/api/debatable/v1/debate",
		cache: false,
		type: "PUT",
		data: JSON.stringify(debate),
		dataType: "json",
    	contentType: "application/json"
	}).done(function(response) {
		callback(response);
	});
	
}

function getDebate(debateId, callback) {

	$.ajax({
		url: "/_ah/api/debatable/v1/debate/" + debateId,
		cache: false,
		type: "GET",
		dataType: "json"
	}).done(function(response) {
		console.log("Server Response: " + response);
		callback(response);
	});
	
}