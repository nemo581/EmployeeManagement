<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.Employee" %>
<%@ page import="java.util.List" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>temp-jsp</title>
    </head>
    <body>
        <table>
            <thead>
                <b>Опции:</b>
            </thead>
            <tbody>
                <tr>
                    <td>
                        <form method"post" action="/simple/employees_list">
                            <button type="submit">Список сотрудников</button>
                        </form>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form method"post" action="/simple/editing_data">
                            <button type="submit">Добавить сотрудника</button>
                        </form>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form method"post" action="/simple/work_schedule">
                            <button type="submit">Расписание на месяц</button>
                        </form>
                    </td>
                </tr>
            </tbody>
        </table>
    </body>
</html>