<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/style.css">
        <title>Список сотрудников — ${month} ${year}</title>
    </head>
    <body>
        <div class = "container">
        <b>Список сотрудников — ${month} ${year}</b><br>
        <a href="${pageContext.request.contextPath}/">← Вернуться на главную</a>
        <div class = "table-wrapper">
            <table class = "employee-list-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>ФИО</th>
                        <th>Смена</th>

                        <th>Табельный номер</th>
                        <th>Департамент</th>
                        <th>Позиция</th>
                        
                        
                        <th>Дата рождение</th>
                        <th>Фото</th>
                        <th>Email</th> 
                        <th>Phone</th>                       
                        <th>Стаж работы</th>
                        <th>Договор расторгнут</th>
                        <th>Создан</th>
                        <th>Обновлен</th>
                        <th>isActive</th>
                        <th>Удален</th>
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
                            <td><strong>${emp.tabNumber}</strong></td>
                            <td><strong>${emp.department}</strong></td>
                            <td><strong>${emp.position}</strong></td>
                            
                            
                            <td>${emp.birthDate}</td>
                            <td>${emp.photoPath}</td>
                            <td>
                                <c:forEach items="${emp.email}" var="emp_email">
                                    <c:choose>
                                        <c:when test="${emp_email.main}">
                                            <strong>${emp_email.email} (основной)</strong>
                                        </c:when>
                                        <c:otherwise>
                                            ${emp_email.email}
                                        </c:otherwise>
                                    </c:choose>
                                    <br>
                                </c:forEach>                            
                            </td>
                            <td>${emp.phone}</td>
                            <td>${emp.hireDate}</td>
                            <td>${emp.terminationDate}</td>
                            <td>${emp.createAt}</td>
                            <td>${emp.updatedAt}</td>
                            <td>${emp.active}</td>
                            <td>${emp.deletedAt}</td>
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