package techshop.web.commands;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import techshop.domain.beans.OrderItemBean;
import techshop.web.exceptions.AccessException;
import techshop.web.utils.ActionType;
import techshop.web.utils.ConfigurationManager;
import techshop.web.utils.MessageManager;
import techshop.web.utils.SessionRequestContent;

/**
 * Initialize cart page and provide remove goods from cart
 */
public class CartCommand implements ActionCommand {

	private static final long serialVersionUID = -3569828540875247826L;

	private static final Logger LOG = Logger.getLogger(CartCommand.class);

	@Override
	public String execute(SessionRequestContent content) {
		LOG.debug("Command starts");

		String page = ConfigurationManager.getProperty("path.page.cart");

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
		// load data for left menu
		ShopCommand shopCommand = new ShopCommand();
		shopCommand.execute(content);

		Map<String, Object> sessionAttributes = content.getSessionAttributes();
		Map<String, Object> requestAttributes = content.getRequestAttributes();

		List<OrderItemBean> orderItemList = (List<OrderItemBean>) sessionAttributes.get("order_item_list");
		if (orderItemList == null || orderItemList.isEmpty()) {
			requestAttributes.put("cart_empty", MessageManager.getProperty("message.no.orderitem"));
			LOG.trace("Order item list has no item");
			return;
		}
		LOG.trace("Obtained order item list ==> " + orderItemList);

		String orderItemIdForRemove = content.getRequestParameter("remove");
		removeFromCart(orderItemIdForRemove, orderItemList);

		int summPrice = 0;
		for (OrderItemBean orderItemBean : orderItemList) {
			summPrice += orderItemBean.getPrice();
		}

		requestAttributes.put("summ_price", summPrice);
		LOG.trace("Set summ price by order items to the request attribures ==> " + summPrice);

		sessionAttributes.put("order_item_list", orderItemList);
		LOG.trace("Set order items to the session attribures ==> " + orderItemList);
	}

	private void removeFromCart(String orderItemIdParam, List<OrderItemBean> orderItemList) {
		if (orderItemIdParam == null || orderItemIdParam.isEmpty()) {
			LOG.trace("No remove param");
			return;
		}

		int orderItemId = Integer.parseInt(orderItemIdParam);
		LOG.trace("Order item ID for remove ==> " + orderItemId);

		OrderItemBean orderItemBean;
		Iterator<OrderItemBean> iter = orderItemList.iterator();
		while (iter.hasNext()) {
			orderItemBean = iter.next();
			if (orderItemBean.getGoods().getId() == orderItemId) {
				iter.remove();
				LOG.trace("Removed goods ==> " + orderItemBean.getGoods());
			}
		}
	}

}
