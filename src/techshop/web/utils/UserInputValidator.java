package techshop.web.utils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;

import techshop.domain.exceptions.DomainException;

/**
 * Input validator.
 * 
 * @author Nikita Datsenko
 *
 */
public class UserInputValidator {

	private static final Logger LOG = Logger.getLogger(UserInputValidator.class);

	public static boolean isValidParameters(String... parameters) {
		for (String param : parameters) {
			if (param == null || param.isEmpty() || param.length() > 50) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Validate email with official java email API.
	 * 
	 * @param email
	 *            to validate.
	 * @throws DomainException
	 *             if email invalid
	 */
	public static boolean isValidEmailAddress(String email) {
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
			return true;
		} catch (AddressException e) {
			LOG.warn("Invalid email ==> " + email, e);
			return false;
		}
	}

	public static boolean isValidDate(String date) {
		if (date == null) {
			return false;
		}
		
		if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
			return true;
		}
		
		return false;
	}
}
