package com.ggs.admin.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ggs.admin.util.JsonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by gswon on 2017/8/1.
 */
@Aspect
@Component
public class LogAspect {

    private final static Logger log = LoggerFactory.getLogger(LogAspect.class);

    private ThreadLocal tlocal =  new ThreadLocal();
    @Autowired
    //private DeviceService deviceService;

    @Pointcut("execution( public * com.ggs.admin.web.*.*(..))")
    public void log(){}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        log.info("doBefore");
        ServletRequestAttributes attributes =  (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =  attributes.getRequest();
        String ip = request.getRemoteAddr();
        String url  =request.getRequestURL().toString();
        String method=request.getMethod();
        String class_method=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        String args="";

        Map<String,Object>params = new HashMap<String,Object>();
        StringBuilder argsStr = new StringBuilder();
        Object[] joinPoingArgs= joinPoint.getArgs();
        if(joinPoingArgs!=null){
            for(int i=0;i<joinPoingArgs.length;i++){
                argsStr.append(joinPoingArgs[i]);
                argsStr.append(",");
            }
        }


        params.put("ip",ip);
        params.put("url",url);
        Map<String,Object>argsMap = new HashMap<String,Object>();
        argsMap.put("args",argsStr.toString());
        Map<String,String[]> parameterMap = request.getParameterMap();
        for(String key:parameterMap.keySet()){
            argsMap.put(key,request.getParameter(key));
        }
        try {
            args= JsonUtil.beanToJson(argsMap);
            params.put("args",args);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        params.put("method",method);
        params.put("class_method",class_method);
        tlocal.set(params);

    }


    @After("log()")
    public void doAfter(){
        log.info("doAfter");
    }


    @AfterReturning(returning = "object",pointcut = "log()")
    public void afterReturn(Object object){
        String result ="";
        try {
            result =JsonUtil.beanToJson(object);
            Map<String,Object> params =(Map<String,Object>)tlocal.get();
            params.put("result",result);
            //deviceService.saveHttpLog(params);
            log.info("httpLog={}",JsonUtil.beanToJson(params));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
