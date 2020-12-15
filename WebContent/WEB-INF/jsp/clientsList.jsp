<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<!-- tag file -->
<%@ taglib tagdir="/WEB-INF/tags" prefix="user" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/leftBar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/clientList.css">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="clients.list.title"/></title>
</head>
<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<%@ include file="/WEB-INF/jspf/adminLeftBar.jspf" %>

<div class="middleContainer">
    <c:if test="${empty list_clients}"><p class="noClients"><fmt:message key="clients.list.no.clients"/></p></c:if>

    <table class="userTable">
        <tr>
            <th><fmt:message key="clients.list.user.id"/></th>
            <th><fmt:message key="clients.list.login"/></th>
            <th><fmt:message key="clients.list.name"/></th>
            <th><fmt:message key="clients.list.email"/></th>
            <th><fmt:message key="clients.list.access"/></th>
            <th></th>
        </tr>
        <c:forEach var="client" items="${list_clients}">
            <tr>
                <user:user user="${client}"/>
                <td>
                    <c:choose>

                        <c:when test="${client.isBlocked}">
                            <form method="post" action="controller" class="unblockContainer">
                                <input type="hidden" name="command" value="all_clients">
                                <input type="hidden" name="client_id" value="${client.id}">
                                <input type="hidden" name="access" value="Unblock">
                                <input type="submit" value="<fmt:message key="clients.unblock"/>">
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form method="post" action="controller" class="blockContainer">
                                <input type="hidden" name="command" value="all_clients">
                                <input type="hidden" name="client_id" value="${client.id}">
                                <input type="hidden" name="access" value="Block">
                                <input type="submit" value="<fmt:message key="clients.block"/>">
                            </form>
                        </c:otherwise>
                    </c:choose>

                </td>
            </tr>
        </c:forEach>
    </table>

</div>

<div class="rightContainer">

</div>

</body>
</html>
