package com.EmployeeManagement.dao.sql;

public enum SqlQuery {
    INSERT_MIN_EMPLOYEE_INFO ("INSERT INTO employee (first_name, last_name, middle_name, shift) VALUES (?, ?, ?, ?);"),
    GET_ALL_EMPLOYEES ("SELECT \n" +
            "    -- Основные поля сотрудника\n" +
            "    COALESCE(e.employee_id, 0) AS employee_id,\n" +
            "    COALESCE(e.tab_number, '') AS tab_number,\n" +
            "    COALESCE(e.shift, '') AS shift,                          -- Автоподстановка пустой строки вместо NULL\n" +
            "    e.first_name,\n" +
            "    e.last_name,\n" +
            "    COALESCE(e.middle_name, '') AS middle_name,\n" +
            "    COALESCE(e.birth_date, NULL) AS birth_date,      -- или NULL, если предпочитаете\n" +
            "    COALESCE(e.photo_path, '') AS photo_path,\n" +
            "    COALESCE(e.hire_date, NULL) AS hire_date,\n" +
            "    COALESCE(e.termination_date, NULL) AS termination_date,\n" +
            "    e.created_at,\n" +
            "    e.updated_at,\n" +
            "    COALESCE(e.is_active, 1) AS is_active,\n" +
            "    e.deleted_at,\n" +
            "\n" +
            "    -- Текущее назначение (отдел и должность)\n" +
            "    COALESCE(d.id, 0) AS department_id,\n" +
            "    COALESCE(d.name, 'Не назначен') AS department_name,      -- Автоподстановка, если нет отдела\n" +
            "    COALESCE(p.id, 0) AS position_id,\n" +
            "    COALESCE(p.name, 'Не назначена') AS position_name,       -- Автоподстановка, если нет должности\n" +
            "    COALESCE(p.salary, 0.00) AS position_salary,\n" +
            "    COALESCE(ea.assignment_date, NULL) AS assignment_date,\n" +
            "    ea.end_date,\n" +
            "\n" +
            "    -- Телефоны (все активные)\n" +
            "    COALESCE(\n" +
            "        GROUP_CONCAT(\n" +
            "            DISTINCT CONCAT(ph.phone, '|', ph.type, '|', IF(ph.is_main = 1, 'main', ''))\n" +
            "            SEPARATOR ';;'\n" +
            "        ), \n" +
            "        ''  -- Если телефонов нет — пустая строка вместо NULL\n" +
            "    ) AS phones,\n" +
            "\n" +
            "    -- Email (все активные)\n" +
            "    COALESCE(\n" +
            "        GROUP_CONCAT(\n" +
            "            DISTINCT CONCAT(em.email, '|', em.type, '|', IF(em.is_main = 1, 'main', ''))\n" +
            "            SEPARATOR ';;'\n" +
            "        ), \n" +
            "        ''\n" +
            "    ) AS emails\n" +
            "\n" +
            "FROM employee e\n" +
            "\n" +
            "-- Текущее назначение\n" +
            "LEFT JOIN employee_assignments ea \n" +
            "    ON e.employee_id = ea.employee_id \n" +
            "    AND ea.is_current = 1 \n" +
            "    AND ea.is_active = 1 \n" +
            "    AND ea.deleted_at IS NULL\n" +
            "\n" +
            "-- Отдел\n" +
            "LEFT JOIN departments d \n" +
            "    ON ea.department_id = d.id \n" +
            "    AND d.is_active = 1 \n" +
            "    AND d.deleted_at IS NULL\n" +
            "\n" +
            "-- Должность\n" +
            "LEFT JOIN positions p \n" +
            "    ON ea.position_id = p.id\n" +
            "\n" +
            "-- Телефоны\n" +
            "LEFT JOIN employee_phone ph \n" +
            "    ON e.employee_id = ph.employee_id \n" +
            "    AND ph.is_active = 1 \n" +
            "    AND ph.deleted_at IS NULL\n" +
            "\n" +
            "-- Email\n" +
            "LEFT JOIN employee_email em \n" +
            "    ON e.employee_id = em.employee_id \n" +
            "    AND em.is_active = 1 \n" +
            "    AND em.deleted_at IS NULL\n" +
            "\n" +
            "WHERE e.is_active = 1 \n" +
            "  AND e.deleted_at IS NULL\n" +
            "\n" +
            "GROUP BY \n" +
            "    e.employee_id\n" +
            "\n" +
            "-- Сортировка:\n" +
            "-- 1. По фамилии\n" +
            "-- 2. По смене: 1 → 2 → 1/2 → NULL (в конец)\n" +
            "-- 3. По имени (для одинаковых фамилий)\n" +
            "ORDER BY \n" +
            "    e.last_name ASC,\n" +
            "\n" +
            "    CASE \n" +
            "        WHEN e.shift = '1'   THEN 1\n" +
            "        WHEN e.shift = '2'   THEN 2\n" +
            "        WHEN e.shift = '1/2' THEN 3\n" +
            "        WHEN e.shift IS NULL THEN 4\n" +
            "        ELSE 5\n" +
            "    END ASC,\n" +
            "\n" +
            "    e.first_name ASC;");
    private String query;

    SqlQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
