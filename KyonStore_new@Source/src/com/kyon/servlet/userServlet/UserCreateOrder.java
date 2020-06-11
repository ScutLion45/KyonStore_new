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
		// ������������ʽ
		req.setCharacterEncoding("utf-8");
		// ������Ӧ�����ʽ����Ӧͷ
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// ��ȡ������Ϣ
    	int oNum = 0;
    	String gId = "";
    	double gPrice = 0.0;
    	String uId = "";	// ��Ϊ��Session��ȡ
    	int arg = 0;		// 1-���빺�ﳵ | 2-��������
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
    	
    	// ����������Ϣ
    	HttpSession hs = req.getSession();
    	Object obj = hs.getAttribute("user");
    	if(obj != null) {
        	int success = 0;
    		// ���û���¼�У�����od.userCreateOrder
    		User u = (User)obj;
    		// �ж���arg=2��ʾ����
    		if(arg == 2 && u.getuBalance() < oNum * gPrice) {
    			// ��Ӧ������
            	String resp_str="{\"isLogin\":1,\"success\":0,\"failedInfo\":\"�˻�����\"}";
            	resp.getWriter().write(resp_str);
    			return;
    		}
    		
    		String resp_str = "";
        	try {
        		success = od.userCreateOrder(oNum, gId, gPrice, uId, arg);
        		resp_str="{\"isLogin\":1,\"success\":"+success+",\"failedInfo\":\"\"}";		// Ψһ�ɹ������
        		
        		// �����ɹ������arg=2�������û���uBalance
        		if(success == 1 && arg == 2) {
        			double uB = u.getuBalance();
        			u.setuBalance(uB - oNum * gPrice);	// ��ȥ�ɽ���
        			hs.setAttribute("user", u);
        		}
        		
        	} catch (Exception e) {
    			e.printStackTrace();
    			success = 0;
    			resp_str="{\"isLogin\":1,\"success\":0,\"failedInfo\":\"�д�����\"}";
    		}
        	
        	// ��Ӧ������
        	resp.getWriter().write(resp_str);
    	} else {
    		// û���û���¼��
    		String resp_str = "{\"isLogin\":0,\"success\":0,\"failedInfo\":\"�û�δ��¼\"}";
        	resp.getWriter().write(resp_str);
    	}

    	
    	
    	
	}

}
