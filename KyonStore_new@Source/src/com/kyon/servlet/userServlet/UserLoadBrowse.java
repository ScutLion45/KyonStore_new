package com.kyon.servlet.userServlet;

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
import com.kyon.dao.UserDao;
import com.kyon.daoImpl.UserDaoImpl;
import com.kyon.pojo.Browse;
import com.kyon.pojo.User;

@WebServlet("/user-load-browse")
public class UserLoadBrowse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDao ud = new UserDaoImpl();
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// ������������ʽ
		req.setCharacterEncoding("utf-8");
		// ������Ӧ�����ʽ����Ӧͷ
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// ��ȡ������Ϣ 
    	String uId = "";	// ��Ϊ��Session��ȡ
//    	if(req.getParameter("uId")!=null) {
//    		uId = req.getParameter("uId");
//    	}
    	
    	// ����������Ϣ
    	HttpSession hs = req.getSession();
    	Object u = hs.getAttribute("user");
    	if(u != null) {
    		uId = ((User)u).getuId();
    	} else {
    		// �û�δ��¼��ֱ����Ӧ
    		String resp_str = "{\"isLogin\":0,\"browseList\":null}";
        	resp.getWriter().write(resp_str);
        	return;
    	}

    	// ������˵���û��ѵ�¼
		List<Browse> lb = new ArrayList<Browse>();
		try {
			lb = ud.loadBrowse(uId);
		} catch (Exception e) {
			e.printStackTrace();
		}

    	// ��Ӧ������
    	String resp_str = "{\"isLogin\":1,\"browseList\":"+new Gson().toJson(lb)+"}";
    	resp.getWriter().write(resp_str);
	}

}
