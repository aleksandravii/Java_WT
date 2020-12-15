package techshop.web.commands;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import techshop.domain.GoodsMatcher;
import techshop.domain.entities.Colour;
import techshop.domain.entities.Goods;
import techshop.domain.entities.Group;
import techshop.domain.entities.Manufacturer;
import techshop.web.exceptions.AccessException;
import techshop.web.utils.ActionType;
import techshop.web.utils.ConfigurationManager;
import techshop.web.utils.MessageManager;
import techshop.web.utils.SessionRequestContent;

/**
 * Selection goods by specified parameters
 */
public class SelectionCatalogCommand implements ActionCommand {

	private static final long serialVersionUID = 3507330198697054375L;

	private static final Logger LOG = Logger.getLogger(SelectionCatalogCommand.class);

	@Override
	public String execute(SessionRequestContent content) {
		LOG.debug("Command starts");

		String page = ConfigurationManager.getProperty("path.page.catalog");

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

	@SuppressWarnings("unchecked")
	private void doGet(SessionRequestContent content) {
		// load data for catalog page
		CatalogCommand catalogCommand = new CatalogCommand();
		catalogCommand.execute(content);

		Map<String, Object> sessionAttributes = content.getSessionAttributes();
		Map<String, Object> requestAttributes = content.getRequestAttributes();
		Map<String, String[]> requestParameters = content.getRequestParameters();

		// obtain user input data
		String[] requestGroups = requestParameters.get("group");
		String[] requestManufacturers = requestParameters.get("manufacturer");
		String[] requestColours = requestParameters.get("colour");

		// obtain data about category
		List<Goods> listGoods = (List<Goods>) requestAttributes.get("goods");
		List<Group> listGroups = (List<Group>) requestAttributes.get("groups");
		List<Manufacturer> listManufacturers = (List<Manufacturer>) requestAttributes.get("manufacturers");
		List<Colour> listColours = (List<Colour>) requestAttributes.get("colours");
		LOG.trace("Obtained goods list from the request attributes ==> " + listGoods);

		GoodsMatcher goodsMatcher = new GoodsMatcher();

		goodsMatcher.setRequestGroups(requestGroups);
		goodsMatcher.setRequestManufacturers(requestManufacturers);
		goodsMatcher.setRequestColours(requestColours);

		goodsMatcher.setListGoods(listGoods);
		goodsMatcher.setListGroups(listGroups);
		goodsMatcher.setListManufacturers(listManufacturers);
		goodsMatcher.setListColours(listColours);

		goodsMatcher.matchGoodsByData();

		List<Goods> matchGoods = goodsMatcher.getListGoods();
		LOG.trace("Matched goods list ==> " + goodsMatcher);

		if (matchGoods.isEmpty()) {
			requestAttributes.put("no_goods", MessageManager.getProperty("message.no.goods.finded"));
		}

		// for sort
		sessionAttributes.put("selected_goods", matchGoods);

		requestAttributes.put("selected_groups", requestGroups);
		requestAttributes.put("selected_manufacturers", requestManufacturers);
		requestAttributes.put("selected_colours", requestColours);

		requestAttributes.put("goods", matchGoods);
		LOG.trace("Set goods to the request attributes ==> " + matchGoods);
	}

}
