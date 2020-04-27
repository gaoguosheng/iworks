package com.ggs.admin.module.work.dao;

import com.ggs.admin.module.work.model.WorkGoalModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by gswon on 2017/8/1.
 */
@Component
public interface WorkGoalMapper {
    public List<Map<String,Object>> query(WorkGoalModel model);
    public Integer queryCount(WorkGoalModel model);
    public void add(WorkGoalModel model);
    public void update(WorkGoalModel model);
    public void del(WorkGoalModel model);
    public List<Map<String,Object>> queryMonthGoal(WorkGoalModel model);
    public void log(WorkGoalModel model);
    public List<Map<String,Object>> queryLog(WorkGoalModel model);
    public List<Map<String,Object>> countUnfinishWorks(WorkGoalModel model);
}
