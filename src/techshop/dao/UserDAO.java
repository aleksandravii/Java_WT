package techshop.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import techshop.dao.exceptions.AlreadyExistException;
import techshop.dao.exceptions.DAOException;
import techshop.dao.utils.ConnectionPool;
import techshop.dao.utils.Query;
import techshop.domain.entities.User;

/**
 * User dao class. Contain methods for user entity
 */
public class UserDAO extends AbstractDAO {

	private static final Logger LOG = Logger.getLogger(UserDAO.class);

	public void insertUser(User user) {
		connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;
		try {
			statement = connection.prepareStatement(Query.INSERT_USER, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getLogin());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getLastName());
			statement.setString(5, user.getEmail());
			statement.setBoolean(6, user.getIsBlocked());
			statement.setInt(7, user.getRoleId());

			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Inserting user failed, no rows affected");
			}

			generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				user.setId(generatedKeys.getInt(1));
			} else {
				throw new SQLException("Inserting user failed, no ID obtained");
			}
			connection.commit();
		} catch (MySQLIntegrityConstraintViolationException e) {
			rollback(connection);
			LOG.warn("Inserting user failed, current user already exist!", e);
			throw new AlreadyExistException(e);
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot save user!", e);
			throw new DAOException(e);
		} finally {
			close(generatedKeys, statement, connection);
		}
	}

	public User getUserByLogin(String login) {
		connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		User user = null;
		try {
			statement = connection.prepareStatement(Query.SELECT_USER_BY_LOGIN);
			statement.setString(1, login);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				user = extractUser(resultSet);
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot obtain user!", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
		return user;
	}

	public User getUserById(int id) {
		connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		User user = null;
		try {
			statement = connection.prepareStatement(Query.SELECT_USER_BY_ID);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				user = extractUser(resultSet);
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot obtain user!", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
		return user;
	}

	public List<User> getUsersByRoleId(int id) {
		List<User> listUser = new ArrayList<>();
		connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Query.SELECT_USER_BY_ROLE_ID);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				listUser.add(extractUser(resultSet));
			}
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot obtain user!", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
		return listUser;
	}

	public void updateUserIsBlocked(int userId, boolean isBlocked) {
		connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Query.UPDATE_USER_IS_BLOCKED);
			statement.setBoolean(1, isBlocked);
			statement.setInt(2, userId);
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			LOG.error("Cannot update user!", e);
			throw new DAOException(e);
		} finally {
			close(resultSet, statement, connection);
		}
	}

	private User extractUser(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getInt("id"));
		user.setLogin(resultSet.getString("login"));
		user.setPassword(resultSet.getString("password"));
		user.setFirstName(resultSet.getString("first_name"));
		user.setLastName(resultSet.getString("last_name"));
		user.setEmail(resultSet.getString("email"));
		user.setBlocked(resultSet.getBoolean("is_blocked"));
		user.setRoleId(resultSet.getInt("role_id"));
		return user;
	}

}
