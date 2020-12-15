package techshop.web.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import techshop.domain.entities.Role;
import techshop.web.utils.ConfigurationManager;
import techshop.web.utils.MessageManager;

/**
 * Security filter
 */
public class AccessFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(AccessFilter.class);

	private Map<Role, List<String>> accessMap = new HashMap<Role, List<String>>();

	private List<String> commons = new ArrayList<String>();

	private List<String> outOfControl = new ArrayList<String>();

	@Override
	public void init(FilterConfig config) throws ServletException {
		LOG.debug("Filter initialization starts");

		accessMap.put(Role.ADMIN, asList(config.getInitParameter("admin")));
		accessMap.put(Role.CLIENT, asList(config.getInitParameter("client")));
		LOG.trace("Access map ==> " + accessMap);

		commons = asList(config.getInitParameter("common"));
		LOG.trace("Common commands ==> " + commons);

		outOfControl = asList(config.getInitParameter("out-of-control"));
		LOG.trace("Out of control commands ==> " + outOfControl);

		LOG.debug("Filter initialization finished");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LOG.debug("Filter starts");

		if (accessAllowed(request)) {
			LOG.debug("Filter finished");
			chain.doFilter(request, response);
		} else {
			request.setAttribute("errorMessage", MessageManager.getProperty("message.err.access"));
			LOG.trace(
					"Set the request attribute: errorMessage ==> " + MessageManager.getProperty("message.err.access"));
			request.getRequestDispatcher(ConfigurationManager.getProperty("path.page.error")).forward(request,
					response);
		}
	}

	@Override
	public void destroy() {
		LOG.debug("Filter destruction starts");
		LOG.debug("Filter destruction finished");
	}

	private boolean accessAllowed(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String commandName = request.getParameter("command");
		LOG.trace("Command name ==> " + commandName);
		if (commandName == null || commandName.isEmpty()) {
			return false;
		}

		if (outOfControl.contains(commandName)) {
			return true;
		}

		HttpSession session = httpRequest.getSession(false);
		if (session == null) {
			return false;
		}

		Role userRole = (Role) session.getAttribute("user_role");
		if (userRole == null) {
			return false;
		}

		return accessMap.get(userRole).contains(commandName) || commons.contains(commandName);
	}

	private List<String> asList(String str) {
		List<String> list = new ArrayList<String>();
		Scanner scanner = new Scanner(str);
		while (scanner.hasNext()) {
			list.add(scanner.next().trim());
		}
		scanner.close();
		return list;
	}

}
