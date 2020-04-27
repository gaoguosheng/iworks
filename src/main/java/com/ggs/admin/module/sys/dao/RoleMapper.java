package com.ggs.admin.module.sys.dao;

import com.ggs.admin.module.sys.model.PermissionModel;
import com.ggs.admin.module.sys.model.RoleModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by gswon on 2017/8/1.
 */
@Component
public interface RoleMapper {
    public List<PermissionModel> getRolePermissions(RoleModel roleModel);
    public List<RoleModel> query(RoleModel roleModel);
    public Integer queryCount(RoleModel roleModel);
    public void add(RoleModel roleModel);
    public void update(RoleModel roleModel);
    public void del(RoleModel roleModel);
    public void addRolePermission(RoleModel roleModel);
    public void delRolePermission(RoleModel roleModel);
    public Integer getNewRoleid();
    public RoleModel getRole(RoleModel roleModel);
}
