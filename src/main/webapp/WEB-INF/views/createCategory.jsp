<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Create New Category</title>
</head>
<body>
    <h1>Create New Category</h1>
    <form:form action="${pageContext.request.contextPath}/administracao/calendar/categories" method="post" modelAttribute="category">
        <label for="name">Category Name:</label>
        <form:input path="name" id="name" required="true"/>
        <br/>
        <label for="duration">Duration (in minutes):</label>
        <form:input path="duration" id="duration" required="true" type="number"/>
        <br/>
        <button type="submit">Add Category</button>
    </form:form>
    <a href="${pageContext.request.contextPath}/administracao/calendar">Back to Calendar</a>
</body>
</html>
