package com.ggs.admin.module.work.web;

import com.ggs.admin.module.sys.model.PageModel;
import com.ggs.admin.module.sys.model.UserModel;
import com.ggs.admin.module.sys.service.SysService;
import com.ggs.admin.module.sys.web.BaseController;
import com.ggs.admin.module.work.model.CmsWorkModel;
import com.ggs.admin.module.work.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 周报管理
 * */
@Controller
@RequestMapping("/admin/cmswork/")
public class CmsWorkController  extends BaseController {

    private final String path=webRoot+"cmswork";

    @Autowired
    private WorkService service;

    @Autowired
    private SysService sysService;

    @RequestMapping("index.do")
    public String index(ModelMap map,CmsWorkModel model) {
        map.put("model",model);
        map.put("projects",service.queryAllProjects());
        return path+"/index";
    }

    @RequestMapping("search.do")
    public String search(ModelMap map,@SessionAttribute UserModel  admin) {
        map.addAttribute("admin",admin);
        map.put("projects",service.queryAllProjects());
        return path+"/search";
    }

    @RequestMapping("query.do")
    @ResponseBody
    public PageModel query(CmsWorkModel cmsWorkModel,@SessionAttribute UserModel  admin){
        cmsWorkModel.setTouser(admin.getUsercode());
        cmsWorkModel.setOrgid(admin.getOrgid());
        PageModel pageModel = service.queryCmsWork(cmsWorkModel);
        return  pageModel;
    }

    @RequestMapping("queryAll.do")
    @ResponseBody
    public PageModel queryAll(CmsWorkModel cmsWorkModel,@SessionAttribute UserModel  admin){
        PageModel pageModel = service.queryCmsWork(cmsWorkModel);
        return  pageModel;
    }

    @RequestMapping("add.do")
    @ResponseBody
    public void add(CmsWorkModel cmsWorkModel,@SessionAttribute UserModel  admin){
        cmsWorkModel.setCreateuser(admin.getUsercode());
        cmsWorkModel.setName(admin.getUsername());
        if(cmsWorkModel.getTaskname()==null)
        cmsWorkModel.setTaskname("新建任务");
        service.addCmsWork(cmsWorkModel);
    }

    @RequestMapping("update.do")
    @ResponseBody
    public void update(CmsWorkModel cmsWorkModel,@SessionAttribute UserModel admin){
        cmsWorkModel.setUpdateuser(admin.getUsercode());
        service.updateCmsWork(cmsWorkModel);
    }

    @RequestMapping("del.do")
    @ResponseBody
    public void del(CmsWorkModel cmsWorkModel){
        service.delCmsWork(cmsWorkModel);
    }

    @RequestMapping("touser.html")
    public String touserHtml(CmsWorkModel cmsWorkModel, ModelMap map){
        map.put("cmswork",cmsWorkModel);
        return path+"/touser";
    }

    @RequestMapping("finishStatus.do")
    @ResponseBody
    public List workFinishStatus(CmsWorkModel cmsWorkModel,@SessionAttribute UserModel  admin){
        cmsWorkModel.setOrgid(admin.getOrgid());
        List list = service.getWorkFinishStatus(cmsWorkModel);
        return  list;
    }

    @RequestMapping("freeStatus.do")
    @ResponseBody
    public PageModel workFreeStatus(CmsWorkModel cmsWorkModel,@SessionAttribute UserModel  admin){
        cmsWorkModel.setOrgid(admin.getOrgid());
        PageModel pageModel = service.getWorkFreeStatus(cmsWorkModel);
        return  pageModel;
    }

    @RequestMapping("weekTotal.do")
    @ResponseBody
    public List weekTotal(CmsWorkModel cmsWorkModel,@SessionAttribute UserModel  admin){
        cmsWorkModel.setOrgid(admin.getOrgid());
        List list = service.getWeekWorkFinishStatus(cmsWorkModel);
        return list;
    }

    @RequestMapping("report.do")
    public String report(HashMap<String, Object> map) {
        return path+"/report";
    }

    @RequestMapping("orgTotal.html")
    public String orgTotalHtml(HashMap<String, Object> map) {
        return path+"/orgTotal";
    }

    @RequestMapping("orgTotal.do")
    @ResponseBody
    public List orgTotal(CmsWorkModel cmsWorkModel,@SessionAttribute UserModel  admin){
        cmsWorkModel.setOrgid(admin.getOrgid());
        List list = service.getWorkOrgTotal(cmsWorkModel);
        return list;
    }

    @RequestMapping("userTotal.do")
    @ResponseBody
    public List userTotal(CmsWorkModel cmsWorkModel,@SessionAttribute UserModel  admin){
        cmsWorkModel.setOrgid(admin.getOrgid());
        List list = service.getWorkUserTotal(cmsWorkModel);
        return list;
    }

    @RequestMapping("userTotal.html")
    public String userTotalHtml(ModelMap map,CmsWorkModel cmsWorkModel) {
        map.put("cmswork",cmsWorkModel);
        return path+"/userTotal";
    }

    @RequestMapping("trend.html")
    public String trend(ModelMap map,CmsWorkModel cmsWorkModel) {
        map.put("cmswork",cmsWorkModel);
        return path+"/trend";
    }

