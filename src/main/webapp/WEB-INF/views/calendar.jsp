<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="calendario.agendamentos.event.StringUtils" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Calendar</title>
</head>
<body>
    <h1>Calendar</h1>
    <table border="1">
        <thead>
            <tr>
                <th>Sunday</th>
                <th>Monday</th>
                <th>Tuesday</th>
                <th>Wednesday</th>
                <th>Thursday</th>
                <th>Friday</th>
                <th>Saturday</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="i" begin="1" end="${daysInMonth}">
                <c:choose>
                    <c:when test="${i % 7 == 1}">
                        <tr>
                    </c:when>
                </c:choose>
                <td>
                    <a href="${pageContext.request.contextPath}/administracao/calendar/events/new/${currentYear}-${currentMonth}-${StringUtils.padLeft(i, 2, '0')}">
                        ${i}
                    </a>
                </td>
                <c:choose>
                    <c:when test="${i % 7 == 0}">
                        </tr>
                    </c:when>
                </c:choose>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
