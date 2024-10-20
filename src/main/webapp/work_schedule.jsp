<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.Employee" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.LinkedHashMap" %>


<html>
<head>
    <meta charset="UTF-8">
    <title>Work-Schedule</title>
</head>
<body>
<form method"post" action="/my-app/">
    <button type="submit">Главная</button>
</form>
<% int count = 1; %>
<% List<Employee> employee_list = (List) request.getAttribute("employee_list"); %>
<b><%= employee_list.get(0).getMonth() %> <%= employee_list.get(0).getYear() %></b>
<p>
<table>
    <thead>
    <tr>
        <th width="20" align="center">№</th>
        <th width="20"></th>
        <th align="left">Ф.И.О.</th>
        <% for(Map.Entry<LocalDate, Integer> d : employee_list.get(0).getEmployeeWorkDays().entrySet()) { %>
            <th>
            <% if((d.getKey().getDayOfWeek().getValue()) == 6 ||
                  (d.getKey().getDayOfWeek().getValue()) == 7) { %>
                <font color = "#ff0000"><%= d.getKey().getDayOfMonth() %> </font>
            <% } else { %>
                <%= d.getKey().getDayOfMonth() %>
            <% } %>
            </th>
        <% } %>
    </tr>
    </thead>
    <tbody>
    <% for (Employee employee : employee_list) { %>
    <tr>
        <td width="20" align="center"><%= count %></td>
        <td width="20" align="center"><%= employee.getShift()%></td>
        <td><%= employee.getFirstName()%>
            <%= employee.getLustName()%>
            <%= employee.getFatherName()%>
        </td>
        <% for (Map.Entry<LocalDate, Integer> sh : employee.getEmployeeWorkDays().entrySet()) { %>
        <td>
            <% if(sh.getValue() == 1) { %>
                <font color = "#ff0000"><%= sh.getKey().getDayOfMonth() %></font>
            <% } else if (sh.getValue() == 0) { %>
                <%= sh.getKey().getDayOfMonth() %>
            <% } %>
        </td>
        <% } %>
    </tr>
    <% count++; %>
    <% } %>
    </tbody>
</table>
</body>
</html>