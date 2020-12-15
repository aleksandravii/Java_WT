package techshop.web.commands;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import techshop.domain.OrderManager;
import techshop.domain.beans.OrderBean;
import techshop.domain.entities.Role;
import techshop.domain.entities.User;
import techshop.web.exceptions.AccessException;
import techshop.web.utils.ActionType;
import techshop.web.utils.ConfigurationManager;
import techshop.web.utils.SessionRequestContent;

/**
 * Initialize orders menu. Provide to change order status to 'cancel' or 'paid'
 */
public class AllOrdersCommand implements ActionCommand {

	private static final long serialVersionUID = 3600900699922918439L;

	private static final Logger LOG = Logger.getLogger(AllOrdersCommand.class);

	@Override
	public String execute(SessionRequestContent content) {
		LOG.debug("Command starts");

		String page = ConfigurationManager.getProperty("path.page.all.orders");

		Map<String, Object> sessionAttributes = content.getSessionAttributes();
		Map<String, Object> requestAttributes = content.getRequestAttributes();

		Role role = (Role) sessionAttributes.get("user_role");
		LOG.trace("Obtained role ==> " + role);
		if (Role.ADMIN != role) {
			LOG.warn("Try access to list orders with role ==> " + role);
			throw new AccessException("Unauthorized access");
		}

		User user = (User) sessionAttributes.get("user");
		LOG.trace("Obtained user ==> " + user);
		if (user == null || user.getRoleId() != role.getId()) {
			LOG.warn("Try access to list orders by unknown user ==> " + user);
			throw new AccessException("Unauthorized access");
		}

		if (ActionType.POST == content.getActionType()) {
			updateOrder(content);
			page = ConfigurationManager.getProperty("redirect.page.all.orders");
		}

		List<OrderBean> listOrderBeans = OrderManager.getAllOrderBeans();
		requestAttributes.put("list_order_beans", listOrderBeans);
		LOG.trace("Set to the request attributes list orders ==> " + listOrderBeans);

		LOG.debug("Command finished");
		return page;
	}

	private void updateOrder(SessionRequestContent content) {
		String strOrderId = content.getRequestParameter("order_id");
		LOG.trace("Obtained order ID ==> " + strOrderId);

		int orderId = Integer.parseInt(strOrderId);

		String status = content.getRequestParameter("status");
		LOG.trace("Obtained order status ==> " + status);

		OrderManager.updateOrderStatus(orderId, status);
		LOG.trace("Order #" + orderId + " has been updated to status " + status);
	}

}
