package techshop.web.commands;

import org.apache.log4j.Logger;

import techshop.web.exceptions.AccessException;
import techshop.web.utils.ActionType;
import techshop.web.utils.SessionRequestContent;

/**
 * Logout command
 */
public class LogoutCommand implements ActionCommand {

	private static final long serialVersionUID = -6698903659681242112L;

	private static final Logger LOG = Logger.getLogger(LogoutCommand.class);

	@Override
	public String execute(SessionRequestContent content) {
		LOG.debug("Command starts");

		if (ActionType.GET == content.getActionType()) {
			content.clearSession();
			LOG.trace("Session cleared");
		} else {
			LOG.error("Try to unknown access to command");
			throw new AccessException("Try to unknown access to command");
		}

		ShopCommand shopCommand = new ShopCommand();

		LOG.debug("Command finished");
		return shopCommand.execute(content);
	}

}
