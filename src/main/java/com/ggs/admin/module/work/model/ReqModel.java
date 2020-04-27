package com.ggs.admin.module.work.model;

import com.ggs.admin.module.sys.model.BaseModel;

public class ReqModel extends BaseModel {
    private Integer id;
    private Integer project_id;
    private String title;
    private String content;
    private String status;
    private String priority;
    private String req_user;
    private String req_finish_date;
    private String dev_user;
    private String dev_start_date;
    private String dev_end_date;
    private String dev_finish_date;
    private String test_user;
    private String test_start_date;
    private String test_end_date;
    private String test_finish_date;
    private String usercode;

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getReq_user() {
        return req_user;
    }

    public void setReq_user(String req_user) {
        this.req_user = req_user;
    }

    public String getReq_finish_date() {
        return req_finish_date;
    }

    public void setReq_finish_date(String req_finish_date) {
        this.req_finish_date = req_finish_date;
    }

    public String getDev_user() {
        return dev_user;
    }

    public void setDev_user(String dev_user) {
        this.dev_user = dev_user;
    }

    public String getDev_start_date() {
        return dev_start_date;
    }

    public void setDev_start_date(String dev_start_date) {
        this.dev_start_date = dev_start_date;
    }

    public String getDev_end_date() {
        return dev_end_date;
    }

    public void setDev_end_date(String dev_end_date) {
        this.dev_end_date = dev_end_date;
    }

    public String getDev_finish_date() {
        return dev_finish_date;
    }

    public void setDev_finish_date(String dev_finish_date) {
        this.dev_finish_date = dev_finish_date;
    }

    public String getTest_user() {
        return test_user;
    }

    public void setTest_user(String test_user) {
        this.test_user = test_user;
    }

    public String getTest_start_date() {
        return test_start_date;
    }

    public void setTest_start_date(String test_start_date) {
        this.test_start_date = test_start_date;
    }

    public String getTest_end_date() {
        return test_end_date;
    }

    public void setTest_end_date(String test_end_date) {
        this.test_end_date = test_end_date;
    }

    public String getTest_finish_date() {
        return test_finish_date;
    }

    public void setTest_finish_date(String test_finish_date) {
        this.test_finish_date = test_finish_date;
    }
}
