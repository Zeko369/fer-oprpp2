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
<jsp:include page="/WEB-INF/components/navbar.jsp">
    <jsp:param name="page" value="voting"/>
</jsp:include>

<div class="content">
    <h1>Vote for your favourite band</h1>
    <p>Clicking on the link will vote</p>

    <button class="btn-secondary btn btn-sm" onclick="selectAll()">Select all</button>

    <form method="post" action="${pageContext.request.contextPath}/voting/vote">
        <ol>
            <jsp:useBean id="voteOptions" scope="request"
                         type="java.util.List<hr.fer.oprpp2.services.votesDB.VoteOption>"/>
            <c:forEach var="voteOption" items="${voteOptions}">
                <li>
                    <label>
                        <input type="checkbox" name="voteIds" value="${voteOption.id()}"/>
                    </label>

                    <a href="<c:url value="/voting/vote?id=${voteOption.id()}"/>">
                            ${voteOption.name()}
                    </a>
                </li>
            </c:forEach>
        </ol>

        <input type="submit" value="Vote for 0" class="btn btn-primary"/>
    </form>
</div>

<script>
  const COUNT_ALL = ${voteOptions.size()};
  const getVotes = () => {
    return Array.from(document.querySelectorAll('input[type="checkbox"]')).reduce((c, a) => a.checked ? c + 1 : c, 0);
  }

  const button = document.querySelector('input[type="submit"]');
  const selectAll = () => {
    const select = getVotes() !== COUNT_ALL;
    document.querySelectorAll('input[type="checkbox"]').forEach(input => {
      input.checked = select;
    });

    button.value = "Vote for " + getVotes().toString();
  }

  document.querySelectorAll('input[type="checkbox"]').forEach(input => {
    input.addEventListener("change", () => {
      button.value = "Vote for " + getVotes().toString();
    });
  });
</script>
</body>
</html>
