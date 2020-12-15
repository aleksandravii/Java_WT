<%@ include file="/WEB-INF/jspf/taglib.jspf"%>
<%@tag pageEncoding="UTF-8"%>
<%@tag body-content="empty"%>
<%@ attribute name="user" required="true" type="techshop.domain.entities.User"%>

<td>${user.id}</td>
<td>${user.login}</td>
<td>${user.firstName} ${user.lastName}</td>
<td>${user.email}</td>

<td>
	<c:choose>
		<c:when test="${user.isBlocked eq true}"><fmt:message key="clients.access.no"/></c:when>
		<c:otherwise><fmt:message key="clients.access.yes"/></c:otherwise>
	</c:choose> 
</td>
			