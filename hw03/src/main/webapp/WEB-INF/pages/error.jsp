<%--
  Created by IntelliJ IDEA.
  User: franzekan
  Date: 05.04.2022.
  Time: 19:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.Objects" %>

<html>
<head>
    <jsp:include page="/WEB-INF/components/head.jsp">
        <jsp:param name="title" value="Error"/>
    </jsp:include>
</head>

<body>
<div class="content">
    <h1>Error occurred</h1>
    <p>
        <%= Objects.requireNonNullElse(request.getAttribute("error"), "Generic error") %>
    </p>

    <a href="${pageContext.request.contextPath}/">Go home</a>
</div>
</body>
</html>
