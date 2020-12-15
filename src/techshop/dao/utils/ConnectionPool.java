package techshop.dao.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import techshop.dao.exceptions.DAOException;

/**
 * Provides to get access to connection pool
 */
public class ConnectionPool {

	private static DataSource dataSource;

	private static final Logger LOG = Logger.getLogger(ConnectionPool.class);

	private ConnectionPool() {
	}

	/**
	 * Get connection from pool connections.
	 * 
	 * @return connection
	 */
	public static synchronized Connection getConnection() {
		try {
			//initDataSource();
			return dataSource.getConnection();
		} catch (SQLException e) {
			LOG.error("Cannot obtain connection");
			throw new DAOException(e);
		}
	}

	private static void initDataSource() {
		if (dataSource == null) {
			try {
				Context initContext = new InitialContext();
				Context envContext = (Context) initContext.lookup("java:comp/env");
				dataSource = (DataSource) envContext.lookup("jdbc/shop");
			} catch (NamingException e) {
				LOG.error("Cannot init data source");
				throw new DAOException(e);
			}
		}
	}

}
