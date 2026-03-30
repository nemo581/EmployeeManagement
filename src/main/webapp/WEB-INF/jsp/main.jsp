<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>EMPLOYEE SYSTEM</title>
    <style>
        body { font-family: Arial, Helvetica, sans-serif; background-color: #f4f4f4; color: #333; margin: 30px; 
            line-height: 1.5; font-size: 14px; }        
        h1 { font-size: 1.8rem; color: #2c3e50; border-bottom: 2px solid #3498db; padding-bottom: 8px;
            margin-bottom: 25px; text-align: center; }        
        .container { max-width: 750px; margin: 0 auto; background: #fff; padding: 25px;
            border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .content-block { padding: 0 20px; }        
        .status { padding: 15px 0; background: #ecf0f1; border-left: 4px solid #3498db; font-size: 1.1rem;
            font-style: italic; color: #2c3e50; margin-bottom: 20px; }
        .btn { display: block; width: 100%; padding: 16px 0; margin-bottom: 15px; background: #3498db;
            color: white; font-size: 1.2rem; text-align: center; text-decoration: none; border-radius: 6px;
            transition: all 0.3s; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
        .btn:hover { background: #2980b9; transform: translateY(-2px); box-shadow: 0 6px 12px rgba(0,0,0,0.15); }
        .btn:last-child { margin-bottom: 0; }
        .footer { margin-top: 35px; text-align: center; color: #7f8c8d; font-size: 0.9rem; }
    </style>
</head>
    <body>
        <div class="container">
            <h1>EMPLOYEE SYSTEM</h1>
            <div class="content-block">
                <div class="status">
                    Добро пожаловать в систему управления персоналом<br>
                    Доступ разрешён • 2026
                </div>
                <a href="${pageContext.request.contextPath}/all_employees" class="btn">Список сотрудников</a>
                <a href="${pageContext.request.contextPath}/employees_schedule" class="btn">График работы</a>
                <a href="${pageContext.request.contextPath}/add_employee" class="btn">Добавить сотрудника</a>
                <a href="${pageContext.request.contextPath}/department_management" class="btn">Управление департаментами и должностями</a>
            </div>
            <div class="footer">
                Java 21 • Apache Tomcat 11.0.15 • 2026
            </div>
        </div>
        </body>
</html>