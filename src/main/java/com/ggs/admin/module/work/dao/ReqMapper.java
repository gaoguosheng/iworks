package com.ggs.admin.module.work.dao;

import com.ggs.admin.module.work.model.ReqModel;

import java.util.List;
import java.util.Map;

public interface ReqMapper {
    public void add(ReqModel model);
    public List<Map> query(ReqModel model);
    public void del(ReqModel model);
    public ReqModel queryById(ReqModel model);
    public void update(ReqModel model);
}
