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
		// ������������ʽ
		req.setCharacterEncoding("utf-8");
		// ������Ӧ�����ʽ����Ӧͷ
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// ��ȡ������Ϣ
    	String oId = "";
    	int arg = 0;		// 2-�û��ӹ��ﳵ����
    	
    	if(req.getParameter("oId")!=null) {
    		oId = req.getParameter("oId");
    	}
    	if(req.getParameter("arg")!=null && req.getParameter("arg")!="") {
    		arg = Integer.parseInt(req.getParameter("arg"));
    	}
    	
    	// new�����ڸ���session��uBalance���û��ˣ�
    	double gPrice = 0.0;
    	int oNum = 0;
    	if(req.getParameter("gPrice")!=null) {
    		gPrice = Double.parseDouble(req.getParameter("gPrice"));
    	}
    	if(req.getParameter("oNum")!=null && req.getParameter("oNum")!="") {
    		oNum = Integer.parseInt(req.getParameter("oNum"));
    	}
    	
    	
    	// �жϵ�ǰ�Ƿ��¼�û�/���з�
//    	Object obj = req.getSession().getAttribute("user");
    	int isLogin = 0;
    	
    	if(req.getSession().getAttribute("user") != null) {
    		isLogin = 1;
    	} else if(req.getSession().getAttribute("pub") != null) {
    		isLogin = 2;
		} else {
    		// �û�δ��¼��ֱ����Ӧ
        	String str="{\"success\":0,\"isLogin\":0}";
        	resp.getWriter().write(str);
    		return;
    	}
    	
    	// ����������Ϣ��������˵�����û�/���з���¼
    	int success = 0;
    	try {
    		success = od.updateOrder(oId, arg);
    		if(success == 1 & isLogin == 1 && arg == 2) {
    			User u = (User)req.getSession().getAttribute("user");
    			// �����ɹ��������û���uBalance
    			double uB = u.getuBalance();
    			u.setuBalance(uB - oNum * gPrice);	// ��ȥ�ɽ���
    			req.getSession().setAttribute("user", u);
    		} else if(success == 1 && isLogin == 2 && arg == 3) {
        		// new��������־��¼
    			String webRoot = this.getServletContext().getRealPath("");		// ��Ŀ��Ŀ¼
    			Object obj = req.getSession().getAttribute("pub");
        		String pUid = ((Publisher)obj).getpUid();						// ��ȡpUid
    			String IPAddr = Utils.getIPAddr(req);							// ��ȡ����IP
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
    	
    	// ��Ӧ������
    	String str="{\"success\":"+success+",\"isLogin\":"+isLogin+"}";
    	resp.getWriter().write(str);
    	
	}

}
