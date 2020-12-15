<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<!-- Custom tag -->
<%@ taglib uri="/WEB-INF/orderCustomTag" prefix="order" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/leftBar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/personalAccount.css">
    <title><fmt:message key="personal.acc.title"/></title>
</head>
<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<%@ include file="/WEB-INF/jspf/leftBar.jspf" %>

<div class="middleContainer">

    <c:if test="${empty list_order_beans}">
        <p class="noOrders"><fmt:message key="orders.no.orders"/></p>
    </c:if>

    <c:if test="${not empty list_order_beans}">
        <table class="orderTable">
            <tr>
                <th><fmt:message key="orders.id"/></th>
                <th class="date"><fmt:message key="orsers.date"/></th>
                <th class="price"><fmt:message key="orsers.price"/>, <fmt:message key="cart.cost.unit"/></th>
                <th class="status"><fmt:message key="orsers.status"/></th>
                <th><fmt:message key="personal.acc.goods"/></th>
            </tr>

            <c:forEach var="order" items="${list_order_beans}">

                <tr>
                    <!-- Custom tag -->
                    <order:orderBeanTable orderBean="${order}"/>
                </tr>

            </c:forEach>

        </table>

        <form method="post" action="controller">
            <input type="hidden" name="command" value="person_account">
            <input type="hidden" name="download" value="downloadReport">
            <input type="submit" value="<fmt:message key="personal.acc.download"/>" class="download">
        </form>
    </c:if>
</div>

<div class="rightContainer">
    <p class="personalData"><fmt:message key="personal.acc.data"/></p>

    <table class="persDataTable">
        <tr>
            <td><fmt:message key="clients.list.name"/>:</td>
            <td>${user.firstName} ${user.lastName}</td>
        </tr>
        <tr>
            <td><fmt:message key="clients.list.login"/>:</td>
            <td>${user.login}</td>
        </tr>
        <tr>
            <td><fmt:message key="clients.list.email"/>:</td>
            <td>${user.email}</td>
        </tr>
    </table>
</div>

</body>
</html>
