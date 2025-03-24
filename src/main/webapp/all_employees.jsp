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
<% int count = 1; %>
<% List<Employee> employee_list = (List) request.getAttribute("employee_list"); %>
<table>
    <thead>
        <tr>
            <th width="20" align="center">№</th>
            <th width="20"></th>
            <th align="left">Ф.И.О.</th>
        </tr>
    </thead>
    <tbody>
    <% for (Employee employee : employee_list) { %>
    <tr>
        <td width="20" align="center"><%= count %></td>
        <td width="20" align="center"><%= employee.getShift()%></td>
        <td><%= employee.getFirstName()%>
            <%= employee.getLustName()%>
            <%= employee.getPatronymicName()%>
        </td>
    </tr>
    <% count++; %>
    <% } %>
    </tbody>
</table>
</body>
</html>