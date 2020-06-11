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
		// ������������ʽ
		req.setCharacterEncoding("utf-8");
		// ������Ӧ�����ʽ����Ӧͷ
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// ��ȡ������Ϣ
    	String pUid = "";	// ��Ϊ��Session��ȡ
//    	if(req.getParameter("pUid")!=null) {
//    		pUid = req.getParameter("pUid");
//    	}
    	
    	// ����������Ϣ
    	HttpSession hs = req.getSession();
    	Object u = hs.getAttribute("pub");
    	if(u != null) {
    		pUid = ((Publisher)u).getpUid();
    	} else {
    		// �û�δ��¼��ֱ����Ӧ
    		String resp_str = "{\"isLogin\":0,\"orderList\":null}";
        	resp.getWriter().write(resp_str);
        	return;
    	}

    	// ������˵�����з��ѵ�¼
    	List<Order> lo = new ArrayList<Order>();
    	try {
    		lo = od.pubLoadOrder(pUid);
    		
    		// new��������־��¼
			String webRoot = this.getServletContext().getRealPath("");		// ��Ŀ��Ŀ¼
			String IPAddr = Utils.getIPAddr(req);							// ��ȡ����IP
			String operationStr = "od.pubLoadOrder("
									+"\""+pUid+"\""
									+")";
			int result = (lo==null ? -1 : lo.size() );
			(new PubDaoImpl()).recordOperation(pUid, operationStr, result, IPAddr, webRoot);
    		
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	

    	// ��Ӧ������
		String resp_str = "{\"isLogin\":2,\"orderList\":"+new Gson().toJson(lo)+"}";
    	resp.getWriter().write(resp_str);
    	
    	
	}

}
