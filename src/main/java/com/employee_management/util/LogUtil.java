package com.employee_management.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class LogUtil {
    private static final DateTimeFormatter FORMATER = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss.SSSS");

    public static void logRequest(HttpServletRequest req, String className, String methodName, String... extraInfo) {
        Map<String, String> mapInfo = new LinkedHashMap<>();
        mapInfo.put("UserIP", req.getRemoteAddr());
        mapInfo.put("UserBrowser", req.getHeader("User-Agent"));
        mapInfo.put("UserReferer:", req.getHeader("Referer"));
        mapInfo.put("Thread:", Thread.currentThread().getName());
        mapInfo.put("Session ID:", req.getSession().getId());
        mapInfo.put("Class:", className);
        mapInfo.put("Method:", methodName);
        mapInfo.put("ReqURI:", "employee-management.ru" + req.getRequestURI());
        mapInfo.put("HTTP Method:", req.getMethod());

        if (extraInfo != null) {
            for (String s : extraInfo) {
                String[] temp = s.split("/");
                mapInfo.put(temp[0], temp[1]);
            }
        }

        System.out.println("/".repeat(28) + ">");

        for (Map.Entry<String, String> info : mapInfo.entrySet()) {
            System.out.printf("%-4s[%s] %s %s\n", "", LocalDateTime.now().format(FORMATER), info.getKey(), info.getValue());
        }
        System.out.println("\\".repeat(28) + ">");

    }

    public static void logResponse(HttpServletResponse resp, String className, String methodName, String... extraInfo) {
        String[][] info = {
                {"UserBrowser:", resp.getHeader("User-Agent")},
                {"UserReferer:", resp.getHeader("Referer")},
                {"Thread:", Thread.currentThread().getName()},
                {"Class:", className},
                {"Method:", methodName},
                {"HTTP Method:", String.valueOf(resp.getStatus())}
        };

        int maxLineLength = 0;
        for (String[] arr : info) {
            int currentLength = 45 + (arr[1] != null ? arr[1].length() : 4);
            if (currentLength > maxLineLength) {
                maxLineLength = currentLength;
            }
        }

        System.out.println("/".repeat(maxLineLength));
        for (String[] arr : info) {
            System.out.printf("%2s[%s] %-13s %s\n", "",
                    LocalDateTime.now().format(FORMATER), arr[0], arr[1]);
        }
        System.out.println("\\".repeat(maxLineLength));
    }
}
