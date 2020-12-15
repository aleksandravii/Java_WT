<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/leftBar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">

    <title><fmt:message key="login.title"/></title>
</head>
<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<%@ include file="/WEB-INF/jspf/leftBar.jspf" %>

<div class="middleContainer">

    <form method="post" action="controller">
        <input type="hidden" name="command" value="login">
        <table class="inputTable">
            <tr class="loginInput">
                <td><p><fmt:message key="login.username"/></p></td>
                <td><input type="text" name="login" value="" required="required" placeholder="login" maxlength="30">
                </td>
            </tr>
            <tr class="passInput">
                <td><p><fmt:message key="login.password"/></p></td>
                <td><input type="password" name="password" value="" required="required" placeholder="password"
                           maxlength="30"></td>
            </tr>
            <tr class="buttonContainer">
                <td></td>
                <td>
                    <input type="submit" value="<fmt:message key="login.submit"/>" class="button">
                    <input type="reset" value="<fmt:message key="login.reset"/>" class="button">

                </td>
            </tr>
        </table>
    </form>

    <c:if test="${not empty invalid_data}">
        <p class="errMessage">${invalid_data}</p>
    </c:if>

    <c:if test="${not empty unknown_user}">
        <p class="errMessage">${unknown_user} <a href="controller?command=registration"><fmt:message key="login.reg.here"/></a></p>
    </c:if>

    <c:if test="${not empty user_blocked}">
        <p class="errMessage">${user_blocked}</p>
    </c:if>

</div>

<div class="rightContainer">

</div>

</body>
</html>
