package com.kyon.servlet.pubServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kyon.dao.PubDao;
import com.kyon.daoImpl.PubDaoImpl;
import com.kyon.pojo.Publisher;
import com.kyon.tools.Utils;

@WebServlet("/pub-edit-profile")
public class PubEditProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PubDao pd = new PubDaoImpl();
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 设置请求编码格式
		req.setCharacterEncoding("utf-8");
		// 设置响应编码格式和响应头
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// 获取请求信息
    	String pUid = "";	// 改为从Session获取
    	String pPwd = "";
    	String pInfo = "";
    	
//    	if(req.getParameter("pUid")!=null)
//    		pUid = req.getParameter("pUid");
    	if(req.getParameter("pPwd")!=null)
    		pPwd = req.getParameter("pPwd");
    	if(req.getParameter("pInfo")!=null)
    		pInfo = req.getParameter("pInfo");

    	// 判断当前是否登录发行方
    	Object obj = req.getSession().getAttribute("pub");
    	if(obj == null) {
    		// 用户未登录，直接响应
        	String str="{\"success\":0,\"isLogin\":0}";
        	resp.getWriter().write(str);
    		return;
    	}
    	pUid = ((Publisher)obj).getpUid();
    	
    	System.out.println("pub_edit_profile["+pUid+","+pPwd+","+pInfo+"]");
    	
    	// 处理请求信息
    	int success = 0;
    	try {
    		int flag = pd.editProfile(pUid, pPwd, pInfo);
    		System.out.println("flag="+flag);
    		if(flag==1)
    			success = 1;
    		
    		// new：操作日志记录
			String webRoot = this.getServletContext().getRealPath("");		// 项目根目录
//    		String pUid = ((Publisher)obj).getpUid();	// 获取pUid
			String IPAddr = Utils.getIPAddr(req);		// 获取请求IP
			String operationStr = "pd.editProfile("
									+"\""+pUid+"\","
									+"\""+pPwd+"\","
									+"\""+pInfo+"\""
									+")";
			int result = success;
			pd.recordOperation(pUid, operationStr, result, IPAddr, webRoot);
    		
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	// 响应处理结果
    	String str="{\"success\":"+success+",\"isLogin\":2}";
    	resp.getWriter().write(str);
    	
	}

}
