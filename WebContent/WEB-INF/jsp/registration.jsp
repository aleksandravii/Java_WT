<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/leftBar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registration.css">

    <!-- reCAPTCHA -->
    <script src='https://www.google.com/recaptcha/api.js'></script>

    <title><fmt:message key="registration.title"/></title>

    <%@ include file="/WEB-INF/jspf/header.jspf" %>

    <%@ include file="/WEB-INF/jspf/leftBar.jspf" %>

</head>
<body>

<div class="middleContainer">

    <form method="post" action="controller">
        <input type="hidden" name="command" value="registration">

        <table class="inputTable">

            <tr>
                <td><p><fmt:message key="registration.first.name"/></p></td>
                <td><input type="text" name="first_name" value="" required="required" placeholder="Your name"
                           maxlength="30"></td>
            </tr>
            <tr>
                <td><p><fmt:message key="registration.last.name"/></p></td>
                <td><input type="text" name="last_name" value="" required="required" placeholder="Your last name"
                           maxlength="30"></td>
            </tr>
            <tr>
                <td><p><fmt:message key="registration.email"/></p></td>
                <td><input type="text" name="email" value="" required="required" placeholder="email" maxlength="30"
                           pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"></td>
            </tr>
            <tr>
                <td><p><fmt:message key="login.username"/></p></td>
                <td><input type="text" name="login" value="" required="required" placeholder="login" maxlength="30">
                </td>
            </tr>
            <tr>
                <td><p><fmt:message key="login.password"/></p></td>
                <td><input type="password" name="password" value="" required="required" placeholder="password"
                           maxlength="30"></td>
            </tr>
        </table>

        <!-- reCAPTCHA -->
        <div class="g-recaptcha" data-sitekey="6LfXlREUAAAAALRda0HkCAxVE3yLGmh2eGH1WK39"></div>

        <input type="submit" value="<fmt:message key="registration.submit"/>" class="button submit">
        <input type="reset" value="<fmt:message key="login.reset"/>" class="button">

    </form>

    <p class="message">${result}</p>
    <c:if test="${not empty success}">
        <p class="message">${success} <a href="controller?command=login"><fmt:message key="registration.login"/></a></p>
    </c:if>

</div>

<div class="rightContainer">
</div>

</body>
</html>
