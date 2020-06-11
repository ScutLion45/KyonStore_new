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
		// 设置请求编码格式
		req.setCharacterEncoding("utf-8");
		// 设置响应编码格式和响应头
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// 获取请求信息
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
    	
    	// 处理请求信息
    	int success=0;
    	
    	try {
			if(arg==1) {
				success = rd.userReg(attach, name, pwd);
				System.out.println("用户注册["+attach+","+name+","+pwd+"]: "+success);
				// 写入session
				// 不使用sendRedirect
			} else if(arg==2) {
				success = rd.pubReg(name, pwd, attach);
				System.out.println("发行方注册["+name+","+pwd+","+attach+"]: "+success);
				// 写入session
			}
		} catch (Exception e) {
			success = -1;
			e.printStackTrace();
		}
    	
    	// 响应处理结果
    	String str="{\"success\":"+success+"}";
    	resp.getWriter().write(str);
	}

}
