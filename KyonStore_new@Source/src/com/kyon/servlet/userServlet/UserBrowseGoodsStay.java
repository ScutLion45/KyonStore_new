package com.kyon.servlet.userServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kyon.dao.UserDao;
import com.kyon.daoImpl.UserDaoImpl;
import com.kyon.pojo.User;

@WebServlet("/browse-goods-stay")
public class UserBrowseGoodsStay extends HttpServlet {
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
    	String uId = "";		// ��Ϊ��Session��ȡ
    	String gId = "";
    	int stay_add = 0;
//    	if(req.getParameter("uId")!=null)
//    		uId = req.getParameter("uId");
    	if(req.getParameter("gId")!=null)
    		gId = req.getParameter("gId");
    	if(req.getParameter("stay_add")!=null)
    		stay_add = Integer.parseInt(req.getParameter("stay_add"));
    	
		System.out.println("[UserBrowseGoodsStay] stay_add="+stay_add);
    	
    	// ��Session��ȡuId
    	Object u = req.getSession().getAttribute("user");
    	if(u != null) {
    		uId = ((User)u).getuId();
    	} else {
    		// �û�δ��¼��ֱ����Ӧ
    		System.out.println("[UserBrowseGoodsStay] No User Logined.");
        	String str="{\"isLogin\": 0,\"success\":0}";
        	resp.getWriter().write(str);
    		return;
    	}

    	// ������˵���û��ѵ�¼
    	// ����������Ϣ
    	int success = 0;
    	try {
    		int flag = ud.browseGoodsStay(uId, gId, stay_add);
    		if(flag==1)
    			success = 1;
    		System.out.println("[UserBrowseGoodsStay] flag="+flag);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	// ��Ӧ������
    	String str="{\"isLogin\": 1,\"success\":"+success+"}";
    	resp.getWriter().write(str);
	}

}
