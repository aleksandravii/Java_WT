<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/leftBar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/goodsSetting.css">
    <title><fmt:message key="goods.setting.title"/></title>
</head>
<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<%@ include file="/WEB-INF/jspf/adminLeftBar.jspf" %>

<div class="middleContainer">

    <p class="title">${title}</p>

    <form method="post" action="controller">
        <input type="hidden" name="command" value="edit_goods">
        <input type="hidden" name="goods_id" value="${goods.id}">

        <table class="inputTable">
            <tr>
                <td><p><fmt:message key="goods.setting.name"/></p></td>
                <td><input type="text" name="goods_name" required="required" maxlength="50" value="${goods.name}"></td>
            </tr>
            <tr>
                <td><p><fmt:message key="goods.setting.price"/></p></td>
                <td><input type="number" name="goods_price" required="required" min="1" max="999999" size="6" step="1"
                           value="${goods.price}"></td>
            </tr>
            <tr>
                <td><p><fmt:message key="goods.setting.release.date"/></p></td>
                <td><input type="date" name="release_date" required="required" min="2000-01-01" max="${current_date}"
                           value="${goods.releaseDate}"></td>
            </tr>
            <tr>
                <td><p><fmt:message key="goods.setting.color"/></p></td>
                <td>
                    <select name="colour_id">
                        <c:forEach var="colour" items="${list_colours}">
                            <c:choose>
                                <c:when test="${colour.id eq goods.colourId}">
                                    <option selected="selected" value="${colour.id}">${colour.colourName}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${colour.id}">${colour.colourName}</option>
                                </c:otherwise>
                            </c:choose>

                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td><p><fmt:message key="goods.setting.group"/></p></td>
                <td>
                    <select name="group_id">
                        <c:forEach var="group" items="${list_groups}">

                            <c:choose>
                                <c:when test="${group.id eq goods.groupId}">
                                    <option selected="selected" value="${group.id}">${group.groupName}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${group.id}">${group.groupName}</option>
                                </c:otherwise>
                            </c:choose>

                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td><p><fmt:message key="goods.setting.manufacturer"/></p></td>
                <td>
                    <select name="manufacturer_id">
                        <c:forEach var="manufacturer" items="${list_manufacturers}">

                            <c:choose>
                                <c:when test="${manufacturer.id eq goods.manufacturerId}">
                                    <option selected="selected"
                                            value="${manufacturer.id}">${manufacturer.manufacturerName}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${manufacturer.id}">${manufacturer.manufacturerName}</option>
                                </c:otherwise>
                            </c:choose>

                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>

        <input type="submit" value="<fmt:message key="registration.submit"/>" class="button">
    </form>
</div>

<div class="rightContainer">

</div>

</body>
</html>
