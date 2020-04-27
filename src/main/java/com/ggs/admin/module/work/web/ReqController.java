package com.ggs.admin.module.work.web;

import com.ggs.admin.comm.DDConst;
import com.ggs.admin.module.sys.model.PageModel;
import com.ggs.admin.module.sys.model.UserModel;
import com.ggs.admin.module.sys.service.SysService;
import com.ggs.admin.module.sys.web.BaseController;
import com.ggs.admin.module.work.model.ReqModel;
import com.ggs.admin.module.work.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * 需求管理
 * */
@Controller
@RequestMapping("/admin/req/")
public class ReqController extends BaseController {
    private final String path=webRoot+"req";

    @Autowired
    private WorkService service;

    @Autowired
    private SysService sysService;


    @RequestMapping("index.do")
    public String index(ModelMap map, @SessionAttribute UserModel admin) {
        map.addAttribute("admin",admin);
        map.addAttribute("projects",service.queryAllProjects());
        map.addAttribute("reqstatus",sysService.getDatadicts(DDConst.REQSTATUS));
        map.addAttribute("userList",sysService.queryAllUsers());
        return path+"/index";
    }

    @RequestMapping("search.do")
    public String search(ModelMap map, @SessionAttribute UserModel admin) {
        map.addAttribute("admin",admin);
        map.addAttribute("projects",service.queryAllProjects());
        map.addAttribute("reqstatus",sysService.getDatadicts(DDConst.REQSTATUS));
        map.addAttribute("userList",sysService.queryAllUsers());
        return path+"/search";
    }

    @RequestMapping("query.do")
    @ResponseBody
    public PageModel query(ReqModel model, @SessionAttribute UserModel admin){
        PageModel pageModel = service.queryReq(model);
        return  pageModel;
    }

    @RequestMapping("add.do")
    public String add(ModelMap map, @SessionAttribute UserModel admin) {
        map.addAttribute("admin",admin);
        map.addAttribute("projects",service.queryAllProjects());
        //产品人员
        map.addAttribute("userList",sysService.queryUsersByPost("01"));
        map.addAttribute("priority_list",sysService.getDatadicts(DDConst.PRIORITY));
        return path+"/add";
    }

    @RequestMapping("save.do")
    @ResponseBody
    public void save(ReqModel model, @SessionAttribute UserModel  admin){
        model.setCreateuser(admin.getUsercode());
        service.addReq(model);
    }

    @RequestMapping("del.do")
    @ResponseBody
    public void del(ReqModel model, @SessionAttribute UserModel  admin){
        service.delReq(model);
    }

    @RequestMapping("edit.do")
    public String edit(ModelMap map,ReqModel model, @SessionAttribute UserModel admin) {
        map.addAttribute("admin",admin);
        map.addAttribute("projects",service.queryAllProjects());
        map.addAttribute("reqUserList",sysService.queryUsersByPost("01"));
        map.addAttribute("devUserList",sysService.queryUsersByPost("02"));
        map.addAttribute("testUserList",sysService.queryUsersByPost("03"));
        map.addAttribute("model",service.queryReqById(model));
        map.addAttribute("reqstatus",sysService.getDatadicts(DDConst.REQSTATUS));
        map.addAttribute("priority_list",sysService.getDatadicts(DDConst.PRIORITY));
        return path+"/edit";
    }

    @RequestMapping("update.do")
    @ResponseBody
    public void update(ReqModel model, @SessionAttribute UserModel  admin){
        model.setUpdateuser(admin.getUsercode());
        service.updateReq(model);
    }

}
