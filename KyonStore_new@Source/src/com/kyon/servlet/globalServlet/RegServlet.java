package com.kyon.servlet.globalServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kyon.dao.RegDao;
import com.kyon.daoImpl.RegDaoImpl;

@WebServlet("/reg")
public class RegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	RegDao rd = new RegDaoImpl();
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// ������������ʽ
		req.setCharacterEncoding("utf-8");
		// ������Ӧ�����ʽ����Ӧͷ
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// ��ȡ������Ϣ
    	String name = "";
    	String pwd = "";
    	String attach="";
    	int arg = 0;
    	if(req.getParameter("name")!=null)
        	name = req.getParameter("name");
    	if(req.getParameter("pwd")!=null)
        	pwd = req.getParameter("pwd");
    	if(req.getParameter("attach")!=null)
    		attach = req.getParameter("attach");
    	if(req.getParameter("arg")!=null)
    		arg = Integer.parseInt(req.getParameter("arg"));
    	
    	// ����������Ϣ
    	int success=0;
    	
    	try {
			if(arg==1) {
				success = rd.userReg(attach, name, pwd);
				System.out.println("�û�ע��["+attach+","+name+","+pwd+"]: "+success);
				// д��session
				// ��ʹ��sendRedirect
			} else if(arg==2) {
				success = rd.pubReg(name, pwd, attach);
				System.out.println("���з�ע��["+name+","+pwd+","+attach+"]: "+success);
				// д��session
			}
		} catch (Exception e) {
			success = -1;
			e.printStackTrace();
		}
    	
    	// ��Ӧ������
    	String str="{\"success\":"+success+"}";
    	resp.getWriter().write(str);
	}

}
