package com.ggs.admin.module.sys.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ggs.admin.module.sys.model.PermissionModel;
import com.ggs.admin.module.sys.service.SysService;
import com.ggs.admin.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin/permission/")
public class PermissionController  extends  BaseController{

    @Autowired
    private SysService service;
    private final String path=webRoot+"permission";

    @RequestMapping("index.do")
    public String index(){
        return path+"/index";
    }

    @RequestMapping("query.do")
    @ResponseBody
    public List query(PermissionModel permissionModel){
        List list = service.queryPermission(permissionModel);
        return list;
    }

    @RequestMapping("add.do")
    @ResponseBody
    public void add(PermissionModel permissionModel){
        service.addPermission(permissionModel);
    }

    @RequestMapping("update.do")
    @ResponseBody
    public void update(PermissionModel permissionModel){
        service.updatePermission(permissionModel);
    }

    @RequestMapping("del.do")
    @ResponseBody
    public void del(PermissionModel permissionModel){
        service.delPermission(permissionModel);
    }

    @RequestMapping("getNewId.do")
    @ResponseBody
    public String getNewId(){
        String json ="{}";
        PermissionModel permissionModel = new PermissionModel();
        permissionModel.setId(service.getNewPermissionId());
        try {
            json = JsonUtil.beanToJson(permissionModel);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

}
