<%@ page import="java.util.Objects" %><%--
  Created by IntelliJ IDEA.
  User: franzekan
  Date: 06.04.2022.
  Time: 21:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark px-4">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">Lab4</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item <%=Objects.requireNonNullElse(request.getParameter("page"), "").equals("index") ? "active" : "" %>">
                <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
            </li>
            <li class="nav-item <%=Objects.requireNonNullElse(request.getParameter("page"), "").equals("voting") ? "active" : "" %>">
                <a class="nav-link" href="${pageContext.request.contextPath}/voting">Voting</a>
            </li>
        </ul>
    </div>
</nav>
