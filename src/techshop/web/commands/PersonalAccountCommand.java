package techshop.web.commands;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import techshop.domain.OrderManager;
import techshop.domain.beans.OrderBean;
import techshop.domain.entities.Role;
import techshop.domain.entities.User;
import techshop.domain.utils.ExelReportUtil;
import techshop.web.exceptions.AccessException;
import techshop.web.utils.ActionType;
import techshop.web.utils.ConfigurationManager;
import techshop.web.utils.SessionRequestContent;

/**
 * Initialize personal account page. Provide download order report
 */
public class PersonalAccountCommand implements ActionCommand {

	private static final long serialVersionUID = 3954341324362875423L;

	private static final Logger LOG = Logger.getLogger(PersonalAccountCommand.class);

	@Override
	public String execute(SessionRequestContent content) {
		LOG.debug("Command starts");

		String page = ConfigurationManager.getProperty("path.page.person.account");

		// load data for left menu on page
		ShopCommand shopCommand = new ShopCommand();
		shopCommand.execute(content);

		Map<String, Object> requestAttributes = content.getRequestAttributes();
		Map<String, Object> sessionAttributes = content.getSessionAttributes();

		Role role = (Role) sessionAttributes.get("user_role");
		LOG.trace("Obtained role ==> " + role);
		if (role == null) {
			LOG.warn("Try access to perlonal account without role");
			throw new AccessException("Unauthorized access");
		}

		User user = (User) sessionAttributes.get("user");
		LOG.trace("Obtained user ==> " + user);
		if (user == null || user.getRoleId() != role.getId()) {
			LOG.warn("Try access to perlonal account by unknown user ==> " + user);
			throw new AccessException("Unauthorized access");
		}

		List<OrderBean> listOrderBeans = OrderManager.getOrderBeansByUserId(user.getId());

		String downloadParam = content.getRequestParameter("download");
		if (ActionType.POST == content.getActionType() && "downloadReport".equals(downloadParam)) {
			LOG.debug("Download starts");
			ExelReportUtil.downloadReport(content.getResponse(), listOrderBeans, user);
			LOG.debug("Download finished");
			page = ConfigurationManager.getProperty("redirect.page.person.account");
		}

		requestAttributes.put("list_order_beans", listOrderBeans);
		LOG.trace("Set to the request attributes order list ==> " + listOrderBeans);

		LOG.debug("LoginCommand finished");
		return page;
	}

}
