package com.ggs.admin.module.work.web;

import com.ggs.admin.comm.DDConst;
import com.ggs.admin.module.sys.model.PageModel;
import com.ggs.admin.module.sys.model.UserModel;
import com.ggs.admin.module.sys.service.SysService;
import com.ggs.admin.module.sys.web.BaseController;
import com.ggs.admin.module.work.model.CmsWorkModel;
import com.ggs.admin.module.work.model.ProblemModel;
import com.ggs.admin.module.work.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * 周报管理
 * */
@Controller
@RequestMapping("/admin/problem/")
public class ProblemController extends BaseController {

    private final String path=webRoot+"problem";

    @Autowired
    private WorkService service;

    @Autowired
    private SysService sysService;

    @RequestMapping("index.do")
    public String index(ModelMap map,ProblemModel model) {
        loadDatadict(map);
        map.put("model",model);
        return path+"/index";
    }

    @RequestMapping("search.do")
    public String search(ModelMap map) {
        loadDatadict(map);
        return path+"/search";
    }

    @RequestMapping("query.do")
    @ResponseBody
    public PageModel query(ProblemModel problemModel,@SessionAttribute UserModel  admin){
        problemModel.setTouser(admin.getUsercode());
        problemModel.setOrgid(admin.getOrgid());
        PageModel pageModel = service.queryProblem(problemModel);
        return  pageModel;
    }

    @RequestMapping("queryAll.do")
    @ResponseBody
    public PageModel queryAll(ProblemModel problemModel,@SessionAttribute UserModel  admin){
        problemModel.setOrgid(admin.getOrgid());
        PageModel pageModel = service.queryProblem(problemModel);
        return  pageModel;
    }

    @RequestMapping("add.do")
    @ResponseBody
    public void add(ProblemModel problemModel,@SessionAttribute UserModel  admin){
        problemModel.setCreateuser(admin.getUsercode());
        service.addProblem(problemModel);
    }

    @RequestMapping("update.do")
    @ResponseBody
    public void update(ProblemModel problemModel,@SessionAttribute UserModel admin){
        problemModel.setUpdateuser(admin.getUsercode());
        service.updateProblem(problemModel);
    }

    @RequestMapping("del.do")
    @ResponseBody
    public void del(ProblemModel problemModel){
        service.delProblem(problemModel);
    }

    @RequestMapping("add.html")
    public String addHtml(ModelMap map){
        loadDatadict(map);
        return path+"/add";
    }

    @RequestMapping("edit.html")
    public String editHtml(ModelMap map,ProblemModel params){
        loadDatadict(map);
        ProblemModel problemModel = service.getProblemById(params);
        map.addAttribute("problem",problemModel);
        return path+"/edit";
    }

    @RequestMapping("touser.html")
    public String touserHtml(ProblemModel problemModel, ModelMap map){
        map.put("problem",problemModel);
        return path+"/touser";
    }

    @RequestMapping("weekTotal.do")
    @ResponseBody
    public List weekTotal(ProblemModel problemModel,@SessionAttribute UserModel  admin){
        problemModel.setOrgid(admin.getOrgid());
        List list = service.getWeekProblemFinishStatus(problemModel);
        return list;
    }

    @RequestMapping("detail.html")
    public String detailHtml(ModelMap map,ProblemModel params){
        loadDatadict(map);
        ProblemModel problemModel = service.getProblemById(params);
        map.addAttribute("problem",problemModel);
        return path+"/detail";
    }

    /**
     * 加载数据字典
     * */
    private void loadDatadict(ModelMap map){
        map.addAttribute("severitys",sysService.getDatadicts(DDConst.SEVERITY));
        map.addAttribute("categorys",sysService.getDatadicts(DDConst.CATEGORY));
        map.addAttribute("causetypes",sysService.getDatadicts(DDConst.CAUSETYPE));
    }

    @RequestMapping("causeTotal.do")
    @ResponseBody
    public List causeTotal(ProblemModel problemModel,@SessionAttribute UserModel  admin){
        problemModel.setOrgid(admin.getOrgid());
        List list = service.causeTypeTotal(problemModel);
        return list;
    }


    @RequestMapping("orgTotal.html")
    public String orgTotalHtml(HashMap<String, Object> map) {
        return path+"/orgTotal";
    }

    @RequestMapping("orgTotal.do")
    @ResponseBody
    public List orgTotal(ProblemModel problemModel,@SessionAttribute UserModel  admin){
        problemModel.setOrgid(admin.getOrgid());
        List list = service.getProblemOrgTotal(problemModel);
        return list;
    }

    @RequestMapping("causeTotal.html")
    public String userTotalHtml(ModelMap map,ProblemModel problemModel) {
        map.put("problem",problemModel);
        return path+"/causeTotal";
    }

    @RequestMapping("trend.html")
    public String trend(ModelMap map,ProblemModel problemModel) {
        map.put("problem",problemModel);
        return path+"/trend";
    }

    @RequestMapping("trend.do")
    @ResponseBody
    public List trend(ProblemModel problemModel,@SessionAttribute UserModel  admin){
        List list = service.getWeekProblemFinishStatus(problemModel);
        return list;
    }

    @RequestMapping("export.do")
    @ResponseBody
    public void export(ProblemModel problemModel, @SessionAttribute UserModel  admin, HttpServletResponse response){
        problemModel.setOrgid(admin.getOrgid());
        problemModel.setLimit(10000);
        PageModel pageModel = service.queryProblem(problemModel);
        //excel标题
        String[] title = {"日期", "创建人","摘要","内容","分类","严重级别","原因类别","原因分析","解决方法","是否解决","解决人","解决日期"};
        String[]key={"createdate","createusername","title","content","categoryname","serverityname","causetypename","cause","solve","isfinish","tousername","finishdate"};
        exportExcel("问题看板",title,key,pageModel.getData(),response);
    }
}
