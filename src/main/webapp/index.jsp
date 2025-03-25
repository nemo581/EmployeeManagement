<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.Employee" %>
<%@ page import="java.util.List" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>INDEX-JSP</title>
    </head>
    <body>
        <table>
            <thead>
                <b>Опции:</b>
            </thead>
            <tbody>
                <tr>
                    <td>
                        <form method="get" action="/simple/employees">
                            <button type="submit">Список всех сотрудников</button>
                        </form>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form method="get" action="/simple/editing_data">
                            <button type="submit">Добавить нового сотрудника</button>
                        </form>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form method="get" action="/simple/work_schedule">
                            <button type="submit">Расписание на месяц</button>
                        </form>
                    </td>
                </tr>
            </tbody>
        </table>
    </body>
</html>