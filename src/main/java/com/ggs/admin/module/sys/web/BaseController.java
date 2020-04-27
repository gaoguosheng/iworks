package com.ggs.admin.module.sys.web;

import com.ggs.admin.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public class BaseController {
    protected final String webRoot="/html/admin/";


    protected void exportExcel(String name,String[]title,String[]keys,List<Map<String,Object>> list,HttpServletResponse response)  {
        //获取数据
        //excel文件名
        String fileName = name  +  ".xls";

        //sheet名
        String sheetName = name;

        String [][]content = new String[list.size()][];
        for(int i=0;i<list.size();i++){
            content[i] = new String[title.length];
            Map map = list.get(i);
            int j=0;
            for(String key:keys){
                content[i][j]= String.valueOf(map.get(key)==null?"":map.get(key));
                j++;
            }
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

        //响应到客户端
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
