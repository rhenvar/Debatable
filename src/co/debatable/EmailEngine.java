package co.debatable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailEngine {

	public void sendMail (int emailTemplate, String email, String url, String debateName, String createdByName) throws UnsupportedEncodingException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		
		// TEMPLATE CONTENT
		String htmlBodyHeader = "" 
				+ "<html><body>"
				+ "<p><strong style='color: rgb(131, 148, 169); font-family: Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px; background-color: rgb(249, 249, 250);'>"
				+ "<table id='bodyWrap' width='100%' cellspacing='0' cellpadding='0' style='background:#f1f1f1;width:100%'>"
				+ "<!--[if mso]><div align='center'><table id='tableForOutlook' width='600' style='display:block; margin:0 auto 0 auto;'><tr><td><div align='left'><![endif]-->"
				+ "<tr><td>"
				+ "<table id='contentWrap' cellpadding='0' cellspacing='0' style='max-width:600px;background:#fff;display:block;margin:10px auto'>"
				+ "<tr><td id='header' height='' style='background:#ffffff;text-align:center;padding-top:5px;max-width:600px;display:block'>"
				+ "<img src='http://www.debatable.co/images/debatable_logo_blue.png' alt='Debatable' style='border:none'></td></tr>"
				+ "<tr><td id='mainContent' style='background:white;padding:40px;max-width:600px;display:block;font-family:Arial;'>"
				+ "<div id='yield' style='color:#888888;font-family:Helvetica,Arial,san-serif;font-size:14px;line-height:150%'>";
				
		String htmlBodyFooter = ""
				+ "<p>&nbsp;</p><p style='font-family:Helvetica,Arial;color:#aaa;font-size:11px;'>If you would like to unsubscribe and stop receiving these emails please <a class='inline' href='/unsubscribe.html'>click here</a>.</p>"
				+ "<p style='font-family:Helvetica,Arial;color:#aaa;font-size:11px;'>[VOICES FREEDOM OF SPEECH, LLC - MAILING ADDRESS NEEDED] &middot; Seattle, WA</p>"
				+ "</div></td></tr></table></td></tr><!--[if gte mso 9]></div></td></tr></table></div><![endif]--></table></strong></p></body></html>";

		String textBodyFooter = ""
				+ "\r\n\r\nIf you would like to unsubscribe and stop receiving these emails please visit http://www.debatable.co/unsubscribe.html\r\n"
				+ "Voices Freedom of Speech, LLC - 2324 N 58th St &middot; Seattle, WA 98103";

		String htmlContent = "";
		String textContent = "";
		String emailSubject = "";

		switch (emailTemplate) {
			// 1 - NEW USER SIGNUP TEMPLATE
			case 1:
				htmlContent = htmlBodyHeader + "Thank you for registering with Debatable! You have begun your 7 day free trial. "
						+ "During the next 7 days you can participate in and create as many debates as you want. "
						+ "Remember to be using Google Chrome as your web browser for compatibility reasons. "
						//+ "If you need some additional tips on how to use debatable click <a href='http://www.debatable.co/help.html'>HERE</a>. "
						+ "If you want to test your connection before debating click <a href='http://www.debatable.co/test.html'>HERE</a>. "
						+ "After the trial has ended we encourage you to subscribe so you can continue to improve your skills. "
						+ "Enjoy!" + htmlBodyFooter;
				textContent = "";
				emailSubject = "Thank you for joining Debatable.";
				break;

			// 2 - DEBATE INVITATION NEW USER
			case 2:
				htmlContent = htmlBodyHeader + "You have been invited to participate in a debate through debatable! Debatable is an "
						+ "online live video debating platform for all of academia. We encourage you to join in and have fun while improving "
						+ "your skills in debate. By clicking this link you start a free, 7 day trial, where you can create and participate in as "
						+ "many debates as you want. Afterwards we encourage you to subscribe in order to keep participating in debate against "
						+ "anyone, anywhere, at anytime. Before using debatable remember to launch Google Chrome as a web browser. "
						//+ "If you need some additional tips on how to use debatable click <a href='http://www.debatable.co/help.html'>HERE</a>. "
						+ "If you want to test your connection before the debate click <a href='http://www.debatable.co/test.html'>HERE</a>. "
						+ "Enjoy the debate!<p><a href='" + url + "'>Join the Debate</a></p>" + htmlBodyFooter;
				textContent = "";
				emailSubject = "You've been invited to a debate.";
				break;

			// 3 - DEBATE INVITATION EXISTING USER	
			case 3:
				htmlContent = htmlBodyHeader + "<p><table width='100%'><tr><td width='25%' align='right'>Debate Name:</td><td><b>" + debateName + "</b></td></tr><tr><td width='25%' align='right'>Created By:</td><td><b>" + createdByName + "</b></td></tr></table></p>You have been invited to participate in a debate through debatable! Debatable is an "
						+ "online live video debating platform for all of academia. We encourage you to join in and have fun while improving "
						+ "your skills in debate. By clicking this link you start a free, 7 day trial, where you can create and participate in as "
						+ "many debates as you want. Afterwards we encourage you to subscribe in order to keep participating in debate against "
						+ "anyone, anywhere, at anytime. Before using debatable remember to launch Google Chrome as a web browser. "
						//+ "If you need some additional tips on how to use debatable click <a href='http://www.debatable.co/help.html'>HERE</a>. "
						+ "If you want to test your connection before the debate click <a href='http://www.debatable.co/test.html'>HERE</a>. "
						+ "Enjoy the debate!<p><a href='" + url + "'>Join the Debate</a></p>" + htmlBodyFooter;
				textContent = "";
				emailSubject = "You've been invited to a debate.";
				break;

			// 4 - DEBATE JUDGE INVITE
			case 4:
				htmlContent = htmlBodyHeader + "<p><table width='100%'><tr><td width='25%' align='right'>Debate Name:</td><td><b>" + debateName + "</b></td></tr><tr><td width='25%' align='right'>Created By:</td><td><b>" + createdByName + "</b></td></tr></table></p>You have been invited to judge a debate through debatable! "
						+ "Debatable is an online live video debating platform for all of academia which allows debaters to debate anyone, anywhere, at anytime. "
						+ "Judging on debatable is always free. "
						+ "Remember to use Google Chrome as a web browser for compatibility reasons. "
						//+ "If you need some tips on how to judge a debate through debatable click HERE. "
						+ "Enjoy the debate!" + htmlBodyFooter;
				textContent = "";
				emailSubject = "You've been invited to judge a debate.";
				break;	

			// 5 - DEBATE REMINDER
			case 5:
				htmlContent = htmlBodyHeader + "You have a debate titled " + debateName + " starting in 30 minutes! "
						+ "If you have not tested your connection yet, click HERE to make sure you are ready to debate. "
						+ "If you need to familiarize yourself with the platform click HERE to learn the ins and outs of debatable. "
						+ "Enjoy the debate!" + htmlBodyFooter;
				textContent = "";
				emailSubject = "You've debate is about to start.";
				break;	

			// 6 - PASSWORD RESET	
			case 6:
				System.out.println("Email to reset is finally: " + email);
				PersistenceManager mgr = getPersistenceManager();
				Query q = mgr.newQuery(User.class);
				q.setFilter("email == emailParam");
				q.declareParameters("String emailParam");
				List <User> result = (List<User>) q.execute(email);
				for (User u : result) {
					htmlContent = htmlBodyHeader + "Please click below to reset your password."
							+ "<p><a href='https://debatable-app.appspot.com/reset.html?token=" + u.getToken() + "'>Reset Password</a>" + htmlBodyFooter;
					textContent = "";
					emailSubject = "Reset your password.";
				}
				break;	
					
			default:
				return;
		}

		try {
		    Message msg = new MimeMessage(session);
	        Multipart mp = new MimeMultipart();

	        MimeBodyPart htmlMime = new MimeBodyPart();
	        htmlMime.setContent(htmlContent, "text/html");
	        mp.addBodyPart(htmlMime);
	        msg.setContent(mp);

	        MimeBodyPart textMime = new MimeBodyPart();
	        textMime.setText(textContent);
	        mp.addBodyPart(textMime);
	        msg.setContent(mp);

	        msg.setFrom(new InternetAddress("shane@debatable.co", "Debatable Support"));
		    msg.addRecipient(Message.RecipientType.TO,
		     new InternetAddress(email, email));
		    msg.setSubject(emailSubject);

		    Transport.send(msg);
			System.out.println("Trying to send email...");

		} catch (AddressException e) {
		    System.out.println(e.toString());
		} catch (MessagingException e) {
		    System.out.println(e.toString());
		}
	}
	
	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}
