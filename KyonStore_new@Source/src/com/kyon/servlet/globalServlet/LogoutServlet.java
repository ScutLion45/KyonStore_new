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
		// 设置请求编码格式
		req.setCharacterEncoding("utf-8");
		// 设置响应编码格式和响应头
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// 获取请求信息
    	int arg = 0;
    	if(req.getParameter("arg")!=null)
    		arg = Integer.parseInt(req.getParameter("arg"));
    	
    	// 处理请求信息
    	HttpSession hs = req.getSession();
//    		/*
		if(arg == 1) {		// 用户登出
//			hs.removeAttribute("user");
			// hs.removeAttribute("uProtrait");
			// hs.removeAttribute("uP_set");
			System.out.println("用户登出");
		}
		else if(arg == 2) {	// 发行方登出
//			hs.removeAttribute("pub");
			System.out.println("发行方登出");
		}
//			*/
		hs.invalidate();    // 直接使之失效，触发UserSessionListener.sessionDestroyed() => 更新用户画像、记录登出日志
    	
    	// 响应处理结果
    	String str="{\"isLogin\": 0}";
    	resp.getWriter().write(str);
    	
    	
	}

}
