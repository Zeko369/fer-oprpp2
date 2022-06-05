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
        <jsp:param name="title" value="Blog form"/>
    </jsp:include>
</head>

<body>
<jsp:include page="/WEB-INF/components/navbar.jsp">
    <jsp:param name="page" value="index"/>
</jsp:include>

<a href="${pageContext.request.contextPath}/servlet/author/${blog.getUser().getUsername()}">Back to user</a>
<c:if test='${requestScope.get("isAuthor")}'>
    <a href="${pageContext.request.contextPath}/servlet/author/${blog.getUser().getUsername()}/${blog.getId()}/edit">Edit</a>
</c:if>

<jsp:useBean id="blog" scope="request" type="hr.fer.oprpp2.model.BlogEntry"/>
<h1>
    <%= blog.getTitle() %>
</h1>
<p>
    <%= blog.getBody() %>
</p>


<h2>Comments:</h2>
<c:choose>
    <c:when test="blog.getComments().size() == 0">
        <h3>No comments...</h3>
    </c:when>
    <c:otherwise>
        <c:forEach items="${blog.getComments()}" var="comment">
            <div class="comment">
                <h3>${comment.getUserEmail() }</h3>
                <p>${comment.getMessage() }</p>
            </div>
        </c:forEach>
    </c:otherwise>
</c:choose>

<h2>Comment</h2>
<form method="post"
      action="${pageContext.request.contextPath}/servlet/author/${blog.getUser().getUsername()}/${blog.getId()}/comment">
    <label>
        Message <br/>
        <textarea name="message"></textarea>
    </label>
    <br/>
    <label>
        Email <br/>
        <input name="email" type="email" required>
    </label>

    <input type="submit" value="Comment"/>
</form>
</body>
</html>
