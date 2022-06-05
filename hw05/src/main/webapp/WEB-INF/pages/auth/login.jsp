<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: franzekan
  Date: 05.06.2022.
  Time: 19:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <jsp:include page="/WEB-INF/components/head.jsp">
        <jsp:param name="title" value="Login"/>
    </jsp:include>
</head>
<body>
<div class="content">
    <h1>Login to the blog site</h1>

    <form method="post">
        <c:if test='${requestScope.containsKey("error")}'>
            <div class="alert alert-danger" role="alert">
                <%= request.getAttribute("error") %>
            </div>
        </c:if>

        <div class="form-group mb-2">
            <label for="username">Username</label>
            <input name="username" class="form-control" id="username" placeholder="Username" value="${requestScope.get("username")}" required>
        </div>

        <div class="form-group mb-2">
            <label for="password">Password</label>
            <input name="password" type="password" class="form-control" id="password" placeholder="Password" required>
        </div>

        <button type="submit" class="btn btn-primary">Login</button>
    </form>

    <a href="${pageContext.request.contextPath}/servlet/auth/register">Register</a>
</div>
</body>
</html>
