package com.ggs.admin.module.sys.web;

import com.ggs.admin.module.sys.model.PageModel;
import com.ggs.admin.module.sys.model.UserModel;
import com.ggs.admin.module.sys.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequestMapping("/admin/user/")
public class UserController extends  BaseController {

    private final String path=webRoot+"user";

    @Autowired
    private SysService service;




    @RequestMapping("index.do")
    public String index(){
        return path+"/index";
    }
    @RequestMapping("query.do")
    @ResponseBody
    public PageModel query(UserModel userModel, @SessionAttribute UserModel admin){
        userModel.setOrgid(admin.getOrgid());
        PageModel pageModel = service.queryUser(userModel);
        return  pageModel;
    }

    @RequestMapping("add.do")
    @ResponseBody
    public void add(UserModel userModel){
        service.addUser(userModel);
    }

    @RequestMapping("update.do")
    @ResponseBody
    public void update(UserModel userModel){
        service.updateUser(userModel);
    }

    @RequestMapping("del.do")
    @ResponseBody
    public void del(UserModel userModel){
        service.delUser(userModel);
    }

    @RequestMapping("add.html")
    public String addHtml(ModelMap map,@SessionAttribute UserModel admin){
        List roles = service.getRoles(new UserModel());
        map.addAttribute("roles",roles);
        map.addAttribute("admin",admin);
        return path+"/add";
    }

    @RequestMapping("edit.html")
    public String editHtml(ModelMap map,UserModel userModel,@SessionAttribute UserModel admin){
        List roles = service.getRoles(new UserModel());
        List userRoles = service.getRoles(userModel);
        map.addAttribute("roles",roles);
        UserModel user = service.getUser(userModel);
        map.addAttribute("user",user);
        map.addAttribute("userRole",userRoles.get(0));
        map.addAttribute("admin",admin);
        return path+"/edit";
    }

    @RequestMapping("resetPwd.do")
    @ResponseBody
    public void resetPwd(UserModel userModel){
        userModel.setPassword("123456");
        service.updateUserPassword(userModel);
    }

}
