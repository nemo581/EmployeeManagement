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