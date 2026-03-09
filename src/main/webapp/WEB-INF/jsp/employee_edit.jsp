<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Рдактирование — ${employee.fullName}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    <style>
        .employee-card {
            max-width: 1100px;
            margin: 0 auto 30px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            overflow: hidden;
        }

        .employee-top {
            display: flex;
            padding: 24px 32px 0 32px;
            gap: 32px;
        }

        .employee-photo-wrapper {
            flex-shrink: 0;
        }

        .employee-photo {
            width: 160px;
            height: 160px;
            border-radius: 10px;
            object-fit: cover;
            border: 3px solid #fff;
            box-shadow: 0 3px 10px rgba(0,0,0,0.08);
        }

        .employee-photo-placeholder {
            width: 160px;
            height: 160px;
            border-radius: 10px;
            background: #e9ecef;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 64px;
            color: #adb5bd;
        }

        .employee-main-info {
            flex: 1;
            padding-top: 8px;
        }

        .employee-name {
            margin: 0 0 6px 0;
            font-size: 26px;
            line-height: 1.2;
        }

        .employee-position {
            margin: 0 0 4px 0;
            font-size: 17px;
            color: #495057;
        }

        .employee-department-shift {
            color: #6c757d;
            font-size: 14px;
        }

        .employee-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
            gap: 0;
            padding: 0 32px 24px 32px;
        }

        .detail-row {
            display: flex;
            padding: 10px 0;
            border-bottom: 1px solid #f0f0f0;
            gap: 16px;
        }

        .detail-row:last-child {
            border-bottom: none;
        }

        .detail-label {
            width: 160px;
            flex-shrink: 0;
            font-weight: 600;
            color: #495057;
            font-size: 13px;
        }

        .detail-value {
            flex: 1;
            color: #212529;
            font-size: 13px;
            line-height: 1.45;
        }

        .detail-value input {
            width: 80%;        /* Занимает всю ширину родительского блока */
            box-sizing: border-box; /* Учитывает внутренние отступы в ширине */
            padding: 4px 8px;   /* Чтобы текст не прилипал к краям */
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .status-active  { color: #28a745; font-weight: 600; }
        .status-inactive{ color: #dc3545; font-weight: 600; }
        .status-terminated { color: #6c757d; font-style: italic; }

        .back-link {
            display: inline-block;
            margin: 20px 0 24px;
            color: #0d6efd;
            font-weight: 500;
            text-decoration: none;
        }

        .back-link:hover { text-decoration: underline; }
    </style>
</head>
<body>

    <div class="container" style="padding-top: 0;">
        <a href="${pageContext.request.contextPath}/" class="back-link">← Вернуться к списку</a>

        <div class="employee-card">
            <div class="employee-top">
                <div class="employee-photo-wrapper">
                    <c:choose>
                        <c:when test="${not empty employee.photoPath}">
                            <img src="${pageContext.request.contextPath}${employee.photoPath}"
                                 alt="${employee.fullName}"
                                 class="employee-photo">
                        </c:when>
                        <c:otherwise>
                            <div class="employee-photo-placeholder">?</div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="employee-main-info">
                    <h1 class="employee-name">ФИО: ${employee.fullName}</h1>
                    <div class="employee-position">Позиция: 
                        <c:choose>
                        <c:when test="${param.edit eq 'true'}">
                            <select name="position">
                                <option value="">Не указано</option>
                                <!-- Здесь можно вывести список из сервлета или статически -->
                                <option value="Менеджер" ${employee.position == 'Менеджер' ? 'selected' : ''}>Менеджер</option>
                                <option value="Разработчик" ${employee.position == 'Разработчик' ? 'selected' : ''}>Разработчик</option>
                                <option value="Аналитик" ${employee.position == 'Аналитик' ? 'selected' : ''}>Аналитик</option>
                                <!-- добавь остальные -->
                            </select>
                        </c:when>
                        <c:otherwise>
                            ${employee.position != null ? employee.position : 'Не указано'}
                        </c:otherwise>
                    </c:choose>
                    </div>
                    <div class="employee-department-shift">
                        Департамент: 
                        <c:choose>
                        <c:when test="${param.edit eq 'true'}">
                            <select name="department">
                                <option value="">Не указано</option>
                                <!-- Аналогично — список департаментов -->
                                <option value="Продажи" ${employee.department == 'Продажи' ? 'selected' : ''}>Продажи</option>
                                <option value="Разработка" ${employee.department == 'Разработка' ? 'selected' : ''}>Разработка</option>
                                <!-- добавь остальные -->
                            </select>
                        </c:when>
                        <c:otherwise>
                            ${employee.department != null ? employee.department : 'Не указано'}
                        </c:otherwise>
                    </c:choose>
                    • Смена:
                    <c:choose>
                        <c:when test="${param.edit eq 'true'}">
                            <select name="shift">
                                <option value="">Не указано</option>
                                <option value="1" ${employee.shift == '1' ? 'selected' : ''}>1</option>
                                <option value="2" ${employee.shift == '2' ? 'selected' : ''}>2</option>
                                <option value="3" ${employee.shift == '3' ? 'selected' : ''}>3</option>
                            </select>
                        </c:when>
                        <c:otherwise>
                            ${employee.shift != null ? employee.shift : 'Не указано'}
                        </c:otherwise>
                    </c:choose>
                    </div>
                </div>
            </div>

            <div class="employee-grid">
                <div class="detail-row">
                    <div class="detail-label">ID</div>
                    <div class="detail-value"><strong>${employee.employeeId != null ? employee.employeeId : 'null'}</strong></div>
                </div>
                <div class="detail-row">
                    <div class="detail-label">Табельный номер</div>
                    <div class="detail-value">
                         <c:choose>
                            <c:when test="${param.edit eq 'true'}">
                                <input type="text" name="tabNumber" value="${employee.tabNumber}" 
                                placeholder="не указано">
                            </c:when>
                            <c:otherwise>
                                ${not empty employee.tabNumber ? employee.tabNumber : 'не указано'}
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="detail-row">
                    <div class="detail-label">Дата рождения</div>
                    <div class="detail-value">${employee.birthDate != null ? employee.birtDate : 'null'}</div>
                </div>
                <div class="detail-row">
                    <div class="detail-label">Email</div>
                    <div class="detail-value">
                        <c:forEach items="${employee.email}" var="e" varStatus="loop">
                            <c:choose>
                                <c:when test="${e.main}"><strong>${e.email} (основной)</strong></c:when>
                                <c:otherwise>${e.email}</c:otherwise>
                            </c:choose>
                            <c:if test="${!loop.last}">, </c:if>
                        </c:forEach>
                    </div>
                </div>
                <div class="detail-row">
                    <div class="detail-label">Телефон</div>
                    <div class="detail-value">${employee.phone != null ? employee.phone : 'null'}</div>
                </div>
                <div class="detail-row">
                    <div class="detail-label">Стаж работы</div>
                    <div class="detail-value">${employee.hireDate != null ? employee.hireDate : 'null'}</div>
                </div>
                <div class="detail-row">
                    <div class="detail-label">Договор расторгнут</div>
                    <div class="detail-value">
                        <c:choose>
                            <c:when test="${not empty employee.terminationDate}">
                                <span class="status-terminated">${employee.terminationDate != null ? employee.terminationDate : 'null'}</span>
                            </c:when>
                            <c:otherwise>—</c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="detail-row">
                    <div class="detail-label">Статус</div>
                    <div class="detail-value">
                        <c:choose>
                            <c:when test="${employee.active}">
                                <span class="status-active">Активен</span>
                            </c:when>
                            <c:otherwise>
                                <span class="status-inactive">Неактивен</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="detail-row">
                    <div class="detail-label">Создан</div>
                    <div class="detail-value">${employee.createAt}</div>
                </div>
                <div class="detail-row">
                    <div class="detail-label">Обновлён</div>
                    <div class="detail-value">${employee.updatedAt}</div>
                </div>
                <div class="detail-row">
                    <div class="detail-label">Удалён</div>
                    <div class="detail-value">
                        <c:choose>
                            <c:when test="${not empty employee.deletedAt}">
                                <span style="color:#dc3545;">${employee.deletedAt}</span>
                            </c:when>
                            <c:otherwise>—</c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>