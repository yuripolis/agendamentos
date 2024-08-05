<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Events</title>
</head>
<body>
    <h1>Events on ${date}</h1>
    <ul>
        <c:forEach items="${events}" var="event">
            <li>${event.title} - ${event.startTime} to ${event.endTime}</li>
        </c:forEach>
    </ul>
    <a href="${pageContext.request.contextPath}/administracao/calendar">Back to Calendar</a>
</body>
</html>
