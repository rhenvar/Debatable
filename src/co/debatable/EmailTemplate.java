package co.debatable;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailTemplate {

	private String msgBodyHeader;
	private String msgBodyFooter;
	private String appUrl = "http://www.debatable.co";

	private static final Logger log = Logger.getLogger(EmailTemplate.class.getName()); 
	
	public EmailTemplate() {
		// ****** Email header template
		msgBodyHeader = "" 
				+ "<html><body>"
				+ "<p><strong style='color: rgb(131, 148, 169); font-family: Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px; background-color: rgb(249, 249, 250);'>"
				+ "<table id='bodyWrap' width='100%' cellspacing='0' cellpadding='0' style='background:#f1f1f1;width:100%'>"
				+ "<!--[if mso]><div align='center'><table id='tableForOutlook' width='600' style='display:block; margin:0 auto 0 auto;'><tr><td><div align='left'><![endif]-->"
				+ "<tr><td>"
				+ "<table id='contentWrap' cellpadding='0' cellspacing='0' style='max-width:600px;background:#fff;display:block;margin:10px auto'>"
				+ "<tr><td id='header' height='20' style='background:#1c65a9;text-align:center;padding-top:5px;max-width:600px;display:block'>&nbsp;</td></tr>"
				+ "<tr><td id='header' height='' style='background:#ffffff;text-align:center;padding-top:5px;max-width:600px;display:block'>"
				+ "<img src='http://www.debatable.co/images/logoEmail.png' alt='Debatable' style='border:none'></td></tr>"
				+ "<tr><td id='mainContent' style='background:white;padding:40px;max-width:600px;display:block;font-family:Arial;'>"
				+ "<div id='yield' style='color:#888888;font-family:Helvetica,Arial,san-serif;font-size:14px;line-height:150%'>";
				
		// ****** Email footer template
		msgBodyFooter = ""
				+ "<p>&nbsp;</p><p style='font-family:Helvetica,Arial;color:#aaa;font-size:11px;'>If you would like to unsubscribe and stop receiving these emails please <a class='inline' href='" + appUrl + "/unsubscribe.html'>click here</a>.</p>"
				+ "<p style='font-family:Helvetica,Arial;color:#aaa;font-size:11px;'>[VOICES FREEDOM OF SPEECH, LLC - MAILING ADDRESS NEEDED] &middot; Seattle, WA</p>"
				+ "</div></td></tr></table></td></tr><!--[if gte mso 9]></div></td></tr></table></div><![endif]--></table></strong></p></body></html>";
	}
	
	public void sendEmail(int template, HashMap <String, String> params, String email, String firstName, String lastName) throws UnsupportedEncodingException {
		
		System.out.println("Trying to send an email...");

		// TODO - Need to send key value pair of parameters to each email template
		
		String msgBody = null;
		String msgSubject = params.get("msgSubject");
		
		switch (template) {

		case 10101: // new user confirmation email
			// * Expected params keys
			// validationCode
			msgBody = ""
			+ "<p class='strong' style='font-family:Helvetica,Arial;font-size:14px;line-height:150%;font-weight:bold;color:#666'>Welcome to Now Meeting!</p>"
			+ "<p style='font-family:'Helvetica','Arial';color:#999;font-size:14px;line-height:150%'>"
			+ "Thank you for joining the Debatable community.  Click below to get started."
			+ "<p style='padding:25px;'><a style='text-decoration:none;' href='" + appUrl + "/verify.html?t=2&c=" + params.get("validationCode") + "&e=" + email + "' >"
			+ "<span style='font-family:Helvetica,Arial;font-size:14px;line-height:150%;padding:10px 25px 10px 25px;background-color:#1c65a9;color:#ffffff;text-decoration:none;'>Verify Email</span>"
			+ "</a></p>"
			+ "<p style='font-family:'Helvetica','Arial';color:#999;font-size:14px;line-height:150%'>Thank you for using Now Meeting!</p>";
			break;

		case 15001: // user admin - password reset
			// * Expected params keys
			// tempPassword
			msgBody = ""
			+ "<p style='font-family:Arial;color:#888888;font-size:14px;'>Please click on the link below to reset your password.</p>"
			+ "<p style='padding:25px;text-align:center;'><a style='text-decoration:none;' href='" + appUrl + "/reset.html?temp=" + params.get("tempPassword") + "&email=" + email + "' >"
			+ "<span style='font-family:Arial;font-size:14px;padding:10px 25px 10px 25px;background-color:#1c65a9;color:#ffffff;text-decoration:none;'>Reset Password</span>"
			+ "</a></p>";
			break;
		}

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		
		String emailName;
		
		if (firstName == null) {
			emailName = email;
		} else {
			if (lastName == null) {
				emailName = firstName;
			} else {
				emailName = firstName + " " + lastName;
			}
		}
			
		try {
		    Message msg = new MimeMessage(session);
		    msg.setFrom(new InternetAddress("brandon@debatable.co", "Debatable"));
		    msg.addRecipient(Message.RecipientType.TO,
		     new InternetAddress("shanedjones@gmail.com", "Shane Jones"));
		    msg.setSubject(msgSubject);
		    msg.setContent(msgBodyHeader + msgBody + msgBodyFooter, "text/html");
		    Transport.send(msg);
		} catch (AddressException e) {
			System.out.println("Issue sending email - AddressException: " + e.toString());
		} catch (MessagingException e) {
			System.out.println("Issue sending email - MessagingException: " + e.toString());
		}
	}
}