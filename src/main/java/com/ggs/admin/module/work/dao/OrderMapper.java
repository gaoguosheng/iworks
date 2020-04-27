package com.ggs.admin.module.work.dao;

import com.ggs.admin.module.work.model.OrderModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by gswon on 2017/8/1.
 */
@Component
public interface OrderMapper {
    public List<Map<String,Object>> query(OrderModel model);
    public List<Map<String,Object>> queryTotal(OrderModel model);
    public void add(OrderModel model);
    public void addLast(OrderModel model);
    public void del(OrderModel model);
    public List<Map<String,Object>> queryProducts(OrderModel model);
    public void addOrderMaster(OrderModel model);
    public List<Map<String,Object>> queryOrders(OrderModel model);
    public List<Map<String,Object>> queryOrderCost(OrderModel model);
    public void addOther(OrderModel model);
}
