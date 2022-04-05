<%--
  Created by IntelliJ IDEA.
  User: franzekan
  Date: 05.04.2022.
  Time: 18:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.Objects" %>
<html>
<head>
    <title>lab3 - home</title>

    <style>
        :root {
            --bg: <%= Objects.requireNonNullElse(request.getSession().getAttribute("pickedBgColor"), "#ffffff") %>;
        }

        body {
            background-color: var(--bg);
        }
    </style>
</head>

<body>
<h2>Hello World!</h2>
<a href="colors.jsp">Colors</a>
</body>
</html>
