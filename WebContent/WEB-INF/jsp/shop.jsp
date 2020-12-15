<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shop.css">
    <title>Tech Shop</title>
</head>
<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<c:if test="${not empty user}">
    <div class="hello">
        <p><fmt:message key="main.hello"/> ${user.firstName}</p>
    </div>
</c:if>
<div class="offer">
    <p><fmt:message key="main.select.category"/></p>
</div>

<div class="mainContainer">

    <div class="main">

        <a href="controller?command=catalog&category=all" class="category">
            <fmt:message key="main_page.all_goods"/>
        </a>

        <c:forEach var="category" items="${categories}">

            <a href="controller?command=catalog&category=${category.categoryName}" class="category">
                <fmt:message key="${category.categoryName}"/>
            </a>

        </c:forEach>

    </div>

</div>

</body>
</html>
