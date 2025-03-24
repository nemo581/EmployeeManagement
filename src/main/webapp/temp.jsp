<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.Employee" %>
<%@ page import="java.util.List" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>All-Employees-List</title>
</head>
<body>
<form method"post" action="/simple/">
    <button type="submit">Главная</button>
</form>
<% int count=0; %>
<% List<Employee> employee_list = (List) request.getAttribute("employee_list"); %>
<% for (Employee employee : employee_list) { %>
        <li>{<%= count+=1 %>}
            <%= employee %>

        </li>
    <% } %>

</body>
</html>