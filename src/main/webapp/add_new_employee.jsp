<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.Employee" %>
<%@ page import="java.util.List" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Add New Employee</title>
    </head>
    <body>
        <form method = "post" action ="">
            Фамилия <label><input type="text" name="Фамилия"></label><br>
            Имя <label><input type="text" name="Фамилия"></label><br>
            Отчество <label><input type="text" name="Отчество"></label><br>
            Смена <label><input type="number" name="Смена"></label><br>
            <input type="submit" value="Добавить" name="Добавить"><br>
        </form>
    </body>
</html>