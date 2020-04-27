package com.ggs.admin.module.work.dao;

import com.ggs.admin.module.work.model.CmsWorkModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by gswon on 2017/8/1.
 */
@Component
public interface CmsWorkMapper {
    public List<Map<String,Object>> query(CmsWorkModel cmsWorkModel);
    public Integer queryCount(CmsWorkModel cmsWorkModel);
    public void add(CmsWorkModel cmsWorkModel);
    public void update(CmsWorkModel cmsWorkModel);
    public void del(CmsWorkModel cmsWorkModel);
    public List<Map<String,Object>>getWorkFinishStatus(CmsWorkModel cmsWorkModel);
    public List<Map<String,Object>>getWorkFreeStatus(CmsWorkModel cmsWorkModel);
    public List<Map<String,Object>>getWeekWorkFinishStatus(CmsWorkModel cmsWorkModel);
    public List<Map<String,Object>>getOrgTotal(CmsWorkModel cmsWorkModel);
    public List<Map<String,Object>>getUserTotal(CmsWorkModel cmsWorkModel);
    public List<Map<String,Object>>userRanking(CmsWorkModel cmsWorkModel);
    public void log(CmsWorkModel model);
    public List<Map<String,Object>> queryLog(CmsWorkModel model);
    public void updatePlanFinishDate(CmsWorkModel cmsWorkModel);
    public List<Map<String,Object>> queryBug(CmsWorkModel cmsWorkModel);
    public void updateBug(CmsWorkModel cmsWorkModel);
    public List<Map<String,Object>> queryAllProjects();
    public List<Map<String,Object>> queryProjectUsed(CmsWorkModel cmsWorkModel);

}
