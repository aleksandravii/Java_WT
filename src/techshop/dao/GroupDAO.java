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
import techshop.domain.entities.Category;
import techshop.domain.entities.Group;

/**
 * Group DAO class. Contain methods for group and category entities..
 */
public class GroupDAO extends AbstractDAO {

	private static final Logger LOG = Logger.getLogger(GroupDAO.class);

	public List<Group> getAllGroups() {
		List<Group> groupList = new ArrayList<>();
		connection = ConnectionPool.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(Query.SELECT_ALL_GROUPS);
			while (resultSet.next()) {
				groupList.add(extractGroup(resultSet));
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot obtain group!", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
		return groupList;
	}

	public List<Category> getAllCategories() {
		List<Category> categoryList = new ArrayList<>();
		connection = ConnectionPool.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(Query.SELECT_ALL_CATEGORIES);
			while (resultSet.next()) {
				categoryList.add(extractCategory(resultSet));
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot obtain category!", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
		return categoryList;
	}

	public List<Category> getCategoriesByName(String categoryName) {
		List<Category> categoryList = new ArrayList<>();
		connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Query.SELECT_CATEGORIES_BY_NAME);
			statement.setString(1, categoryName);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				categoryList.add(extractCategory(resultSet));
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot obtain category!", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
		return categoryList;
	}

	public List<Group> getGroupsByCategory(String categoryName) {
		List<Group> groupList = new ArrayList<>();
		connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Query.SELECT_GROUP_BY_CATEGORY);
			statement.setString(1, categoryName);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				groupList.add(extractGroup(resultSet));
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot obtain groups!", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
		return groupList;
	}

	private Group extractGroup(ResultSet resultSet) throws SQLException {
		Group group = new Group();
		group.setId(resultSet.getInt("id"));
		group.setGroupName(resultSet.getString("group_name"));
		group.setCategoryId(resultSet.getInt("category_id"));
		return group;
	}

	private Category extractCategory(ResultSet resultSet) throws SQLException {
		Category category = new Category();
		category.setId(resultSet.getInt("id"));
		category.setCategoryName(resultSet.getString("category_name"));
		return category;
	}

}
