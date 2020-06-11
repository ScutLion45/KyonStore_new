package com.kyon.servlet.userServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kyon.dao.UserDao;
import com.kyon.daoImpl.UserDaoImpl;
import com.kyon.pojo.*;

@WebServlet("/browse-goods")
public class UserBrowseGoods extends HttpServlet {
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
    	String uId = "";		// 改为从Session获取
    	String gId = "";
//    	if(req.getParameter("uId")!=null)
//    		uId = req.getParameter("uId");
    	if(req.getParameter("gId")!=null)
    		gId = req.getParameter("gId");
    	
    	// 从Session获取uId
    	Object u = req.getSession().getAttribute("user");
    	if(u != null) {
    		uId = ((User)u).getuId();
    	} else {
    		// 用户未登录，直接响应
        	String str="{\"success\":1}";
        	resp.getWriter().write(str);
    		return;
    	}

//    	req.getSession().getId()
    	
    	// 到这里说明用户已登录
    	// 处理请求信息
    	int success = 0;
    	try {
    		success = ud.browseGoods(uId, gId);
    		System.out.println("UserBrowseGoods: "+success);
		} catch (Exception e) {
			e.printStackTrace();
			success = 0;
		}
    	
    	// 响应处理结果
    	String str="{\"success\":"+success+"}";
    	resp.getWriter().write(str);

	}

}
