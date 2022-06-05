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
        <jsp:param name="title" value="New blog"/>
    </jsp:include>
</head>
<body>

<jsp:useBean id="blog" scope="request" type="hr.fer.oprpp2.model.BlogEntry"/>
<h1>
    <%= blog.getTitle() %>
</h1>
<p>
    <%= blog.getBody() %>
</p>
</body>
</html>
