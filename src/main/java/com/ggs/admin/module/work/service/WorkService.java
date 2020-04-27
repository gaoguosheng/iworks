package com.ggs.admin.module.work.service;

import com.ggs.admin.module.sys.model.PageModel;
import com.ggs.admin.module.work.dao.*;
import com.ggs.admin.module.work.model.*;
import com.ggs.admin.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class WorkService{
    @Autowired
    private CmsWorkMapper cmsWorkMapper;//周报dao

    @Autowired
    private ProblemMapper problemMapper;//问题dao

    @Autowired
    private WorkGoalMapper workGoalMapper;//工作目标dao

    @Autowired
    private OrderMapper orderMapper;//订单

    @Autowired
    private ReqMapper reqMapper;//需求

    /**
     * 查询周报
     * */
    public PageModel queryCmsWork(CmsWorkModel cmsWorkModel){
        String code="0";
        String msg="";
        List list = null;
        Integer counter=0;

        PageInfo<Map> pageInfo=null;


        try {

            PageHelper.startPage(cmsWorkModel.getPage(),cmsWorkModel.getLimit());
            list = cmsWorkMapper.query(cmsWorkModel);
            pageInfo = new PageInfo<Map>(list);


            //counter= cmsWorkMapper.queryCount(cmsWorkModel);
        }catch (Exception e){
            e.printStackTrace();
            code="1";
            msg="查询用户失败";
        }
        PageModel pageModel =new PageModel(code,msg,(int)pageInfo.getTotal(),pageInfo.getList());
        return pageModel;
    }

    /**
     * 添加周报
     * */
    public void addCmsWork(CmsWorkModel cmsWorkModel){
        cmsWorkMapper.add(cmsWorkModel);
    }

    /**
     * 更新周报
     * */
    public void updateCmsWork(CmsWorkModel cmsWorkModel){
        cmsWorkMapper.update(cmsWorkModel);
        cmsWorkMapper.updatePlanFinishDate(cmsWorkModel);
        if(cmsWorkModel.getRemark()!=null){
            cmsWorkModel.setCreateuser(cmsWorkModel.getUpdateuser());
            cmsWorkMapper.log(cmsWorkModel);
        }
    }


    /**
     * 删除周报
     * */
    public void delCmsWork(CmsWorkModel cmsWorkModel){
        cmsWorkMapper.del(cmsWorkModel);
    }



    /**
     * 获取工作完成情况
     * */
    public List<Map<String,Object>>getWorkFinishStatus(CmsWorkModel cmsWorkModel){
        return cmsWorkMapper.getWorkFinishStatus(cmsWorkModel);
    }

    /**
     * 获取工作空闲状态
     * */
    public PageModel getWorkFreeStatus(CmsWorkModel cmsWorkModel) {
        String code="0";
        String msg="";
        List<Map<String,Object>> list = null;
        Integer counter=0;
        try {
            list = cmsWorkMapper.getWorkFreeStatus(cmsWorkModel);
            List <Map<String,Object>> workList = cmsWorkMapper.query(cmsWorkModel);
            List<String> dateList = DateUtil.findDates(cmsWorkModel.getDatestart(),cmsWorkModel.getDateend());

            for(Map<String,Object> map:list){
                String usercode = (String) map.get("usercode");

                        map.put("freedays",0);

                        for(int i=0;i<dateList.size();i++){
                            if(map.get("day"+i)==null){
                                map.put("day"+i,0);
                            }
                            String date = dateList.get(i);
                            for(Map<String,Object>work:workList){
                                String touser = (String)work.get("touser");
                                if(usercode.equals(touser)){
                                    //找到对应任务
                                    String createdate = (String)work.get("createdate");
                                    String planfinishdate = (String)work.get("planfinishdate");

                                    List <String> planDateList = DateUtil.findDates(createdate,planfinishdate);

                                    for(String planDate:planDateList){
                                        if(planDate.equals(date)){
                                            Integer dayCount = (Integer) map.get("day"+i);
                                            map.put("day"+i,++dayCount);
                                            break;
                                        }
                                    }
                                }
                          }
                            if(((Integer)map.get("day"+i))==0){
                                Integer dayCount = (Integer) map.get("freedays");
                                map.put("freedays",++dayCount);
                            }


                        }
            }

            counter= list.size();
        }catch (Exception e){
            e.printStackTrace();
            code="1";
            msg="查询失败";
        }
        PageModel pageModel =new PageModel(code,msg,counter,list);
        return pageModel;

    }

    /**
     * 获取工作完成情况
     * */
    public List<Map<String,Object>>getWeekWorkFinishStatus(CmsWorkModel cmsWorkModel){
        return cmsWorkMapper.getWeekWorkFinishStatus(cmsWorkModel);
    }


    /**
     * 问题管理
     * */
    public PageModel queryProblem(ProblemModel problemModel){
        String code="0";
        String msg="";
        List list = null;
        Integer counter=0;
        try {
            list = problemMapper.query(problemModel);
            counter= problemMapper.queryCount(problemModel);
        }catch (Exception e){
            e.printStackTrace();
            code="1";
            msg="查询用户失败";
        }
        PageModel pageModel =new PageModel(code,msg,counter,list);
        return pageModel;
    }
    /**
     * 添加问题
     * */
    public void addProblem(ProblemModel problemModel){
        problemMapper.add(problemModel);
    }

    /**
     * 更新问题
     * */
    public void updateProblem(ProblemModel problemModel){
        problemMapper.update(problemModel);
    }


    /**
     * 删除问题
     * */
    public void delProblem(ProblemModel problemModel){
        problemMapper.del(problemModel);
    }

    /**
     * 查找一个问题通过id
     * */
    public ProblemModel getProblemById(ProblemModel problemModel){
        return problemMapper.queryById(problemModel);
    }


    /**
     * 获取问题完成情况
     * */
    public List<Map<String,Object>>getWeekProblemFinishStatus(ProblemModel problemModel){
        return problemMapper.getWeekProblemFinishStatus(problemModel);
    }

    /**
     * 问题原因分析
     * */
    public List<Map<String,Object>>causeTypeTotal(ProblemModel problemModel){
        return problemMapper.causeTypeTotal(problemModel);
    }

    /**
     * 获取工作完成情况
     * */
    public List<Map<String,Object>>getWorkOrgTotal(CmsWorkModel cmsWorkModel){
        return cmsWorkMapper.getOrgTotal(cmsWorkModel);
    }

    /**
     * 获取工作完成情况
     * */
    public List<Map<String,Object>>getWorkUserTotal(CmsWorkModel cmsWorkModel){
        return cmsWorkMapper.getUserTotal(cmsWorkModel);
    }


    /**
     * 获取工作完成情况
     * */
    public List<Map<String,Object>>getProblemOrgTotal(ProblemModel problemModel){
        return problemMapper.getOrgTotal(problemModel);
    }

    /**
     * 获取工作完成情况
     * */
    public List<Map<String,Object>>getProblemUserTotal(ProblemModel problemModel){
        return problemMapper.getUserTotal(problemModel);
    }

    /**
     * 获得用户排名
     * */
    public PageModel userRanking(CmsWorkModel cmsWorkModel) {
        String code="0";
        String msg="";
        List<Map<String,Object>> list = null;
        Integer counter=0;
        try {
            list = cmsWorkMapper.userRanking(cmsWorkModel);
            for(int i=0;i<list.size();i++){
                Map<String,Object> map = list.get(i);
                map.put("rownum",i+1);
            }


            counter= list.size();
        }catch (Exception e){
            e.printStackTrace();
            code="1";
            msg="查询失败";
        }
        PageModel pageModel =new PageModel(code,msg,counter,list);
        return pageModel;

    }


    /**
     * 查询目标
     * */
    public PageModel queryWorkGoal(WorkGoalModel model){
        String code="0";
        String msg="";
        List list = null;
        Integer counter=0;
        PageInfo<Map> pageInfo=null;
        try {

            PageHelper.startPage(model.getPage(),model.getLimit());
            list = workGoalMapper.query(model);
            pageInfo = new PageInfo<Map>(list);
            //counter= workGoalMapper.queryCount(model);
        }catch (Exception e){
            e.printStackTrace();
            code="1";
            msg="查询用户失败";
        }
        PageModel pageModel =new PageModel(code,msg,(int)pageInfo.getTotal(),pageInfo.getList());
        return pageModel;
    }

    /**
     * 添加目标
     * */
    public void addWorkGoal(WorkGoalModel model){
        workGoalMapper.add(model);
    }

    /**
     * 更新目标
     * */
    public void updateWorkGoal(WorkGoalModel model){
        workGoalMapper.update(model);
        if(model.getRemark()!=null){
            model.setCreateuser(model.getUpdateuser());
            workGoalMapper.log(model);
        }
    }


    /**
     * 删除目标
     * */
    public void delWorkGoal(WorkGoalModel model){
        workGoalMapper.del(model);
    }


    /**
     * 查询当月目标
     * */
    public List<Map<String,Object>> queryMonthGoal(WorkGoalModel model){
        return workGoalMapper.queryMonthGoal(model);
    }

    /**
     * 查询日志
     * */
    public List<Map<String,Object>> queryGoalLog(WorkGoalModel model){
        return workGoalMapper.queryLog(model);
    }

    /**
     * 查询日志
     * */
    public List<Map<String,Object>> queryWorkLog(CmsWorkModel model){
        return cmsWorkMapper.queryLog(model);
    }


    /**
     * 统计待办事项
     * */
    public List<Map<String,Object>> countUnfinishWorks(WorkGoalModel model){
        return workGoalMapper.countUnfinishWorks(model);
    }



    /**
     * 查询订单
     * */
    public PageModel queryOrder(OrderModel model){
        String code="0";
        String msg="";
        List list = null;
        PageInfo<Map> pageInfo=null;
        try {

            PageHelper.startPage(model.getPage(),model.getLimit());
            list = orderMapper.query(model);
            pageInfo = new PageInfo<Map>(list);

        }catch (Exception e){
            e.printStackTrace();
            code="1";
            msg="查询用户失败";
        }
        PageModel pageModel =new PageModel(code,msg,(int)pageInfo.getTotal(),pageInfo.getList());
        return pageModel;
    }

    /**
     * 查询订单
     * */
    public PageModel queryProducts(OrderModel model){
        String code="0";
        String msg="";
        List list = null;
        PageInfo<Map> pageInfo=null;
        try {

            PageHelper.startPage(model.getPage(),model.getLimit());
            list = orderMapper.queryProducts(model);
            pageInfo = new PageInfo<Map>(list);

        }catch (Exception e){
            e.printStackTrace();
            code="1";
            msg="查询用户失败";
        }
        PageModel pageModel =new PageModel(code,msg,(int)pageInfo.getTotal(),pageInfo.getList());
        return pageModel;
    }

    /**
     * 添加订单
     * */
    public void addOrder(OrderModel model){
        orderMapper.add(model);
    }

    /**
     * 添加订单
     * */
    public void delOrder(OrderModel model){
        orderMapper.del(model);
    }

    /**
     * 查询订单
     * */
    public PageModel queryOrderTotal(OrderModel model){
        String code="0";
        String msg="";
        List list = null;
        PageInfo<Map> pageInfo=null;
        try {

            PageHelper.startPage(model.getPage(),model.getLimit());
            list = orderMapper.queryTotal(model);
            pageInfo = new PageInfo<Map>(list);

        }catch (Exception e){
            e.printStackTrace();
            code="1";
            msg="查询用户失败";
        }
        PageModel pageModel =new PageModel(code,msg,(int)pageInfo.getTotal(),pageInfo.getList());
        return pageModel;
    }

    /**
     * 添加订单
     * */
    public void addLastOrder(OrderModel model){
        orderMapper.addLast(model);
    }

    /**
     * 添加订单
     * */
    public void addOrderMaster(OrderModel model){
        orderMapper.addOrderMaster(model);
    }

    /**
     * 查询主订单
     * */
    public List queryOrders(OrderModel model){
        PageHelper.startPage(1,30);
        List  list = orderMapper.queryOrders(model);
        PageInfo<Map> pageInfo=new PageInfo<Map>(list);
        return pageInfo.getList();
    }


    /**
     * 查询周报
     * */
    public PageModel queryBug(CmsWorkModel cmsWorkModel){
        String code="0";
        String msg="";
        List list = null;
        Integer counter=0;

        PageInfo<Map> pageInfo=null;


        try {

            PageHelper.startPage(cmsWorkModel.getPage(),cmsWorkModel.getLimit());
            list = cmsWorkMapper.queryBug(cmsWorkModel);
            pageInfo = new PageInfo<Map>(list);


            //counter= cmsWorkMapper.queryCount(cmsWorkModel);
        }catch (Exception e){
            e.printStackTrace();
            code="1";
            msg="查询用户失败";
        }
        PageModel pageModel =new PageModel(code,msg,(int)pageInfo.getTotal(),pageInfo.getList());
        return pageModel;
    }

    /**
     * 更新周报
     * */
    public void updateBug(CmsWorkModel cmsWorkModel){
        cmsWorkMapper.updateBug(cmsWorkModel);
    }

    public List queryAllProjects(){
        return cmsWorkMapper.queryAllProjects();
    }



    /**
     * 获取项目资源状态
     * */
    public PageModel queryProjectUsed(CmsWorkModel cmsWorkModel) {
        String code="0";
        String msg="";
        List<Map<String,Object>> list = null;
        Integer counter=0;
        try {
            list = cmsWorkMapper.queryProjectUsed(cmsWorkModel);
            counter= list.size();
        }catch (Exception e){
            e.printStackTrace();
            code="1";
            msg="查询失败";
        }
        PageModel pageModel =new PageModel(code,msg,counter,list);
        return pageModel;

    }


    /**
     * 查询订单
     * */
    public PageModel queryOrderCost(OrderModel model){
        String code="0";
        String msg="";
        List list = null;
        PageInfo<Map> pageInfo=null;
        try {

            PageHelper.startPage(model.getPage(),model.getLimit());
            list = orderMapper.queryOrderCost(model);
            pageInfo = new PageInfo<Map>(list);

        }catch (Exception e){
            e.printStackTrace();
            code="1";
            msg="查询用户失败";
        }
        PageModel pageModel =new PageModel(code,msg,(int)pageInfo.getTotal(),pageInfo.getList());
        return pageModel;
    }

    /**
     * 添加订单
     * */
    public void addOtherOrder(OrderModel model){
        orderMapper.addOther(model);
    }

    /**
     * 添加需求
     * */
    public void addReq(ReqModel model){
        reqMapper.add(model);
    }

    /**
     * 查询需求
     * */
    public PageModel queryReq(ReqModel model){
        String code="0";
        String msg="";
        List list = null;
        PageInfo<Map> pageInfo=null;
        try {

            PageHelper.startPage(model.getPage(),model.getLimit());
            list = reqMapper.query(model);
            pageInfo = new PageInfo<Map>(list);

        }catch (Exception e){
            e.printStackTrace();
            code="1";
            msg="查询用户失败";
        }
        PageModel pageModel =new PageModel(code,msg,(int)pageInfo.getTotal(),pageInfo.getList());
        return pageModel;
    }

    /**
     * 删除需求
     * */
    public void delReq(ReqModel model){
        reqMapper.del(model);
    }

    /**
     * 查询需求
     * */
    public ReqModel queryReqById(ReqModel model){
        return reqMapper.queryById(model);
    }

    /**
     * 更新需求
     * */
    public void updateReq(ReqModel model){
        reqMapper.update(model);
    }

}
