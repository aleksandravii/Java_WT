package techshop.web.commands;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import techshop.domain.GroupManager;
import techshop.domain.entities.Category;
import techshop.web.utils.ConfigurationManager;
import techshop.web.utils.SessionRequestContent;

/**
 * Initialize data for shop main page
 */
public class ShopCommand implements ActionCommand {

	private static final long serialVersionUID = -1854028941651330749L;

	private static final Logger LOG = Logger.getLogger(ShopCommand.class);

	@Override
	public String execute(SessionRequestContent content) {
		LOG.debug("Command starts");
		String page = ConfigurationManager.getProperty("path.page.shop");

		Map<String, Object> requestAttributes = content.getRequestAttributes();

		List<Category> categoryList = GroupManager.getAllCategories();
		requestAttributes.put("categories", categoryList);
		LOG.trace("Set category list to request attributes ==> " + categoryList);

		LOG.debug("Command finished");
		return page;
	}

}
