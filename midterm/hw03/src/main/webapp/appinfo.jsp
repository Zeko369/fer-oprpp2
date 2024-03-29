<%--
  Created by IntelliJ IDEA.
  User: franzekan
  Date: 06.04.2022.
  Time: 01:18
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta http-equiv="refresh" content="1">

    <jsp:include page="/WEB-INF/components/head.jsp">
        <jsp:param name="title" value="AppInfo"/>
    </jsp:include>
</head>
<body>
<jsp:include page="/WEB-INF/components/navbar.jsp">
    <jsp:param name="page" value="appinfo"/>
</jsp:include>

<div class="content">
    <h1>App has been running for</h1>
    <br/>

    <jsp:useBean id="TIME_SINCE_STARTED" scope="request" type="java.lang.String"/>
    <h2>${TIME_SINCE_STARTED}</h2>
</div>
</body>
</html>
