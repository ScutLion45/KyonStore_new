package com.kyon.servlet.pubServlet;

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
import com.kyon.daoImpl.PubDaoImpl;
import com.kyon.pojo.Order;
import com.kyon.tools.Utils;
import com.kyon.pojo.*;

@WebServlet("/pub-load-order")
public class PubLoadOrder extends HttpServlet {
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
    	String pUid = "";	// 改为从Session获取
//    	if(req.getParameter("pUid")!=null) {
//    		pUid = req.getParameter("pUid");
//    	}
    	
    	// 处理请求信息
    	HttpSession hs = req.getSession();
    	Object u = hs.getAttribute("pub");
    	if(u != null) {
    		pUid = ((Publisher)u).getpUid();
    	} else {
    		// 用户未登录，直接响应
    		String resp_str = "{\"isLogin\":0,\"orderList\":null}";
        	resp.getWriter().write(resp_str);
        	return;
    	}

    	// 到这里说明发行方已登录
    	List<Order> lo = new ArrayList<Order>();
    	try {
    		lo = od.pubLoadOrder(pUid);
    		
    		// new：操作日志记录
			String webRoot = this.getServletContext().getRealPath("");		// 项目根目录
			String IPAddr = Utils.getIPAddr(req);							// 获取请求IP
			String operationStr = "od.pubLoadOrder("
									+"\""+pUid+"\""
									+")";
			int result = (lo==null ? -1 : lo.size() );
			(new PubDaoImpl()).recordOperation(pUid, operationStr, result, IPAddr, webRoot);
    		
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	

    	// 响应处理结果
		String resp_str = "{\"isLogin\":2,\"orderList\":"+new Gson().toJson(lo)+"}";
    	resp.getWriter().write(resp_str);
    	
    	
	}

}
