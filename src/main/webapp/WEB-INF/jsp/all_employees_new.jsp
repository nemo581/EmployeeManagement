<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>LIST EMPLOYEES — ${month} - ${year}</title>
        <style>
            *,
            *::before, 
            *::after {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                background-color: rgba(11, 53, 159, 1);
                margin: 0px;
                flex-direction: column;
                min-height: 100vh;
                font-family: Arial, Helvetica, sans-serif;
                color: rgba(200, 211, 255, 0.8);
                font-size: 13px;                
                -webkit-font-smoothing: antialiased;
                -moz-osx-font-smoothing: grayscale;
            }

            .top-strip, .bottom-strip {
                /*background-color: rgba(11, 22, 156, 1);*/
                background-color: rgba(2, 37, 125, 1);
                height: 20px;
                width: 100%;
                position: fixed;
                left:0;
                z-index: 1000;
            }

            .top-strip {
                top: 0;
                box-shadow: 0px 0px 15px 5px rgba(57, 244, 0, 0.6);
            }

            .bottom-strip {
                bottom: 0;
                box-shadow: 0px 0px 15px 5px rgba(57, 244, 0, 0.6);
            }

            .content {
                padding: 35px 15px;
                flex-direction: column;
                flex: 1;
            }
        </style>
    </head>
    <body>
        <header class="top-strip"></header>
        <main class="content">
            <b>Список сотрудников — ${year}—${month}</b><br>
            <table>
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
                            <td><strong>${emp.employeeId}</strong></td>
                            <td>
                                <c:url var="empInfo" value="/employee">
                                    <c:param name="id" value="${emp.employeeId}"/>
                                    <c:param name="action" value="info"/>
                                </c:url>
                                <a href="${empInfo}" class="employee-link">
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
                            <td>
                                <c:forEach items="${emp.phone}" var="emp_phone">
                                    <c:choose>
                                        <c:when test="${emp_phone.main}">
                                            <strong>${emp_phone.phone} (основной)</strong>
                                        </c:when>
                                        <c:otherwise>
                                            ${emp_phone.phone}
                                        </c:otherwise>
                                    </c:choose>
                                    <br>
                                </c:forEach>
                            </td>
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
        </main>
        <footer class="bottom-strip">
            Всего сотрудников: ${employees.size()}
        </footer>
    </body>
</html>