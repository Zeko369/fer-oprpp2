<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Objects" %><%--
  Created by IntelliJ IDEA.
  User: franzekan
  Date: 06.04.2022.
  Time: 21:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark px-4">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">Lab5 - blog app</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item <%=Objects.requireNonNullElse(request.getParameter("page"), "").equals("index") ? "active" : "" %>">
                <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
            </li>
        </ul>
    </div>

    <c:choose>
        <c:when test='${pageContext.session.getAttribute("userId") != null}'>
            <a href="${pageContext.request.contextPath}/servlet/auth/logout">Logout</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/servlet/auth/login">Login</a>
        </c:otherwise>
    </c:choose>
</nav>
