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

    <form method="post" action="${pageContext.request.contextPath}/voting/vote?pollId=${param.get("pollId")}">
        <ol>
            <jsp:useBean id="pollOptions" scope="request"
                         type="java.util.List<hr.fer.oprpp2.model.PollOption>"/>
            <c:forEach var="pollOption" items="${pollOptions}">
                <li>
                    <label>
                        <input type="checkbox" name="voteIds" value="${pollOption.getId()}"/>
                    </label>

                        ${pollOption.getTitle()}
                    <a class="btn btn-sm btn-primary mb-2 text-white"
                       href="<c:url value='/voting/vote?id=${pollOption.getId()}&pollId=${param.get("pollId")}'/>">
                        Like
                    </a>
                    <a class="btn btn-sm btn-danger mb-2 text-white"
                       href="<c:url value='/voting/vote?id=${pollOption.getId()}&pollId=${param.get("pollId")}&dislike=true'/>">
                        Dislike
                    </a>
                </li>
            </c:forEach>
        </ol>

        <input type="submit" data-action="Like" value="Like for 0" class="btn btn-primary"/>
        <input type="submit" data-action="Dislike" value="Dislike for 0" class="btn btn-danger"
               formaction="${pageContext.request.contextPath}/voting/vote?pollId=${param.get("pollId")}&dislike=true"
        />
    </form>
</div>

<script>
  const COUNT_ALL = ${pollOptions.size()};
  const getVotes = () => {
    return Array.from(document.querySelectorAll('input[type="checkbox"]')).reduce((c, a) => a.checked ? c + 1 : c, 0);
  }

  const button = document.querySelectorAll('input[type="submit"]');
  const selectAll = () => {
    const select = getVotes() !== COUNT_ALL;
    document.querySelectorAll('input[type="checkbox"]').forEach(input => {
      input.checked = select;
    });

    button.forEach(btn => btn.value = btn.dataset.action + " for " + getVotes().toString());
  }

  document.querySelectorAll('input[type="checkbox"]').forEach(input => {
    input.addEventListener("change", () => {
      button.forEach(btn => btn.value = btn.dataset.action + " for " + getVotes().toString());
    });
  });
</script>
</body>
</html>
