<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/errorPage.css">
    <title><fmt:message key="error.page"/></title>
</head>
<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<p><fmt:message key="error.page"/></p>

<c:set var="code"
       value="${requestScope['javax.servlet.error.status_code']}"/>
<c:set var="message"
       value="${requestScope['javax.servlet.error.message']}"/>

<c:if test="${not empty code}">
    <h3>
        <fmt:message key="error.code"/>
        <c:out value="${code}"/>
    </h3>
</c:if>

<c:if test="${not empty message1}">
    <p>
        <c:out value="${message1}"/>
    </p>
</c:if>

<c:if test="${not empty errorMessage}">
    <p>
        <c:out value="${errorMessage}"/>
    </p>
</c:if>
</body>
</html>
