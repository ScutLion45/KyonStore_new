package com.kyon.servlet.globalServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kyon.dao.LoginDao;
import com.kyon.daoImpl.LoginDaoImpl;

@WebServlet("/check-umail")
public class CheckUMailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	LoginDao ld = new LoginDaoImpl();
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// ������������ʽ
		req.setCharacterEncoding("utf-8");
		// ������Ӧ�����ʽ����Ӧͷ
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// ��ȡ������Ϣ
    	String umail = "";
    	if(req.getParameter("umail")!=null)
    		umail = req.getParameter("umail");
//    	System.out.println(umail);
    	
    	// ����������Ϣ
    	int exist = 0;
    	try {
			exist = ld.checkUMail(umail);
			System.out.println("�������["+umail+"]: "+exist);

		} catch (Exception e) {
			exist = -1;
			e.printStackTrace();
		}
    	
    	// ��Ӧ������
    	String str="{\"exist\":"+exist+"}";
    	resp.getWriter().write(str);
	}

}
