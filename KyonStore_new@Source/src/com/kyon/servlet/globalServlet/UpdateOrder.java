package com.kyon.servlet.globalServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kyon.dao.OrderDao;
import com.kyon.daoImpl.OrderDaoImpl;
import com.kyon.daoImpl.PubDaoImpl;
import com.kyon.pojo.Publisher;
import com.kyon.pojo.User;
import com.kyon.tools.Utils;

@WebServlet("/update-order")
public class UpdateOrder extends HttpServlet {
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
    	String oId = "";
    	int arg = 0;		// 2-用户从购物车结算
    	
    	if(req.getParameter("oId")!=null) {
    		oId = req.getParameter("oId");
    	}
    	if(req.getParameter("arg")!=null && req.getParameter("arg")!="") {
    		arg = Integer.parseInt(req.getParameter("arg"));
    	}
    	
    	// new：用于更新session的uBalance（用户端）
    	double gPrice = 0.0;
    	int oNum = 0;
    	if(req.getParameter("gPrice")!=null) {
    		gPrice = Double.parseDouble(req.getParameter("gPrice"));
    	}
    	if(req.getParameter("oNum")!=null && req.getParameter("oNum")!="") {
    		oNum = Integer.parseInt(req.getParameter("oNum"));
    	}
    	
    	
    	// 判断当前是否登录用户/发行方
//    	Object obj = req.getSession().getAttribute("user");
    	int isLogin = 0;
    	
    	if(req.getSession().getAttribute("user") != null) {
    		isLogin = 1;
    	} else if(req.getSession().getAttribute("pub") != null) {
    		isLogin = 2;
		} else {
    		// 用户未登录，直接响应
        	String str="{\"success\":0,\"isLogin\":0}";
        	resp.getWriter().write(str);
    		return;
    	}
    	
    	// 处理请求信息：到这里说明有用户/发行方登录
    	int success = 0;
    	try {
    		success = od.updateOrder(oId, arg);
    		if(success == 1 & isLogin == 1 && arg == 2) {
    			User u = (User)req.getSession().getAttribute("user");
    			// 操作成功，更新用户的uBalance
    			double uB = u.getuBalance();
    			u.setuBalance(uB - oNum * gPrice);	// 减去成交额
    			req.getSession().setAttribute("user", u);
    		} else if(success == 1 && isLogin == 2 && arg == 3) {
        		// new：操作日志记录
    			String webRoot = this.getServletContext().getRealPath("");		// 项目根目录
    			Object obj = req.getSession().getAttribute("pub");
        		String pUid = ((Publisher)obj).getpUid();						// 获取pUid
    			String IPAddr = Utils.getIPAddr(req);							// 获取请求IP
    			String operationStr = "od.updateOrder("
    									+"\""+oId+"\","
    									+arg+""
    									+")";
    			int result = success;
    			(new PubDaoImpl()).recordOperation(pUid, operationStr, result, IPAddr, webRoot);
    		}
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	// 响应处理结果
    	String str="{\"success\":"+success+",\"isLogin\":"+isLogin+"}";
    	resp.getWriter().write(str);
    	
	}

}
