package com.kyon.servlet.userServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kyon.dao.OrderDao;
import com.kyon.daoImpl.OrderDaoImpl;

@WebServlet("/user-edit-order")
public class UserEditOrder extends HttpServlet {
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
    	String oId = "";
    	int oNum = 0;
    	if(req.getParameter("oId")!=null) {
    		oId = req.getParameter("oId");
    	}
    	if(req.getParameter("oNum")!=null && req.getParameter("oNum")!="") {
    		oNum = Integer.parseInt(req.getParameter("oNum"));
    	}
    	
    	// �жϵ�ǰ�Ƿ��¼�û�
    	Object obj = req.getSession().getAttribute("user");
    	if(obj == null) {
    		// �û�δ��¼��ֱ����Ӧ
        	String str="{\"success\":0,\"isLogin\":0}";
        	resp.getWriter().write(str);
    		return;
    	}
    	
    	// ����������Ϣ
    	int success = 0;
    	try {
    		success = od.userEditOrder(oId, oNum);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	// ��Ӧ������
    	String str="{\"success\":"+success+",\"isLogin\":1}";
    	resp.getWriter().write(str);
    	
	}

}
