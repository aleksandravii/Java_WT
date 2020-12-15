package techshop.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import techshop.dao.exceptions.DAOException;
import techshop.dao.utils.ConnectionPool;
import techshop.dao.utils.Query;
import techshop.domain.entities.Order;
import techshop.domain.entities.OrderItem;

/**
 * Order dao class. Contain methods for order entity
 */
public class OrderDAO extends AbstractDAO {

	private static final Logger LOG = Logger.getLogger(OrderDAO.class);

	public void insertOrder(Order order) {
		connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;
		try {
			statement = connection.prepareStatement(Query.INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
			statement.setDate(1, order.getDate());
			statement.setString(2, order.getStatus());
			statement.setInt(3, order.getPrice());
			statement.setInt(4, order.getUserId());

			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Inserting order failed, no rows affected");
			}

			generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				order.setId(generatedKeys.getInt(1));
			} else {
				throw new SQLException("Inserting order failed, no ID obtained");
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot save order!", e);
			throw new DAOException(e);
		} finally {
			close(generatedKeys, statement, connection);
		}
	}

	public void insertOrderItem(OrderItem orderItem) {
		connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;
		try {
			statement = connection.prepareStatement(Query.INSERT_ORDER_ITEM, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, orderItem.getAmount());
			statement.setInt(2, orderItem.getOrderId());
			statement.setInt(3, orderItem.getGoodsId());

			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Inserting order item failed, no rows affected");
			}

			generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				orderItem.setId(generatedKeys.getInt(1));
			} else {
				throw new SQLException("Inserting order item failed, no ID obtained");
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot save order item!", e);
			throw new DAOException(e);
		} finally {
			close(generatedKeys, statement, connection);
		}
	}

	public void updateOrderStatus(int orderId, String status) {
		connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Query.UPDATE_ORDER_STATUS);
			statement.setString(1, status);
			statement.setInt(2, orderId);
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot update order!", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
	}

	public List<Order> getOrdersByUserId(int userId) {
		List<Order> listOrder = new ArrayList<>();
		connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Query.SELECT_ORDERS_BY_USER_ID);
			statement.setInt(1, userId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				listOrder.add(extractOrder(resultSet));
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot obtain orders!", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
		return listOrder;
	}

	public List<Order> getAllOrders() {
		List<Order> listOrder = new ArrayList<>();
		connection = ConnectionPool.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(Query.SELECT_ALL_ORDERS);
			while (resultSet.next()) {
				listOrder.add(extractOrder(resultSet));
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot obtain orders!", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
		return listOrder;
	}

	private Order extractOrder(ResultSet resultSet) throws SQLException {
		Order order = new Order();
		order.setId(resultSet.getInt("id"));
		order.setDate(resultSet.getDate("date"));
		order.setStatus(resultSet.getString("status"));
		order.setPrice(resultSet.getInt("price"));
		order.setUserId(resultSet.getInt("user_id"));
		return order;
	}
}
