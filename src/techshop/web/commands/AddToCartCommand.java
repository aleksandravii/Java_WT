package techshop.web.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import techshop.domain.GoodsManager;
import techshop.domain.beans.OrderItemBean;
import techshop.domain.entities.Goods;
import techshop.web.exceptions.AccessException;
import techshop.web.utils.ActionType;
import techshop.web.utils.ConfigurationManager;
import techshop.web.utils.MessageManager;
import techshop.web.utils.SessionRequestContent;

/**
 * Add goods to cart. Cart hold goods in user session
 */
public class AddToCartCommand implements ActionCommand {

	private static final long serialVersionUID = -4144001284559578004L;

	private static final Logger LOG = Logger.getLogger(AddToCartCommand.class);

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

		LOG.debug("Command finised");
		return page;
	}

	@SuppressWarnings("unchecked")
	private void doGet(SessionRequestContent content) {
		// load data for left menu
		CatalogCommand catalogCommand = new CatalogCommand();
		catalogCommand.execute(content);

		Map<String, Object> sessionAttributes = content.getSessionAttributes();
		Map<String, Object> requestAttributes = content.getRequestAttributes();

		int goodsId = Integer.parseInt(content.getRequestParameter("goods_id"));
		LOG.trace("Obtained goods ID ==> " + goodsId);

		OrderItemBean orderItemBean = new OrderItemBean();
		Goods goods = GoodsManager.getGoodsById(goodsId);

		orderItemBean.setGoods(goods);
		orderItemBean.setAmount(1);

		List<OrderItemBean> orderItemList = (List<OrderItemBean>) sessionAttributes.get("order_item_list");
		if (orderItemList == null) {
			orderItemList = new ArrayList<>();
		}
		LOG.trace("Obtained order list from session ==> " + orderItemBean);

		if (!orderItemList.contains(orderItemBean)) {
			orderItemList.add(orderItemBean);
			LOG.trace("Added to cart goods ==> " + goods);
			requestAttributes.put("add_to_cart", MessageManager.getProperty("message.info.success.add"));
		} else {
			LOG.trace("Goods already exist in cart");
			requestAttributes.put("add_to_cart", MessageManager.getProperty("message.info.no.add"));
		}

		requestAttributes.put("added_goods_id", goodsId);
		LOG.trace("Set to the request attributes goods ID ==> " + goodsId);

		sessionAttributes.put("order_item_list", orderItemList);
		LOG.trace("Set to the session attributes order items ==> " + orderItemList);
	}

}
