<jsp:useBean id="author" scope="request" type="hr.fer.oprpp2.model.BlogUser"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: franzekan
  Date: 05.06.2022.
  Time: 19:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <jsp:include page="/WEB-INF/components/head.jsp">
        <jsp:param name="title" value="Blogs:${author.getFullName()}"/>
    </jsp:include>
</head>

<body>
<jsp:include page="/WEB-INF/components/navbar.jsp">
    <jsp:param name="page" value="index"/>
</jsp:include>

<div class="content">
    <h1>Author ${author.getFullName()}</h1>
    <c:if test='${requestScope.get("isAuthor")}'>
        <a href="${pageContext.request.contextPath}/servlet/author/${author.getUsername()}/new">new</a>
    </c:if>

    <ul>
        <jsp:useBean id="blogs" scope="request" type="java.util.List<hr.fer.oprpp2.model.BlogEntry>"/>
        <c:forEach items="${blogs}" var="blog">
            <li>
                <a href="${pageContext.request.contextPath}/servlet/author/${author.getUsername()}/${blog.getId()}">${blog.title}</a>

                <c:if test='${requestScope.get("isAuthor")}'>
                    <a href="${pageContext.request.contextPath}/servlet/author/${author.getUsername()}/${blog.getId()}/edit">EDIT</a>
                </c:if>
            </li>
        </c:forEach>
    </ul>
</div>
</body>
</html>
