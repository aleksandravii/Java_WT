package techshop.web.commands;

import java.util.Map;

import org.apache.log4j.Logger;

import techshop.web.utils.ActionType;
import techshop.web.utils.ConfigurationManager;
import techshop.web.utils.MessageManager;
import techshop.web.utils.SessionRequestContent;

/**
 * Change language
 */
public class LanguageCommand implements ActionCommand {

	private static final long serialVersionUID = 6219809685933672563L;

	private static final Logger LOG = Logger.getLogger(LanguageCommand.class);

	/**
	 * Provide to redirect to current page where command has been called. Set
	 * language to session and message manager locale.
	 * 
	 * @see MessageManager
	 */
	@Override
	public String execute(SessionRequestContent content) {
		LOG.debug("Command starts");

		String page = null;

		if (ActionType.POST == content.getActionType()) {
			page = doPost(content);
		}

		LOG.debug("Command finished");
		return page;
	}

	private String doPost(SessionRequestContent content) {
		Map<String, Object> sessionAttributes = content.getSessionAttributes();

		String redirectPage = "controller?";

		String language = content.getRequestParameter("language");
		LOG.trace("Obtained language ==> " + language);

		if (language != null && !language.isEmpty()) {
			sessionAttributes.put("language", language);
			MessageManager.setLocale(language);
			LOG.trace("Language change to ==> " + language);
		}

		String path = content.getRequestParameter("path");
		LOG.trace("Obtained: path ==> " + path);

		if (path == null || "".equals(path) || "command=logout".equals(path)) {
			redirectPage = ConfigurationManager.getProperty("redirect.page.shop");
			return redirectPage;
		}

		return redirectPage + path;
	}

}
