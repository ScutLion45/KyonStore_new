package com.kyon.servlet.globalServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import com.kyon.dao.LoginDao;
import com.kyon.daoImpl.LoginDaoImpl;

@WebServlet("/check-name")
public class CheckNameServlet extends HttpServlet {
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
    	
    	
//    	HttpSession hs = req.getSession();
//    	hs.setAttribute("attr_test_key", new String("attr_test_val"));
//    	String attr_test_val = (String)hs.getAttribute("attr_test_key");
//    	hs.removeAttribute("attr_test_key");
    	
    	// ��ȡ������Ϣ
    	String name = "";
    	int arg = 0;
    	if(req.getParameter("name")!=null)
        	name = req.getParameter("name");
//    	System.out.println(name);
    	if(req.getParameter("arg")!=null)
    		arg = Integer.parseInt(req.getParameter("arg"));
    	
    	// ����������Ϣ
    	int exist = 0;
    	try {
			if(arg==1) {
				exist = ld.checkUName(name);
				System.out.println("�û�����["+name+"]: "+exist);
			} else if(arg==2) {
				exist = ld.checkPName(name);
				System.out.println("���з�����["+name+"]: "+exist);
			}
		} catch (Exception e) {
			exist = -1;
			e.printStackTrace();
		}
    	
    	// ��Ӧ������
    	String str="{\"exist\":"+exist+"}";
    	resp.getWriter().write(str);
	}

}
