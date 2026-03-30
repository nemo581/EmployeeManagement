/**
 * 1. ЭТАЛОН (Табельный №, Дата)
 */
function initEdit(el, fieldName, inputType) {
    const container = el.closest('.detail-value');
    const hiddenInput = container.querySelector(`input[name="${fieldName}"]`);
    const placeholder = (inputType === 'date' ? 'выбрать дату' : 'не указано');
    const val = (el.textContent.trim() === placeholder) ? '' : el.textContent.trim();

    const wrap = document.createElement('span');
    wrap.style.cssText = 'display: inline-flex; align-items: center; gap: 8px;';
    const input = document.createElement('input');
    input.type = inputType; input.value = val;
    input.style.width = (inputType === 'date') ? "140px" : "120px";

    const save = () => {
        const v = input.value.trim();
        el.textContent = v || placeholder;
        if (hiddenInput) hiddenInput.value = v;
        el.style.display = ''; wrap.remove();
    };

    input.onblur = () => setTimeout(save, 200);
    input.onkeydown = (e) => {
        if (e.key === 'Enter') { e.preventDefault(); save(); }
        if (e.key === 'Escape') { el.style.display = ''; wrap.remove(); }
    };

    el.style.display = 'none';
    wrap.appendChild(input);
    container.insertBefore(wrap, el);
    input.focus();
}

/**
 * 2. КОНТАКТЫ (Email и Телефоны)
 */

// Фиксация (блокировка)
function lockContact(input) {
    if (!input || input.readOnly) return;
    const row = input.closest('.dynamic-item');
    const select = row.querySelector('select');
    const editIcon = row.querySelector('.edit-icon');

    input.readOnly = true;
    if (select) select.classList.add('locked');
    if (editIcon) editIcon.style.display = 'inline';
}

// Редактирование (разблокировка)
function unlockContact(btn) {
    const row = btn.closest('.dynamic-item');
    const input = row.querySelector('input');
    const select = row.querySelector('select');

    input.readOnly = false;
    if (select) select.classList.remove('locked');
    btn.style.display = 'none';
    input.focus();
}

// Добавление новой строки
function addContactRow(link, type, serverName) {
    const container = link.closest('.detail-value');
    const list = container.querySelector('.items-list');
    const div = document.createElement('div');
    div.className = 'dynamic-item';
    
    div.innerHTML = `
        <input type="${type}" name="${serverName}" placeholder="Введите данные..."
               onkeydown="if(event.key==='Enter'){event.preventDefault(); lockContact(this);}"
               onblur="lockContact(this)">
        <select name="${serverName}Types">
            <option value="WORK">Раб.</option>
            <option value="PERSONAL">Личн.</option>
        </select>
        <span class="edit-icon" onclick="unlockContact(this)" style="display:none;">✏️</span>
        <span class="remove-btn" onclick="this.parentElement.remove()">✕</span>
    `;
    
    list.appendChild(div);
    div.querySelector('input').focus();
}

// Инициализация: Блокировка Enter для всей формы
document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('editForm');
    if (form) {
        form.addEventListener('keydown', (e) => {
            if (e.key === 'Enter' && e.target.tagName === 'INPUT') {
                e.preventDefault(); // Запрет отправки формы
            }
        });
    }
});
