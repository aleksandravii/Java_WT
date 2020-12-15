package techshop.web.utils;

import org.apache.log4j.Logger;

import techshop.domain.exceptions.AppException;
import techshop.web.commands.ActionCommand;
import techshop.web.commands.CommandEnum;

/**
 * Action factory. Implements factory pattern.
 * 
 * @author Nikita Datsenko
 * @see CommandEnum
 *
 */
public class ActionFactory {

	private static final String COMMAND = "command";

	private static final Logger LOG = Logger.getLogger(ActionFactory.class);

	/**
	 * Select command by specified parameter.
	 * 
	 * @param content
	 * @return selected command.
	 */
	public ActionCommand defineCommand(SessionRequestContent content) {
		ActionCommand current = null;

		String action = content.getRequestParameter(COMMAND);
		if (action == null || action.isEmpty()) {
			LOG.error("Obtained no action command");
			throw new AppException("No action type");
		}

		try {
			CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
			current = currentEnum.getCurrentCommand();
		} catch (IllegalArgumentException e) {
			LOG.error("Wrong action: ", e);
			throw new AppException("Command not found or wrong");
		}

		LOG.debug("Select command ==> " + current.getClass().getSimpleName());

		return current;
	}

}
