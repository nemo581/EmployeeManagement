package com.employee_management.repository.query;

public enum SqlQuery {
    INSERT_MIN_EMPLOYEE_INFO("INSERT INTO employee (first_name, last_name, middle_name, shift) VALUES (?, ?, ?, ?);"),
    GET_ALL_EMPLOYEES("""
            SELECT\s
                -- Основные поля сотрудника
                COALESCE(e.employee_id, 0) AS employee_id,
                COALESCE(e.tab_number, '') AS tab_number,
                COALESCE(e.shift, '') AS shift,                          -- Автоподстановка пустой строки вместо NULL
                e.first_name,
                e.last_name,
                COALESCE(e.middle_name, '') AS middle_name,
                COALESCE(e.birth_date, NULL) AS birth_date,      -- или NULL, если предпочитаете
                COALESCE(e.photo_path, '') AS photo_path,
                COALESCE(e.hire_date, NULL) AS hire_date,
                COALESCE(e.termination_date, NULL) AS termination_date,
                e.created_at,
                e.updated_at,
                COALESCE(e.is_active, 1) AS is_active,
                e.deleted_at,

                -- Текущее назначение (отдел и должность)
                COALESCE(d.id, 0) AS department_id,
                COALESCE(d.name, 'Не назначен') AS department_name,      -- Автоподстановка, если нет отдела
                COALESCE(p.id, 0) AS position_id,
                COALESCE(p.name, 'Не назначена') AS position_name,       -- Автоподстановка, если нет должности
                COALESCE(p.salary, 0.00) AS position_salary,
                COALESCE(ea.assignment_date, NULL) AS assignment_date,
                ea.end_date,

                -- Телефоны (все активные)
                COALESCE(
                    GROUP_CONCAT(
                        DISTINCT CONCAT(ph.phone, '|', ph.type, '|', IF(ph.is_main = 1, 'main', ''))
                        SEPARATOR ';;'
                    ),\s
                    ''  -- Если телефонов нет — пустая строка вместо NULL
                ) AS phones,

                -- Email (все активные)
                COALESCE(
                    GROUP_CONCAT(
                        DISTINCT CONCAT(em.email, '|', em.type, '|', IF(em.is_main = 1, 'main', ''))
                        SEPARATOR ';;'
                    ),\s
                    ''
                ) AS emails

            FROM employee e

            -- Текущее назначение
            LEFT JOIN employee_assignments ea\s
                ON e.employee_id = ea.employee_id\s
                AND ea.is_current = 1\s
                AND ea.is_active = 1\s
                AND ea.deleted_at IS NULL

            -- Отдел
            LEFT JOIN departments d\s
                ON ea.department_id = d.id\s
                AND d.is_active = 1\s
                AND d.deleted_at IS NULL

            -- Должность
            LEFT JOIN positions p\s
                ON ea.position_id = p.id

            -- Телефоны
            LEFT JOIN employee_phone ph\s
                ON e.employee_id = ph.employee_id\s
                AND ph.is_active = 1\s
                AND ph.deleted_at IS NULL

            -- Email
            LEFT JOIN employee_email em\s
                ON e.employee_id = em.employee_id\s
                AND em.is_active = 1\s
                AND em.deleted_at IS NULL

            WHERE e.is_active = 1\s
              AND e.deleted_at IS NULL

            GROUP BY\s
                e.employee_id

            -- Сортировка:
            -- 1. По фамилии
            -- 2. По смене: 1 → 2 → 1/2 → NULL (в конец)
            -- 3. По имени (для одинаковых фамилий)
            ORDER BY\s
                e.last_name ASC,

                CASE\s
                    WHEN e.shift = '1'   THEN 1
                    WHEN e.shift = '2'   THEN 2
                    WHEN e.shift = '1/2' THEN 3
                    WHEN e.shift IS NULL THEN 4
                    ELSE 5
                END ASC,

                e.first_name ASC;"""),

    GET_ALL_EMPLOYEES_TEST("""
            SELECT\s
                e.employee_id,
                e.tab_number,
                e.shift,
                e.first_name,
                e.last_name,
                e.middle_name,
                e.birth_date,
                e.photo_path,
                e.hire_date,
                e.termination_date,
                e.created_at,
                e.updated_at,
                e.is_active,
                e.deleted_at,
               \s
                d.id          AS department_id,
                d.name        AS department_name,
                p.id          AS position_id,
                p.name        AS position_name,
                p.salary      AS position_salary,
                ea.assignment_date,
                ea.end_date
            FROM employee e
            LEFT JOIN employee_assignments ea\s
                ON e.employee_id = ea.employee_id\s
                AND ea.is_current = 1\s
                AND ea.is_active = 1\s
                AND ea.deleted_at IS NULL
            LEFT JOIN departments d\s
                ON ea.department_id = d.id\s
                AND d.is_active = 1\s
                AND d.deleted_at IS NULL
            LEFT JOIN positions p\s
                ON ea.position_id = p.id
            WHERE e.is_active = 1\s
              AND e.deleted_at IS NULL
            ORDER BY e.last_name ASC,
                     CASE\s
                         WHEN e.shift = '1'   THEN 1
                         WHEN e.shift = '2'   THEN 2
                         WHEN e.shift = '1/2' THEN 3
                         WHEN e.shift IS NULL THEN 4
                         ELSE 5
                     END ASC,
                     e.first_name ASC;"""),
    GET_CONTACT_INFO_BY_EMPLOYEE_ID("""
            SELECT\s
                'phone' AS source,
                employee_id,
                phone AS value,
                type,
                is_main,
                is_active
            FROM employee_phone
            WHERE employee_id IN (?,?,?,...)\s
              AND is_active = 1\s
              AND deleted_at IS NULL

            UNION ALL

            SELECT\s
                'email' AS source,
                employee_id,
                email AS value,
                type,
                is_main,
                is_active
            FROM employee_email
            WHERE employee_id IN (?,?,?,...)\s
              AND is_active = 1\s
              AND deleted_at IS NULL;"""),

    GET_ALL_EMPLOYEES_CONTACT("""
            SELECT\s
                'phone' AS source,
                employee_id,
                phone AS value,
                type,
                is_main,
                is_active
            FROM employee_phone
            WHERE employee_id IN (%s)\s
              AND is_active = 1\s
              AND deleted_at IS NULL

            UNION ALL

            SELECT\s
                'email' AS source,
                employee_id,
                email AS value,
                type,
                is_main,
                is_active
            FROM employee_email
            WHERE employee_id IN (%s)\s
              AND is_active = 1\s
              AND deleted_at IS NULL;"""),

    GET_DEPARTMENTS("SELECT id, name FROM departments WHERE is_active = 1 ORDER BY name"),

    ADD_DEPARTMENT("INSERT INTO departments (name, is_active) VALUES (?, 1)"),

    ADD_POSITION("INSERT INTO positions (name, salary) VALUES (?, ?)"),

    LINK_POSITION_DEPARTMENT("INSERT IGNORE INTO department_positions (department_id, position_id) VALUES (?, ?)");
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
