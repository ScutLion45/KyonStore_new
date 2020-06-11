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
import com.kyon.dao.OrderDao;
import com.kyon.daoImpl.OrderDaoImpl;
import com.kyon.pojo.Order;
import com.kyon.pojo.User;

@WebServlet("/user-load-order")
public class UserLoadOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	OrderDao od = new OrderDaoImpl();
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
    	double uBalance = 0.0;	// 用户余额
    	int arg = 0;
//    	if(req.getParameter("uId")!=null) {
//    		uId = req.getParameter("uId");
//    	}
    	if(req.getParameter("arg")!=null && req.getParameter("arg")!="") {
    		arg = Integer.parseInt(req.getParameter("arg"));
    	}
    	
    	// 处理请求信息
    	HttpSession hs = req.getSession();
    	Object u = hs.getAttribute("user");
    	if(u != null) {
    		uId = ((User)u).getuId();
    		uBalance = ((User)u).getuBalance();
    	} else {
    		// 用户未登录，直接响应
    		String resp_str = "{\"isLogin\":0,\"uBalance\":0,\"orderList\":null}";
        	resp.getWriter().write(resp_str);
        	return;
    	}

    	// 到这里说明用户已登录
    	List<Order> lo = new ArrayList<Order>();
    	try {
    		lo = od.userLoadOrder(uId, arg);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	// 响应处理结果
    	String resp_str = "{\"isLogin\":1,\"uBalance\":"+uBalance+",\"orderList\":"+new Gson().toJson(lo)+"}";
    	resp.getWriter().write(resp_str);
    	
    	
	}

}
