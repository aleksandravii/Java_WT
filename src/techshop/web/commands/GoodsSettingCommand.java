package techshop.web.commands;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import techshop.domain.GoodsManager;
import techshop.domain.GroupManager;
import techshop.domain.entities.Colour;
import techshop.domain.entities.Goods;
import techshop.domain.entities.Group;
import techshop.domain.entities.Manufacturer;
import techshop.domain.entities.Role;
import techshop.domain.entities.User;
import techshop.web.exceptions.AccessException;
import techshop.web.exceptions.UnknownDataException;
import techshop.web.utils.ActionType;
import techshop.web.utils.ConfigurationManager;
import techshop.web.utils.MessageManager;
import techshop.web.utils.SessionRequestContent;

/**
 * Initialize edit page for edit or create goods. If obtained parameter for
 * remove goods that should remove and forward to all goods page
 */
public class GoodsSettingCommand implements ActionCommand {

	private static final long serialVersionUID = 1596618248072727319L;

	private static final Logger LOG = Logger.getLogger(GoodsSettingCommand.class);

	/**
	 * Action type POST send only accord remove goods request, otherwise it can
	 * be obtained request for create or update goods.
	 */
	@Override
	public String execute(SessionRequestContent content) {
		LOG.debug("Command starts");
		String page = ConfigurationManager.getProperty("path.page.goods.setting");

		initCommand(content);

		if (ActionType.POST == content.getActionType()) {
			page = removeGoods(content);
		} else if (ActionType.GET == content.getActionType()) {
			setAttributesForEditPage(content);
		} else {
			String errorMessage = "Try to unknown access to command";
			LOG.error(errorMessage);
			throw new AccessException(errorMessage);
		}

		LOG.debug("Command finished");
		return page;
	}

	private void initCommand(SessionRequestContent content) {
		Map<String, Object> sessionAttributes = content.getSessionAttributes();

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
	}

	private String removeGoods(SessionRequestContent content) {
		String page = ConfigurationManager.getProperty("redirect.page.all.goods");

		String commandParam = content.getRequestParameter("do_goods");

		if (!"remove".equalsIgnoreCase(commandParam)) {
			LOG.error("Try to unknown access to command");
			throw new AccessException("Try to unknown access to command");
		}

		String goodsIdParam = content.getRequestParameter("goods_id");
		int goodsId = Integer.parseInt(goodsIdParam);
		LOG.trace("Removing Goods ID ==> " + goodsId);

		GoodsManager.removeGoods(goodsId);

		return page;
	}

	private void setAttributesForEditPage(SessionRequestContent content) {
		Map<String, Object> requestAttributes = content.getRequestAttributes();

		String commandParam = content.getRequestParameter("do_goods");
		LOG.trace("Obtained command param ==> " + commandParam);

		if ("create_goods".equalsIgnoreCase(commandParam)) {
			setCreateGoodsAttributes(content);
		} else if ("edit".equalsIgnoreCase(commandParam)) {
			setEditGoodsAttributes(content);
		} else {
			LOG.warn("Invalid param ==> " + commandParam);
			throw new UnknownDataException("Invalid param");
		}

		List<Colour> listColours = GoodsManager.getAllColours();
		List<Group> listGroups = GroupManager.getAllGroups();
		List<Manufacturer> listManufacturers = GoodsManager.getAllManufacturers();

		Date currentDate = new java.sql.Date(new java.util.Date().getTime());
		requestAttributes.put("current_date", currentDate);
		LOG.trace("Set date to the request attributes ==> " + currentDate);

		requestAttributes.put("list_colours", listColours);
		LOG.trace("Set list colours to the request attributes ==> " + listColours);

		requestAttributes.put("list_groups", listGroups);
		LOG.trace("Set list groups to the request attributes ==> " + listGroups);

		requestAttributes.put("list_manufacturers", listManufacturers);
		LOG.trace("Set list manufacturers to the request attributes ==> " + listManufacturers);
	}

	private void setCreateGoodsAttributes(SessionRequestContent content) {
		Map<String, Object> requestAttributes = content.getRequestAttributes();
		requestAttributes.put("title", MessageManager.getProperty("goods.setting.new.goods"));
	}

	private void setEditGoodsAttributes(SessionRequestContent content) {
		Map<String, Object> requestAttributes = content.getRequestAttributes();

		String goodsIdParam = content.getRequestParameter("goods_id");
		LOG.trace("Obtained goods ID ==> " + goodsIdParam);
		int goodsId = Integer.parseInt(goodsIdParam);

		requestAttributes.put("title", MessageManager.getProperty("goods.setting.edit.goods"));

		Goods goods = GoodsManager.getGoodsById(goodsId);
		requestAttributes.put("goods", goods);
		LOG.trace("Set goods for edit to the request attributes ==> " + goods);
	}

}
