<%--
  Created by IntelliJ IDEA.
  User: franzekan
  Date: 06.04.2022.
  Time: 19:42
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <jsp:include page="/WEB-INF/components/head.jsp">
        <jsp:param name="title" value="Colors"/>
    </jsp:include>
</head>
<body>
<h1>Vote for your favourite band</h1>
<p>Clicking on the link will vote</p>

<ol>
    <jsp:useBean id="voteOptions" scope="request"
                 type="java.util.List<hr.fer.oprpp2.services.VotesDB.VoteOption>"/>
    <c:forEach var="voteOption" items="${voteOptions}">
        <li>
            <a href="<c:url value="/glasanje-glasaj?id=${voteOption.id()}"/>">
                ${voteOption.name()}
            </a>
        </li>
    </c:forEach>
</ol>
</body>
</html>
