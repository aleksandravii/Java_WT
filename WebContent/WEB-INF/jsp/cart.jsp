<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/leftBar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css">
    <title><fmt:message key="cart.title"/></title>
</head>
<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<%@ include file="/WEB-INF/jspf/leftBar.jspf" %>

<form action="controller" method="post" class="middleContainer">

    <p class="cartEmpty">${cart_empty}</p>
    <c:if test="${not empty order_item_list}">
        <c:forEach var="order_item_bean" items="${order_item_list}">
            <div class="goods">
                <input type="hidden" name="command" value="reg_order"/>

                <div class="leftGoods">
                    <p>${order_item_bean.goods.name}</p>
                    <p><fmt:message key="cart.price"/> ${order_item_bean.goods.price} <fmt:message
                        key="cart.cost.unit"/></p>
                    <a href="controller?command=cart&remove=${order_item_bean.goods.id}" class="cartRemove"><fmt:message
                        key="cart.remove"/></a>
                </div>
                <div class="rightGoods">
                    <p><fmt:message key="cart.amount"/></p>
                    <input type="number" name="goods_amount" min="1" max="100" size="3" step="1"
                           value="${order_item_bean.amount}"/>
                </div>
            </div>
        </c:forEach>

        <p class="sum"><fmt:message key="cart.total"/> ${summ_price} <fmt:message key="cart.cost.unit"/></p>

        <input type="submit" value="<fmt:message key="cart.register.order"/>" class="register">

    </c:if>
    <c:if test="${not empty success_order}">
        <p class="result">
            <fmt:message key="cart.success_order"/><br><a href="controller?command=person_account"><fmt:message
            key="cart.personal.account"/></a>
        </p>
    </c:if>

</form>

<div class="rightContainer">

</div>

</body>
</html>
