package com.ggs.admin.module.sys.model;

public class DatadictModel {
    private int id;
    private String code;
    private String name;
    private String ddtype;
    private String ddtypename;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDdtypename() {
        return ddtypename;
    }

    public void setDdtypename(String ddtypename) {
        this.ddtypename = ddtypename;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDdtype() {
        return ddtype;
    }

    public void setDdtype(String ddtype) {
        this.ddtype = ddtype;
    }
}
