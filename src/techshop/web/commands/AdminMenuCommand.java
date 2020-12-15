package techshop.web.commands;

import java.util.Map;

import org.apache.log4j.Logger;

import techshop.domain.entities.Role;
import techshop.domain.entities.User;
import techshop.web.exceptions.AccessException;
import techshop.web.utils.ActionType;
import techshop.web.utils.ConfigurationManager;
import techshop.web.utils.SessionRequestContent;

/**
 * Initialize admin menu. Check if role is admin and forward to admin page
 */
public class AdminMenuCommand implements ActionCommand {

	private static final long serialVersionUID = 2203082436700053569L;

	private static final Logger LOG = Logger.getLogger(AdminMenuCommand.class);

	@Override
	public String execute(SessionRequestContent content) {
		LOG.debug("Command starts");

		String page = ConfigurationManager.getProperty("path.page.admin.menu");

		if (ActionType.GET == content.getActionType()) {
			doGet(content);
		} else {
			String errorMessage = "Try to unknown access to command";
			LOG.error(errorMessage);
			throw new AccessException(errorMessage);
		}

		LOG.debug("Command finished");
		return page;
	}

	private void doGet(SessionRequestContent content) {
		Map<String, Object> sessionAttributes = content.getSessionAttributes();

		Role role = (Role) sessionAttributes.get("user_role");
		LOG.trace("Obtained role ==> " + role);
		if (Role.ADMIN != role) {
			LOG.warn("Try access to admin menu with role ==> " + role);
			throw new AccessException("Unauthorized access");
		}

		User user = (User) sessionAttributes.get("user");
		LOG.trace("Obtained user ==> " + user);
		if (user == null || user.getRoleId() != role.getId()) {
			LOG.warn("Try access to admin menu by unknown user ==> " + user);
			throw new AccessException("Unauthorized access");
		}
	}

}
