<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminMenu.css">
    <title><fmt:message key="admin.menu.title"/></title>
</head>
<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="leftContainer"></div>

<div class="middleContainer">

    <a href="controller?command=all_clients" class="client">
        <fmt:message key="admin.menu.clients"/>
    </a>

    <a href="controller?command=all_orders" class="order">
        <fmt:message key="admin.menu.orders"/>
    </a>

    <a href="controller?command=all_goods" class="goods">
        <fmt:message key="admin.menu.goods"/>
    </a>

</div>

<div class="rightContainer"></div>

</body>
</html>
