<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: franzekan
  Date: 05.04.2022.
  Time: 18:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <jsp:include page="/WEB-INF/components/head.jsp">
        <jsp:param name="title" value="Home"/>
    </jsp:include>
</head>

<body>
<jsp:include page="/WEB-INF/components/navbar.jsp">
    <jsp:param name="page" value="index"/>
</jsp:include>

<div class="content">
    <h1>All blog users</h1>

    <c:if test='${pageContext.session.getAttribute("userId") == null}'>
        <a href="${pageContext.request.contextPath}/servlet/auth/login">Login</a>
    </c:if>

    <ul>
        <jsp:useBean id="authors" scope="request" type="java.util.List<hr.fer.oprpp2.model.BlogUser>"/>
        <c:forEach items="${authors}" var="author">
            <li>
                <a href="${pageContext.request.contextPath}/servlet/author/${author.getUsername()}">${author.getFullName()}</a>
            </li>
        </c:forEach>
    </ul>
</div>
</body>
</html>
