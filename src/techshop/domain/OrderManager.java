package techshop.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import techshop.dao.OrderDAO;
import techshop.dao.exceptions.DAOException;
import techshop.domain.beans.OrderBean;
import techshop.domain.beans.OrderItemBean;
import techshop.domain.entities.Order;
import techshop.domain.entities.OrderItem;
import techshop.domain.exceptions.DomainException;
import techshop.domain.utils.OrderStatus;

/**
 * Contain methods to manage order, order item entities and order bean. Methods
 * call data from DAO layer, and process it. If occurred error, then perform add
 * information to LOG and throws {@link DomainException}
 */
public class OrderManager {

	private static OrderDAO orderDAO = new OrderDAO();

	private static final Logger LOG = Logger.getLogger(OrderManager.class);

	/**
	 * Initialize {@link Order}, set it to database and return.
	 * 
	 * @param userId
	 *            for set {@link Order#setUserId(int)}
	 * @return ititialized {@link Order}
	 */
	public static Order createOrder(int userId, int orderPrice) {
		LOG.debug("Start creating Order");
		Order order = new Order();
		order.setDate(new java.sql.Date(new java.util.Date().getTime()));
		order.setStatus(OrderStatus.REGISTERED.getName());
		order.setPrice(orderPrice);
		order.setUserId(userId);
		saveOrder(order);
		LOG.trace("Created: " + order);
		LOG.debug("Finished creating Order");
		return order;
	}

	/**
	 * Initialize {@link OrderItem} ant set it to database.
	 * 
	 * @param orderItemBean
	 *            get fields values to save in {@link OrderItem}
	 * @param orderId
	 *            for set {@link OrderItem#setOrderId(int)}
	 */
	public static void createOrderItem(OrderItemBean orderItemBean, int orderId) {
		LOG.debug("Start creating OrderItem");
		OrderItem orderItem = new OrderItem();
		orderItem.setAmount(orderItemBean.getAmount());
		orderItem.setOrderId(orderId);
		orderItem.setGoodsId(orderItemBean.getGoods().getId());
		saveOrderItem(orderItem);
		LOG.trace("Created: " + orderItem);
		LOG.debug("Finished creating OrderItem");
	}

	public static List<OrderBean> getOrderBeansByUserId(int userId) {
		List<OrderBean> listOrderBeans = new ArrayList<>();
		List<Order> listOrders = getOrdersByUserId(userId);
		OrderBean orderBean = null;
		for (Order order : listOrders) {
			orderBean = new OrderBean();
			orderBean.setId(order.getId());
			orderBean.setDate(order.getDate());
			orderBean.setStatus(order.getStatus());
			orderBean.setPrice(order.getPrice());
			orderBean.setGoodsNames(GoodsManager.getGoodsNamesByOrderId(order.getId()));
			listOrderBeans.add(orderBean);
		}
		return listOrderBeans;
	}

	/**
	 * Obtain all orders, set data to {@link OrderBean} and init list
	 * 
	 * @return list {@link OrderBean}
	 */
	public static List<OrderBean> getAllOrderBeans() {
		List<OrderBean> listOrderBeans = new ArrayList<>();
		List<Order> listOrders = getAllOrders();
		OrderBean orderBean = null;
		for (Order order : listOrders) {
			orderBean = new OrderBean();
			orderBean.setId(order.getId());
			orderBean.setDate(order.getDate());
			orderBean.setStatus(order.getStatus());
			orderBean.setPrice(order.getPrice());
			orderBean.setUser(UserManager.getUserById(order.getUserId()));
			listOrderBeans.add(orderBean);
		}
		return listOrderBeans;
	}

	public static void updateOrderStatus(int orderId, String status) {
		try {
			orderDAO.updateOrderStatus(orderId, status);
		} catch (DAOException e) {
			LOG.error("Cannot update order!", e);
			throw new DomainException(e);
		}
	}

	private static void saveOrder(Order order) {
		try {
			orderDAO.insertOrder(order);
		} catch (DAOException e) {
			LOG.error("Cannot save order item!", e);
			throw new DomainException(e);
		}
	}

	private static void saveOrderItem(OrderItem orderItem) {
		try {
			orderDAO.insertOrderItem(orderItem);
		} catch (DAOException e) {
			LOG.error("Cannot save order item!", e);
			throw new DomainException(e);
		}
	}

	private static List<Order> getAllOrders() {
		try {
			return orderDAO.getAllOrders();
		} catch (DAOException e) {
			LOG.error("Cannot obtain orders!", e);
			throw new DomainException(e);
		}
	}

	private static List<Order> getOrdersByUserId(int userId) {
		try {
			return orderDAO.getOrdersByUserId(userId);
		} catch (DAOException e) {
			LOG.error("Cannot obtain orders!", e);
			throw new DomainException(e);
		}
	}

}
