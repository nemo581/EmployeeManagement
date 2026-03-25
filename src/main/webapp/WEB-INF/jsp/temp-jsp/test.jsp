<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Редактирование — ${employee.fullName}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    <style>
        .employee-card { max-width: 1100px; margin: 0 auto 30px; background: #fff; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); overflow: hidden; }
        .employee-top { display: flex; padding: 24px 32px 0 32px; gap: 32px; }
        .employee-photo-wrapper { flex-shrink: 0; }
        .employee-photo { width: 160px; height: 160px; border-radius: 10px; object-fit: cover; border: 3px solid #fff; box-shadow: 0 3px 10px rgba(0,0,0,0.08); }
        .employee-photo-placeholder { width: 160px; height: 160px; border-radius: 10px; background: #e9ecef; display: flex; align-items: center; justify-content: center; font-size: 64px; color: #adb5bd; }
        .employee-main-info { flex: 1; padding-top: 8px; }
        .employee-name { margin: 0 0 6px 0; font-size: 26px; line-height: 1.2; }
        .employee-department, .employee-position-shift { margin-bottom: 10px; font-size: 14px; color: #495057; }
        
        .employee-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(320px, 1fr)); gap: 0; padding: 0 32px 24px 32px; }
        .detail-row { display: flex; padding: 10px 0; border-bottom: 1px solid #f0f0f0; gap: 16px; align-items: center; }
        .detail-row:last-child { border-bottom: none; }
        .detail-label { width: 160px; flex-shrink: 0; font-weight: 600; color: #495057; font-size: 13px; }
        .detail-value { flex: 1; color: #212529; font-size: 13px; line-height: 1.45; }
        .status-active { color: #28a745; font-weight: 600; }
        .status-inactive { color: #dc3545; font-weight: 600; }
        .status-terminated { color: #6c757d; font-style: italic; }
        
        /* Стили для элементов управления */
        select, input, input[type="date"], input[type="email"], input[type="tel"] {
            width: 100%; max-width: 300px; padding: 6px 8px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; font-size: 13px;
        }
        .editable-field { cursor: pointer; border-bottom: 1px dashed #0d6efd; display: inline-block; min-width: 100px; }
        .editable-field:hover { background-color: #f8f9fa; }
        
        .dynamic-item { display: flex; align-items: center; gap: 8px; margin-bottom: 8px; }
        .remove-btn { color: #dc3545; cursor: pointer; font-weight: bold; padding: 0 5px; }
        
        .save-btn { margin: 20px 32px; padding: 10px 24px; background: #28a745; color: #fff; border: none; border-radius: 4px; cursor: pointer; font-weight: 600; }
        .save-btn:hover { background: #218838; }
        .back-link { display: inline-block; margin: 20px 0 24px; color: #0d6efd; text-decoration: none; }
    </style>
</head>
<body>
<div class="container" style="padding: 20px;">
    <a href="${pageContext.request.contextPath}/" class="back-link">← Вернуться к списку</a>

    <form action="${pageContext.request.contextPath}/employee" method="post" id="editForm">
        <input type="hidden" name="employeeId" value="${employee.employeeId}">

        <div class="employee-card">
            <!-- Верхняя часть: Фото и основные селекты -->
            <div class="employee-top">
                <div class="employee-photo-wrapper">
                    <c:choose>
                        <c:when test="${not empty employee.photoPath}">
                            <img src="${pageContext.request.contextPath}${employee.photoPath}" alt="${employee.fullName}" class="employee-photo">
                        </c:when>
                        <c:otherwise>
                            <div class="employee-photo-placeholder">?</div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="employee-main-info">
                    <h1 class="employee-name">${employee.fullName}</h1>
                    
                    <div class="employee-department">
                        Департамент: 
                        <select name="departmentId">
                            <option value="">— Выберите департамент —</option>
                                <c:forEach items="${departments}" var="dept">
                                    <option value="${dept.id}" 
                                        ${(not empty employee.department and employee.department.id == dept.id) ? 'selected' : ''}>
                                        ${dept.name}
                                    </option>
                                </c:forEach>
                        </select>
                    </div>

                    <div class="employee-position-shift">
                        Должность: 
                        <select name="positionId">
                            <option value="">— Выберите должность —</option>
                                <c:forEach items="${positions}" var="pos">
                                    <option value="${pos.id}" 
                                        ${(not empty employee.position and employee.position.id == pos.id) ? 'selected' : ''}>
                                        ${pos.name}
                                    </option>
                                </c:forEach>
                        </select>
                        &nbsp; • &nbsp; Смена:
                        <select name="shift">
                            <option value="1" ${employee.shift == '1' ? 'selected' : ''}>1</option>
                            <option value="2" ${employee.shift == '2' ? 'selected' : ''}>2</option>
                            <option value="3" ${employee.shift == '3' ? 'selected' : ''}>3</option>
                            <option value="2/2" ${employee.shift == '2/2' ? 'selected' : ''}>2/2</option>
                        </select>
                    </div>
                </div>
            </div>

            <!-- Сетка подробностей -->
            <div class="employee-grid">
                <div class="detail-row">
                    <div class="detail-label">ID</div>
                    <div class="detail-value"><strong>${not empty employee.employeeId ? employee.employeeId : 'null'}</strong></div>
                </div>
                <!-- Табельный номер (Двойной клик) -->
                <div class="detail-row">
                    
                    <div class="detail-label">Табельный номер</div>
                    <div class="detail-value" id="tabContainer">
                        <span class="editable-field" ondblclick="initEdit(this, 'tabNumber', 'text')">
                            ${not empty employee.tabNumber ? employee.tabNumber : 'не указано'}
                        </span>
                        <input type="hidden" name="tabNumber" value="${employee.tabNumber}">
                    </div>
                </div>

                <!-- Дата рождения (Двойной клик) -->
                <div class="detail-row">
                    <div class="detail-label">Дата рождения</div>
                    <div class="detail-value">
                        <span class="editable-field" ondblclick="initEdit(this, 'birthDate', 'date')">
                            ${not empty employee.birthDate ? employee.birthDate : 'выбрать дату'}
                        </span>
                        <input type="hidden" name="birthDate" value="${employee.birthDate}">
                    </div>
                </div>

                <!-- Email (Динамический список) -->
                <div class="detail-row" style="grid-column: 1 / -1; border-bottom: none;">
                    <div class="detail-label">Email адреса</div>
                    <div class="detail-value" id="email-list">
                        <c:forEach items="${employee.email}" var="emailObj">
                            <div class="dynamic-item">
                                <!-- Используем emailObj.email, так как в объекте Email это поле со строкой -->
                                <input type="email" name="emails" value="${emailObj.email}" placeholder="Email">
                                <span class="remove-btn" onclick="this.parentElement.remove()">×</span>
                            </div>
                        </c:forEach>
                        <!-- Поле для нового значения -->
                        <div class="dynamic-item">
                            <input type="email" name="emails" placeholder="Добавить email..." oninput="checkEmptyRow(this, 'email')">
                        </div>
                    </div>
                </div>

                <!-- Телефон (Динамический список) -->
                <div class="detail-row" style="grid-column: 1 / -1;">
                    <div class="detail-label">Телефоны</div>
                    <div class="detail-value" id="phone-list">
                        <c:forEach items="${employee.phone}" var="phoneObj">
                            <div class="dynamic-item">
                                <!-- Предполагаем, что у объекта Phone поле называется phone или number -->
                                <input type="tel" name="phones" value="${phoneObj.phone}" placeholder="Телефон">
                                <span class="remove-btn" onclick="this.parentElement.remove()">×</span>
                            </div>
                        </c:forEach>
                        <!-- Поле для нового значения -->
                        <div class="dynamic-item">
                            <input type="tel" name="phones" placeholder="Добавить телефон..." oninput="checkEmptyRow(this, 'tel')">
                        </div>
                    </div>
                </div>

                <div class="detail-row">
                    <div class="detail-label">Стаж работы</div>
                    <div class="detail-value">${not empty employee.hireDate ? employee.hireDate : 'null'}</div>
                </div>

                <div class="detail-row">
                    <div class="detail-label">Договор расторгнут</div>
                    <div class="detail-value">
                        <c:choose>
                            <c:when test="${not empty employee.terminationDate}">
                                <span class="status-terminated">${employee.terminationDate}</span>
                            </c:when>
                            <c:otherwise>—</c:otherwise>
                        </c:choose>
                    </div>
                </div>
                
                <div class="detail-row">
                    <div class="detail-label">Статус</div>
                    <div class="detail-value">
                        <c:choose>
                            <c:when test="${employee.active}"><span class="status-active">Активен</span></c:when>
                            <c:otherwise><span class="status-inactive">Неактивен</span></c:otherwise>
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

            <button type="submit" class="save-btn">Сохранить изменения</button>
        </div>
    </form>
</div>

<script>
    /**
     * Превращает текстовое поле в input при двойном клике
     */
    function initEdit(element, fieldName, inputType) {
        const currentValue = element.innerText.trim();
        const input = document.createElement('input');
        input.type = inputType;
        input.value = (currentValue === 'не указано' || currentValue === 'выбрать дату') ? '' : currentValue;
        
        // Когда фокус уходит - возвращаем текст и обновляем скрытое поле
        input.onblur = function() {
            const newValue = this.value;
            element.innerText = newValue || (inputType === 'date' ? 'выбрать дату' : 'не указано');
            element.nextElementSibling.value = newValue; // Обновляем hidden input
            element.style.display = 'inline-block';
            this.remove();
        };

        element.style.display = 'none';
        element.parentElement.insertBefore(input, element);
        input.focus();
    }

    /**
     * Добавляет новую строку в список, если текущая последняя заполнена
     */
    function checkEmptyRow(input, type) {
        const container = input.closest('.detail-value');
        const allInputs = container.querySelectorAll('input');
        const lastInput = allInputs[allInputs.length - 1];

        if (lastInput.value.trim() !== "") {
            // Добавляем крестик удаления текущему инпуту
            const item = lastInput.parentElement;
            if (!item.querySelector('.remove-btn')) {
                const span = document.createElement('span');
                span.className = 'remove-btn';
                span.innerHTML = '×';
                span.onclick = function() { this.parentElement.remove(); };
                item.appendChild(span);
            }

            // Создаем новую пустую строку
            const newDiv = document.createElement('div');
            newDiv.className = 'dynamic-item';
            newDiv.innerHTML = `
                <input type="${type}" name="${type}s" 
                       placeholder="Добавить еще..." 
                       oninput="checkEmptyRow(this, '${type}')">
            `;
            container.appendChild(newDiv);
        }
    }
</script>
</body>
</html>