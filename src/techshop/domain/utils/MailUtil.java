package techshop.domain.utils;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import techshop.domain.entities.User;
import techshop.domain.exceptions.MailException;
import techshop.web.utils.MessageManager;

/**
 * Provide to send registration notification
 */
public class MailUtil {

	private static final String USER_NAME = "techshopepam@gmail.com";

	private static final String PASSWORD = "techshoppass1244";

	private static final Logger LOG = Logger.getLogger(MailUtil.class);

	public static void sendRegistrationNotification(User user) {
		String subject = MessageManager.getProperty("message.info.success.registration");
		String message = message(user);
		try {
			sendMail(user.getEmail(), subject, message);
		} catch (MessagingException e) {
			LOG.error("Registration notification is not sent", e);
			throw new MailException();
		}
		LOG.trace("Notifacation was sended to user: " + user.getLogin());
	}

	private static String message(User user) {
		return user.getFirstName() + MessageManager.getProperty("message.info.notification.registration") + "\n\r"
				+ MessageManager.getProperty("message.info.notification.registration.login") + " " + user.getLogin()
				+ MessageManager.getProperty("message.info.notification.registration.password") + " "
				+ user.getPassword();
	}

	private static void sendMail(String mail, String subject, String message)
			throws AddressException, MessagingException {
		LOG.debug("Start sending mail");
		Message msg = new MimeMessage(getSession());
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
		msg.setSubject(subject);
		msg.setText(message);
		Transport.send(msg);
		LOG.debug("Finished sending mail");
	}

	private static Session getSession() {
		Session session = Session.getInstance(getProperties(), new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USER_NAME, PASSWORD);
			}
		});
		return session;
	}

	private static Properties getProperties() {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");
		return properties;
	}

}
