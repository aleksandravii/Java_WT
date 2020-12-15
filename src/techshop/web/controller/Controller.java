package techshop.web.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import techshop.domain.exceptions.AppException;
import techshop.web.commands.ActionCommand;
import techshop.web.utils.ActionFactory;
import techshop.web.utils.ActionType;
import techshop.web.utils.ConfigurationManager;
import techshop.web.utils.MessageManager;
import techshop.web.utils.SessionRequestContent;

/**
 * Servlet handles all requests by client and select page by specified
 * parameter.
 */

public class
Controller extends HttpServlet {

	private static final long serialVersionUID = 7869471693440471248L;

	private static final Logger LOG = Logger.getLogger(Controller.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response, ActionType.GET);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response, ActionType.POST);
	}

	/**
	 * Handles all request. 
	 * 
	 * @param request
	 * @param response
	 * @param actionType
	 * @throws ServletException
	 * @throws IOException
	 */
	private void process(HttpServletRequest request, HttpServletResponse response, ActionType actionType)
			throws ServletException, IOException {
		LOG.debug("Controller starts");

		try {
			processRequest(request, response, actionType);
		} catch (Exception e) {
			LOG.error("Error message: " + e.getMessage());
			request.setAttribute("errorMessage", MessageManager.getProperty("message.err.internal"));

			String errorPage = ConfigurationManager.getProperty("path.page.error");

			RequestDispatcher requestDispatcher = request.getRequestDispatcher(errorPage);
			requestDispatcher.forward(request, response);
		}

		LOG.debug("Controller finished");
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response, ActionType actionType)
			throws ServletException, IOException {
		String page = null;

		ActionFactory actionFactory = new ActionFactory();

		SessionRequestContent content = new SessionRequestContent(request, response, actionType);
		content.extractValues();

		ActionCommand command = actionFactory.defineCommand(content);

		try {
			page = command.execute(content);
		} finally {
			//if execute throw exception the attributes should be inserted
			content.insertAttributes();
		}

		if (page == null) {
			throw new AppException("Obtained null page");
		}

		if (ActionType.GET == actionType) {
			LOG.trace("Forward to page ==> " + page);
			request.getRequestDispatcher(page).forward(request, response);
		} else if (ActionType.POST == actionType) {
			LOG.trace("Redirect to page ==> " + page);
			response.sendRedirect(page);
		} else {
			throw new AppException("No action type");
		}
	}

}
