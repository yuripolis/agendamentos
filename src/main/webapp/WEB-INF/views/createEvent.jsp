<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Create Event</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: center;
        }
    </style>
</head>
<body>
    <h1>Create Event for ${date}</h1>
    <form action="${pageContext.request.contextPath}/administracao/calendar/events" method="post">
    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="date" value="${date}" />
        <input type="text" name="title" placeholder="Event Title" required />
        <select name="categoryId" required>
            <option value="">Select Category</option>
            <c:forEach var="category" items="${categories}">
                <option value="${category.id}">${category.name}</option>
            </c:forEach>
        </select>
        <table>
            <thead>
                <tr>
                    <th>Time Slot</th>
                    <th>Select</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="hour" begin="9" end="17">
                    <c:forEach var="minute" begin="0" end="30" step="30">
                        <c:set var="timeSlot" value="${hour < 10 ? '0' : ''}${hour}:${minute == 0 ? '00' : minute}" />
                        <c:set var="isAvailable" value="true" />
                        <c:forEach var="event" items="${events}">
                            <c:if test="${timeSlot >= event.startTime.toString() && timeSlot < event.endTime.toString()}">
                                <c:set var="isAvailable" value="false" />
                            </c:if>
                        </c:forEach>
                        <c:if test="${isAvailable}">
                            <tr>
                                <td>${timeSlot}</td>
                                <td>
                                    <input type="radio" name="startTime" value="${timeSlot}" required />
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </c:forEach>
            </tbody>
        </table>
        <button type="submit">Add Event</button>
    </form>
    <a href="${pageContext.request.contextPath}/administracao/calendar">Back to Calendar</a>
</body>
</html>
