package com.ggs.admin.module.sys.web;

import com.ggs.admin.comm.DDConst;
import com.ggs.admin.module.sys.model.DatadictModel;
import com.ggs.admin.module.sys.model.OrgModel;
import com.ggs.admin.module.sys.model.PageModel;
import com.ggs.admin.module.sys.model.UserModel;
import com.ggs.admin.module.sys.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin/datadict/")
public class DatadictController extends  BaseController{

    @Autowired
    private SysService service;
    private final String path=webRoot+"datadict";

    @RequestMapping("index.do")
    public String index(ModelMap map){
        map.addAttribute("ddtypes",service.getDatadicts(DDConst.ROOT));
        return path+"/index";
    }

    @RequestMapping("query.do")
    @ResponseBody
    public PageModel query(DatadictModel datadictModel){
        PageModel pageModel = service.queryDatadict(datadictModel);
        return  pageModel;
    }

    @RequestMapping("add.do")
    @ResponseBody
    public void add(DatadictModel datadictModel){
        service.addDatadict(datadictModel);
    }

    @RequestMapping("update.do")
    @ResponseBody
    public void update(DatadictModel datadictModel){
        service.updateDatadict(datadictModel);
    }

    @RequestMapping("del.do")
    @ResponseBody
    public void del(DatadictModel datadictModel){
        service.delDatadict(datadictModel);
    }

    @RequestMapping("add.html")
    public String addHtml(ModelMap map){
        map.addAttribute("ddtypes",service.getDatadicts(DDConst.ROOT));
        return path+"/add";
    }

    @RequestMapping("edit.html")
    public String editHtml(ModelMap map,DatadictModel datadictModel){
        map.addAttribute("ddtypes",service.getDatadicts(DDConst.ROOT));
        DatadictModel datadict = service.getDatadictById(datadictModel);
        map.addAttribute("datadict",datadict);
        return path+"/edit";
    }

}
