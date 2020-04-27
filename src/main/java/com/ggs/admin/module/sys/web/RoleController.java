package com.ggs.admin.module.sys.web;

import com.ggs.admin.module.sys.model.PageModel;
import com.ggs.admin.module.sys.model.PermissionModel;
import com.ggs.admin.module.sys.model.RoleModel;
import com.ggs.admin.module.sys.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin/role/")
public class RoleController extends BaseController {

    @Autowired
    private SysService service;
    private final String path=webRoot+"role";

    @RequestMapping("index.do")
    public String index(){
        return path+"/index";
    }

    @RequestMapping("query.do")
    @ResponseBody
    public PageModel query(RoleModel roleModel){
        PageModel pageModel = service.queryRole(roleModel);
        return pageModel;
    }

    @RequestMapping("add.do")
    @ResponseBody
    public void add(RoleModel roleModel){
        service.addRole(roleModel);
    }

    @RequestMapping("update.do")
    @ResponseBody
    public void update(RoleModel roleModel){
        service.updateRole(roleModel);
    }

    @RequestMapping("del.do")
    @ResponseBody
    public void del(RoleModel roleModel){
        service.delRole(roleModel);
    }

    @RequestMapping("add.html")
    public String addHtml(ModelMap map){
        List permissions = service.getRolePermissions(new RoleModel());
        map.addAttribute("permissions",permissions);
        return path+"/add";
    }

    @RequestMapping("edit.html")
    public String editHtml(ModelMap map,RoleModel roleModel){
        RoleModel role = service.getRole(roleModel);
        map.addAttribute("role",role);
        return path+"/edit";
    }

    @RequestMapping("getPermissions.do")
    @ResponseBody
    public List<PermissionModel> getPermissions(RoleModel roleModel){
        List<PermissionModel> permissions = service.getRolePermissions(new RoleModel());
        if(roleModel!=null   && roleModel.getId()>0){
            List<PermissionModel>userPermissions = service.getRolePermissions(roleModel);
            for(PermissionModel p :permissions){
                for(PermissionModel userP :userPermissions){
                    if(userP.getId()==p.getId()){
                        p.setChecked(true);
                    }
                }
            }
        }
        return permissions;
    }


}
