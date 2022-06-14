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
        <jsp:param name="title" value="Voting"/>
    </jsp:include>

</head>
<body>
<jsp:include page="/WEB-INF/components/navbar.jsp">
    <jsp:param name="page" value="voting"/>
</jsp:include>

<div class="content">
    <h1>Polls</h1>
    <jsp:useBean id="polls" scope="request"
                 type="java.util.List<hr.fer.oprpp2.model.Poll>"/>
    <c:forEach var="poll" items="${polls}">
        <li>
            <a href="<c:url value="/voting?pollId=${poll.getId()}"/>">${poll.getTitle()}</a>
            ${poll.getMessage()}
        </li>
    </c:forEach>
</div>
</body>
</html>
