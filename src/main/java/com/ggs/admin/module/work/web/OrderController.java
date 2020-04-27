package com.ggs.admin.module.work.web;

import com.ggs.admin.module.sys.model.PageModel;
import com.ggs.admin.module.sys.model.UserModel;
import com.ggs.admin.module.sys.service.SysService;
import com.ggs.admin.module.sys.web.BaseController;
import com.ggs.admin.module.work.model.OrderModel;
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
import java.util.Map;

/**
 * 订单管理
 * */
@Controller
@RequestMapping("/admin/order/")
public class OrderController extends BaseController {
    private final String path=webRoot+"order";

    @Autowired
    private WorkService service;

    @Autowired
    private SysService sysService;


    @RequestMapping("index.do")
    public String index(ModelMap map, OrderModel model,@SessionAttribute UserModel  admin) {
        map.put("model",model);
        map.addAttribute("admin",admin);
        List orderList = service.queryOrders(model);
        map.addAttribute("orderList",orderList);
        return path+"/index";
    }

    @RequestMapping("query.do")
    @ResponseBody
    public PageModel query(OrderModel model, @SessionAttribute UserModel admin){
        PageModel pageModel = service.queryOrder(model);
        return  pageModel;
    }

    @RequestMapping("total.html")
    public String total(ModelMap map, OrderModel model,@SessionAttribute UserModel admin) {
        map.put("model",model);
        map.addAttribute("admin",admin);
        List orderList = service.queryOrders(model);
        map.addAttribute("orderList",orderList);
        return path+"/total";
    }

    @RequestMapping("queryTotal.do")
    @ResponseBody
    public PageModel queryTotal(OrderModel model, @SessionAttribute UserModel admin){
        PageModel pageModel = service.queryOrderTotal(model);
        return  pageModel;
    }

    @RequestMapping("queryProducts.do")
    @ResponseBody
    public PageModel queryProducts(OrderModel model, @SessionAttribute UserModel admin){
        model.setLimit(10000);
        PageModel pageModel = service.queryProducts(model);
        return  pageModel;
    }

    @RequestMapping("add.html")
    public String addHtml(ModelMap map,@SessionAttribute UserModel  admin,OrderModel model){
        map.addAttribute("admin",admin);
        OrderModel model1 = new OrderModel();
        model1.setOrder_date(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        List orderList = service.queryOrders(model1);
        map.addAttribute("orderList",orderList);
        List userList = sysService.queryAllUsers();
        map.addAttribute("userList",userList);
        return path+"/add";
    }

    @RequestMapping("add.do")
    @ResponseBody
    public void add(OrderModel model, @SessionAttribute UserModel  admin){
        model.setCreateuser(admin.getUsercode());
        service.addOrder(model);
    }

    @RequestMapping("del.do")
    @ResponseBody
    public void del(OrderModel model){
        service.delOrder(model);
    }

    @RequestMapping("export.do")
    @ResponseBody
    public void export(OrderModel model, @SessionAttribute UserModel  admin, HttpServletResponse response){
        //model.setOrgid(admin.getOrgid());
        model.setLimit(10000);
        PageModel pageModel = service.queryOrderTotal(model);
        List <Map<String,Object>> list = pageModel.getData();
        double total_price = 0;
        for(Map map:list){
            total_price+=Double.valueOf(String.valueOf(map.get("total_price")));
            map.put("use_time",model.getUse_time());
        }
        Map map = new HashMap();
        map.put("orgname","合计：");
        map.put("total_price",total_price);
        list.add(map);
        //excel标题
        String[] title = {"部门", "姓名","加班餐","价格（元）","用餐时间"};
        String[]key={"orgname","username","product_name","total_price","use_time"};
        String exp_name = model.getExp_name();
        exp_name = exp_name.replaceAll("yyyyMMdd",new SimpleDateFormat("yyyyMMdd").format(new Date()));
        exportExcel(exp_name,title,key,list,response);
    }

    @RequestMapping("addLast.do")
    @ResponseBody
    public void addLast(OrderModel model, @SessionAttribute UserModel  admin){
        service.addLastOrder(model);
    }

    @RequestMapping("addOrderMaster.do")
    @ResponseBody
    public void addOrderMaster(OrderModel model, @SessionAttribute UserModel  admin){
        model.setOrdername(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        service.addOrderMaster(model);
    }

    @RequestMapping("cost.html")
    public String orderCost(ModelMap map, OrderModel model) {
        map.put("model",model);
        List orderList = service.queryOrders(model);
        map.addAttribute("orderList",orderList);
        map.addAttribute("defDate",new SimpleDateFormat("yyyy-MM").format(new Date()));
        return path+"/cost";
    }

    @RequestMapping("queryOrderCost.do")
    @ResponseBody
    public PageModel queryOrderCost(OrderModel model, @SessionAttribute UserModel admin){
        PageModel pageModel = service.queryOrderCost(model);
        return  pageModel;
    }

    @RequestMapping("exportCost.do")
    @ResponseBody
    public void exportCost(OrderModel model, @SessionAttribute UserModel  admin, HttpServletResponse response){
        //model.setOrgid(admin.getOrgid());
        model.setLimit(10000);
        PageModel pageModel = service.queryOrderCost(model);
        List <Map<String,Object>> list = pageModel.getData();
        double total_price = 0;
        for(Map map:list){
            total_price+=Double.valueOf(String.valueOf(map.get("cost")));
        }
        Map map = new HashMap();
        map.put("orgname","合计：");
        map.put("cost",total_price);
        list.add(map);
        //excel标题
        String[] title = {"部门", "姓名","工作日加班天数","周末节假日加班天数","应发补贴（元）"};
        String[]key={"orgname","username","workday_counter","weekend_counter","cost"};
        exportExcel("加班补贴_"+model.getOrder_date(),title,key,list,response);
    }

    @RequestMapping("addOther.do")
    @ResponseBody
    public void addOther(OrderModel model, @SessionAttribute UserModel  admin){
        service.addOtherOrder(model);
    }



}
