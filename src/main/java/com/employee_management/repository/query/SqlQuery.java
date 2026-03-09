package com.employee_management.repository.query;

public enum SqlQuery {
    INSERT_MIN_EMPLOYEE_INFO("INSERT INTO employee (first_name, last_name, middle_name, shift) VALUES (?, ?, ?, ?);"),

    GET_ALL_EMPLOYEES("""
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

    GET_EMPLOYEE_BY_ID("""
            SELECT
            e.employee_id,
            e.tab_number,
            e.first_name,
            e.last_name,
            e.middle_name,
            e.birth_date,
            e.photo_path,
            e.shift,
            e.hire_date,
            e.termination_date,
            e.created_at,
            e.updated_at,
            e.is_active,
            e.deleted_at,

            -- текущее назначение
            curr_dep.name          AS current_department_name,
            curr_pos.name          AS current_position_name,
            curr_assign.assignment_date,
            curr_assign.end_date,
            curr_assign.is_current

            FROM employee e

            LEFT JOIN employee_assignments curr_assign
            ON curr_assign.employee_id = e.employee_id
            AND curr_assign.is_current = 1

            LEFT JOIN departments curr_dep
            ON curr_dep.id = curr_assign.department_id

            LEFT JOIN positions curr_pos
            ON curr_pos.id = curr_assign.position_id

            WHERE e.employee_id = ?          -- ← ваш ID
            AND e.deleted_at IS NULL
            AND e.is_active = 1;"""),

    GET_EMAIL_BY_EMPLOYEE_ID("""
            SELECT id, email, is_main, is_active, type, created_at, updated_at, deleted_at, employee_id
            FROM employee_email
            WHERE employee_id = ? AND is_active = 1;
            """),
    GET_PHONE_BY_EMPLOYEE_ID("""
            SELECT id, phone, type, is_main, is_active, created_at, updated_at, deleted_at, employee_id
            FROM employee_phone
            WHERE employee_id = ? AND is_active = 1;
            """),

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
