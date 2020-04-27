package com.ggs.admin.listener;

import com.ggs.admin.module.sys.model.UserModel;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Set;

@WebListener
public class SessionListener  implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session =httpSessionEvent.getSession();
        ServletContext context = session.getServletContext();
        UserModel admin = (UserModel) session.getAttribute("admin");
        Set<String> onlineList = (Set<String>) context.getAttribute("onlineList");
        if(onlineList!=null&& admin!=null)
            onlineList.remove(admin.getUsercode());
    }
}
