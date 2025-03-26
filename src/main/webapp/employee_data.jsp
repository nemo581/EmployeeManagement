<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.Employee" %>
<%@ page import="java.util.List" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Employees-Data</title>
    </head>
    <body>
        <form method="post" action="/simple">
            <button type="submit">Главная</button>
        </form>

        <form method="get" action="/simple/employees">
            <button type="submit">Вернуться</button>
        </form>

        <% Employee employee = (Employee) request.getAttribute("employee"); %>

        <table style="background-color:rgba(2, 233, 233, 0.438)">        
            <tr>
                <th>Id</th>
                <th>Ф.И.О.</th>
                <th>Табельный номер</th>
                <th>Смена</th>
                <th>Телефон</th>
                <th>Email</th>
            </tr>
            <tr>
                <th><% out.print(employee.getId()); %></th> 
                <td><% out.print(employee.getLastName() + " " + employee.getFirstName() + " " + employee.getFatherName()); %></td> 
                <td><% out.print(employee.getServiceNumber()); %></td>
                <td><% out.print(employee.getShift()); %> </td>
                <td><% out.print(employee.getPhoneNumbers()); %></td>
                <td><% out.print(employee.getEmail()); %></td>
            </tr>
        </table>
    </body>
</html>