<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<!-- tag file -->
<%@ taglib tagdir="/WEB-INF/tags" prefix="goods" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/catalogStyle.css">
    <title><fmt:message key="catalog.title"/></title>
</head>
<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="leftContainer">

    <div class="menu">
        <a href="controller?command=catalog&category=all" class="category">
            <fmt:message key="main_page.all_goods"/>
        </a>
        <c:forEach var="category" items="${all_categories}">
            <a href="controller?command=catalog&category=${category.categoryName}" class="category">
                <fmt:message key="${category.categoryName}"/>
            </a>
        </c:forEach>
    </div>

	<!-- additional task -->
	<form action="controller" class="size">
		<input type="hidden" name="command" value="catalog">
		<input type="hidden" name="category" value="all">
		<p>Input size:</p>
		<input type="number" name="size">
		<input type="submit" value="OK">
	</form>
	
    <form action="controller" class="categoryMenu">
        <input type="hidden" name="command" value="selection_catalog"/>
        <p class="catName"><fmt:message key="catalog.chooose.group"/></p>


        <c:forEach var="category" items="${choose_categories}">
            <div class="group">
                <p class="groupName"><fmt:message key="${category.categoryName}"/></p>
                <c:forEach var="group" items="${groups}">
                <c:if test="${group.categoryId eq category.id}">
                <p><input type="checkbox" name="group" value="${group.groupName}">${group.groupName}<p>
                </c:if>
                </c:forEach>
            </div>
        </c:forEach>

        <hr>

        <c:if test="${not empty manufacturers}">
            <div class="group">
                <p class="groupName"><fmt:message key="catalog.manufacturers"/></p>
                <c:forEach var="manufacturer" items="${manufacturers}">
                    <p>
                        <input type="checkbox" name="manufacturer" value="${manufacturer.manufacturerName}">
                            ${manufacturer.manufacturerName}
                    </p>
                </c:forEach>
            </div>
            <hr>
        </c:if>

        <c:if test="${not empty colours}">
            <div class="group">
                <p class="groupName"><fmt:message key="catalog.colours"/></p>
                <c:forEach var="colour" items="${colours}">
                    <p>
                        <input type="checkbox" name="colour" value="${colour.colourName}">
                            ${colour.colourName}
                    </p>
                </c:forEach>
            </div>
        </c:if>
		
        <input type="submit" value="<fmt:message key="catalog.refresh"/>" class="refresh">
    </form>
</div>

<div class="middleContainer">
    <div class="catalogHead">
        <div class="price">

            <form action="controller" class="priceForm">
                <input type="hidden" name="command" value="price_catalog"/>

                <p><fmt:message key="catalog.price.from"/></p>
                <input type="number" name="price_from" min="${min_price}" max="${max_price}" size="6" step="1"
                       value="${cur_min_price}"/>
                <p><fmt:message key="catalog.price.to"/></p>
                <input type="number" name="price_to" min="${min_price}" max="${max_price}" size="6" step="1"
                       value="${cur_max_price}"/>

                <input type="submit" value="OK" class="inputPrice">
            </form>

        </div>

        <div class="sort">
            <form action="controller">
                <input type="hidden" name="command" value="sort_catalog"/>
                <input type="hidden" name="sort_param" value="sort">
                <select name="sort" class="selectSort">
                    <option disabled="disabled" selected="selected"><fmt:message key="catalog.sort.select"/></option>
                    <option value="name_a_z"><fmt:message key="catalog.sort.name.az"/></option>
                    <option value="name_z_a"><fmt:message key="catalog.sort.name.za"/></option>
                    <option value="from_cheap_to_expensive"><fmt:message key="catalog.sort.cheap"/></option>
                    <option value="from_expensive_to_cheap"><fmt:message key="catalog.sort.expensive"/></option>
                    <option value="release_date_new"><fmt:message key="catalog.sort.new"/></option>
                    <option value="release_date_old"><fmt:message key="catalog.sort.old"/></option>
                </select>
                <input type="submit" value="OK" class="inputSort">
            </form>
        </div>
    </div>

    <div class="mainCatalog">

        <div class="noGoods">
            <p>${no_goods}</p>
        </div>

        <c:forEach var="goods" items="${goods}">
            <form action="controller" class="goods">
                <input type="hidden" name="command" value="add_to_cart"/>
                <input type="hidden" name="goods_id" value="${goods.id}">

                <div class="leftGoods">

                    <goods:goods goods="${goods}"/>

                </div>
                <div class="rightGoods">
                    <input type="submit" value="<fmt:message key="catalog.add.to.cart"/>" class="addToCart">

                    <c:if test="${added_goods_id eq goods.id}">
                        <p>${add_to_cart}</p>
                    </c:if>
                </div>
            </form>
        </c:forEach>

    </div>
</div>
</body>
</html>
