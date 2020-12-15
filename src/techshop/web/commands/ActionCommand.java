package techshop.web.commands;

import java.io.Serializable;

import techshop.web.utils.SessionRequestContent;

/**
 * Interface defines one action execute(), define the implementation of an
 * interface to implement less business logic implementation of the appropriate
 * command.
 * Implements Command pattern
 */
public interface ActionCommand extends Serializable {

	/**
	 * Execution method for command.
	 * 
	 * @param content 
	 * @return page
	 */
	String execute(SessionRequestContent content);

}
