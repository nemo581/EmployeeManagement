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
                display: flex;
                align-items: center;

                justify-content: space-between;
                padding: 0 15px;
                color: rgba(200, 211, 255, 0.8);
                font-size: 12px;
                font-family: sans-serif;
                box-sizing: border-box;

            }

            .bottom-strip {
                bottom: 0;
                box-shadow: 0px 0px 15px 5px rgba(57, 244, 0, 0.6);
                display: flex;
                align-items: center;
                justify-content: flex-end;
                padding-right: 15px;
                color: rgba(200, 211, 255, 0.8);
                font-size: 12px;
                font-family: sans-serif;
                box-sizing: border-box;
            }

            .top-menu a {
                color: rgba(200, 211, 255, 0.8);
                text-decoration: none;
                margin-right: 15px;
            }

            .top-menu a:hover {
                color: #39f400;
            }

            .content {
                padding: 35px 15px;
                flex-direction: column;
                flex: 1;
            }

            .table-position {
                display: flex;
                align-items: center;     /* Центрирует по вертикали */
                min-height: 100vh;       /* Растягивает body на всю высоту экрана */                
            }

            .employee-list-table {
                /*width: 1500px;
                min-width: 1500px;*/
                table-layout: auto;
                margin: auto;
                border-collapse: collapse;
            }

            .employee-list-table th {
                position: sticky;
                top: 17px;
                z-index: 10;
                box-shadow: 0 2px 2px -1px rgba(0, 0, 0, 0.4);
            }

            table, th, td {
                border: 1px solid rgba(200, 211, 255, 0.15);
                border-collapse: collapse;
                padding: 8px 12px;
                vertical-align: top;
            }
            
            td { vertical-align: top; }

            th {
                background-color: rgba(2, 37, 125, 1);
                text-trasform: uppercase;
                letter-spacing: 1px;
            }
            /* ID */
            .employee-list-table th:nth-child(1)    { min-width: 34px; text-align: center; vertical-align: middle; }
            .employee-list-table td:nth-child(1)    { min-width: 34px; text-align: center; }
            /* ФИО */
            .employee-list-table th:nth-child(2)    { min-width: 50px; text-align: center; vertical-align: middle; }
            .employee-list-table td:nth-child(2)    { min-width: 50px; white-space: nowrap; }
            /* shift */
            .employee-list-table th:nth-child(3)    { min-width: 60px; text-align: center; vertical-align: middle; }
            .employee-list-table td:nth-child(3)    { min-width: 60px; text-align: center; }
            /* tab_numb */
            .employee-list-table th:nth-child(4)    { min-width: 85px; text-align: center; vertical-align: middle; }
            .employee-list-table td:nth-child(4)    { min-width: 85px; text-align: center; }
            /* department */
            .employee-list-table th:nth-child(5)    { min-width: 98px; text-align: center; vertical-align: middle; }
            .employee-list-table td:nth-child(5)    { min-width: 98px; text-align: center; }
            /* Position */
            .employee-list-table th:nth-child(6)    { min-width: 70px; text-align: center; vertical-align: middle; }
            .employee-list-table td:nth-child(6)    { min-width: 70px; text-align: center;}
            /* birthday */
            .employee-list-table th:nth-child(7)    { min-width: 83px; text-align: center; vertical-align: middle; }
            .employee-list-table td:nth-child(7)    { min-width: 83px; text-align: center;}
            /* photo */
            .employee-list-table th:nth-child(8)    { min-width: 52px; text-align: center; vertical-align: middle; }
            .employee-list-table td:nth-child(8)    { min-width: 52px; text-align: center;}
            /* email */
            .employee-list-table th:nth-child(9)    { min-width: 53px; text-align: center; vertical-align: middle; }
            .employee-list-table td:nth-child(9)    { min-width: 53px; text-align: center;}
            /* phone */
            .employee-list-table th:nth-child(10)   { min-width: 74px; text-align: center; vertical-align: middle; }
            .employee-list-table td:nth-child(10)   { min-width: 74px; text-align: center;}
            /* hireDate */
            .employee-list-table th:nth-child(11)   { min-width: 64px; text-align: center; vertical-align: middle; }
            .employee-list-table td:nth-child(11)   { min-width: 64px; text-align: center;}
            /* terminationDate */
            .employee-list-table th:nth-child(12)   { min-width: 84px; text-align: center; vertical-align: middle; }
            .employee-list-table td:nth-child(12)   { min-width: 84px; text-align: center;}
            /* created */
            .employee-list-table th:nth-child(13)   { min-width: 65px; text-align: center; vertical-align: middle; }
            .employee-list-table td:nth-child(13)   { min-width: 65px; text-align: center;}
            /* updated */
            .employee-list-table th:nth-child(14)   { min-width: 79px; text-align: center; vertical-align: middle; }
            .employee-list-table td:nth-child(14)   { min-width: 79px; text-align: center;}
            /* active */
            .employee-list-table th:nth-child(15)   { min-width: 64px; text-align: center; vertical-align: middle; }
            .employee-list-table td:nth-child(15)   { min-width: 64px; text-align: center;}
            /* deleted */
            .employee-list-table th:nth-child(16)   { min-width: 64px; text-align: center; vertical-align: middle; }
            .employee-list-table td:nth-child(16)   { min-width: 64px; text-align: center;}
        </style>
    </head>
    <body>
        <header class="top-strip">
            <nav class="top-menu">
                <a href="${pageContext.request.contextPath}/">Главная</a>
                <a href="#">...</a>
            </nav>
            <div>
                Всего сотрудников: ${employees.size()} || LIST EMPLOYEES — ${month} — ${year}
            </div>
        </header>
        <main class="content">
            <div class = "table-position">
                <table class = "employee-list-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>ФИО</th>
                            <th>Смена</th>
                            <th>Табельный<br>номер</th>
                            <th>Департамент</th>
                            <th>Позиция</th>                       
                            <th>Дата рождения</th>
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
                                <td class="employeeInfo"><strong>${emp.employeeId}</strong></td>
                                <td class="employee-info">
                                    <!--<c:url var="employeeUrl" value="/employee/${emp.employeeId}" /> -->
                                    <c:url var="empInfo" value="/employee">
                                        <c:param name="id" value="${emp.employeeId}"/>
                                        <c:param name="action" value="info"/>
                                    </c:url>
                                    <a href="${empInfo}" class="employee-link" style = "text-decoration: none; color: rgba(200, 211, 255, 0.8);">
                                        <strong>${emp.lastName} ${emp.firstName}<br>${emp.middleName}</strong>
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
            </div>
        </main>
        <footer class="bottom-strip">
            ${session} - [ referer: ${referer} ] :: [ method: ${method} ] :: [ locale: ${local} ] -- ${protocol}://${ip}:${serverPort}${reqUri}
        </footer>
    </body>
</html>