package com.employee_management.controller;

import com.employee_management.util.LogUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//@WebServlet(urlPatterns = {"/"}, loadOnStartup = 0)
//public class MainController extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher("/WEB-INF/jsp/...").forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
//    }
//
//    @Override
//    public void destroy() {
//        super.destroy();
//    }
//
//    @Override
//    public void init() throws ServletException {
//        super.init();
//    }
//}

@Controller
public class MainController {
    @GetMapping({"", "/"})
    public String handleRootRequest(HttpServletRequest req) {
        LogUtil.logRequest(req, this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());
        return "main";
    }
}