package com.kyon.servlet.globalServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import com.kyon.pojo.*;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// ������������ʽ
		req.setCharacterEncoding("utf-8");
		// ������Ӧ�����ʽ����Ӧͷ
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// ��ȡ������Ϣ
    	int arg = 0;
    	if(req.getParameter("arg")!=null)
    		arg = Integer.parseInt(req.getParameter("arg"));
    	
    	// ����������Ϣ
    	HttpSession hs = req.getSession();
//    		/*
		if(arg == 1) {		// �û��ǳ�
//			hs.removeAttribute("user");
			// hs.removeAttribute("uProtrait");
			// hs.removeAttribute("uP_set");
			System.out.println("�û��ǳ�");
		}
		else if(arg == 2) {	// ���з��ǳ�
//			hs.removeAttribute("pub");
			System.out.println("���з��ǳ�");
		}
//			*/
		hs.invalidate();    // ֱ��ʹ֮ʧЧ������UserSessionListener.sessionDestroyed() => �����û����񡢼�¼�ǳ���־
    	
    	// ��Ӧ������
    	String str="{\"isLogin\": 0}";
    	resp.getWriter().write(str);
    	
    	
	}

}
