package com.ggs.admin.module.sys.model;

public class RoleModel extends BaseModel {
    private int id;
    private String name;
    private String permissionids[];
    private String permissionid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getPermissionids() {
        return permissionids;
    }

    public void setPermissionids(String[] permissionids) {
        this.permissionids = permissionids;
    }

    public String getPermissionid() {
        return permissionid;
    }

    public void setPermissionid(String permissionid) {
        this.permissionid = permissionid;
    }
}
