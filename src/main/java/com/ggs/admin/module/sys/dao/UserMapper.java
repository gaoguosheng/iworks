package com.ggs.admin.module.sys.dao;

import com.ggs.admin.module.sys.model.PermissionModel;
import com.ggs.admin.module.sys.model.RoleModel;
import com.ggs.admin.module.sys.model.UserModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by gswon on 2017/8/1.
 */
@Component
public interface UserMapper {
    public List<UserModel> getUser(UserModel userModel);
    public void updateUserPassword(UserModel userModel);
    public List<PermissionModel> getUserPermissions(UserModel userModel);
    public List<UserModel> query(UserModel userModel);
    public Integer queryCount(UserModel userModel);
    public void add(UserModel userModel);
    public void update(UserModel userModel);
    public void del(UserModel userModel);
    public List<RoleModel>getRoles(UserModel userModel);
    public void addUserRole(UserModel userModel);
    public void delUserRole(UserModel userModel);
    public List<Map> queryAll(Map params);
}
