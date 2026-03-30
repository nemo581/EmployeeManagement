<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Редактирование — ${employee.fullName}</title>
        <style>
            :root { --label-w: 180px; --primary: #0d6efd; --border: #e9ecef; --text-muted: #6c757d; }
            body { font-family: 'Segoe UI', system-ui, sans-serif; background: #f4f7f6; color: #212529; padding: 20px; margin: 0; }
            .container { max-width: 1100px; margin: 0 auto; }
            .back-link { display: inline-block; margin-bottom: 20px; color: var(--primary); text-decoration: none; font-weight: 500; }

            .employee-card { background: #fff; border-radius: 12px; box-shadow: 0 4px 20px rgba(0,0,0,0.08); overflow: hidden; }

            /* Шапка */
            .card-header { display: flex; padding: 32px; gap: 32px; background: #fafafa; border-bottom: 1px solid var(--border); align-items: flex-start; }
            .photo-box { width: 150px; height: 150px; border-radius: 12px; object-fit: cover; border: 3px solid #fff; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }
            .photo-placeholder { width: 150px; height: 150px; border-radius: 12px; background: #e9ecef; display: flex; align-items: center; justify-content: center; font-size: 50px; color: #adb5bd; }
            .header-main { flex: 1; }
            .name-title { margin: 0 0 15px 0; font-size: 28px; color: #1a1d20; }

            /* Сетка выравнивания */
            .info-grid { padding: 24px 32px; display: grid; grid-template-columns: 1fr 1fr; gap: 0 60px; }
            .detail-row { display: flex; padding: 12px 0; border-bottom: 1px solid #f8f9fa; align-items: center; }
            .full-width { grid-column: 1 / -1; border-top: 1px solid var(--border); margin-top: 20px; padding-top: 20px; }

            .detail-label { width: var(--label-w); flex-shrink: 0; font-size: 13px; font-weight: 600; color: var(--text-muted); text-transform: uppercase; letter-spacing: 0.5px; }
            .detail-value { flex: 1; font-size: 15px; }

            /* Элементы управления */
            select, input { padding: 8px 12px; border: 1px solid #ced4da; border-radius: 6px; font-size: 14px; width: 100%; max-width: 280px; transition: border-color 0.2s; }
            select:focus, input:focus { border-color: var(--primary); outline: none; box-shadow: 0 0 0 3px rgba(13,110,253,0.1); }
            .editable { border-bottom: 1px dashed var(--primary); cursor: pointer; color: var(--primary); padding: 2px 4px; border-radius: 4px; }
            .editable:hover { background: #f0f7ff; }

            .status-tag { padding: 4px 12px; border-radius: 20px; font-size: 12px; font-weight: 700; }
            .status-active { background: #e6f4ea; color: #1e7e34; }
            .status-inactive { background: #fdf2f2; color: #d32f2f; }

            .dynamic-item { display: flex; align-items: center; gap: 10px; margin-bottom: 10px; }
            .remove-btn { color: #dc3545; cursor: pointer; font-size: 18px; padding: 0 5px; }
            .save-btn { background: #28a745; color: #fff; border: none; padding: 14px 40px; border-radius: 8px; font-weight: 600; cursor: pointer; margin: 30px 32px; transition: 0.2s; }
            .save-btn:hover { background: #218838; transform: translateY(-1px); }

            .dynamic-item input[readonly] { border-color: transparent; background: transparent; cursor: default; padding-left: 0; max-width: 180px; }

            /* Селектор типа в режиме "Текст" */
            .dynamic-item select.locked { border-color: transparent; background: transparent; pointer-events: none; appearance: none; padding-left: 0; width: auto; color: var(--text-muted); }
            .add-link { color: var(--primary); cursor: pointer; font-size: 14px; font-weight: 500; margin-top: 10px; display: inline-block; }
            .edit-icon { margin: 0 10px; cursor: pointer; color: var(--text-muted); font-size: 14px; }
            .edit-icon:hover { color: var(--primary); }

            @media (max-width: 850px) { .info-grid { grid-template-columns: 1fr; } }
        </style>
    </head>
    <body>
        <div class="container">
            <a href="${pageContext.request.contextPath}/" class="back-link">← Вернуться к списку сотрудников</a>
            <form action="${pageContext.request.contextPath}/employee" method="post" id="editForm">
                <input type="hidden" name="employeeId" value="${employee.employeeId}">
                <div class="employee-card">
                    <div class="card-header">
                        <c:choose>
                            <c:when test="${not empty employee.photoPath}">
                                <img src="${pageContext.request.contextPath}${employee.photoPath}" class="photo-box">
                            </c:when>
                            <c:otherwise>
                                <div class="photo-placeholder">?</div>
                            </c:otherwise>
                        </c:choose>

                        <div class="header-main">
                            <h1 class="name-title">${employee.fullName}</h1>
                            <div style="display: flex; gap: 20px; flex-wrap: wrap;">
                                <div>
                                    <span style="display:block; font-size:12px; color:var(--text-muted); margin-bottom:4px;">Департамент</span>
                                    <select name="departmentId">
                                        <option value="">— Выберите —</option>
                                        <c:forEach items="${departments}" var="d">
                                            <option value="${d.id}" <c:if test="${not empty employee.department && employee.department.id == d.id}">selected</c:if>>${d.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div>
                                    <span style="display:block; font-size:12px; color:var(--text-muted); margin-bottom:4px;">Должность</span>
                                    <select name="positionId">
                                        <option value="">— Выберите —</option>
                                        <c:forEach items="${positions}" var="p">
                                            <option value="${p.id}" <c:if test="${not empty employee.position && employee.position.id == p.id}">selected</c:if>>${p.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="info-grid">
                        <!-- Левая колонка -->
                        <div class="col">
                            <div class="detail-row">
                                <div class="detail-label">ID Сотрудника</div>
                                <div class="detail-value"><strong>#${employee.employeeId}</strong></div>
                            </div>
                            <div class="detail-row">
                                <div class="detail-label">Табельный №</div>
                                <div class="detail-value">
                                    <span class="editable" ondblclick="initEdit(this, 'tabNumber', 'text')">
                                        <c:choose><c:when test="${not empty employee.tabNumber}">${employee.tabNumber}</c:when><c:otherwise>не указано</c:otherwise></c:choose>
                                    </span>
                                    <input type="hidden" name="tabNumber" value="${employee.tabNumber}">
                                </div>
                            </div>
                            <div class="detail-row">
                                <div class="detail-label">Дата рождения</div>
                                <div class="detail-value">
                                    <span class="editable" ondblclick="initEdit(this, 'birthDate', 'date')">
                                        <c:choose><c:when test="${not empty employee.birthDate}">${employee.birthDate}</c:when><c:otherwise>выбрать дату</c:otherwise></c:choose>
                                    </span>
                                    <input type="hidden" name="birthDate" value="${employee.birthDate}">
                                </div>
                            </div>
                            <div class="detail-row">
                                <div class="detail-label">Смена</div>
                                <div class="detail-value">
                                    <select name="shift" style="max-width: 120px;">
                                        <option value="1" <c:if test="${employee.shift == '1'}">selected</c:if>>1</option>
                                        <option value="2" <c:if test="${employee.shift == '2'}">selected</c:if>>2</option>
                                        <option value="3" <c:if test="${employee.shift == '3'}">selected</c:if>>3</option>
                                        <option value="2/2" <c:if test="${employee.shift == '2/2'}">selected</c:if>>2/2</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <!-- Правая колонка -->
                        <div class="col">
                            <div class="detail-row">
                                <div class="detail-label">Статус</div>
                                <div class="detail-value">
                                    <c:choose>
                                        <c:when test="${employee.active}"><span class="status-tag status-active">АКТИВЕН</span></c:when>
                                        <c:otherwise><span class="status-tag status-inactive">НЕАКТИВЕН</span></c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="detail-row">
                                <div class="detail-label">Стаж (принят)</div>
                                <div class="detail-value">${not empty employee.hireDate ? employee.hireDate : '—'}</div>
                            </div>
                            <div class="detail-row">
                                <div class="detail-label">Уволен</div>
                                <div class="detail-value">
                                    <c:choose><c:when test="${not empty employee.terminationDate}"><span style="color:#dc3545">${employee.terminationDate}</span></c:when><c:otherwise>—</c:otherwise></c:choose>
                                </div>
                            </div>
                            <div class="detail-row">
                                <div class="detail-label">Обновлено</div>
                                <div class="detail-value" style="color:var(--text-muted); font-size:13px;">${employee.updatedAt}</div>
                            </div>
                        </div>

                        <div class="full-width">
                        <!-- EMAIL -->
                            <div class="detail-row" style="border:0; align-items: flex-start; margin-bottom: 15px;">
                                <div class="detail-label">Email адреса</div>
                                <div class="detail-value">
                                    <div class="items-list">
                                        <c:forEach items="${employee.email}" var="e">
                                            <div class="dynamic-item">
                                                <input type="email" name="emails" value="${e.email}" readonly
                                                onblur="lockContact(this)"
                                                ondblclick="unlockContact(this.parentElement.querySelector('.edit-icon'))"
                                                onkeydown="if(event.key==='Enter'){event.preventDefault(); lockContact(this);}">
                                                <select name="emailsTypes" class="locked">
                                                    <option value="WORK" ${e.type == 'WORK' ? 'selected' : ''}>Раб.</option>
                                                    <option value="PERSONAL" ${e.type == 'PERSONAL' ? 'selected' : ''}>Личн.</option>
                                                </select>
                                                <span class="edit-icon" onclick="unlockContact(this)">✏️</span>
                                                <span class="remove-btn" onclick="this.parentElement.remove()">✕</span>
                                            </div>
                                        </c:forEach>
                                    </div>
                                    <span class="add-link" onclick="addContactRow(this, 'email', 'emails')">+ Добавить email</span>
                                </div>
                            </div>

                        <!-- ТЕЛЕФОНЫ -->
                        <div class="detail-row" style="border:0; align-items: flex-start;">
                            <div class="detail-label">Телефоны</div>
                            <div class="detail-value">
                                <div class="items-list">
                                    <c:forEach items="${employee.phone}" var="p">
                                        <div class="dynamic-item">
                                            <input type="tel" name="phones" value="${p.phone}" readonly
                                            onblur="lockContact(this)"
                                            ondblclick="unlockContact(this.parentElement.querySelector('.edit-icon'))"
                                            onkeydown="if(event.key==='Enter'){event.preventDefault(); lockContact(this);}">
                                            <select name="phonesTypes" class="locked">
                                                <option value="WORK" ${p.type == 'WORK' ? 'selected' : ''}>Раб.</option>
                                                <option value="PERSONAL" ${p.type == 'PERSONAL' ? 'selected' : ''}>Личн.</option>
                                            </select>
                                            <span class="edit-icon" onclick="unlockContact(this)">✏️</span>
                                            <span class="remove-btn" onclick="this.parentElement.remove()">✕</span>
                                        </div>
                                    </c:forEach>
                                </div>
                                <span class="add-link" onclick="addContactRow(this, 'tel', 'phones')">+ Добавить телефон</span>
                            </div>
                        </div>
                    </div>
                    <button type="submit" class="save-btn">Сохранить все изменения</button>
                </div>
            </form>
        </div>
        <script src="static/js/emp_edit.js" defer></script>
    </body>
</html>