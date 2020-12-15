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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/orderList.css">
    <title><fmt:message key="orders.title"/></title>
</head>
<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<%@ include file="/WEB-INF/jspf/adminLeftBar.jspf" %>

<div class="middleContainer">

    <table class="orderTable">
        <tr>
            <th><fmt:message key="orders.id"/></th>
            <th><fmt:message key="orsers.date"/></th>
            <th><fmt:message key="orsers.price"/></th>
            <th><fmt:message key="orsers.status"/></th>
            <th><fmt:message key="orsers.client.name"/></th>
            <th></th>
        </tr>
        <tr>
            <c:forEach var="order" items="${list_order_beans}">

        <tr>
            <!-- Custom tag -->
            <order:orderBeanTable orderBean="${order}"/>

            <td>

                <form method="post" action="controller">
                    <input type="hidden" name="command" value="all_orders">
                    <input type="hidden" name="order_id" value="${order.id}">
                    <input type="hidden" name="status" value="paid">
                    <input type="submit" value="<fmt:message key="orders.paid"/>">
                </form>
                <form method="post" action="controller">
                    <input type="hidden" name="command" value="all_orders">
                    <input type="hidden" name="order_id" value="${order.id}">
                    <input type="hidden" name="status" value="cancel">
                    <input type="submit" value="<fmt:message key="orsers.cancel"/>">
                </form>

            </td>
        </tr>

        </c:forEach>
        </tr>
    </table>

</div>

<div class="rightContainer">

</div>
</body>
</html>
