package com.ggs.admin.module.work.dao;

import com.ggs.admin.module.work.model.ProblemModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by gswon on 2017/8/1.
 */
@Component
public interface ProblemMapper {
    public List<Map<String,Object>> query(ProblemModel problemModel);
    public Integer queryCount(ProblemModel problemModel);
    public void add(ProblemModel problemModel);
    public void update(ProblemModel problemModel);
    public void del(ProblemModel problemModel);
    public ProblemModel queryById(ProblemModel problemModel);
    public List<Map<String,Object>>getWeekProblemFinishStatus(ProblemModel problemModel);
    public List<Map<String,Object>>causeTypeTotal(ProblemModel problemModel);
    public List<Map<String,Object>>getOrgTotal(ProblemModel problemModel);
    public List<Map<String,Object>>getUserTotal(ProblemModel problemModel);
}
