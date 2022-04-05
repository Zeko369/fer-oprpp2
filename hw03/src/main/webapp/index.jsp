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
<h2>Hello World!</h2>
<a href="colors.jsp">Colors</a>

<a href="trigonometric?a=0&b=90">Trigonometric default</a>

<form action="trigonometric" method="GET">
    <div class="form-group mb-2">
        <label for="fromAngle">Početni kut:</label>
        <input type="number" name="a" min="0" max="360" step="1" value="0" class="form-control" id="fromAngle"
               placeholder="From angle">
    </div>

    <div class="form-group mb-2">
        <label for="toAngle">Završni kut:</label>
        <input type="number" name="a" min="0" max="360" step="1" value="360" class="form-control" id="toAngle"
               placeholder="To angle">
    </div>

    <button type="submit" class="btn btn-primary">Tabeliraj</button>
    <button type="reset" class="btn btn-secondary">Reset</button>
</form>
</body>
</html>
