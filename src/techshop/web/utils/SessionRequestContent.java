package techshop.web.utils;

import techshop.web.utils.ActionType;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The class allows you to retrieve the necessary information from request, and
 * save it in instance.
 * 
 * @author Nikita Datsenko
 *
 */
public class SessionRequestContent {

	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private ActionType actionType;

	private Map<String, Object> requestAttributes = new HashMap<>();

	private Map<String, String[]> requestParameters = new HashMap<>();

	private Map<String, Object> sessionAttributes = new HashMap<>();

	public SessionRequestContent(HttpServletRequest request, HttpServletResponse response, ActionType actionType) {
		this.request = request;
		this.response = response;
		this.actionType = actionType;
	}

	/**
	 * Methods for extracting information from request
	 */
	public void extractValues() {
		extractRequestAttributes();
		extractRequestParameters();
		extractSessionAttributes();
	}

	private void extractRequestAttributes() {
		Enumeration<String> attributeNames = request.getAttributeNames();
		String attributeName;
		while (attributeNames.hasMoreElements()) {
			attributeName = attributeNames.nextElement();
			requestAttributes.put(attributeName, request.getAttribute(attributeName));
		}
	}

	private void extractRequestParameters() {
		Enumeration<String> parameterNames = request.getParameterNames();
		String parameterName;
		while (parameterNames.hasMoreElements()) {
			parameterName = parameterNames.nextElement();
			requestParameters.put(parameterName, request.getParameterValues(parameterName));
		}
	}

	private void extractSessionAttributes() {
		Enumeration<String> attributeNames = request.getSession().getAttributeNames();
		String attributeName;
		while (attributeNames.hasMoreElements()) {
			attributeName = attributeNames.nextElement();
			sessionAttributes.put(attributeName, request.getSession().getAttribute(attributeName));
		}
	}

	/**
	 * Methods for inserting attributes from Maps
	 */
	public void insertAttributes() {
		setAttributes();
		setSessionAttributes();
	}

	private void setAttributes() {
		if (requestAttributes.isEmpty()) {
			return;
		}
		for (Map.Entry<String, Object> pair : requestAttributes.entrySet()) {
			request.setAttribute(pair.getKey(), pair.getValue());
		}
	}

	private void setSessionAttributes() {
		if (sessionAttributes.isEmpty()) {
			return;
		}
		for (Map.Entry<String, Object> pair : sessionAttributes.entrySet()) {
			request.getSession().setAttribute(pair.getKey(), pair.getValue());
		}
	}

	/**
	 * Methods for obtaining contain data
	 */
	public Map<String, Object> getRequestAttributes() {
		return requestAttributes;
	}

	public Map<String, String[]> getRequestParameters() {
		return requestParameters;
	}

	public String getRequestParameter(String key) {
		if (requestParameters.containsKey(key) && requestParameters.get(key).length != 0) {
			return requestParameters.get(key)[0];
		} else {
			return null;
		}
	}
	
	public Map<String, Object> getSessionAttributes() {
		return sessionAttributes;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * Method for clearing session.
	 */
	public void clearSession() {
		sessionAttributes.clear();
		if (request.getSession() != null) {
			request.getSession().invalidate();
		}
	}

}
