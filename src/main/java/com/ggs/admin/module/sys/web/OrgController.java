package com.ggs.admin.module.sys.web;

import com.ggs.admin.module.sys.model.OrgModel;
import com.ggs.admin.module.sys.model.UserModel;
import com.ggs.admin.module.sys.service.SysService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequestMapping("/admin/org/")
public class OrgController extends  BaseController{

    @Autowired
    private SysService service;
    private final String path=webRoot+"org";

    @RequestMapping("index.do")
    public String index(){
        return path+"/index";
    }

    @RequestMapping("query.do")
    @ResponseBody
    public List query(OrgModel orgModel, @SessionAttribute UserModel admin){
        orgModel.setOrgid(admin.getOrgid());
        List list = service.queryOrg(orgModel);
        return  list;
    }

    @RequestMapping("add.do")
    @ResponseBody
    public void add(OrgModel orgModel){
        service.addOrg(orgModel);
    }

    @RequestMapping("update.do")
    @ResponseBody
    public void update(OrgModel orgModel){
        service.updateOrg(orgModel);
    }

    @RequestMapping("del.do")
    @ResponseBody
    public void del(OrgModel orgModel){
        service.delOrg(orgModel);
    }

    @RequestMapping("getNewId.do")
    @ResponseBody
    public OrgModel getNewId(){
        OrgModel orgModel = new OrgModel();
        orgModel.setId(service.getNewOrgId());
        return  orgModel;
    }

    @RequestMapping("queryAll.do")
    @ResponseBody
    public List queryAll(OrgModel orgModel){
        List list = service.queryOrg(orgModel);
        return  list;
    }

}
