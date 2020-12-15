package techshop.web.commands;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.log4j.Logger;

import techshop.domain.GoodsManager;
import techshop.domain.entities.Goods;
import techshop.domain.entities.Role;
import techshop.domain.entities.User;
import techshop.web.exceptions.AccessException;
import techshop.web.exceptions.UnknownDataException;
import techshop.web.utils.ActionType;
import techshop.web.utils.ConfigurationManager;
import techshop.web.utils.MessageManager;
import techshop.web.utils.SessionRequestContent;
import techshop.web.utils.UserInputValidator;

/**
 * Edit goods manager. Provide create and update goods by specified parameters
 */
public class EditGoodsCommand implements ActionCommand {

	private static final long serialVersionUID = -7947258983870047205L;

	private static final Logger LOG = Logger.getLogger(EditGoodsCommand.class);

	/**
	 * Extract goods by specified parameters, and if goods id parameter is equal
	 * to null or empty that create new goods, else update current.
	 */
	@Override
	public String execute(SessionRequestContent content) {
		LOG.debug("Command starts");

		String page = ConfigurationManager.getProperty("redirect.page.all.goods");

		if (ActionType.POST == content.getActionType()) {
			doPost(content);
		} else {
			String errorMessage = "Try to unknown access to command";
			LOG.error(errorMessage);
			throw new AccessException(errorMessage);
		}

		LOG.debug("Command finished");
		return page;
	}

	private void doPost(SessionRequestContent content) {
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

		Goods goods = extractGoods(content);
		LOG.trace("Obtained goods ==> " + goods);

		String goodsIdParam = content.getRequestParameter("goods_id");
		LOG.trace("Obtained goods ID ==> " + goodsIdParam);

		if (goodsIdParam == null || goodsIdParam.isEmpty()) {
			GoodsManager.createNewGoods(goods);
			LOG.trace("Goods has been created ==> " + goods);
		} else {
			int goodsId = Integer.parseInt(goodsIdParam);
			goods.setId(goodsId);
			GoodsManager.updateGoods(goods);
			LOG.trace("Goods has been updated ==> " + goods);
		}
	}

	private Goods extractGoods(SessionRequestContent content) {
		Map<String, Object> requestAttributes = content.getRequestAttributes();

		String goodsIdParam = content.getRequestParameter("goods_id");
		String goodsName = content.getRequestParameter("goods_name");
		String goodsPriceParam = content.getRequestParameter("goods_price");
		String releaseDateParam = content.getRequestParameter("release_date");
		String colourIdParam = content.getRequestParameter("colour_id");
		String groupIdParam = content.getRequestParameter("group_id");
		String manufacturerIdParam = content.getRequestParameter("manufacturer_id");

		if (!UserInputValidator.isValidParameters(goodsName, goodsPriceParam, colourIdParam, groupIdParam,
				manufacturerIdParam)) {
			LOG.error("Invalid param");
			requestAttributes.put("message", MessageManager.getProperty("message.info.invalid.input.data"));
			throw new UnknownDataException("Invalid param");
		}

		if (!UserInputValidator.isValidDate(releaseDateParam)) {
			LOG.error("Invalid date");
			requestAttributes.put("message1", MessageManager.getProperty("message.info.invalid.date"));
			throw new UnknownDataException("Invalid date");
		}

		int goodsId = 0;
		if (goodsIdParam != null && !goodsIdParam.isEmpty()) {
			goodsId = Integer.parseInt(goodsIdParam);
		}
		int goodsPrice = Integer.parseInt(goodsPriceParam);
		int colourId = Integer.parseInt(colourIdParam);
		int groupId = Integer.parseInt(groupIdParam);
		int manufacturerId = Integer.parseInt(manufacturerIdParam);
		if (goodsPrice < 1 || goodsPrice > 999999) {
			requestAttributes.put("message", MessageManager.getProperty("message.info.invalid.price"));
			throw new UnknownDataException("Invalid input price!");
		}

		Date releaseDate = convertStringToDate(releaseDateParam);
		Date currentDate = new java.sql.Date(new java.util.Date().getTime());
		if (releaseDate.compareTo(currentDate) > 0) {
			LOG.error("Invalid date param. Release date is after current date.");
			requestAttributes.put("message", MessageManager.getProperty("message.info.invalid.date"));
			throw new UnknownDataException("Invalid date");
		}

		Goods goods = new Goods();

		goods.setId(goodsId);
		goods.setName(goodsName);
		goods.setPrice(goodsPrice);
		goods.setReleaseDate(releaseDate);
		goods.setColourId(colourId);
		goods.setGroupId(groupId);
		goods.setManufacturerId(manufacturerId);

		return goods;
	}

	private Date convertStringToDate(String dateParam) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = new Date(sdf.parse(dateParam).getTime());
		} catch (ParseException e) {
			LOG.error("Invalid date param", e);
			throw new UnknownDataException(e);
		}
		return date;
	}

}
