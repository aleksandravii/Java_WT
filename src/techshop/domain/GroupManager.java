package techshop.domain;

import java.util.List;

import org.apache.log4j.Logger;

import techshop.dao.GroupDAO;
import techshop.dao.exceptions.DAOException;
import techshop.domain.entities.Category;
import techshop.domain.entities.Group;
import techshop.domain.exceptions.DomainException;

/**
 * Contain methods to manage group entity. Methods call data from DAO layer and
 * process it. If occurred error, then perform add information to LOG and throws
 * {@link DomainException}
 */
public class GroupManager {

	private static GroupDAO groupDAO = new GroupDAO();

	private static final Logger LOG = Logger.getLogger(GroupManager.class);

	public static List<Group> getAllGroups() {
		try {
			return groupDAO.getAllGroups();
		} catch (DAOException e) {
			LOG.error("Cannot obtain groups!", e);
			throw new DomainException(e);
		}
	}

	public static List<Group> getGroupsByCategory(String categoryName) {
		try {
			return groupDAO.getGroupsByCategory(categoryName);
		} catch (DAOException e) {
			LOG.error("Cannot obtain groups!", e);
			throw new DomainException(e);
		}
	}

	public static List<Category> getAllCategories() {
		try {
			return groupDAO.getAllCategories();
		} catch (DAOException e) {
			LOG.error("Cannot obtain categories!", e);
			throw new DomainException(e);
		}
	}

	public static List<Category> getCategoriesByName(String categoryName) {
		try {
			return groupDAO.getCategoriesByName(categoryName);
		} catch (DAOException e) {
			LOG.error("Cannot obtain category!", e);
			throw new DomainException(e);
		}
	}

}
