<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<!-- tag file -->
<%@ taglib tagdir="/WEB-INF/tags" prefix="goods" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/leftBar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/goodsList.css">
    <title><fmt:message key="goods.setting.title"/></title>
</head>
<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<%@ include file="/WEB-INF/jspf/adminLeftBar.jspf" %>

<div class="middleContainer">

    <c:if test="${empty list_goods}">
        <div class="noGoods">
            <p><fmt:message key="goods.no.goods"/></p>
        </div>
    </c:if>

    <c:forEach var="goods" items="${list_goods}">
        <div class="goods">
            <form action="controller" class="leftGoods">

                <input type="hidden" name="command" value="goods_setting">
                <input type="hidden" name="goods_id" value="${goods.id}">
                <input type="hidden" name="do_goods" value="edit">

                <goods:goods goods="${goods}"/>

                <input type="submit" value="<fmt:message key="goods.edit"/>" class="edit">

            </form>

            <form method="post" action="controller" class="rightGoods">
                <input type="hidden" name="command" value="goods_setting">
                <input type="hidden" name="goods_id" value="${goods.id}">
                <input type="hidden" name="do_goods" value="remove">

                <input type="submit" value="<fmt:message key="goods.remove"/>" class="removeButton">
            </form>
        </div>
    </c:forEach>

</div>

<div class="rightContainer">
    <form action="controller" class="newGoodsButContainer">
        <input type="hidden" name="command" value="goods_setting">
        <input type="hidden" name="do_goods" value="create_goods">

        <input type="submit" value="<fmt:message key="goods.create"/>" class="newGoodsBut">
    </form>
</div>

</body>
</html>
