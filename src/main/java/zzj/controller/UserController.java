package zzj.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import zzj.bean.User;
import zzj.service.IUserService;

@Controller
public class UserController {

    @Resource
    private IUserService userService;
    
    @RequestMapping(value = "/login", method =RequestMethod.POST)
    public String userLogin(HttpServletRequest request, Model model) {
        String page = "index";
        String userName = request.getParameter("name");
        String password = request.getParameter("password");
        User user = this.userService.getUserByName(userName);
        if(user != null) {  
            if(user.getPassword().equals(password)) {
                HttpSession session = request.getSession();
                if (session.getAttribute("user") == null) {
                    session.setAttribute("user", user);
                }
                page = "home";
            }

        }
        return page;
    }
    
    @RequestMapping("/logout")
    public String userLogout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "index";
    }
}
