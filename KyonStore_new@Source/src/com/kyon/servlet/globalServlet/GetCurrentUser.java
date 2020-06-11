package com.kyon.servlet.globalServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
//import com.kyon.pojo.*;

@WebServlet("/get-current-user")
public class GetCurrentUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
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
    	int arg = 0;
    	if(req.getParameter("arg")!=null)
    		arg = Integer.parseInt(req.getParameter("arg"));
    	
    	// 处理请求信息
    	HttpSession hs = req.getSession();
    	Object obj = null;
    	int isLogin = 0;
    	
    	// ver2.0：用户存Session['user']，发行方存Session['pub']：com.kyon.servlet.globalServlet.LoginServlet
    	if(arg == 1)
    		obj = hs.getAttribute("user");
    	else if(arg == 2)
    		obj = hs.getAttribute("pub");
    	else if(arg == 3) {
    		// 全局查询：即查询是否有用户或发行方登录
    		if(hs.getAttribute("user")!=null) {
    			isLogin = 1;
    		} else if(hs.getAttribute("pub")!=null) {
    			isLogin = 2;
    		} else
    			isLogin = 0;
    		// 直接返回
    		resp.getWriter().write("{\"isLogin\":"+isLogin+",\"user\":null}");
    		return;
    	}
    	
    	// ver1.0：用户和发行方都存Session['user']
    	// Object obj = hs.getAttribute("user");	// Object对象Gson也能正确parse

    	// 到这里说明arg = 1 或 2
    	if(obj != null) {
    		isLogin = arg;
    		// 有用户/发行方登录中（是用户还是发行方由前端判断
    		resp.getWriter().write("{\"isLogin\":"+isLogin+",\"user\":"+new Gson().toJson(obj)+"}");
    	} else {
    		isLogin = 0;
    		// 没有用户/发行方登录中
    		resp.getWriter().write("{\"isLogin\":"+isLogin+",\"user\":null}");
    	}
    	
    	
    	
    	
	}

}
