<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>EMPLOYEE SYSTEM</title>
        <style>
            *,
            *::before, 
            *::after {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                /*background-color: rgba(17, 34, 175, 1);*/
                background-color: rgba(11, 53, 159, 1);
                margin: 0px;
                flex-direction: column;
                min-height: 100vh;
                font-family: Arial, Helvetica, sans-serif;
                font-size: 13px;                
                -webkit-font-smoothing: antialiased;
                -moz-osx-font-smoothing: grayscale;
            }

            .top-strip, .bottom-strip {
                /*background-color: rgba(11, 22, 156, 1);*/
                background-color: rgba(2, 37, 125, 1);
                height: 20px;
                width: 100%;
                position: fixed;
                left:0;
                z-index: 1000;
            }

            .top-strip {
                top: 0;
                box-shadow: 0px 0px 15px 5px rgba(57, 244, 0, 0.6);
            }

            .bottom-strip {
                bottom: 0;
                box-shadow: 0px 0px 15px 5px rgba(57, 244, 0, 0.6);
            }

            .content {
                padding: 35px 15px;
                flex-direction: column;
                flex: 1;
            }

            .menu-container {
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                height: 70vh;
                gap: 0px;
            }

            .menu-container a {
                font-family: inherit;
                display: flex;
                align-items: center;
                justify-content: center;
                width: 100%;
                max-width: 440px;
                min-height: 40px;
                padding: 10px 45px;
                margin-bottom: 2px;
                text-decoration: none;
                text-align: center;
                box-sizing: border-box;
                word-break: break-word; /* Разрывает длинные абракадабры */
                overflow-wrap: anywhere; /* Дополнительная страховка */

                /* Фиксированные цвета полосок (не меняются) */
                --stripe-color: rgba(11, 53, 159, 1);
                /* Цвет фона, который БУДЕТ меняться */
                --current-bg: rgba(2, 37, 125, 0.7);

                background-color: var(--current-bg);
    
                /* Убираем filter из анимации, оставляем только чистый цвет для скорости */
                transition: background-color 0.3s cubic-bezier(0.4, 0, 0.2, 1), 
                            color 0.3s cubic-bezier(0.4, 0, 0.2, 1); 
                will-change: background-color, color;
                color: rgba(200, 211, 255, 0.8);
    
                background-image: 
                linear-gradient(to right, 
                var(--stripe-color) 2px, transparent 2px, 
                transparent 4px, var(--stripe-color) 4px, 
                var(--stripe-color) 6px, transparent 6px, 
                transparent 8px, var(--stripe-color) 8px, 
                var(--stripe-color) 10px, transparent 10px, 
                transparent 12px),
                linear-gradient(to left, 
                var(--stripe-color) 2px, transparent 2px, 
                transparent 4px, var(--stripe-color) 4px, 
                var(--stripe-color) 6px, transparent 6px, 
                transparent 8px, var(--stripe-color) 8px, 
                var(--stripe-color) 10px, transparent 10px, 
                transparent 12px);

                background-repeat: no-repeat;
                background-size: 12px 100%;
                background-position: 2px 0, calc(100% - 2px) 0;
            }

            .menu-container a:hover {
                /* Меняем ТОЛЬКО основной фон */
                --current-bg: rgba(2, 37, 125, 1);    
                /* Убираем filter, чтобы не затрагивать яркость темных полосок */
                color: rgba(243, 246, 255, 1);
            }            
        </style>
    </head>
    <body>
        <header class="top-strip"></header>
        <main class="content">
            <div class="menu-container">
                <a href="${pageContext.request.contextPath}/all_employees" class="btn">Список сотрудников</a>
                <a href="${pageContext.request.contextPath}/employees_schedule" class="btn">График работы</a>
                <a href="${pageContext.request.contextPath}/add_employee" class="btn">Добавить сотрудника</a>
                <a href="${pageContext.request.contextPath}/department_management" class="btn">Управление департаментами и должностями</a>
            </div>
        </main>
        <footer class="bottom-strip"></footer>        
    </body>
</html>