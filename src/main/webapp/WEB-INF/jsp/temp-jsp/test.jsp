<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Обновленный URI для Jakarta EE (Tomcat 10/11) --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Редактирование — ${employee.fullName}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    <style>
        /* Дополнительные стили для управления списками */
        .dynamic-row { display: flex; gap: 8px; margin-bottom: 5px; align-items: center; }
        .btn-del { color: #dc3545; cursor: pointer; font-weight: bold; }
        .btn-add { color: #0d6efd; cursor: pointer; font-size: 12px; display: inline-block; margin-top: 5px; }
        select, input, input[type="date"], input[type="tel"], input[type="email"] {
            padding: 6px; border: 1px solid #ccc; border-radius: 4px; width: 100%;
        }
        .save-bar { background: #f8f9fa; padding: 20px; text-align: right; border-top: 1px solid #eee; }
        .btn-submit { background: #28a745; color: white; border: none; padding: 10px 25px; border-radius: 4px; cursor: pointer; }
    </style>
</head>
<body>
<div class="container">
    <a href="${pageContext.request.contextPath}/employees" class="back-link">← К списку</a>

    <form action="${pageContext.request.contextPath}/employee/update" method="post">
        <%-- Скрытое ID для поиска сотрудника в БД --%>
        <input type="hidden" name="id" value="${employee.employeeId}">

        <div class="employee-card">
            <div class="employee-top">
                <div class="employee-main-info">
                    <h1 class="employee-name">ФИО: ${employee.fullName}</h1>

                    <div class="detail-row">
                        <div class="detail-label">Позиция</div>
                        <div class="detail-value">
                            <select name="positionId">
                                <option>position-1</option>
                                <option>position-2</option>
                                <option>position-3</option>
                            </select>
                        </div>
                    </div>

                    <div class="detail-row">
                        <div class="detail-label">Департамент</div>
                        <div class="detail-value">
                            <select name="departmentId">
                                <option>dep-1</option>
                                <option>dep-2</option>
                                <option>dep-3</option>
                            </select>
                        </div>
                    </div>

                    <div class="detail-row">
                        <div class="detail-label">Смена</div>
                        <div class="detail-value">
                            <select name="shiftId">
                                <option>1</option>
                                <option>2</option>
                                <option>3</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>

            <div class="employee-grid">
                <%-- Дата рождения --%>
                <div class="detail-row">
                    <div class="detail-label">Дата рождения</div>
                    <div class="detail-value">
                        <input type="date" name="birthDate" value="${employee.birthDate}">
                    </div>
                </div>

                <%-- Динамический список Email --%>
                <div class="detail-row">
                    <div class="detail-label">Email адреса</div>
                    <div class="detail-value" id="email-container">
                        <c:forEach items="${employee.emails}" var="mail">
                            <div class="dynamic-row">
                                <input type="email" name="emails" value="${mail.email}">
                                <span class="btn-del" onclick="this.parentElement.remove()">×</span>
                            </div>
                        </c:forEach>
                        <span class="btn-add" onclick="addField('email-container', 'emails', 'email')">+ Добавить email</span>
                    </div>
                </div>

                <%-- Динамический список Телефонов --%>
                <div class="detail-row">
                    <div class="detail-label">Телефоны</div>
                    <div class="detail-value" id="phone-container">
                        <c:forEach items="${employee.phones}" var="ph">
                            <div class="dynamic-row">
                                <input type="tel" name="phones" value="${ph.number}">
                                <span class="btn-del" onclick="this.parentElement.remove()">×</span>
                            </div>
                        </c:forEach>
                        <span class="btn-add" onclick="addField('phone-container', 'phones', 'tel')">+ Добавить телефон</span>
                    </div>
                </div>
            </div>

            <div class="save-bar">
                <button type="submit" class="btn-submit">Сохранить изменения</button>
            </div>
        </div>
    </form>
</div>

<script>
    // Функция для добавления новых полей ввода
    function addField(containerId, name, type) {
        const container = document.getElementById(containerId);
        const div = document.createElement('div');
        div.className = 'dynamic-row';
        div.innerHTML = `
            <input type="${type}" name="${name}" value="">
            <span class="btn-del" onclick="this.parentElement.remove()">×</span>
        `;
        container.insertBefore(div, container.lastElementChild);
    }
</script>
</body>
</html>