    @RequestMapping("trend.do")
    @ResponseBody
    public List trend(CmsWorkModel cmsWorkModel,@SessionAttribute UserModel  admin){
        List list = service.getWeekWorkFinishStatus(cmsWorkModel);
        return list;
    }


    @RequestMapping("userRanking.html")
    public String userRankingHtml(ModelMap map,CmsWorkModel cmsWorkModel) {
        map.addAttribute("model",cmsWorkModel);
        return path+"/userRanking";
    }

    @RequestMapping("userRanking.do")
    @ResponseBody
    public PageModel userRanking(CmsWorkModel cmsWorkModel,@SessionAttribute UserModel  admin){
        List<String>orgids = new ArrayList<String>();
        String ids = cmsWorkModel.getIds();
        if(ids!=null && ids.trim().length()>0){
            for(String id:ids.split(",")){
                orgids.add(id);
            }
            cmsWorkModel.setOrgids(orgids);
        }
        PageModel pageModel = service.userRanking(cmsWorkModel);
        return  pageModel;
    }

    @RequestMapping("queryLog.do")
    @ResponseBody
    public List queryLog(CmsWorkModel model, @SessionAttribute UserModel  admin){
        model.setTouser(admin.getUsercode());
        model.setOrgid(admin.getOrgid());
        List list= service.queryWorkLog(model);
        return  list;
    }

    @RequestMapping("exportRanking.do")
    @ResponseBody
    public void exportRanking(CmsWorkModel cmsWorkModel, @SessionAttribute UserModel  admin, HttpServletResponse response){
        List<String>orgids = new ArrayList<String>();
        String ids = cmsWorkModel.getIds();
        if(ids!=null && ids.trim().length()>0){
            for(String id:ids.split(",")){
                orgids.add(id);
            }
            cmsWorkModel.setOrgids(orgids);
        }
        PageModel pageModel = service.userRanking(cmsWorkModel);
        //excel标题
        String[] title = {"排名", "姓名","组织名称","任务数量"};
        String[]key={"rownum","username","orgname","counter"};
        exportExcel("任务排名",title,key,pageModel.getData(),response);
    }

    @RequestMapping("exportWork.do")
    @ResponseBody
    public void exportWork(CmsWorkModel cmsWorkModel, @SessionAttribute UserModel  admin, HttpServletResponse response){
        cmsWorkModel.setOrgid(admin.getOrgid());
        cmsWorkModel.setLimit(10000);
        PageModel pageModel = service.queryCmsWork(cmsWorkModel);
        //excel标题
        String[] title = {"安排日期", "组织","负责人","任务单号","任务名称","是否竣工","竣工日期","完成情况"};
        String[]key={"createdate","orgname","tousername","taskno","taskname","isfinish","finishdate","remark"};
        exportExcel("任务看板",title,key,pageModel.getData(),response);
    }


    @RequestMapping("bug.do")
    public String bug(ModelMap map,CmsWorkModel model,@SessionAttribute UserModel  admin) {
        map.addAttribute("admin",admin);
        map.put("model",model);
        List userList = sysService.queryAllUsers();
        map.addAttribute("userList",userList);
        return path+"/bug";
    }

    @RequestMapping("queryBug.do")
    @ResponseBody
    public PageModel queryBug(CmsWorkModel cmsWorkModel,@SessionAttribute UserModel  admin){
        cmsWorkModel.setTouser(admin.getUsercode());
        cmsWorkModel.setOrgid(admin.getOrgid());
        PageModel pageModel = service.queryBug(cmsWorkModel);
        return  pageModel;
    }

    @RequestMapping("bugSearch.do")
    public String bugSearch(ModelMap map,CmsWorkModel model,@SessionAttribute UserModel  admin) {
        map.put("model",model);
        map.addAttribute("admin",admin);
        return path+"/bugSearch";
    }


    @RequestMapping("queryAllBug.do")
    @ResponseBody
    public PageModel queryAllBug(CmsWorkModel cmsWorkModel,@SessionAttribute UserModel  admin){

        PageModel pageModel = service.queryBug(cmsWorkModel);
        return  pageModel;
    }

    @RequestMapping("updateBug.do")
    @ResponseBody
    public void updateBug(CmsWorkModel cmsWorkModel,@SessionAttribute UserModel admin){
        cmsWorkModel.setUpdateuser(admin.getUsercode());
        service.updateBug(cmsWorkModel);
    }

    @RequestMapping("freeTotal.do")
    @ResponseBody
    public PageModel freeTotal(CmsWorkModel cmsWorkModel,@SessionAttribute UserModel  admin){
        PageModel pageModel = service.getWorkFreeStatus(cmsWorkModel);
        return  pageModel;
    }

    @RequestMapping("freeTotal.html")
    public String freeTotalHtml(ModelMap map,CmsWorkModel cmsWorkModel) {
        map.put("cmswork",cmsWorkModel);
        return path+"/freeTotal";
    }

    @RequestMapping("queryProjectUsed.do")
    @ResponseBody
    public PageModel queryProjectUsed(CmsWorkModel cmsWorkModel,@SessionAttribute UserModel  admin){
        PageModel pageModel = service.queryProjectUsed(cmsWorkModel);
        return  pageModel;
    }

    @RequestMapping("projectUsed.html")
    public String queryProjectUsedHtml(ModelMap map,CmsWorkModel cmsWorkModel) {
        map.put("cmswork",cmsWorkModel);
        return path+"/projectUsed";
    }

}
