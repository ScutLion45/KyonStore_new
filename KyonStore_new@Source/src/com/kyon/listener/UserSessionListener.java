package com.kyon.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.kyon.pojo.*;
import com.kyon.dao.UserProDao;
import com.kyon.daoImpl.*;

@WebListener
public class UserSessionListener implements HttpSessionListener {

    public UserSessionListener() {
        // TODO Auto-generated constructor stub
    }

    public void sessionCreated(HttpSessionEvent hse)  {
    	HttpSession hs = hse.getSession();
    	System.out.println("Session Created   [JSSESSIONID="+hs.getId()+"]");
    	
    	// 拦截：并设置session最大有效时限为5*60秒，即5分钟
    	hs.setMaxInactiveInterval(5 * 60);		// 单位：秒
    }

    public void sessionDestroyed(HttpSessionEvent hse)  { 
    	HttpSession hs = hse.getSession();
    	String webRoot = hs.getServletContext().getRealPath("");	// 项目根目录
		UserProDao upd = new UserProDaoImpl();
    	
    	System.out.println("Session Destroyed [JSSESSIONID="+hs.getId()+"; webRoot={ "+webRoot+"} ]");
    	
    	
    	// 获取登录时Session记录的IPAddr
    	String IPAddr = null;
    	if(hs.getAttribute("IPAddr") != null) {
    		IPAddr = (String)hs.getAttribute("IPAddr");
    	}
    	
    	// 构建用户画像
    	// 读取user
    	// 调用UserProDao.buildUserProtrait()
    	try {
    		User u = (User)hs.getAttribute("user");
    		if(u != null) {
    			// 有用户登录中
    			String uId = u.getuId();
    			upd.buildUserPortrait(uId, webRoot);
    			System.out.println("UserProDao.buildUserPortrait("+uId+") finished.");
    			// 记录登出日志
    			if(IPAddr != null)
    				upd.recordLogin_out(uId, 1, 1, IPAddr, webRoot);
    		} else {
    			System.out.println("UserSessionListener>>> No User Logined.");
    			Publisher p = (Publisher)hs.getAttribute("pub");
    			if(p != null) {
    				// 有发行方登录中
    				String pUid = p.getpUid();
        			// 记录登出日志
        			if(IPAddr != null)
        				upd.recordLogin_out(pUid, 2, 1, IPAddr, webRoot);
    			} else {
        			System.out.println("UserSessionListener>>> No Publisher Logined.");
    			}
    		}
    	/* } catch(ClassCastException e) {
    		// Session存的是Publisher
			System.out.println("Failed to cast to `com.kyon.pojo.User`");*/
		} catch(Exception e) {
			// 错误发生
    		e.printStackTrace();
    	}
    	
    }
	
}
