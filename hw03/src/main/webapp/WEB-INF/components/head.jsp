<%--
  Created by IntelliJ IDEA.
  User: franzekan
  Date: 05.04.2022.
  Time: 19:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.Objects" %>

<title>
    lab3 - <%= Objects.requireNonNullElse(request.getParameter("title"), "page") %>
</title>
<style>
    <% String background = Objects.requireNonNullElse((String)request.getSession().getAttribute("pickedBgColor"), "#ffffff"); %>
    <% String foreground = Objects.requireNonNullElse((String)request.getSession().getAttribute("pickedFgColor"), "#ffffff"); %>

    :root {
        --bg: <%= background %>;
        --fg: <%= foreground %>;
        --linkColor: <%= foreground.equals("#000000") ? "#080be7" : "#23a2ff" %>;
        --linkVisiteColor: <%= foreground.equals("#000000") ? "#af04f3" : "#de89ff" %>;
    }

    body {
        background-color: var(--bg);
        color: var(--fg);
    }

    a:link {
        color: var(--linkColor);
    }

    a:visited {
        color: var(--linkVisiteColor);
    }
</style>
