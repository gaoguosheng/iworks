package com.ggs.admin.module.sys.service;

import com.ggs.admin.module.sys.dao.*;
import com.ggs.admin.module.sys.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SysService {

    @Autowired
    private UserMapper userMapper;//用户dao

    @Autowired
    private RoleMapper roleMapper;//角色dao

    @Autowired
    private PermissionMapper permissionMapper;//权限dao

    @Autowired
    private OrgMapper orgMapper;//组织dao

    @Autowired
    private DatadictMapper datadictMapper;//数据字典dao


    /**
     * 获取用户信息
     * */
    public UserModel getUser(UserModel userModel){
        List<UserModel> userList = userMapper.getUser(userModel);
        if(userList.size()>0){
            return userList.get(0);
        }
        return null;
    }

    /**
     * 更新密码
     * */
    public void updateUserPassword(UserModel userModel){
        userMapper.updateUserPassword(userModel);
    }

    /**
     * 获取权限列表
     * */
    public List<PermissionModel> getUserPermissions(UserModel userModel){
        return userMapper.getUserPermissions(userModel);
    }

    /**
     * 查询用户
     * */
    public PageModel queryUser(UserModel userModel){
        String code="0";
        String msg="";
        List list = null;
        Integer counter=0;
        try {
            list = userMapper.query(userModel);
            counter= userMapper.queryCount(userModel);
        }catch (Exception e){
            e.printStackTrace();
            code="1";
            msg="查询失败";
        }
        PageModel pageModel =new PageModel(code,msg,counter,list);
        return pageModel;
    }

    /**
     * 增加用户
     * */
    public void addUser(UserModel userModel){
        userMapper.add(userModel);
        userMapper.delUserRole(userModel);
        userMapper.addUserRole(userModel);

    }
    /**
     * 修改用户
     * */
    public void updateUser(UserModel userModel){
        userMapper.update(userModel);
        userMapper.delUserRole(userModel);
        userMapper.addUserRole(userModel);
    }

    /**
     * 删除用户
     * */
    public void delUser(UserModel userModel) {
        userMapper.del(userModel);
        userMapper.delUserRole(userModel);
    }

    /**
     * 获取角色
     * */
    public List<RoleModel> getRoles(UserModel userModel){
        return userMapper.getRoles(userModel);
    }


    /**
     * 查询角色
     * */
    public PageModel queryRole(RoleModel roleModel){
        String code="0";
        String msg="";
        List list = null;
        Integer counter=0;
        try {
            list = roleMapper.query(roleModel);
            counter= roleMapper.queryCount(roleModel);
        }catch (Exception e){
            e.printStackTrace();
            code="1";
            msg="查询失败";
        }
        PageModel pageModel =new PageModel(code,msg,counter,list);
        return pageModel;
    }

    /**
     * 增加角色
     * */
    public void addRole(RoleModel roleModel){
        int id = roleMapper.getNewRoleid();
        roleModel.setId(id);
        roleMapper.add(roleModel);
        roleMapper.delRolePermission(roleModel);
        for(String pid : roleModel.getPermissionids()){
            roleModel.setPermissionid(pid);
            roleMapper.addRolePermission(roleModel);
        }
    }
    /**
     * 修改用户
     * */
    public void updateRole(RoleModel roleModel){
        roleMapper.update(roleModel);
        roleMapper.delRolePermission(roleModel);
        for(String pid : roleModel.getPermissionids()){
            roleModel.setPermissionid(pid);
            roleMapper.addRolePermission(roleModel);
        }
    }

    /**
     * 删除用户
     * */
    public void delRole(RoleModel roleModel) {
        roleMapper.del(roleModel);
        roleMapper.delRolePermission(roleModel);
    }

    /**
     * 查询权限
     * */
    public List<PermissionModel> getRolePermissions(RoleModel roleModel){
        return roleMapper.getRolePermissions(roleModel);
    }

    /**
     * 获取一个角色
     * */
    public RoleModel getRole(RoleModel roleModel){
        return roleMapper.getRole(roleModel);
    }

    /**
     * 查询权限
     * */
    public List<PermissionModel> queryPermission(PermissionModel permissionModel){
        return permissionMapper.query(permissionModel);
    }
    /**
     * 添加菜单
     * */
    public void addPermission(PermissionModel permissionModel){ permissionMapper.add(permissionModel);}

    /**
     * 修改菜单
     * */
    public void updatePermission(PermissionModel permissionModel){permissionMapper.update(permissionModel);}

    /**
     * 删除菜单
     * */
    public void delPermission(PermissionModel permissionModel){permissionMapper.del(permissionModel);}

    /**
     * 获取最新权限 id
     * */
    public Integer getNewPermissionId(){
        return permissionMapper.getNewPermissionId();
    }


    /**
     * 查询组织
     * */
    public List<OrgModel> queryOrg(OrgModel orgModel){
        return orgMapper.query(orgModel);
    }
    /**
     * 添加组织
     * */
    public void addOrg(OrgModel orgModel){ orgMapper.add(orgModel);}

    /**
     * 修改组织
     * */
    public void updateOrg(OrgModel orgModel){orgMapper.update(orgModel);}

    /**
     * 删除组织
     * */
    public void delOrg(OrgModel orgModel){orgMapper.del(orgModel);}

    /**
     * 获取最新组织 id
     * */
    public Integer getNewOrgId(){
        return orgMapper.getNewId();
    }

    /**
     * 获取数据字典
     * */
    public List<DatadictModel> getDatadicts(String ddtype){
        DatadictModel datadictModel = new DatadictModel();
        datadictModel.setDdtype(ddtype);
        return datadictMapper.query(datadictModel);
    }

    /**
     * 查询数据字典
     * */
    public PageModel queryDatadict(DatadictModel datadictModel){
        String code="0";
        String msg="";
        List list = null;
        Integer counter=0;
        try {
            list = datadictMapper.query(datadictModel);
            counter= datadictMapper.queryCount(datadictModel);
        }catch (Exception e){
            e.printStackTrace();
            code="1";
            msg="查询失败";
        }
        PageModel pageModel =new PageModel(code,msg,counter,list);
        return pageModel;
    }

    /**
     * 添加数据字典
     * */
    public void addDatadict(DatadictModel datadictModel){ datadictMapper.add(datadictModel);}

    /**
     * 修改数据字典
     * */
    public void updateDatadict(DatadictModel datadictModel){datadictMapper.update(datadictModel);}

    /**
     * 删除数据字典
     * */
    public void delDatadict(DatadictModel datadictModel){datadictMapper.del(datadictModel);}

    /**
     * 通过id获取数据字典
     * */
    public DatadictModel getDatadictById(DatadictModel datadictModel){
        return datadictMapper.getDatadictById(datadictModel);
    }

    public List<Map> queryAllUsers(){
        Map params = new HashMap();
        return queryUsers(params);
    }

    public List<Map> queryUsers(Map params){
        return userMapper.queryAll(params);
    }

    /**
     * 通过岗位查询用户
     * */
    public List<Map> queryUsersByPost(String post){
        Map params = new HashMap();
        params.put("post",post);
        return queryUsers(params);
    }



}
