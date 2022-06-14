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
    <div class="row">
        <div class="col-md-6">
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
        <div class="col-md-6">
            <c:if test='${requestScope.get("isLoggedIn")}'>
                <c:choose>
                    <c:when test='${requestScope.get("isAuthor")}'>
                        <h2>Comments on your page</h2>
                        <c:if test='${author.myComments.size() == 0}'>
                            <h3>No comments yet</h3>
                        </c:if>
                        <c:forEach items="${author.myComments}" var="comment">
                            <li>
                                <h4>${comment.getCommenter().getUsername()}</h4>
                                <p>${comment.getComment()}</p>
                            </li>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <h2>Comment to this user</h2>
                        <form action="${pageContext.request.contextPath}/servlet/author/${author.getUsername()}" method="post">
                            <div class="form-group mb-2">
                                <label for="comment">Comment</label>
                                <textarea name="comment" class="form-control" id="comment" placeholder="Comment"
                                          required></textarea>
                            </div>

                            <input type="submit">
                        </form>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
