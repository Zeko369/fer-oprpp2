<%--
  Created by IntelliJ IDEA.
  User: franzekan
  Date: 05.04.2022.
  Time: 20:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <jsp:include page="/WEB-INF/components/head.jsp">
        <jsp:param name="title" value="Trigonometric"/>
    </jsp:include>
</head>
<body>
<h1>Trigonometric values</h1>

<table class="table table-striped">
    <thead>
    <tr>
        <th>Angle</th>
        <th>Sin</th>
        <th>Cos</th>
    </tr>
    </thead>
    <tbody>

    <jsp:useBean id="values" scope="request" type="java.util.List<hr.fer.oprpp2.servlets.TrigonometricServlet.TrigonometricValue>"/>
    <c:forEach var="row" items="${values}">
        <tr>
            <td>${row.angle()}</td>
            <td>${row.sin()}</td>
            <td>${row.cos()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
