<%@ page import="java.util.Random" %><%--
  Created by IntelliJ IDEA.
  User: franzekan
  Date: 05.04.2022.
  Time: 20:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <jsp:include page="/WEB-INF/components/head.jsp">
        <jsp:param name="title" value="Funny story"/>
    </jsp:include>

    <style>
        #story {
            color: <%= String.format("#%6x", new Random().nextInt((int) Math.pow(16, 6)))  %>;
        }
    </style>

    <script>
      const getNewJoke = () => {
        fetch('https://icanhazdadjoke.com', {headers: {Accept: 'application/json'}})
          .then(res => res.json())
          .then(data => {
            document.querySelector("#story").innerText = data['joke'];
          })
      }
    </script>
</head>
<body>
<p id="story">This is a really funny story...</p>
<button class="btn btn-primary" onclick="getNewJoke()">Get a new joke</button>
</body>
</html>
