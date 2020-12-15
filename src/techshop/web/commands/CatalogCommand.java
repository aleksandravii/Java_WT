package techshop.web.commands;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import techshop.domain.GoodsManager;
import techshop.domain.GroupManager;
import techshop.domain.entities.Category;
import techshop.domain.entities.Colour;
import techshop.domain.entities.Goods;
import techshop.domain.entities.Group;
import techshop.domain.entities.Manufacturer;
import techshop.web.exceptions.AccessException;
import techshop.web.utils.ActionType;
import techshop.web.utils.ConfigurationManager;
import techshop.web.utils.SessionRequestContent;

/**
 * Initialize data for catalog page
 */
public class CatalogCommand implements ActionCommand {

	private static final long serialVersionUID = 7001897149806346885L;

	private static final Logger LOG = Logger.getLogger(CatalogCommand.class);

	/**
	 * Initialize data for catalog page by user selected category and forward to
	 * catalog page.
	 */
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

	private void doGet(SessionRequestContent content) {
		Map<String, Object> sessionAttributes = content.getSessionAttributes();
		Map<String, Object> requestAttributes = content.getRequestAttributes();

		String category = content.getRequestParameter("category");
		if (category == null) {
			category = (String) sessionAttributes.get("category");
		} else {
			sessionAttributes.put("category", category);
		}
		LOG.trace("Obtained category ==> " + category);

		List<Category> allCategories = GroupManager.getAllCategories();

		List<Goods> goodsList;
		List<Group> groupList;
		List<Manufacturer> manufacturerList;
		List<Category> categoryList;
		List<Colour> colourList;

		String sizeParam = content.getRequestParameter("size");
		int size = 0;
		if (sizeParam != null && !sizeParam.isEmpty()) {
			size = Integer.parseInt(sizeParam);
		}
		LOG.trace("obtained size ==> " + size);

		if ("all".equalsIgnoreCase(category)) {
			goodsList = GoodsManager.getAllGoods();
			categoryList = allCategories;
			groupList = GroupManager.getAllGroups();
			manufacturerList = GoodsManager.getAllManufacturers();
			colourList = GoodsManager.getAllColours();
		} else {
			categoryList = GroupManager.getCategoriesByName(category);
			goodsList = GoodsManager.getGoodsByCategory(category);
			groupList = GroupManager.getGroupsByCategory(category);
			manufacturerList = GoodsManager.getManufacturersByCategory(category);
			colourList = GoodsManager.getColoursByCategory(category);
		}

		//additional task 
		if (size > 0) {
			goodsList = GoodsManager.getGoodsByCount(size);
		}

		setPrice(content);

		setGoodsForSort(content, goodsList);

		removeMessageFromCart(content);

		requestAttributes.put("goods", goodsList);
		LOG.trace("Set to the request attributes list goods ==> " + goodsList);

		requestAttributes.put("groups", groupList);
		LOG.trace("Set to the request attributes list groups ==> " + groupList);

		requestAttributes.put("manufacturers", manufacturerList);
		LOG.trace("Set to the request attributes list manufacturers ==> " + manufacturerList);

		requestAttributes.put("colours", colourList);
		LOG.trace("Set to the request attributes list colours ==> " + colourList);

		requestAttributes.put("all_categories", allCategories);
		LOG.trace("Set to the request attributes all categories ==> " + allCategories);

		requestAttributes.put("choose_categories", categoryList);
		LOG.trace("Set to the request attributes list choose categories ==> " + goodsList);
	}

	/**
	 * Set max and min price for price form on page.
	 * 
	 */
	private void setPrice(SessionRequestContent content) {
		Map<String, Object> requestAttributes = content.getRequestAttributes();

		int maxPrice = GoodsManager.getMaxPrice();
		int minPrice = GoodsManager.getMinPrice();

		requestAttributes.put("cur_max_price", maxPrice);
		LOG.trace("Set to the request attributes max price ==> " + maxPrice);

		requestAttributes.put("cur_min_price", minPrice);
		LOG.trace("Set to the request attributes min price ==> " + minPrice);
	}

	/**
	 * Set to session list of goods for sort command. Contain goods in session
	 * provide to user sort goods previously selected by different parameters.
	 */
	private void setGoodsForSort(SessionRequestContent content, List<Goods> goodsList) {
		Map<String, Object> sessionAttributes = content.getSessionAttributes();

		String sortParam = content.getRequestParameter("sort_param");

		if (!"sort".equals(sortParam)) {
			sessionAttributes.put("selected_goods", goodsList);
		}
	}

	private void removeMessageFromCart(SessionRequestContent content) {
		Map<String, Object> sessionAttributes = content.getSessionAttributes();
		sessionAttributes.put("success_order", null);
	}

}
