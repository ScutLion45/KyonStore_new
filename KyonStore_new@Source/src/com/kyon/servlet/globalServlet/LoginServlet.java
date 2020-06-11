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
		// 设置请求编码格式
		req.setCharacterEncoding("utf-8");
		// 设置响应编码格式和响应头
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// 获取请求信息
    	String name = "";
    	String pwd = "";
    	int arg = 0;
    	if(req.getParameter("name")!=null)
        	name = req.getParameter("name");
    	if(req.getParameter("pwd")!=null)
        	pwd = req.getParameter("pwd");
    	if(req.getParameter("arg")!=null)
    		arg = Integer.parseInt(req.getParameter("arg"));

    	// 处理请求信息
   	 	HttpSession hs = req.getSession();
		String webRoot = this.getServletContext().getRealPath("");		// 项目根目录
		UserProDao upd = new UserProDaoImpl();

		String IPAddr = Utils.getIPAddr(req);		// 获取请求IP
		
		System.out.println("LoginServlet: webRoot={ "+webRoot+"}");
		
    	if(arg==1) {
    		User u = null;
    		try {
    			u = ld.checkUPwd(name, pwd);
    			// new：写入Session['user']
    			if(u != null) {
    				hs.setAttribute("user", u);
    				
// /*				// 登录后加载用户画像，并写入Session（联动修改LogoutServlet）
	    			Map<String, List<ScoreTuple>> uPortrait = new HashMap<>();
	    			Map<String, Set<String>> uP_set = new HashMap<>();
	    			upd.loadUserPortrait(u.getuId(), uPortrait, uP_set, webRoot);
	    			hs.setAttribute("uPortrait", uPortrait);
	    			hs.setAttribute("uP_set", uP_set);
// */				// ...
	    			// 设置SessionListener，销毁时调用upd.buildUserPortrait()，更新用户画像
	    			
	    			// 记录登录日志
	    			upd.recordLogin_out(u.getuId(), 1, 0, IPAddr, webRoot);
	    			// IPAddr写入Session
	    			hs.setAttribute("IPAddr", IPAddr);
	    			
    			}
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    		System.out.println("用户登录["+name+","+pwd+"], "+(u != null));
    		// 响应处理结果
    		resp.getWriter().write(new Gson().toJson(u));
    	} else if(arg==2) {
    		Publisher p = null;
    		try {
    			p = ld.checkPPwd(name, pwd);
    			// new：写入Session['pub']
    			if(p != null) {
    				hs.setAttribute("pub", p);
	    			// 记录登录日志
	    			upd.recordLogin_out(p.getpUid(), 2, 0, IPAddr, webRoot);
	    			// IPAddr写入Session
	    			hs.setAttribute("IPAddr", IPAddr);
    			}
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    		System.out.println("发行方登录["+name+","+pwd+"], "+(p != null));
    		// 响应处理结果
    		resp.getWriter().write(new Gson().toJson(p));
    	}
    	
    	// 响应处理结果
    	// String str="{\"success\":"+success+"}";
    	// resp.getWriter().write(str);
    	
	}
	

}
