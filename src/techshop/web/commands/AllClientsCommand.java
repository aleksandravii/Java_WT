package techshop.web.commands;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import techshop.domain.UserManager;
import techshop.domain.entities.Role;
import techshop.domain.entities.User;
import techshop.web.exceptions.AccessException;
import techshop.web.exceptions.UnknownDataException;
import techshop.web.utils.ActionType;
import techshop.web.utils.ConfigurationManager;
import techshop.web.utils.SessionRequestContent;

/**
 * Initialize page that contain all clients data and provide block and unblock
 * them
 */
public class AllClientsCommand implements ActionCommand {

	private static final long serialVersionUID = 8476581799155489616L;

	private static final Logger LOG = Logger.getLogger(AllClientsCommand.class);

	@Override
	public String execute(SessionRequestContent content) {
		LOG.debug("Command starts");

		String page = ConfigurationManager.getProperty("path.page.all.clients");

		Map<String, Object> sessionAttributes = content.getSessionAttributes();
		Map<String, Object> requestAttributes = content.getRequestAttributes();

		Role role = (Role) sessionAttributes.get("user_role");
		LOG.trace("Obtained role ==> " + role);
		if (Role.ADMIN != role) {
			LOG.warn("Try access to clients list with role ==> " + role);
			throw new AccessException("Unauthorized access");
		}

		User user = (User) sessionAttributes.get("user");
		LOG.trace("Obtained user ==> " + user);
		if (user == null || user.getRoleId() != role.getId()) {
			LOG.warn("Try access to clients list by unknown user ==> " + user);
			throw new AccessException("Unauthorized access");
		}

		if (ActionType.POST == content.getActionType()) {
			changeClientAccess(content);
			page = ConfigurationManager.getProperty("redirect.page.all.clients");
		}

		List<User> listClients = UserManager.getAllClients();
		requestAttributes.put("list_clients", listClients);
		LOG.trace("Set to the request attributes list clients ==> " + listClients);

		LOG.debug("Command finished");
		return page;
	}

	private void changeClientAccess(SessionRequestContent content) {
		String strClientId = content.getRequestParameter("client_id");
		LOG.trace("Obtained client ID ==> " + strClientId);
		int clientId = Integer.parseInt(strClientId);

		String clientAccess = content.getRequestParameter("access");
		LOG.trace("Command access obtained ==> " + clientAccess);

		if ("block".equalsIgnoreCase(clientAccess)) {
			UserManager.blockClient(clientId);
			LOG.info("Client #" + clientId + " has been blocked");
		} else if ("unblock".equalsIgnoreCase(clientAccess)) {
			UserManager.unblockClient(clientId);
			LOG.trace("Client #" + clientId + " has been unblocked");
		} else {
			LOG.error("Invalid obtained data");
			throw new UnknownDataException("Invalid obtained data");
		}

	}

}
