package techshop.web.commands;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import techshop.domain.GoodsManager;
import techshop.domain.entities.Goods;
import techshop.web.exceptions.AccessException;
import techshop.web.utils.ActionType;
import techshop.web.utils.ConfigurationManager;
import techshop.web.utils.MessageManager;
import techshop.web.utils.SessionRequestContent;
import techshop.web.utils.UserInputValidator;

/**
 * Choose goods by specified price range
 */
public class PriceCatalogCommand implements ActionCommand {

	private static final long serialVersionUID = -4114592296942283251L;

	private static final Logger LOG = Logger.getLogger(PriceCatalogCommand.class);

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

		String priceFromStr = content.getRequestParameter("price_from");
		String priceToStr = content.getRequestParameter("price_to");

		int maxPrice = GoodsManager.getMaxPrice();
		int minPrice = GoodsManager.getMinPrice();

		int priceFrom = 0;
		int priceTo = 0;
		if (UserInputValidator.isValidParameters(priceFromStr, priceToStr)) {
			priceFrom = Integer.parseInt(priceFromStr);
			priceTo = Integer.parseInt(priceToStr);
			if (priceFrom > priceTo || priceFrom < minPrice || priceTo > maxPrice) {
				throw new IllegalArgumentException(MessageManager.getProperty("message.info.invalid.range.input.data"));
			}
		} else {
			priceFrom = minPrice;
			priceTo = maxPrice;
		}
		LOG.trace("Obtained price from: " + priceFrom + ", to: " + priceTo);

		requestAttributes.put("cur_max_price", priceTo);
		requestAttributes.put("cur_min_price", priceFrom);
		LOG.trace("Set to the request attributes max price: " + maxPrice + ", min price: " + minPrice);

		List<Goods> goods = (List<Goods>) requestAttributes.get("goods");
		LOG.trace("Obtained goods list ==> " + goods);
		if (goods == null || goods.isEmpty()) {
			requestAttributes.put("no_goods", MessageManager.getProperty("message.no.goods.finded"));
		}

		List<Goods> matchGoods = GoodsManager.findGoodsByPriceRange(goods, priceFrom, priceTo);
		LOG.trace("Goods list after find by price range ==> " + matchGoods);
		if (matchGoods.isEmpty()) {
			requestAttributes.put("no_goods", MessageManager.getProperty("message.no.goods.finded"));
		}

		// for sort
		sessionAttributes.put("selected_goods", matchGoods);

		requestAttributes.put("goods", matchGoods);
	}

}
