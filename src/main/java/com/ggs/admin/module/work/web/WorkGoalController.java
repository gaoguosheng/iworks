package com.ggs.admin.module.work.web;

import com.ggs.admin.module.sys.model.PageModel;
import com.ggs.admin.module.sys.model.UserModel;
import com.ggs.admin.module.sys.web.BaseController;
import com.ggs.admin.module.work.model.CmsWorkModel;
import com.ggs.admin.module.work.model.WorkGoalModel;
import com.ggs.admin.module.work.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 目标管理
 * */
@Controller
@RequestMapping("/admin/workgoal/")
public class WorkGoalController extends BaseController {

    private final String path=webRoot+"workgoal";

    @Autowired
    private WorkService service;

    @RequestMapping("index.do")
    public String index(ModelMap map,WorkGoalModel model) {
        map.addAttribute("model",model);
        return path+"/index";
    }

    @RequestMapping("search.do")
    public String search(HashMap<String, Object> map) {
        return path+"/search";
    }

    @RequestMapping("query.do")
    @ResponseBody
    public PageModel query(WorkGoalModel model, @SessionAttribute UserModel  admin){
        model.setTouser(admin.getUsercode());
        model.setOrgid(admin.getOrgid());
        PageModel pageModel = service.queryWorkGoal(model);
        return  pageModel;
    }

    @RequestMapping("queryAll.do")
    @ResponseBody
    public PageModel queryAll(WorkGoalModel model, @SessionAttribute UserModel  admin){
        model.setOrgid(admin.getOrgid());
        PageModel pageModel = service.queryWorkGoal(model);
        return  pageModel;
    }

    @RequestMapping("add.do")
    @ResponseBody
    public void add(WorkGoalModel model,@SessionAttribute UserModel  admin){
        model.setCreateuser(admin.getUsercode());
        model.setTouser(admin.getUsercode());
        model.setGoalno(new SimpleDateFormat("yyyy-MM").format(new Date()));
        model.setGoalname("新建目标");
        model.setPlandate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        model.setPriority("2");
        model.setSchedule("0");
        service.addWorkGoal(model);
    }

    @RequestMapping("update.do")
    @ResponseBody
    public void update(WorkGoalModel model,@SessionAttribute UserModel admin){
        model.setUpdateuser(admin.getUsercode());
        service.updateWorkGoal(model);
    }

    @RequestMapping("del.do")
    @ResponseBody
    public void del(WorkGoalModel model){
        service.delWorkGoal(model);
    }

    @RequestMapping("touser.html")
    public String touserHtml(WorkGoalModel model, ModelMap map){
        map.put("model",model);
        return path+"/touser";
    }

    @RequestMapping("queryMonthGoal.do")
    @ResponseBody
    public List queryMonthGoal(WorkGoalModel model, @SessionAttribute UserModel  admin){
        model.setTouser(admin.getUsercode());
        model.setOrgid(admin.getOrgid());
        List list= service.queryMonthGoal(model);
        return  list;
    }

    @RequestMapping("queryLog.do")
    @ResponseBody
    public List queryLog(WorkGoalModel model, @SessionAttribute UserModel  admin){
        model.setTouser(admin.getUsercode());
        model.setOrgid(admin.getOrgid());
        List list= service.queryGoalLog(model);
        return  list;
    }

    @RequestMapping("countUnfinishWorks.do")
    @ResponseBody
    public List countUnfinishWorks(WorkGoalModel model, @SessionAttribute UserModel  admin){
        model.setTouser(admin.getUsercode());
        List list= service.countUnfinishWorks(model);
        return  list;
    }

    @RequestMapping("export.do")
    @ResponseBody
    public PageModel export(WorkGoalModel model, @SessionAttribute UserModel  admin, HttpServletResponse response){
        model.setOrgid(admin.getOrgid());
        model.setLimit(10000);
        PageModel pageModel = service.queryWorkGoal(model);
        //excel标题
        String[] title = {"目标批次", "目标名称","负责人","优先级","计划日期","完成进度%","是否竣工","竣工日期","完成情况"};
        String[]key={"goalno","goalname","tousername","prioiity","plandate","schedule","isfinish","finishdate","remark"};
        exportExcel("目标看板",title,key,pageModel.getData(),response);
        return  pageModel;
    }
}
