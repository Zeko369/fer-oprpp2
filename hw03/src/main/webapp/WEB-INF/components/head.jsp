<%--
  Created by IntelliJ IDEA.
  User: franzekan
  Date: 05.04.2022.
  Time: 19:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.Objects" %>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>

<title>
    lab3 - <%= Objects.requireNonNullElse(request.getParameter("title"), "page") %>
</title>
<style>
    <% String background = Objects.requireNonNullElse((String)request.getSession().getAttribute("pickedBgColor"), "#ffffff"); %>
    <% String foreground = Objects.requireNonNullElse((String)request.getSession().getAttribute("pickedFgColor"), "#000000"); %>

    :root {
        --bg: <%= background %>;
        --fg: <%= foreground %>;
        --linkColor: <%= foreground.equals("#000000") ? "#080be7" : "#23a2ff" %>;
        --linkVisiteColor: <%= foreground.equals("#000000") ? "#af04f3" : "#de89ff" %>;
    }
</style>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css"/>
