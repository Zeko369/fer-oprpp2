<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: franzekan
  Date: 05.06.2022.
  Time: 19:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Blogs</title>
</head>
<body>
<h1>Blogs</h1>
<ul>
    <jsp:useBean id="blogs" scope="request" type="java.util.List<hr.fer.oprpp2.model.BlogEntry>"/>
    <c:forEach items="${blogs}" var="blog">
        <li>${blog.title}</li>
    </c:forEach>
</ul>
</body>
</html>
