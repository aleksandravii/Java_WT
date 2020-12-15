package techshop.domain;

import java.util.List;

import org.apache.log4j.Logger;

import techshop.dao.UserDAO;
import techshop.dao.exceptions.AlreadyExistException;
import techshop.dao.exceptions.DAOException;
import techshop.domain.entities.Role;
import techshop.domain.entities.User;
import techshop.domain.exceptions.DomainException;
import techshop.domain.exceptions.SameUserException;
import techshop.domain.utils.MailUtil;

/**
 * Contain methods to manage user entity. Methods call data from DAO layer and
 * process it. If occurred error, then perform add information to LOG and throws
 * {@link DomainException}
 */
public class UserManager {

	private static final Logger LOG = Logger.getLogger(UserManager.class);

	private static UserDAO userDAO = new UserDAO();

	/**
	 * Provide save user to database, set id {@link User#setId(int)} from
	 * database and send confirm to email.
	 * 
	 * @param user
	 *            to save
	 */
	public static void registrationUser(User user) {
		LOG.debug("Start creating user");
		saveUser(user);
		MailUtil.sendRegistrationNotification(user);
		LOG.trace("Created: " + user);
		LOG.debug("Finished creating user");
	}

	public static User getUserByLogin(String login) {
		try {
			return userDAO.getUserByLogin(login);
		} catch (DAOException e) {
			LOG.error("Cannot obtain user!", e);
			throw new DomainException(e);
		}
	}

	public static User getUserById(int id) {
		try {
			return userDAO.getUserById(id);
		} catch (DAOException e) {
			LOG.error("Cannot obtain user!", e);
			throw new DomainException(e);
		}
	}

	public static List<User> getAllClients() {
		try {
			return userDAO.getUsersByRoleId(Role.CLIENT.getId());
		} catch (DAOException e) {
			LOG.error("Cannot obtain user!", e);
			throw new DomainException(e);
		}
	}

	public static void unblockClient(int clientId) {
		try {
			userDAO.updateUserIsBlocked(clientId, false);
		} catch (DAOException e) {
			LOG.error("Cannot unblock user!", e);
			throw new DomainException(e);
		}
	}

	public static void blockClient(int clientId) {
		if (isAdmin(clientId)) {
			LOG.error("Attempt to block Admin! User ID ==> " + clientId);
			throw new DomainException("Attempt to block Admin!");
		}
		try {
			userDAO.updateUserIsBlocked(clientId, true);
		} catch (DAOException e) {
			LOG.error("Cannot block user!", e);
			throw new DomainException(e);
		}
	}

	private static void saveUser(User user) {
		try {
			userDAO.insertUser(user);
		} catch (AlreadyExistException e) {
			LOG.warn("Current user already exist", e);
			throw new SameUserException(e);
		} catch (DAOException e) {
			LOG.error("Cannot save user!", e);
			throw new DomainException(e);
		}
	}

	private static boolean isAdmin(int userId) {
		User user = null;
		try {
			user = userDAO.getUserById(userId);
		} catch (DAOException e) {
			LOG.error("Cannot obtain user!", e);
			throw new DomainException(e);
		}
		if (Role.ADMIN.getId() == user.getRoleId()) {
			return true;
		}
		return false;
	}

}
