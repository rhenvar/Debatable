function createTimerModal(session, times, pAbb, oAbb) {

	var $timerButton = $("<div>", {
		class: "timerButton"
	});
	$timerButton.text("Judge Controls");
	$timerButton.appendTo($('#judgeTimerContainer'));	

	var $modalBox = $("<div>", {
		class: "modalBox"
	});

	var $proWins = $("<div>", {
		class: "timerButtonGold"
	});
	$proWins.text(pAbb + " Wins");
	$proWins.appendTo($modalBox);

	$proWins.click(function() {
		session.signal(
		  {
		    data:"wp"
		  },
		  function(error) {
		    if (error) {
		      console.log("signal error ("
		                   + error.code
		                   + "): " + error.message);
		    } else {
		      console.log("signal sent.");
		    }
		  }
		);
		$modalBox.hide();			
	});

	var $oppWins = $("<div>", {
		class: "timerButtonGold"
	});
	$oppWins.text(oAbb + " Wins");
	$oppWins.appendTo($modalBox);

	$oppWins.click(function() {
		session.signal(
		  {
		    data:"wo"
		  },
		  function(error) {
		    if (error) {
		      console.log("signal error ("
		                   + error.code
		                   + "): " + error.message);
		    } else {
		      console.log("signal sent.");
		    }
		  }
		);
		$modalBox.hide();			
	});

	var $muteGuests = $("<div>", {
		class: "timerButton"
	});
	$muteGuests.text("Mute Guests");
	$muteGuests.appendTo($modalBox);

	$muteGuests.click(function() {
		session.signal(
		  {
		    data:"mute"
		  },
		  function(error) {
		    if (error) {
		      console.log("signal error ("
		                   + error.code
		                   + "): " + error.message);
		    } else {
		      console.log("signal sent.");
		    }
		  }
		);
		$modalBox.hide();			
	});

	var $unmuteGuests = $("<div>", {
		class: "timerButton"
	});
	$unmuteGuests.text("Unmute Guests");
	$unmuteGuests.appendTo($modalBox);

	$unmuteGuests.click(function() {
		session.signal(
		  {
		    data:"unmute"
		  },
		  function(error) {
		    if (error) {
		      console.log("signal error ("
		                   + error.code
		                   + "): " + error.message);
		    } else {
		      console.log("signal sent.");
		    }
		  }
		);
		$modalBox.hide();			
	});

	var $stopTimer = $("<div>", {
		class: "timerButton"
	});
	$stopTimer.text("Stop Timer");
	$stopTimer.appendTo($modalBox);

	$stopTimer.click(function() {
		session.signal(
		  {
		    data:"stop"
		  },
		  function(error) {
		    if (error) {
		      console.log("signal error ("
		                   + error.code
		                   + "): " + error.message);
		    } else {
		      console.log("signal sent.");
		    }
		  }
		);
		$modalBox.hide();			
	});

	var timeList = times.split(',');

	timeList.forEach(function(timeCode) {
		var $timer = $("<div>", {
			class: "timerButton"
		});
		
		var labelAbb;
		
		if (timeCode.substring(0,1) == "p") {
			labelAbb = pAbb;
		} else if (timeCode.substring(0,1) == "o") {
			labelAbb = oAbb;
		} else {
			labelAbb = "?";
		}

	    var timeValue = timeCode.substring(1,timeCode.length);
        var minutes = parseInt(timeValue / 60, 10);
        var seconds = parseInt(timeValue % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

		$timer.text(labelAbb + " " + minutes + ":" + seconds);
		$timer.appendTo($modalBox);

		$timer.click(function() {
			session.signal(
			  {
			    data:timeCode
			  },
			  function(error) {
			    if (error) {
			      console.log("signal error ("
			                   + error.code
			                   + "): " + error.message + " timeCode: " + timeCode);
			    } else {
			      console.log("signal sent with timeCode: " + timeCode);
			    }
			  }
			);
			$modalBox.hide();			
		});

	});

	var $cancelTimer = $("<div>", {
		class: "timerButton"
	});
	$cancelTimer.text("Cancel");
	// REMOVED BECAUSE ITS A DUPLICATE FEATURE TO STOP TIMER ABOVE
	//	$cancelTimer.appendTo($modalBox);

	$cancelTimer.click(function() {
		session.signal(
				  {
				    data:"stop"
				  },
				  function(error) {
				    if (error) {
				      console.log("signal error ("
				                   + error.code
				                   + "): " + error.message);
				    } else {
				      console.log("signal sent.");
				    }
				  }
				);
				$modalBox.hide();			
	});

	$modalBox.hide();			
	$("body").append($modalBox);

	$timerButton.click(function() {
		$modalBox.show();					
	});
}


function createTimerModalNPDA(session) {

	var $timerButton = $("<div>", {
		class: "timerButton"
	});
	$timerButton.text("Judge Controls");
	$timerButton.appendTo($('#judgeTimerContainer'));	

	$modalBox = $("<div>", {
		class: "modalBoxWide"
	});
	
	// *** LEFT COLUMN ***
	var $leftCol = $("<div>", {
		class: "judgeControlCol"
	});
	$leftCol.appendTo($modalBox);

		// *** PROP WINS BUTTON ***
		var $propWinsButton = $("<div>", {	class: "timerButtonGold" });
		$propWinsButton.text("Prop Wins");
		$propWinsButton.appendTo($leftCol);
		$propWinsButton.click(function() {
			sendSignal("wp");
		});
	
		// *** PROP #1 (7 MIN) BUTTON ***
		var $prop1_7min_Button = $("<div>", {	class: "timerButton" });
		$prop1_7min_Button.text("Prop #1 (7:00)");
		$prop1_7min_Button.appendTo($leftCol);
		$prop1_7min_Button.click(function() {
			sendSignal("p-1-420");
		});
	
		// *** PROP #2 (8 MIN) BUTTON ***
		var $prop2_8min_Button = $("<div>", {	class: "timerButton" });
		$prop2_8min_Button.text("Prop #2 (8:00)");
		$prop2_8min_Button.appendTo($leftCol);
		$prop2_8min_Button.click(function() {
			sendSignal("p-2-480");
		});
	
		// *** PROP REBUTTAL (5 MIN) BUTTON ***
		var $prop_r_5min_Button = $("<div>", {	class: "timerButton" });
		$prop_r_5min_Button.text("Prop Rebuttal (5:00)");
		$prop_r_5min_Button.appendTo($leftCol);
		$prop_r_5min_Button.click(function() {
			sendSignal("p-r-300");
		});

	// *** CENTER COLUMN ***
	var $centerCol = $("<div>", {
		class: "judgeControlCol"
	});
	$centerCol.appendTo($modalBox);

		// *** CLOSE BUTTON ***
		var $closeButton = $("<div>", {	class: "timerButtonClose" });
		$closeButton.text("Close");
		$closeButton.appendTo($centerCol);
		$closeButton.click(function() {
			$modalBox.hide();
		});

		// *** MUTE GUESTS BUTTON ***
		var $muteGuestseButton = $("<div>", {	class: "timerButton" });
		$muteGuestseButton.text("Mute Guests");
		$muteGuestseButton.appendTo($centerCol);
		$muteGuestseButton.click(function() {
			sendSignal("m");
		});

		// *** MUTE GUESTS BUTTON ***
		var $unmuteGuestseButton = $("<div>", {	class: "timerButton" });
		$unmuteGuestseButton.text("Unmute Guests");
		$unmuteGuestseButton.appendTo($centerCol);
		$unmuteGuestseButton.click(function() {
			sendSignal("u");
		});

		// *** STOP TIMER BUTTON ***
		var $stopTimerButton = $("<div>", {	class: "timerButton" });
		$stopTimerButton.text("Stop Timer");
		$stopTimerButton.appendTo($centerCol);
		$stopTimerButton.click(function() {
			sendSignal("s");
		});

	// *** RIGHT COLUMN ***
	var $rightCol = $("<div>", {
		class: "judgeControlCol"
	});
	$rightCol.appendTo($modalBox);

		// *** OPP WINS BUTTON ***
		var $oppWinsButton = $("<div>", {	class: "timerButtonGold" });
		$oppWinsButton.text("Opp Wins");
		$oppWinsButton.appendTo($rightCol);
		$oppWinsButton.click(function() {
			sendSignal("wo");
		});

		// *** OPP #1 (8 MIN) BUTTON ***
		var $opp1_8min_Button = $("<div>", {	class: "timerButton" });
		$opp1_8min_Button.text("Opp #1 (8:00)");
		$opp1_8min_Button.appendTo($rightCol);
		$opp1_8min_Button.click(function() {
			sendSignal("o-1-480");
		});

		// *** OPP #2 (8 MIN) BUTTON ***
		var $opp2_8min_Button = $("<div>", {	class: "timerButton" });
		$opp2_8min_Button.text("Opp #2 (8:00)");
		$opp2_8min_Button.appendTo($rightCol);
		$opp2_8min_Button.click(function() {
			sendSignal("o-2-480");
		});

		// *** OPP REBUTTAL (4 MIN) BUTTON ***
		var $opp_r_8min_Button = $("<div>", {	class: "timerButton" });
		$opp_r_8min_Button.text("Opp Rebuttal (4:00)");
		$opp_r_8min_Button.appendTo($rightCol);
		$opp_r_8min_Button.click(function() {
			sendSignal("o-r-240");
		});
		
	/*
	var $proWins = $("<div>", {
		class: "timerButton"
	});
	$proWins.text(pAbb + " Wins");
	$proWins.appendTo($modalBox);

	$proWins.click(function() {
		session.signal(
		  {
		    data:"wp"
		  },
		  function(error) {
		    if (error) {
		      console.log("signal error ("
		                   + error.code
		                   + "): " + error.message);
		    } else {
		      console.log("signal sent.");
		    }
		  }
		);
		$modalBox.hide();			
	});

	var $oppWins = $("<div>", {
		class: "timerButton"
	});
	$oppWins.text(oAbb + " Wins");
	$oppWins.appendTo($modalBox);

	$oppWins.click(function() {
		session.signal(
		  {
		    data:"wo"
		  },
		  function(error) {
		    if (error) {
		      console.log("signal error ("
		                   + error.code
		                   + "): " + error.message);
		    } else {
		      console.log("signal sent.");
		    }
		  }
		);
		$modalBox.hide();			
	});

	var $muteGuests = $("<div>", {
		class: "timerButton"
	});
	$muteGuests.text("Mute Guests");
	$muteGuests.appendTo($modalBox);

	$muteGuests.click(function() {
		session.signal(
		  {
		    data:"mute"
		  },
		  function(error) {
		    if (error) {
		      console.log("signal error ("
		                   + error.code
		                   + "): " + error.message);
		    } else {
		      console.log("signal sent.");
		    }
		  }
		);
		$modalBox.hide();			
	});

	var $unmuteGuests = $("<div>", {
		class: "timerButton"
	});
	$unmuteGuests.text("Unmute Guests");
	$unmuteGuests.appendTo($modalBox);

	$unmuteGuests.click(function() {
		session.signal(
		  {
		    data:"unmute"
		  },
		  function(error) {
		    if (error) {
		      console.log("signal error ("
		                   + error.code
		                   + "): " + error.message);
		    } else {
		      console.log("signal sent.");
		    }
		  }
		);
		$modalBox.hide();			
	});

	var $stopTimer = $("<div>", {
		class: "timerButton"
	});
	$stopTimer.text("Stop Timer");
	$stopTimer.appendTo($modalBox);

	$stopTimer.click(function() {
		session.signal(
		  {
		    data:"stop"
		  },
		  function(error) {
		    if (error) {
		      console.log("signal error ("
		                   + error.code
		                   + "): " + error.message);
		    } else {
		      console.log("signal sent.");
		    }
		  }
		);
		$modalBox.hide();			
	});

	var timeList = times.split(',');

	timeList.forEach(function(timeCode) {
		var $timer = $("<div>", {
			class: "timerButton"
		});
		
		var labelAbb;
		
		if (timeCode.substring(0,1) == "p") {
			labelAbb = pAbb;
		} else if (timeCode.substring(0,1) == "o") {
			labelAbb = oAbb;
		} else {
			labelAbb = "?";
		}

	    var timeValue = timeCode.substring(1,timeCode.length);
        var minutes = parseInt(timeValue / 60, 10);
        var seconds = parseInt(timeValue % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

		$timer.text(labelAbb + " " + minutes + ":" + seconds);
		$timer.appendTo($modalBox);

		$timer.click(function() {
			session.signal(
			  {
			    data:timeCode
			  },
			  function(error) {
			    if (error) {
			      console.log("signal error ("
			                   + error.code
			                   + "): " + error.message + " timeCode: " + timeCode);
			    } else {
			      console.log("signal sent with timeCode: " + timeCode);
			    }
			  }
			);
			$modalBox.hide();			
		});

	});

	var $cancelTimer = $("<div>", {
		class: "timerButton"
	});
	$cancelTimer.text("Cancel");
	// REMOVED BECAUSE ITS A DUPLICATE FEATURE TO STOP TIMER ABOVE
	//	$cancelTimer.appendTo($modalBox);

	$cancelTimer.click(function() {
		session.signal(
				  {
				    data:"stop"
				  },
				  function(error) {
				    if (error) {
				      console.log("signal error ("
				                   + error.code
				                   + "): " + error.message);
				    } else {
				      console.log("signal sent.");
				    }
				  }
				);
				$modalBox.hide();			
	});
*/
	$modalBox.hide();			
	$("body").append($modalBox);

	$timerButton.click(function() {
		$modalBox.show();					
	});
}

function sendSignal(signalData) {
	session.signal(
			  {
			    data:signalData
			  },
			  function(error) {
			    if (error) {
			      console.log("signal error ("
			                   + error.code
			                   + "): " + error.message);
			    } else {
			      console.log("signal sent.");
			    }
			  });

	$modalBox.hide();
	console.log("Should be hidden");

}

function startTimer(duration, display) {
    clearInterval(timerInterval);
	$('#propScore').text("");
	$('#oppScore').text("");
    var timer = duration, minutes, seconds;
    timerInterval = setInterval(function () {
        minutes = parseInt(timer / 60, 10);
        seconds = parseInt(timer % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        display.text(minutes + ":" + seconds);

        if (timer == 30) {
        	var audio = new Audio('TimerSound.mp3');
			audio.play();
        }

        if (--timer < 0) {
        	var audio = new Audio('TimerSound.mp3');
			audio.play();
        	clearInterval(timerInterval);
        }
    }, 1000);
}

function startTimerShowcase() {
	console.log("timer started...");
	var timerArray = ["p90", "o90", "p60", "o60", "p30", "o30"];
	console.log(timerArray);

	var speakerTimer = "oppTimer";

	console.log("duration is: " + duration);
    clearInterval(timerInterval);
	var timer = duration, minutes, seconds;
	
	if (speakerTimer == "propTimer") {
		speakerTimer = "oppTimer";
	} else {
		speakerTimer = "propTimer";			
	}

	var incrementScale;
	var duration;
	var firstPass = true;
	var speaker;
	
    timerInterval = setInterval(function () {
		duration--;
		
    	if (duration < 0 || firstPass == true) {
    		duration = timerArray.shift();
    		speaker = duration.substring(0,1);
    		duration = parseInt(duration.substring(1,duration.length));
    		incrementScale = 400 / duration;
    		firstPass = false;
    	}

    	if (speaker == "p") {
        	$('#propTimer').css({
        		"background-color" : "rgba(0,200,0,0.7)",
        		"width" : Math.floor(duration * incrementScale)
        	});
    		
    	} else {
        	$('#oppTimer').css({
        		"background-color" : "rgba(0,200,0,0.7)",
        		"width" : Math.floor(duration * incrementScale)
        	});
    		
    	}

    	
        minutes = parseInt(duration / 60, 10);
        seconds = parseInt(duration % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        if (duration == 0) {
        	var audio = new Audio('TimerSound.mp3');
			audio.play();
			if (timerArray.length == 0) {
				clearInterval(timerInterval);				
			} else {
				console.log(timerArray.length);
			}
        }
    }, 1000);					

    console.log("timer ended...");

}

function showcaseTimeSet(duration, side) {
	var incrementScale = 400 / duration;

	console.log("duration is: " + duration);
    clearInterval(timerInterval);
	var timer = duration, minutes, seconds;
	
    timerInterval = setInterval(function () {
		duration--;

    	$('#' + side  + 'Timer').css({
    		"background-color" : "rgba(0,200,0,0.7)",
    		"width" : Math.floor(timer * incrementScale)
    	});

    	
        minutes = parseInt(timer / 60, 10);
        seconds = parseInt(timer % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        if (--timer < 0) {
        	var audio = new Audio('BellSound.mp3');
			audio.play();
        	clearInterval(timerInterval);
        	console.log("timer complete, returning true...");
        	return true;
        }
    }, 1000);					
	
}

function createNameTag(name, size, $appendToElement) {
	var nameTagDiv = $('<div>', {
		class: 'nameTag ' + size
	});
	nameTagDiv.text(name);
	nameTagDiv.hide();
	var destination = $appendToElement.offset();
	console.log(destination);
	nameTagDiv.appendTo($appendToElement);
	nameTagDiv.css({position: 'absolute', top: destination.top + $appendToElement.outerHeight() - nameTagDiv.outerHeight(), left: destination.left});
	nameTagDiv.show();
}

function getQueryVariable(variable)
{
       var query = window.location.search.substring(1);
       var vars = query.split("&");
       for (var i=0;i<vars.length;i++) {
               var pair = vars[i].split("=");
               if(pair[0] == variable){return pair[1];}
       }
       return(false);
}

function clickScore(direction) {
	if (direction == "left") {
		var score = $('#propPoints').text();
		score++;
		if (score < 10) {
			$('#propPoints').text("0" + score);			
		} else {
			$('#propPoints').text(score);
		}
	}

	if (direction == "right") {
		var score = $('#oppPoints').text();
		score++;
		if (score < 10) {
			$('#oppPoints').text("0" + score);			
		} else {
			$('#oppPoints').text(score);
		}
	}

}

function getSpeakerFromContacts(speakerRole) {
	
	var $contactsBox = $("<div>", { class: "modalBoxContacts" });

	var $firstNameRow = $("<div>", { class: "contactsRow" });
	
	var $firstNameLabel = $("<div>", { class: "contactsBoxLabel" });
	$firstNameLabel.html("First Name:");
	$firstNameLabel.appendTo($firstNameRow);

	var $firstNameInput = $("<input>", { class: "contactsBoxInput" });
	$firstNameInput.appendTo($firstNameRow);
	
	$firstNameRow.appendTo($contactsBox);

	var $lastNameRow = $("<div>", { class: "contactsRow" });

	var $lastNameLabel = $("<div>", { class: "contactsBoxLabel" });
	$lastNameLabel.html("Last Name:");
	$lastNameLabel.appendTo($lastNameRow);

	var $lastNameInput = $("<input>", { class: "contactsBoxInput" });
	$lastNameInput.appendTo($lastNameRow);

	$lastNameRow.appendTo($contactsBox);

	var $emailRow = $("<div>", { class: "contactsRow" });

	var $emailLabel = $("<div>", { class: "contactsBoxLabel" });
	$emailLabel.html("Email:");
	$emailLabel.appendTo($emailRow);

	var $emailInput = $("<input>", { class: "contactsBoxInput" });
	$emailInput.appendTo($emailRow);

	$emailRow.appendTo($contactsBox);

	var $saveRow = $("<div>", { class: "contactsRowRight" });

	var $cancelButton = $("<div>", { class: "addContactButton" });
	$cancelButton.text("Cancel");
	$cancelButton.appendTo($saveRow);
	$cancelButton.click(function() {
		$firstNameInput.val("");
		$lastNameInput.val("");
		$emailInput.val("");
		$contactsBox.hide();
	})

	var $addButton = $("<div>", { class: "addContactButton" });
	$addButton.text("Add");
	$addButton.appendTo($saveRow);
	$addButton.click(function() {
		updateSpeakerInfo(speakerRole, $firstNameInput.val(), $lastNameInput.val(), $emailInput.val());
		$contactsBox.hide();
	})

	$saveRow.appendTo($contactsBox);
	
	var $selectFromContacts = $("<p>");
	$selectFromContacts.html("<p>Or select a speaker from your contacts:</p>");
	$selectFromContacts.appendTo($contactsBox);

	var $contactSelectList = $("<div>", { class: "contactSelectList" });
	$contactSelectList.appendTo($contactsBox);


	getContactsByUserId($.cookie("deUserID"), function(response) {
		for (var obj in response.items) {
			publishContactsList(response.items[obj].contactFirstName, response.items[obj].contactLastName, response.items[obj].contactEmail, $contactSelectList, speakerRole, $contactsBox);
		}
	});

	var $addMeRow = $("<div>", { class: "contactsRow" });

	var $addMeButton = $("<div>", { class: "addMeButton" });
	$addMeButton.text("Add me as this speaker");
	$addMeButton.appendTo($addMeRow);
	$addMeButton.click(function() {
		updateSpeakerInfo(speakerRole, $.cookie("deFirstName"), $.cookie("deLastName"), $.cookie("deEmail"));
		$contactsBox.hide();
	})

	$addMeRow.appendTo($contactsBox);

	$contactsBox.appendTo(document.body);
	
}

function publishContactsList(firstName, lastName, email, element, speakerRole, container) {
	var $newRow = $("<div>", { class: "contactsRow" });
	
	var $contactItemButton = $("<div>", { class: "addContactButton" });
	$contactItemButton.text("Add");
	$contactItemButton.appendTo($newRow);
	$contactItemButton.click(function() {
		updateSpeakerInfo(speakerRole, firstName, lastName, email);
		container.hide();
	})

	var $contactItemName = $("<div>", { class: "contactName" });
	$contactItemName.html(firstName + " " + lastName + "<br/>" + email);
	$contactItemName.appendTo($newRow);
	
	$newRow.appendTo(element);
}

function updateSpeakerInfo(speakerRole, firstName, lastName, email) {
	switch (speakerRole) {
	
	case "prop1":
		$('#formProp1FirstName').val(firstName);
		$('#formProp1LastName').val(lastName);
		$('#formProp1Email').val(email);
		$('#prop1Speaker').text(firstName + " " + lastName + " (" + email + ")")
		break;
		
	case "prop2":
		$('#formProp2FirstName').val(firstName);
		$('#formProp2LastName').val(lastName);
		$('#formProp2Email').val(email);
		$('#prop2Speaker').text(firstName + " " + lastName + " (" + email + ")")
		break;
		
	case "opp1":
		$('#formOpp1FirstName').val(firstName);
		$('#formOpp1LastName').val(lastName);
		$('#formOpp1Email').val(email);
		$('#opp1Speaker').text(firstName + " " + lastName + " (" + email + ")")
		break;
		
	case "opp2":
		$('#formOpp2FirstName').val(firstName);
		$('#formOpp2LastName').val(lastName);
		$('#formOpp2Email').val(email);
		$('#opp2Speaker').text(firstName + " " + lastName + " (" + email + ")")
		break;
		
	case "judge":
		$('#formJudgeFirstName').val(firstName);
		$('#formJudgeLastName').val(lastName);
		$('#formJudgeEmail').val(email);
		$('#judgeSpeaker').text(firstName + " " + lastName + " (" + email + ")")
		break;
	
	}
}