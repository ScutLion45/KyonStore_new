package com.kyon.servlet.globalServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.kyon.dao.*;
import com.kyon.daoImpl.*;
import com.kyon.pojo.*;
import com.kyon.tools.Utils;

import java.util.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	LoginDao ld = new LoginDaoImpl();
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// ������������ʽ
		req.setCharacterEncoding("utf-8");
		// ������Ӧ�����ʽ����Ӧͷ
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// ��ȡ������Ϣ
    	String name = "";
    	String pwd = "";
    	int arg = 0;
    	if(req.getParameter("name")!=null)
        	name = req.getParameter("name");
    	if(req.getParameter("pwd")!=null)
        	pwd = req.getParameter("pwd");
    	if(req.getParameter("arg")!=null)
    		arg = Integer.parseInt(req.getParameter("arg"));

    	// ����������Ϣ
   	 	HttpSession hs = req.getSession();
		String webRoot = this.getServletContext().getRealPath("");		// ��Ŀ��Ŀ¼
		UserProDao upd = new UserProDaoImpl();

		String IPAddr = Utils.getIPAddr(req);		// ��ȡ����IP
		
		System.out.println("LoginServlet: webRoot={ "+webRoot+"}");
		
    	if(arg==1) {
    		User u = null;
    		try {
    			u = ld.checkUPwd(name, pwd);
    			// new��д��Session['user']
    			if(u != null) {
    				hs.setAttribute("user", u);
    				
// /*				// ��¼������û����񣬲�д��Session�������޸�LogoutServlet��
	    			Map<String, List<ScoreTuple>> uPortrait = new HashMap<>();
	    			Map<String, Set<String>> uP_set = new HashMap<>();
	    			upd.loadUserPortrait(u.getuId(), uPortrait, uP_set, webRoot);
	    			hs.setAttribute("uPortrait", uPortrait);
	    			hs.setAttribute("uP_set", uP_set);
// */				// ...
	    			// ����SessionListener������ʱ����upd.buildUserPortrait()�������û�����
	    			
	    			// ��¼��¼��־
	    			upd.recordLogin_out(u.getuId(), 1, 0, IPAddr, webRoot);
	    			// IPAddrд��Session
	    			hs.setAttribute("IPAddr", IPAddr);
	    			
    			}
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    		System.out.println("�û���¼["+name+","+pwd+"], "+(u != null));
    		// ��Ӧ������
    		resp.getWriter().write(new Gson().toJson(u));
    	} else if(arg==2) {
    		Publisher p = null;
    		try {
    			p = ld.checkPPwd(name, pwd);
    			// new��д��Session['pub']
    			if(p != null) {
    				hs.setAttribute("pub", p);
	    			// ��¼��¼��־
	    			upd.recordLogin_out(p.getpUid(), 2, 0, IPAddr, webRoot);
	    			// IPAddrд��Session
	    			hs.setAttribute("IPAddr", IPAddr);
    			}
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    		System.out.println("���з���¼["+name+","+pwd+"], "+(p != null));
    		// ��Ӧ������
    		resp.getWriter().write(new Gson().toJson(p));
    	}
    	
    	// ��Ӧ������
    	// String str="{\"success\":"+success+"}";
    	// resp.getWriter().write(str);
    	
	}
	

}
