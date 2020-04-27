package com.ggs.admin.module.sys.dao;

import com.ggs.admin.module.sys.model.OrgModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by gswon on 2017/8/1.
 */
@Component
public interface OrgMapper {
    public List<OrgModel> query(OrgModel orgModel);
    public void add(OrgModel orgModel);
    public void update(OrgModel orgModel);
    public void del(OrgModel orgModel);
    public Integer getNewId();
}
