package com.ggs.admin.module.sys.dao;

import com.ggs.admin.module.sys.model.PermissionModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by gswon on 2017/8/1.
 */
@Component
public interface PermissionMapper {
    public List<PermissionModel> query(PermissionModel permissionModel);
    public void add(PermissionModel permissionModel);
    public void update(PermissionModel permissionModel);
    public void del(PermissionModel permissionModel);
    public Integer getNewPermissionId();
}
