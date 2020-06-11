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
		// 设置请求编码格式
		req.setCharacterEncoding("utf-8");
		// 设置响应编码格式和响应头
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	
//    	HttpSession hs = req.getSession();
//    	hs.setAttribute("attr_test_key", new String("attr_test_val"));
//    	String attr_test_val = (String)hs.getAttribute("attr_test_key");
//    	hs.removeAttribute("attr_test_key");
    	
    	// 获取请求信息
    	String name = "";
    	int arg = 0;
    	if(req.getParameter("name")!=null)
        	name = req.getParameter("name");
//    	System.out.println(name);
    	if(req.getParameter("arg")!=null)
    		arg = Integer.parseInt(req.getParameter("arg"));
    	
    	// 处理请求信息
    	int exist = 0;
    	try {
			if(arg==1) {
				exist = ld.checkUName(name);
				System.out.println("用户检验["+name+"]: "+exist);
			} else if(arg==2) {
				exist = ld.checkPName(name);
				System.out.println("发行方检验["+name+"]: "+exist);
			}
		} catch (Exception e) {
			exist = -1;
			e.printStackTrace();
		}
    	
    	// 响应处理结果
    	String str="{\"exist\":"+exist+"}";
    	resp.getWriter().write(str);
	}

}
