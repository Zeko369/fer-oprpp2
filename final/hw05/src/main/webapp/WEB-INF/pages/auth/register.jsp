<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: franzekan
  Date: 05.06.2022.
  Time: 19:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.Objects" %>

<html>
<head>
    <jsp:include page="/WEB-INF/components/head.jsp">
        <jsp:param name="title" value="Register"/>
    </jsp:include>
</head>
<body>
<div class="content">
    <h1>Register</h1>

    <form method="post">
        <c:if test='${requestScope.containsKey("error")}'>
            <div class="alert alert-danger" role="alert">
                <%= request.getAttribute("error") %>
            </div>
        </c:if>

        <div class="form-group mb-2">
            <label for="username">Username</label>
            <input name="username" class="form-control" id="username" placeholder="foobar" value="${Objects.requireNonNullElse(requestScope.get("username"), "foobar")}" required>
        </div>

        <div class="form-group mb-2">
            <label for="email">Email</label>
            <input name="email" type="email" class="form-control" id="email" placeholder="foo@bar.com" value="${Objects.requireNonNullElse(requestScope.get("email"), "foo@bar.com")}" required>
        </div>

        <div class="form-group mb-2">
            <label for="firstName">First Name</label>
            <input name="firstName" class="form-control" id="firstName" placeholder="Foo" value="${Objects.requireNonNullElse(requestScope.get("firstName"), "Foo")}" required>
        </div>

        <div class="form-group mb-2">
            <label for="lastName">Last Name</label>
            <input name="lastName" class="form-control" id="lastName" placeholder="Bar" value="${Objects.requireNonNullElse(requestScope.get("lastName"), "Bar")}" required>
        </div>

        <div class="form-group mb-2">
            <label for="password">Password</label>
            <input name="password" type="password" class="form-control" id="password" placeholder="Password" value="${Objects.requireNonNullElse(requestScope.get("password"), "foobar123")}" required>
        </div>

        <button type="submit" class="btn btn-primary">Register</button>
    </form>

    <a href="${pageContext.request.contextPath}/servlet/auth/login">Login</a>
</div>
</body>
</html>
