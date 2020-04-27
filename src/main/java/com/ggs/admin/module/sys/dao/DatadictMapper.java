package com.ggs.admin.module.sys.dao;

import com.ggs.admin.module.sys.model.DatadictModel;
import com.ggs.admin.module.sys.model.OrgModel;
import com.ggs.admin.module.sys.model.UserModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by gswon on 2017/8/1.
 */
@Component
public interface DatadictMapper {
    public List<DatadictModel> query(DatadictModel datadictModel);
    public Integer queryCount(DatadictModel datadictModel);
    public void add(DatadictModel datadictModel);
    public void update(DatadictModel datadictModel);
    public void del(DatadictModel datadictModel);
    public DatadictModel getDatadictById(DatadictModel datadictModel);
}
