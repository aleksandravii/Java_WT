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
import techshop.domain.entities.Colour;
import techshop.domain.entities.Goods;
import techshop.domain.entities.Manufacturer;

/**
 * Goods DAO class. Contain methods for goods, goods manufacturer and goods
 * colors entities
 */
public class GoodsDAO extends AbstractDAO {

	private static final Logger LOG = Logger.getLogger(GoodsDAO.class);

	public List<Goods> getAllGoods() {
		List<Goods> goodsList = new ArrayList<>();
		connection = ConnectionPool.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(Query.SELECT_ALL_GOODS);
			while (resultSet.next()) {
				goodsList.add(extractGood(resultSet));
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot obtain goods!", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
		return goodsList;
	}

	public List<Manufacturer> getAllManufacturers() {
		List<Manufacturer> manufacturerList = new ArrayList<>();
		connection = ConnectionPool.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(Query.SELECT_ALL_MANUFACTURERS);
			while (resultSet.next()) {
				manufacturerList.add(extractManufacturer(resultSet));
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot obtain manufacturers!", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
		return manufacturerList;
	}

	public List<Colour> getAllColours() {
		List<Colour> colourList = new ArrayList<>();
		connection = ConnectionPool.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(Query.SELECT_ALL_COLOURS);
			while (resultSet.next()) {
				colourList.add(extractColour(resultSet));
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot obtain colours", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
		return colourList;
	}

	public Goods getGoodsById(int id) {
		connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Goods goods = null;
		try {
			statement = connection.prepareStatement(Query.SELECT_GOODS_BY_ID);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				goods = extractGood(resultSet);
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot obtain goods", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
		return goods;
	}

	public List<Goods> getGoodsByCategory(String categoryName) {
		List<Goods> goodsList = new ArrayList<>();
		connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Query.SELECT_GOODS_BY_CATEGORY);
			statement.setString(1, categoryName);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				goodsList.add(extractGood(resultSet));
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot obtain goods", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
		return goodsList;
	}

	public List<Manufacturer> getManufacturersByCategory(String categoryName) {
		List<Manufacturer> manufacturerList = new ArrayList<>();
		connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Query.SELECT_MANUFACTURER_BY_CATEGORY);
			statement.setString(1, categoryName);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				manufacturerList.add(extractManufacturer(resultSet));
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot obtain manufacturers!", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
		return manufacturerList;
	}

	public List<Colour> getColoursByCategory(String categoryName) {
		List<Colour> colourList = new ArrayList<>();
		connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Query.SELECT_COLOUR_BY_CATEGORY);
			statement.setString(1, categoryName);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				colourList.add(extractColour(resultSet));
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot obtain colours", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
		return colourList;
	}

	public List<String> getGoodsNamesByOrderId(int orderId) {
		List<String> listGoodsNames = new ArrayList<>();
		connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Query.SELECT_GOODS_NAMES_BY_ORDER_ID);
			statement.setInt(1, orderId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				listGoodsNames.add(resultSet.getString("name"));
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot obtain goods!", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
		return listGoodsNames;
	}

	public Colour getColourByName(String name) {
		connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Colour color = null;
		try {
			statement = connection.prepareStatement(Query.SELECT_COLOR_BY_NAME);
			statement.setString(1, name);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				color = extractColour(resultSet);
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot obtain colours", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
		return color;
	}

	public Goods insertGoods(final Goods goods) {
		connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;
		try {
			statement = connection.prepareStatement(Query.INSERT_GOODS, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, goods.getName());
			statement.setInt(2, goods.getPrice());
			statement.setDate(3, goods.getReleaseDate());
			statement.setInt(4, goods.getColourId());
			statement.setInt(5, goods.getGroupId());
			statement.setInt(6, goods.getManufacturerId());

			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Inserting goods failed, no rows affected");
			}

			generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				goods.setId(generatedKeys.getInt(1));
			} else {
				throw new SQLException("Inserting goods failed, no ID obtained");
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot save goods!", e);
			throw new DAOException(e);
		} finally {
			close(generatedKeys, statement, connection);
		}
		return goods;
	}

	public void updateGoods(Goods goods) {
		connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Query.UPDATE_GOODS);
			statement.setString(1, goods.getName());
			statement.setInt(2, goods.getPrice());
			statement.setDate(3, goods.getReleaseDate());
			statement.setInt(4, goods.getColourId());
			statement.setInt(5, goods.getGroupId());
			statement.setInt(6, goods.getManufacturerId());
			statement.setInt(7, goods.getId());
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot update goods!", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
	}

	public void removeGoods(int id) {
		connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Query.REMOVE_GOODS);
			statement.setInt(1, id);
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot remove goods!", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
	}

	public int getMaxPrice() {
		int maxPrice = 0;
		connection = ConnectionPool.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(Query.SELECT_MAX_PRICE);
			while (resultSet.next()) {
				maxPrice = resultSet.getInt("price");
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot max obtain price", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
		return maxPrice;
	}

	public int getMinPrice() {
		int minPrice = 0;
		connection = ConnectionPool.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(Query.SELECT_MIN_PRICE);
			while (resultSet.next()) {
				minPrice = resultSet.getInt("price");
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot min obtain price", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
		return minPrice;
	}
	
	//additional task
	public List<Goods> getGoodsByCount(int count) {
		List<Goods> goodsList = new ArrayList<>();
		connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Query.SELECT_GOODS_BY_COUNT);
			statement.setInt(1, count);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				goodsList.add(extractGood(resultSet));
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot obtain goods!", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
		return goodsList;
	}

	private Goods extractGood(ResultSet resultSet) throws SQLException {
		Goods goods = new Goods();
		goods.setId(resultSet.getInt("id"));
		goods.setName(resultSet.getString("name"));
		goods.setPrice(resultSet.getInt("price"));
		goods.setReleaseDate(resultSet.getDate("release_date"));
		goods.setColourId(resultSet.getInt("colour_id"));
		goods.setManufacturerId(resultSet.getInt("manufacturer_id"));
		goods.setGroupId(resultSet.getInt("group_id"));
		return goods;
	}

	private Manufacturer extractManufacturer(ResultSet resultSet) throws SQLException {
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setId(resultSet.getInt("id"));
		manufacturer.setManufacturerName(resultSet.getString("manufacturer_name"));
		return manufacturer;
	}

	private Colour extractColour(ResultSet resultSet) throws SQLException {
		Colour colour = new Colour();
		colour.setId(resultSet.getInt("id"));
		colour.setColourName(resultSet.getString("colour_name"));
		return colour;
	}

}
