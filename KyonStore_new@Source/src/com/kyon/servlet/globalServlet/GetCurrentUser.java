package com.kyon.servlet.globalServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
//import com.kyon.pojo.*;

@WebServlet("/get-current-user")
public class GetCurrentUser extends HttpServlet {
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
    	Object obj = null;
    	int isLogin = 0;
    	
    	// ver2.0���û���Session['user']�����з���Session['pub']��com.kyon.servlet.globalServlet.LoginServlet
    	if(arg == 1)
    		obj = hs.getAttribute("user");
    	else if(arg == 2)
    		obj = hs.getAttribute("pub");
    	else if(arg == 3) {
    		// ȫ�ֲ�ѯ������ѯ�Ƿ����û����з���¼
    		if(hs.getAttribute("user")!=null) {
    			isLogin = 1;
    		} else if(hs.getAttribute("pub")!=null) {
    			isLogin = 2;
    		} else
    			isLogin = 0;
    		// ֱ�ӷ���
    		resp.getWriter().write("{\"isLogin\":"+isLogin+",\"user\":null}");
    		return;
    	}
    	
    	// ver1.0���û��ͷ��з�����Session['user']
    	// Object obj = hs.getAttribute("user");	// Object����GsonҲ����ȷparse

    	// ������˵��arg = 1 �� 2
    	if(obj != null) {
    		isLogin = arg;
    		// ���û�/���з���¼�У����û����Ƿ��з���ǰ���ж�
    		resp.getWriter().write("{\"isLogin\":"+isLogin+",\"user\":"+new Gson().toJson(obj)+"}");
    	} else {
    		isLogin = 0;
    		// û���û�/���з���¼��
    		resp.getWriter().write("{\"isLogin\":"+isLogin+",\"user\":null}");
    	}
    	
    	
    	
    	
	}

}
