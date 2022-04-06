<%--
  Created by IntelliJ IDEA.
  User: franzekan
  Date: 06.04.2022.
  Time: 19:43
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="hr.fer.oprpp2.util.Theme" %>

<html>
<head>
    <jsp:include page="/WEB-INF/components/head.jsp">
        <jsp:param name="title" value="Colors"/>
    </jsp:include>
</head>
<body>
<jsp:include page="/WEB-INF/components/navbar.jsp">
    <jsp:param name="page" value="voting/results"/>
</jsp:include>

<div class="content">
    <h1>Results</h1>
    <p>These are the results</p>

    <table class="table table-striped <%= Theme.getFromRequest(request).equals("dark") ? "table-dark" : "" %>">
        <thead>
        <tr>
            <th>Band</th>
            <th>Votes</th>
        </tr>
        </thead>
        <tbody>
        <jsp:useBean id="votes" scope="request" type="java.util.List<hr.fer.oprpp2.services.votesDB.WholeVote>"/>
        <c:forEach var="result" items="${votes}">
            <tr>
                <td>${result.name()}</td>
                <td>${result.votes()}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h2>Chart</h2>
    <img src="${pageContext.request.contextPath}/voting/export?format=graph" alt="Chart"/>

    <h2>Export data</h2>
    <p>
        Click <a href="${pageContext.request.contextPath}/voting/export?format=xlsx">here</a> to download the results
        as XLSX.
    </p>
    <%--<p>Click <a href="${pageContext.request.contextPath}/voting/export?format=json">here</a> to download the results as JSON.</p>--%>

    <h2>Other</h2>
    <p>Links to songs of winners</p>
    <ul>
        <jsp:useBean id="winners" scope="request" type="java.util.List<hr.fer.oprpp2.services.votesDB.WholeVote>"/>
        <c:forEach var="winner" items="${winners}">
            <li>
                <a target="_blank" rel="noreferrer" href="${winner.youtubeLink()}">
                        ${winner.name()}
                </a>
            </li>
        </c:forEach>
    </ul>
</div>
</body>
</html>
