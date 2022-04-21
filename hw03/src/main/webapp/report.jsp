<%--
  Created by IntelliJ IDEA.
  User: franzekan
  Date: 06.04.2022.
  Time: 21:20
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <jsp:include page="/WEB-INF/components/head.jsp">
        <jsp:param name="title" value="Languages report"/>
    </jsp:include>
</head>
<body>
<jsp:include page="/WEB-INF/components/navbar.jsp">
    <jsp:param name="page" value="report"/>
</jsp:include>

<div class="content">
    <h1>Languages report</h1>
    <img src="${pageContext.request.contextPath}/reportImage" alt="Languages report"/>
</div>
</body>
</html>
