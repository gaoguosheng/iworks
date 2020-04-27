package com.ggs.admin.module.sys.web;

import com.ggs.admin.module.sys.model.UserModel;
import com.ggs.admin.module.sys.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController extends  BaseController{

    @Autowired
    private SysService service;

    @Value("${admin-title}")
    private String title;

    @RequestMapping("/login.html")
    public String login(ModelMap map)
    {
        map.addAttribute("title",title);
        return "/html/login";
    }

    @RequestMapping("/checkLogin.do")
    public String checkLogin(ModelMap map, HttpSession session, UserModel userModel, HttpServletResponse response){
        map.addAttribute("title",title);
        UserModel user = service.getUser(userModel);
        if(user==null){
            map.put("msg","用户不存在或者密码不正确");
            return "/html/login";
        }else{
            session.setAttribute("admin",user);
            try {
                response.sendRedirect("admin/index.do");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    @RequestMapping("/")
    public void webRoot(HttpServletResponse response){
        try {
            response.sendRedirect("admin/index.do");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
