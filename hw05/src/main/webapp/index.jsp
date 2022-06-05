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
    <h1>Hello World!</h1>
    <a href="${pageContext.request.contextPath}/voting">Go check out all the polls</a>
</div>
</body>
</html>
