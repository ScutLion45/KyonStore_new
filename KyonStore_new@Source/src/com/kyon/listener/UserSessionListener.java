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
    	
    	// ���أ�������session�����Чʱ��Ϊ5*60�룬��5����
    	hs.setMaxInactiveInterval(5 * 60);		// ��λ����
    }

    public void sessionDestroyed(HttpSessionEvent hse)  { 
    	HttpSession hs = hse.getSession();
    	String webRoot = hs.getServletContext().getRealPath("");	// ��Ŀ��Ŀ¼
		UserProDao upd = new UserProDaoImpl();
    	
    	System.out.println("Session Destroyed [JSSESSIONID="+hs.getId()+"; webRoot={ "+webRoot+"} ]");
    	
    	
    	// ��ȡ��¼ʱSession��¼��IPAddr
    	String IPAddr = null;
    	if(hs.getAttribute("IPAddr") != null) {
    		IPAddr = (String)hs.getAttribute("IPAddr");
    	}
    	
    	// �����û�����
    	// ��ȡuser
    	// ����UserProDao.buildUserProtrait()
    	try {
    		User u = (User)hs.getAttribute("user");
    		if(u != null) {
    			// ���û���¼��
    			String uId = u.getuId();
    			upd.buildUserPortrait(uId, webRoot);
    			System.out.println("UserProDao.buildUserPortrait("+uId+") finished.");
    			// ��¼�ǳ���־
    			if(IPAddr != null)
    				upd.recordLogin_out(uId, 1, 1, IPAddr, webRoot);
    		} else {
    			System.out.println("UserSessionListener>>> No User Logined.");
    			Publisher p = (Publisher)hs.getAttribute("pub");
    			if(p != null) {
    				// �з��з���¼��
    				String pUid = p.getpUid();
        			// ��¼�ǳ���־
        			if(IPAddr != null)
        				upd.recordLogin_out(pUid, 2, 1, IPAddr, webRoot);
    			} else {
        			System.out.println("UserSessionListener>>> No Publisher Logined.");
    			}
    		}
    	/* } catch(ClassCastException e) {
    		// Session�����Publisher
			System.out.println("Failed to cast to `com.kyon.pojo.User`");*/
		} catch(Exception e) {
			// ������
    		e.printStackTrace();
    	}
    	
    }
	
}
