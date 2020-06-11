package com.kyon.servlet.userServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.kyon.dao.UserDao;
import com.kyon.daoImpl.UserDaoImpl;
import com.kyon.pojo.Browse;
import com.kyon.pojo.User;

@WebServlet("/user-load-browse")
public class UserLoadBrowse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDao ud = new UserDaoImpl();
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// 设置请求编码格式
		req.setCharacterEncoding("utf-8");
		// 设置响应编码格式和响应头
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// 获取请求信息 
    	String uId = "";	// 改为从Session获取
//    	if(req.getParameter("uId")!=null) {
//    		uId = req.getParameter("uId");
//    	}
    	
    	// 处理请求信息
    	HttpSession hs = req.getSession();
    	Object u = hs.getAttribute("user");
    	if(u != null) {
    		uId = ((User)u).getuId();
    	} else {
    		// 用户未登录，直接响应
    		String resp_str = "{\"isLogin\":0,\"browseList\":null}";
        	resp.getWriter().write(resp_str);
        	return;
    	}

    	// 到这里说明用户已登录
		List<Browse> lb = new ArrayList<Browse>();
		try {
			lb = ud.loadBrowse(uId);
		} catch (Exception e) {
			e.printStackTrace();
		}

    	// 响应处理结果
    	String resp_str = "{\"isLogin\":1,\"browseList\":"+new Gson().toJson(lb)+"}";
    	resp.getWriter().write(resp_str);
	}

}
