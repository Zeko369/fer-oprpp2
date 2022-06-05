<%--
  Created by IntelliJ IDEA.
  User: franzekan
  Date: 05.06.2022.
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <jsp:include page="/WEB-INF/components/head.jsp">
        <jsp:param name="title" value="Blog form"/>
    </jsp:include>
</head>

<body>
<jsp:include page="/WEB-INF/components/navbar.jsp">
    <jsp:param name="page" value="index"/>
</jsp:include>

<div class="content">
    <h1>Blog form</h1>

    <form method="post">
        <c:if test='${requestScope.containsKey("error")}'>
            <div class="alert alert-danger" role="alert">
                <%= request.getAttribute("error") %>
            </div>
        </c:if>

        <div class="form-group mb-2">
            <label for="title">Title</label>
            <input name="title" class="form-control" id="title" placeholder="Title" value="${requestScope.get("title")}"
                   required>
        </div>

        <div class="form-group mb-2">
            <label for="body">Body</label>
            <textarea name="body" class="form-control" id="body" placeholder="Body"
                      required>${requestScope.get("body")}</textarea>
        </div>

        <button type="submit" class="btn btn-primary">${requestScope.get("body") == null ? "Create" : "Update"}</button>
    </form>
</div>
</body>
</html>

