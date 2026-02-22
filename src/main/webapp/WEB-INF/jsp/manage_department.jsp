<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Управление департаментами и должностями</title>
    <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
            background-color: #f4f4f4;
            color: #333;
            margin: 30px;
            line-height: 1.5;
            font-size: 14px;
        }
        h1 {
            font-size: 1.8rem;
            color: #2c3e50;
            border-bottom: 2px solid #3498db;
            padding-bottom: 8px;
            margin-bottom: 25px;
        }
        h2 {
            font-size: 1.3rem;
            color: #2c3e50;
            margin: 20px 0 15px;
        }
        .container {
            max-width: 750px;
            margin: 0 auto;
            background: #fff;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        form {
            margin-bottom: 25px;
            padding: 18px;
            background: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 6px;
        }
        label {
            display: block;
            margin: 12px 0 4px;
            font-weight: bold;
            color: #2c3e50;
            font-size: 0.95rem;
        }
        input[type=text], input[type=number], select {
            width: 100%;
            padding: 8px 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 0.95rem;
        }
        input:focus, select:focus {
            border-color: #3498db;
            outline: none;
            box-shadow: 0 0 5px rgba(52,152,219,0.3);
        }
        button {
            background: #3498db;
            color: white;
            padding: 10px 18px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 1rem;
            margin-top: 12px;
        }
        button:hover {
            background: #2980b9;
        }
        button:disabled {
            background: #95a5a6;
            cursor: not-allowed;
        }
        .info {
            padding: 12px;
            background: #ecf0f1;
            border-left: 4px solid #3498db;
            margin: 18px 0;
            font-size: 0.95rem;
            font-style: italic;
        }
        .back-link {
            display: inline-block;
            margin: 20px 0;
            color: #3498db;
            text-decoration: none;
            font-weight: bold;
            font-size: 0.95rem;
        }
        .back-link:hover {
            text-decoration: underline;
        }
        .footer {
            margin-top: 35px;
            text-align: center;
            color: #7f8c8d;
            font-size: 0.85rem;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Управление департаментами и должностями</h1>

    <!-- Добавление нового департамента -->
    <form id="addDeptForm">
        <h2>Добавить новый департамент</h2>
        <label for="deptName">Название департамента</label>
        <input type="text" id="deptName" placeholder="Например: IT-отдел" required>
        <button type="submit">Добавить департамент</button>
    </form>

    <c:choose>
        <c:when test="${empty departments}">
            <div class="info">Список департаментов пуст. Добавьте первый департамент с помощью формы выше.</div>
        </c:when>
        <c:otherwise>
            <!-- Добавление должности -->
            <form id="positionForm">
                <h2>Добавить должность</h2>

                <label for="existingDept">Выберите департамент</label>
                <select id="existingDept" required>
                    <option value="">— Выберите департамент —</option>
                    <c:forEach items="${departments}" var="dept">
                        <option value="${dept.id}">${dept.name}</option>
                    </c:forEach>
                </select>

                <div id="positionBlock" style="display: none; margin-top: 18px;">
                    <label for="positionSelect">Действие с должностью</label>
                    <select id="positionSelect">
                        <option value="">— Выберите действие —</option>
                        <option value="new">Добавить новую должность</option>
                    </select>

                    <div id="newPositionInput" style="display: none; margin-top: 18px;">
                        <label for="newPositionName">Название должности</label>
                        <input type="text" id="newPositionName" placeholder="Например: Senior Java Developer" required>
                        <label for="salary">Зарплата (опционально)</label>
                        <input type="number" id="salary" step="0.01" placeholder="180000.00">
                    </div>
                </div>

                <button type="submit" id="submitBtn" disabled>
                    Добавить должность
                </button>
            </form>

            <div class="info">
                Текущий выбранный департамент: <strong><span id="selectedInfo">ни один</span></strong>
            </div>
        </c:otherwise>
    </c:choose>

    <a href="${pageContext.request.contextPath}/" class="back-link">← Вернуться на главную</a>

    <div class="footer">
        Система управления персоналом © 2026
    </div>
</div>

<script>
    // Элементы
    const deptSelect = document.getElementById('existingDept');
    const positionBlock = document.getElementById('positionBlock');
    const positionSelect = document.getElementById('positionSelect');
    const newPositionInput = document.getElementById('newPositionInput');
    const newPositionName = document.getElementById('newPositionName');
    const salaryInput = document.getElementById('salary');
    const submitBtn = document.getElementById('submitBtn');
    const selectedInfo = document.getElementById('selectedInfo');

    console.log({deptSelect, positionBlock, submitBtn, addDeptForm});

    // Обновление выбранного департамента (только если элементы существуют)
    if (deptSelect && positionBlock && submitBtn && selectedInfo) {
        deptSelect.addEventListener('change', function () {
            const selectedText = this.options[this.selectedIndex].text.trim();
            selectedInfo.textContent = selectedText || 'ни один';

            if (this.value) {
                positionBlock.style.display = 'block';
                submitBtn.disabled = false;
            } else {
                positionBlock.style.display = 'none';
                submitBtn.disabled = true;
            }

            // Сброс только при смене департамента
            if (positionSelect) positionSelect.value = '';
            if (newPositionInput) newPositionInput.style.display = 'none';
            if (newPositionName) newPositionName.value = '';
            if (salaryInput) salaryInput.value = '';
        });
    }

    if (positionSelect && newPositionInput) {
        positionSelect.addEventListener('change', function () {
            newPositionInput.style.display = this.value === 'new' ? 'block' : 'none';
            if (this.value === 'new' && newPositionName) newPositionName.focus();
        });
    }

    // === Добавление должности ===
    const positionForm = document.getElementById('positionForm');
    if (positionForm) {
        positionForm.addEventListener('submit', async function (e) {
            e.preventDefault();

            // 1. Читаем и сохраняем данные сразу
            const deptId = deptSelect?.value;
            const positionNameRaw = newPositionName?.value;
            const positionName = positionNameRaw?.trim();
            const salary = salaryInput?.value ? parseFloat(salaryInput.value) : null;

            // 2. Проверки
            if (!deptId) return alert('Пожалуйста, выберите департамент');
            if (positionSelect?.value !== 'new') return alert('Поддерживается только добавление новой должности');
            if (!positionName) return alert('Введите название должности');

            // 3. ФИКСИРУЕМ имя для сообщения — теперь оно точно не изменится
            const fixedPositionName = positionName;

            try {
                const response = await fetch('${pageContext.request.contextPath}/department_management', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        action: 'add_position_to_dept',
                        department_id: parseInt(deptId),
                        position_name: positionName,
                        salary: salary
                    })
                });

                const data = await response.json();

                if (response.ok) {
                    // Используем зафиксированное имя — оно НЕ может быть пустым
                    alert(`Должность успешно добавлена и привязана к департаменту!`);

                    // Теперь безопасно очищаем
                    if (positionSelect) positionSelect.value = '';
                    if (newPositionInput) newPositionInput.style.display = 'none';
                    if (newPositionName) newPositionName.value = '';
                    if (salaryInput) salaryInput.value = '';
                } else {
                    alert('Ошибка: ' + (data.message || 'Не удалось добавить должность'));
                }
            } catch (err) {
                alert('Ошибка соединения с сервером');
                console.error(err);
            }
        });
    }

    // === Добавление департамента ===
    document.getElementById('addDeptForm')?.addEventListener('submit', async function (e) {
        e.preventDefault();

        console.log("=== Кнопка 'Добавить департамент' нажата ===");
        console.log("preventDefault сработал");

        const deptNameInput = document.getElementById('deptName');
        console.log("Значение поля:", deptNameInput)
        const departmentNameRaw = deptNameInput.value;
        const departmentName = departmentNameRaw.trim();

        if (!departmentName) return alert('Введите название департамента');

        // Фиксируем имя для сообщения
        const fixedDepartmentName = departmentName;

        try {
            const response = await fetch('${pageContext.request.contextPath}/department_management', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ name: departmentName })
            });

            const data = await response.json();

            if (response.ok) {
                alert(`Департамент успешно добавлен!`);
                deptNameInput.value = '';
                location.reload();
            } else {
                alert('Ошибка: ' + (data.message || 'Не удалось добавить департамент'));
            }
        } catch (err) {
            alert('Ошибка соединения с сервером');
            console.error(err);
        }
    });
</script>

</body>
</html>