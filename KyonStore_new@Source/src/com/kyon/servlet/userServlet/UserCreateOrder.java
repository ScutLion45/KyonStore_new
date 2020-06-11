package com.kyon.servlet.userServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kyon.dao.OrderDao;
import com.kyon.daoImpl.OrderDaoImpl;
import com.kyon.pojo.User;

@WebServlet("/user-create-order")
public class UserCreateOrder extends HttpServlet {
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
    	int oNum = 0;
    	String gId = "";
    	double gPrice = 0.0;
    	String uId = "";	// 改为从Session获取
    	int arg = 0;		// 1-加入购物车 | 2-立即购买
    	if(req.getParameter("oNum")!=null && req.getParameter("oNum")!="") {
    		oNum = Integer.parseInt(req.getParameter("oNum"));
    	}
    	if(req.getParameter("gId")!=null) {
    		gId = req.getParameter("gId");
    	}
    	if(req.getParameter("gPrice")!=null) {
    		gPrice = Double.parseDouble(req.getParameter("gPrice"));
    	}
//    	if(req.getParameter("uId")!=null) {
//    		uId = req.getParameter("uId");
//    	}
    	if(req.getParameter("arg")!=null && req.getParameter("arg")!="") {
    		arg = Integer.parseInt(req.getParameter("arg"));
    	}
    	
    	// 处理请求信息
    	HttpSession hs = req.getSession();
    	Object obj = hs.getAttribute("user");
    	if(obj != null) {
        	int success = 0;
    		// 有用户登录中，调用od.userCreateOrder
    		User u = (User)obj;
    		// 判断余额（arg=2表示购买）
    		if(arg == 2 && u.getuBalance() < oNum * gPrice) {
    			// 响应处理结果
            	String resp_str="{\"isLogin\":1,\"success\":0,\"failedInfo\":\"账户余额不足\"}";
            	resp.getWriter().write(resp_str);
    			return;
    		}
    		
    		String resp_str = "";
        	try {
        		success = od.userCreateOrder(oNum, gId, gPrice, uId, arg);
        		resp_str="{\"isLogin\":1,\"success\":"+success+",\"failedInfo\":\"\"}";		// 唯一成功的情况
        		
        		// 操作成功，如果arg=2，更新用户的uBalance
        		if(success == 1 && arg == 2) {
        			double uB = u.getuBalance();
        			u.setuBalance(uB - oNum * gPrice);	// 减去成交额
        			hs.setAttribute("user", u);
        		}
        		
        	} catch (Exception e) {
    			e.printStackTrace();
    			success = 0;
    			resp_str="{\"isLogin\":1,\"success\":0,\"failedInfo\":\"有错误发生\"}";
    		}
        	
        	// 响应处理结果
        	resp.getWriter().write(resp_str);
    	} else {
    		// 没有用户登录中
    		String resp_str = "{\"isLogin\":0,\"success\":0,\"failedInfo\":\"用户未登录\"}";
        	resp.getWriter().write(resp_str);
    	}

    	
    	
    	
	}

}
