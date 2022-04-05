<%--
  Created by IntelliJ IDEA.
  User: franzekan
  Date: 05.04.2022.
  Time: 18:12
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <jsp:include page="/WEB-INF/components/head.jsp">
       <jsp:param name="title" value="Colors" />
    </jsp:include>
</head>
<body>
    <a href="<c:url value="/setcolor"/>?color=WHITE">WHITE</a>
    <a href="<c:url value="/setcolor"/>?color=RED">RED</a>
    <a href="<c:url value="/setcolor"/>?color=GREEN">GREEN</a>
    <a href="<c:url value="/setcolor"/>?color=CYAN">CYAN</a>
    <a href="<c:url value="/setcolor"/>?color=DARK">DARK</a>
</body>
</html>
