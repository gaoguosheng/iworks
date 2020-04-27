package com.ggs.admin.module.sys.web;

import com.ggs.admin.module.sys.model.PermissionModel;
import com.ggs.admin.module.sys.model.ResultModel;
import com.ggs.admin.module.sys.model.UserModel;
import com.ggs.admin.module.sys.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

@Controller
@RequestMapping("/admin/")
public class IndexController extends  BaseController{

    @Autowired
    private SysService service;
    @Value("${admin-title}")
    private String title;

    @RequestMapping("index.do")
    public String index(ModelMap map, HttpSession session,Integer subsysid,HttpServletResponse response) {
        UserModel admin  = (UserModel) session.getAttribute("admin");
        if(admin==null){
            try {
                response.sendRedirect("../login.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            List<PermissionModel> permissionModelList = service.getUserPermissions(admin);
            map.addAttribute("admin",admin);
            map.addAttribute("permissons",permissionModelList);
            map.addAttribute("title",title);
            if(subsysid==null)subsysid=1;
            map.addAttribute("subsysid",subsysid);
            ServletContext context = session.getServletContext();
            Set<String> onlineList = (Set<String>) context.getAttribute("onlineList");
            if(onlineList==null){
                onlineList = new HashSet<String>();
            }
            onlineList.add(admin.getUsercode());
            context.setAttribute("onlineList",onlineList);
            return webRoot+"index";
        }
        return null;

    }

    @RequestMapping("main.do")
    public String main(ModelMap map,@SessionAttribute UserModel  admin) {
        map.addAttribute("admin",admin);
        return webRoot+"main";
    }


    @RequestMapping("logout.do")
    public void logout(HttpSession session,HttpServletResponse response){
        ServletContext context = session.getServletContext();
        UserModel admin = (UserModel) session.getAttribute("admin");
        Set<String> onlineList = (Set<String>) context.getAttribute("onlineList");
        onlineList.remove(admin.getUsercode());
        session.removeAttribute("admin");
        session.invalidate();
        try {
            response.sendRedirect("../login.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("pwd.html")
    public String pwd(){
        return webRoot+"pwd";
    }

    @RequestMapping("changePwd.do")
    @ResponseBody
    public ResultModel changePwd(UserModel userModel, @SessionAttribute UserModel  admin){
        admin.setPassword(userModel.getPassword());
        service.updateUserPassword(admin);
        return new ResultModel("0","修改密码成功");
    }

    @RequestMapping("/")
    public void webRoot(HttpServletResponse response){
        try {
            response.sendRedirect("/admin/index.do");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("online.do")
    @ResponseBody
    public Set getOnlineList(HttpSession session){
        ServletContext context = session.getServletContext();
        Set list = (Set) context.getAttribute("onlineList");
        return list;
    }

}
