<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.Employee" %>
<%@ page import="java.util.List" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>All-Employees-List</title>
    </head>
    <body>
        <form method="get" action="/simple">
            <button type="submit">Главная</button>
        </form>        
        <% List<Employee> employee_list = (List) request.getAttribute("employee_list"); %>
        <table style="background-color:rgba(2, 233, 233, 0.438)">        
            <tr>
                <th>Id</th>
                <th>Ф.И.О.</th>
                <th>Табельный номер</th>
                <th>Смена</th>
                <th>Телефон</th>
                <th>Email</th>
            </tr>    
            <% for (Employee employee : employee_list) { %> 
                <tr>
                    <td><% out.print(employee.getId()); %></td> 
                    <td>
                        <a href="/simple/employee_data?id=<%= employee.getId() %>" style="color: black;"><% out.print(employee.getLastName() + " " + employee.getFirstName() + " " + employee.getFatherName()); %></a>
                    </td> 
                    <td><a href="" style="color: black;"><% out.print(employee.getServiceNumber()); %></a></td>
                    <td><% out.print(employee.getShift()); %> </td>
                    <td><% out.print(employee.getPhoneNumbers()); %></td>
                    <td><% out.print(employee.getEmail()); %></td>
                </tr>
            <% }; %>    
        </table>
    </body>
</html>