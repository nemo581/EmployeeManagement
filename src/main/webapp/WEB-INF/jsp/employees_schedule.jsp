<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/style.css">
        <title>{test_2.jsp} Расписание — ${month} ${year}</title>
    </head>
    <body>
        <div class = "container">
            <b>Расписание — ${month} ${year}</b><br>
            <c:set var="daysCount" value="${fn:length(matrix_days)}"/>
            <p>В месяце ${daysInMonth} дней</p>
            <a href="${pageContext.request.contextPath}/">← Вернуться на главную</a>
            <div class = "table-wrapper">
                <table class="schedule-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>ФИО</th>
                            <th>Смена</th>
                            <c:forEach var="row" items="${matrix_days}" varStatus="rowIdx">
                                <th>
                                    <c:forEach var="call" items="${row}" varStatus="colIdx">
                                        ${call}
                                    </c:forEach>
                                </th>
                            </c:forEach>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${employees}" var="emp">
                            <tr>
                                <td class="employee-info"><strong>${emp.employeeId}</strong></td>
                                <td class="employee-info">
                                    <c:url var="employeeUrl" value="/employee/${emp.employeeId}" />
                                    <a href="${employeeUrl}" class="employee-link">
                                        <strong>${emp.fullName}</strong>
                                    </a>
                                </td>
                                <td><strong>${emp.shift}</strong></td>
                                <c:forEach begin="1" end="${fn:length(matrix_days)}"> <!-- Динамически по длине matrix_days -->
                                    <td><!-- Здесь статус присутствия сотрудника в этот день --></td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty employees}">
                            <tr>
                                <td colspan="3" style="text-align:center; color:#999; padding:30px;">
                                    Список сотрудников пуст
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>