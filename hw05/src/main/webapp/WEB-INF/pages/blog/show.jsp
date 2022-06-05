<jsp:useBean id="author" scope="request" type="hr.fer.oprpp2.model.BlogUser"/>
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
<h1>${blog.getTitle()}</h1>
</body>
</html>
