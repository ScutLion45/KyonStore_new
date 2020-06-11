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
		// 设置请求编码格式
		req.setCharacterEncoding("utf-8");
		// 设置响应编码格式和响应头
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// 获取请求信息
    	String umail = "";
    	if(req.getParameter("umail")!=null)
    		umail = req.getParameter("umail");
//    	System.out.println(umail);
    	
    	// 处理请求信息
    	int exist = 0;
    	try {
			exist = ld.checkUMail(umail);
			System.out.println("邮箱检验["+umail+"]: "+exist);

		} catch (Exception e) {
			exist = -1;
			e.printStackTrace();
		}
    	
    	// 响应处理结果
    	String str="{\"exist\":"+exist+"}";
    	resp.getWriter().write(str);
	}

}
