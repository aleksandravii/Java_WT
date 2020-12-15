package techshop.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * Root DAO class that contain util methods for DAO classes.
 * 
 *
 */
public abstract class AbstractDAO {

	private static final Logger LOG = Logger.getLogger(AbstractDAO.class);

	protected Connection connection;

	/**
	 * Rollbacks a connection.
	 * 
	 * @param con
	 *            Connection to be rollbacked.
	 */
	protected void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException e) {
				LOG.error("Cannot rollback transaction!", e);
			}
		}
	}

	/**
	 * Closes resources.
	 */
	protected void close(ResultSet rs, Statement st, Connection con) {
		closeResultSet(rs);
		closeStatement(st);
		closeConnection(con);
	}

	private void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				LOG.error("Cannot close a result set!", e);
			}
		}
		rs = null;
	}

	private void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				LOG.error("Cannot close a statement!", e);
			}
		}
		st = null;
	}

	private void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				LOG.error("Cannot close a connection!", e);
			}
		}
		con = null;
	}
}
