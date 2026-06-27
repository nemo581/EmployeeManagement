<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>EMPLOYEE-INFO — ${employee.fullName}</title>
        <style>
            *,
            *::before, 
            *::after {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                background-color: rgba(11, 53, 159, 1);
                margin: 0px;
                flex-direction: column;
                min-height: 100vh;
                font-family: Arial, Helvetica, sans-serif;
                color: rgba(200, 211, 255, 0.8);
                font-size: 13px;                
                -webkit-font-smoothing: antialiased;
                -moz-osx-font-smoothing: grayscale;
            }

            .top-strip, .bottom-strip {
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
                display: flex;
                align-items: center;
                padding-left: 15px;
                color: rgba(200, 211, 255, 0.8);
                font-size: 12px;
                font-family: sans-serif;

            }

            .bottom-strip {
                bottom: 0;
                box-shadow: 0px 0px 15px 5px rgba(57, 244, 0, 0.6);
                display: flex;
                align-items: center;
                justify-content: flex-end;
                padding-right: 15px;
                color: rgba(200, 211, 255, 0.8);
                font-size: 12px;
                font-family: sans-serif;
                box-sizing: border-box;
            }

            .top-menu a {
                color: rgba(200, 211, 255, 0.8);
                text-decoration: none;
                margin-right: 15px;
            }

            .top-menu a:hover {
                color: #39f400;
            }

            .content {
                padding: 35px 15px;
                flex-direction: column;
                flex: 1;
            }
        </style>
    </head>
        <body>
            <header class="top-strip">
                <nav class="top-menu">
                    <a href="${pageContext.request.contextPath}/">Главная</a>
                    <a href="#">...</a>
                </nav>
            </header>
            <main class="content">
            </main>
            <footer class="bottom-strip">
                /*${session} - [ referer: ${referer} ] :: [ method: ${method} ] :: [ local: ${local} ] -- ${protocol}://${ip}:${serverPort}${reqUri}*/
            </footer>
        </body>
    </head>
</html>