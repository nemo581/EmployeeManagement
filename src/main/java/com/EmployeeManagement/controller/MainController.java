package com.EmployeeManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//@WebServlet(urlPatterns = {"/"}, loadOnStartup = 0)
//public class MainServlet extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(req, resp);
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
    @GetMapping ({"", "/"})
    public String handleRootRequest() {
        return "main";
    }
}
