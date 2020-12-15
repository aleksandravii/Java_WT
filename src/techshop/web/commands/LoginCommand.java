package techshop.web.commands;

import java.util.Map;

import org.apache.log4j.Logger;

import techshop.domain.UserManager;
import techshop.domain.entities.Role;
import techshop.domain.entities.User;
import techshop.web.exceptions.AccessException;
import techshop.web.utils.ActionType;
import techshop.web.utils.ConfigurationManager;
import techshop.web.utils.MessageManager;
import techshop.web.utils.SessionRequestContent;
import techshop.web.utils.UserInputValidator;

/**
 * Login command
 */
public class LoginCommand implements ActionCommand {

	private static final long serialVersionUID = 4689763151664039521L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(SessionRequestContent content) {
		LOG.debug("Command starts");

		String page = ConfigurationManager.getProperty("redirect.page.shop");

		// init data for main page
		ShopCommand shopCommand = new ShopCommand();
		shopCommand.execute(content);
				
		LOG.trace("ActionType ==> " + content.getActionType().name());
		if (ActionType.GET == content.getActionType()) {
			page = ConfigurationManager.getProperty("path.page.login");
		} else if (ActionType.POST == content.getActionType()) {
			page = loginProcess(content);
		} else {
			String errorMessage = "Try to unknown access to command";
			LOG.error(errorMessage);
			throw new AccessException(errorMessage);
		}

		LOG.debug("LoginCommand finished");
		return page;
	}

	private String loginProcess(SessionRequestContent content) {

		Map<String, Object> sessionAttributes = content.getSessionAttributes();

		String login = content.getRequestParameter("login");
		LOG.trace("Request parameter login ==> " + login);

		String password = content.getRequestParameter("password");

		if (!UserInputValidator.isValidParameters(login, password)) {
			LOG.warn("Invalid input data");
			sessionAttributes.put("invalid_data", MessageManager.getProperty("message.info.invalid.input.data"));
			return ConfigurationManager.getProperty("redirect.page.login");
		}

		User user = UserManager.getUserByLogin(login);
		LOG.trace("Found in DB: user ==> " + user);

		if (user == null || !password.equals(user.getPassword())) {
			LOG.info("Unknown user");
			sessionAttributes.put("unknown_user", MessageManager.getProperty("message.info.wrong.login.password"));
			return ConfigurationManager.getProperty("redirect.page.login");
		}

		if (user.getIsBlocked()) {
			LOG.info("Blocked user");
			sessionAttributes.put("user_blocked", MessageManager.getProperty("message.info.user.blocked"));
			return ConfigurationManager.getProperty("redirect.page.login");
		}

		Role userRole = Role.getRoleByUser(user);

		sessionAttributes.put("user", user);
		LOG.trace("Set the session attribute: user ==> " + user);

		sessionAttributes.put("user_role", userRole);
		LOG.trace("Set the session attribute: userRole ==> " + userRole);

		LOG.trace("User ==> " + user.getLogin() + " logged as " + userRole.toString().toLowerCase());
		
		return ConfigurationManager.getProperty("redirect.page.shop");
	}

}
