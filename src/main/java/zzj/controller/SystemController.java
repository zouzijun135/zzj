package zzj.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import zzj.bean.User;


@Controller
public class SystemController {
    
    @RequestMapping("/index")
    public String showIndexPage(HttpServletRequest request,Model model) {
        User user = (User)request.getSession().getAttribute("user");
        String page = "index";
        if (user != null) {
            String userName = user.getUsername();
            if((userName != null) && (!userName.equals(""))) {
                page = "home";
            }
        }
        return page;
    }
}
