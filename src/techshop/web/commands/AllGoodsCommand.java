package techshop.web.commands;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import techshop.domain.GoodsManager;
import techshop.domain.entities.Goods;
import techshop.domain.entities.Role;
import techshop.domain.entities.User;
import techshop.web.exceptions.AccessException;
import techshop.web.utils.ActionType;
import techshop.web.utils.ConfigurationManager;
import techshop.web.utils.SessionRequestContent;

/**
 * Initialize page that contain all goods data
 */
public class AllGoodsCommand implements ActionCommand {

	private static final long serialVersionUID = 6111995013164315216L;

	private static final Logger LOG = Logger.getLogger(AllGoodsCommand.class);

	@Override
	public String execute(SessionRequestContent content) {
		LOG.debug("Command starts");

		String page = ConfigurationManager.getProperty("path.page.all.goods");

		if (ActionType.GET == content.getActionType()) {
			doGet(content);
		} else {
			String errorMessage = "Try to unknown access to command";
			LOG.error(errorMessage);
			throw new AccessException(errorMessage);
		}

		LOG.debug("Command finished");
		return page;
	}

	private void doGet(SessionRequestContent content) {
		Map<String, Object> sessionAttributes = content.getSessionAttributes();
		Map<String, Object> requestAttributes = content.getRequestAttributes();

		Role role = (Role) sessionAttributes.get("user_role");
		LOG.trace("Obtained role ==> " + role);
		if (Role.ADMIN != role) {
			LOG.warn("Try access to all goods list with role ==> " + role);
			throw new AccessException("Unauthorized access");
		}

		User user = (User) sessionAttributes.get("user");
		LOG.trace("Obtained user ==> " + user);
		if (user == null || user.getRoleId() != role.getId()) {
			LOG.warn("Try access to all goods list by unknown user ==> " + user);
			throw new AccessException("Unauthorized access");
		}

		List<Goods> listGoods = GoodsManager.getAllGoods();

		requestAttributes.put("list_goods", listGoods);
		LOG.trace("Set to the request attributes list goods ==> " + listGoods);
	}

}
