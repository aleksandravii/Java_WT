package techshop.web.commands;

import java.util.Map;

import org.apache.log4j.Logger;

import techshop.domain.UserManager;
import techshop.domain.entities.Role;
import techshop.domain.entities.User;
import techshop.domain.exceptions.MailException;
import techshop.domain.exceptions.SameUserException;
import techshop.web.utils.ActionType;
import techshop.web.utils.CaptchaController;
import techshop.web.utils.ConfigurationManager;
import techshop.web.utils.MessageManager;
import techshop.web.utils.SessionRequestContent;
import techshop.web.utils.UserInputValidator;

/**
 * Registration users
 */
public class RegistrationCommand implements ActionCommand {

	private static final long serialVersionUID = 3835786074868589008L;

	private static final Logger LOG = Logger.getLogger(RegistrationCommand.class);

	/**
	 * Save user data by specified parameters.
	 * 
	 * @param content
	 * @return path to registration page
	 */
	@Override
	public String execute(SessionRequestContent content) {
		// load gata for left menu on page
		ShopCommand shopCommand = new ShopCommand();
		shopCommand.execute(content);

		String page = ConfigurationManager.getProperty("path.page.registration");

		if (content.getActionType() == ActionType.POST) {
			page = doPost(content);
		}

		return page;

	}

	private String doPost(SessionRequestContent content) {
		String page = ConfigurationManager.getProperty("redirect.page.redistration");

		Map<String, Object> sessionAttributes = content.getSessionAttributes();

		User user = extractUserFromRequest(content);

		if (!UserInputValidator.isValidParameters(user.getFirstName(), user.getLastName(), user.getLogin(),
				user.getPassword())) {
			LOG.trace("Invalid input data");
			sessionAttributes.put("result", MessageManager.getProperty("message.info.invalid.input.data"));
			return page;
		}

		if (!UserInputValidator.isValidEmailAddress(user.getEmail())) {
			LOG.trace("Invalid input email");
			sessionAttributes.put("result", MessageManager.getProperty("message.info.invalid.input.data"));
			return page;
		}

		boolean isValidCaptcha = CaptchaController.isValid(content.getRequestParameter("g-recaptcha-response"));
		if (!isValidCaptcha) {
			LOG.trace("Captcha is invalid!");
			sessionAttributes.put("result", MessageManager.getProperty("message.info.invalid.captcha"));
			return page;
		}

		try {
			UserManager.registrationUser(user);
		} catch (SameUserException e) {
			LOG.trace("User already exist", e);
			sessionAttributes.put("result", MessageManager.getProperty("message.info.already.reg"));
			return page;
		} catch (MailException e) {
			LOG.warn("Registration notification is not sent", e);
		}

		// clear messages to user
		sessionAttributes.put("result", null);

		sessionAttributes.put("success", MessageManager.getProperty("message.info.success.registration"));

		return page;
	}

	private User extractUserFromRequest(SessionRequestContent content) {
		String firstName = content.getRequestParameter("first_name");
		String lastName = content.getRequestParameter("last_name");
		String email = content.getRequestParameter("email");
		String login = content.getRequestParameter("login");
		String password = content.getRequestParameter("password");

		User user = new User();

		user.setLogin(login);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setBlocked(false);
		user.setRoleId(Role.CLIENT.getId());

		return user;
	}

}
