package com.kyon.servlet.userServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kyon.dao.UserDao;
import com.kyon.daoImpl.UserDaoImpl;
import com.kyon.pojo.Goods;

@WebServlet("/search-goods")
public class UserSearchGoods extends HttpServlet {
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
    	int gType = 0;
    	String gPubTime = "";
    	String gName = "";

    	if(req.getParameter("gType")!=null)
    		gType = Integer.parseInt(req.getParameter("gType"));
    	if(req.getParameter("gPubTime")!=null)
    		gPubTime = req.getParameter("gPubTime");
    	if(req.getParameter("gName")!=null)
      	 	gName = req.getParameter("gName");

    	// ����������Ϣ
    	List<Goods> lg = new ArrayList<Goods>();
    	try {
    		lg = ud.searchGoods(gType, gPubTime, gName, req.getSession());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	

    	// ��Ӧ������
    	String resp_str = "{\"data\":"+new Gson().toJson(lg)+"}";
    	resp.getWriter().write(resp_str);    	
	}

}
