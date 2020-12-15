package techshop.web.commands;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import techshop.domain.OrderManager;
import techshop.domain.beans.OrderItemBean;
import techshop.domain.entities.Order;
import techshop.domain.entities.Role;
import techshop.domain.entities.User;
import techshop.web.exceptions.AccessException;
import techshop.web.exceptions.UnknownDataException;
import techshop.web.utils.ActionType;
import techshop.web.utils.ConfigurationManager;
import techshop.web.utils.MessageManager;
import techshop.web.utils.SessionRequestContent;

/**
 * Command provide register order by current order items
 */
public class RegisterOrderCommand implements ActionCommand {

	private static final long serialVersionUID = 2049316347309185219L;

	private static final Logger LOG = Logger.getLogger(RegisterOrderCommand.class);

	@Override
	public String execute(SessionRequestContent content) {
		LOG.debug("Command starts");

		String page = ConfigurationManager.getProperty("redirect.page.cart");

		if (ActionType.POST == content.getActionType()) {
			doPost(content);
		} else {
			String errorMessage = "Try to unknown access to command";
			LOG.error(errorMessage);
			throw new AccessException(errorMessage);
		}

		LOG.debug("Command finished");
		return page;
	}

	@SuppressWarnings("unchecked")
	private void doPost(SessionRequestContent content) {
		Map<String, String[]> requestParameters = content.getRequestParameters();
		Map<String, Object> sessionAttributes = content.getSessionAttributes();

		List<OrderItemBean> orderItemList = (List<OrderItemBean>) sessionAttributes.get("order_item_list");
		if (orderItemList == null || orderItemList.isEmpty()) {
			return;
		}

		Role role = (Role) sessionAttributes.get("user_role");
		LOG.trace("Obtained role ==> " + role);
		if (role == null) {
			LOG.warn("Try access to perlonal account without role");
			throw new AccessException("Unauthorized access");
		}

		User user = (User) sessionAttributes.get("user");
		LOG.trace("Obtained user ==> " + user);
		if (user == null || user.getRoleId() != role.getId()) {
			LOG.warn("Try access to perlonal account by unknown user ==> " + user);
			throw new AccessException("Unauthorized access");
		}

		// obtained amount array expended in order items
		String[] paramsGoodsAmount = requestParameters.get("goods_amount");
		int counter = 0;
		int amount = 0;

		for (OrderItemBean orderItemBean : orderItemList) {
			amount = Integer.parseInt(paramsGoodsAmount[counter++]);
			if (amount < 1 || amount > 100) {
				throw new UnknownDataException("Invalid input data");
			}
			orderItemBean.setAmount(amount);
		}

		int orderPrice = 0;
		for (OrderItemBean orderItemBean : orderItemList) {
			orderPrice += orderItemBean.getPrice();
		}
		LOG.trace("Result order price ==> " + orderPrice);

		Order order = OrderManager.createOrder(user.getId(), orderPrice);
		LOG.trace("Create order ==> " + order);

		int orderId = order.getId();

		for (OrderItemBean orderItemBean : orderItemList) {
			OrderManager.createOrderItem(orderItemBean, orderId);
		}

		orderItemList.clear();
		LOG.trace("Order item list has been cleared");

		sessionAttributes.put("success_order", MessageManager.getProperty("message.info.success.reg.order"));
	}

}
