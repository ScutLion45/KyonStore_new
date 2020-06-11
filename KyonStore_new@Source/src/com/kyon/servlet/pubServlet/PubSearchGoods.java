package com.kyon.servlet.pubServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kyon.dao.PubDao;
import com.kyon.daoImpl.PubDaoImpl;
import com.kyon.pojo.Goods;
import com.kyon.pojo.Publisher;
import com.kyon.tools.Utils;

@WebServlet("/pub-search-goods")
public class PubSearchGoods extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PubDao pd = new PubDaoImpl();
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
    	String pUid = "";	// 根据isUserSearch判断取法
    	int gType = 0;
    	String gPubTime = "";
    	int gState = 0;
    	String gName = "";
    	int isUserSearch = -1;	// 1-用户查询；0-发行方查询
    	
    	// new
//    	if(req.getParameter("pUid")!=null)
//        	pUid = req.getParameter("pUid");
    	if(req.getParameter("gType")!=null)
    		gType = Integer.parseInt(req.getParameter("gType"));
    	if(req.getParameter("gPubTime")!=null)
    		gPubTime = req.getParameter("gPubTime");
    	if(req.getParameter("gState")!=null)
    		gState = Integer.parseInt(req.getParameter("gState"));
    	if(req.getParameter("gName")!=null)
      	 	gName = req.getParameter("gName");
    	
    	// new
    	int isLogin = 0;
    	if(req.getParameter("isUserSearch")!=null)
    		isUserSearch = Integer.parseInt(req.getParameter("isUserSearch"));
    	
    	if(isUserSearch == 0) {
    		// 查询登录中的发行方的商品
    		// 从Session获取pUid
    		Publisher p = (Publisher)req.getSession().getAttribute("pub");
    		if(p != null) {
    			// 发行方登录中
    			pUid = p.getpUid();
    			isLogin = 2;
    		} else {
    			// 发行方未登录，直接响应
    	    	String resp_str = "{\"isLogin\":0,\"data\":null}";
    	    	resp.getWriter().write(resp_str);
    			return;
    		}
    	} else {
    		// 用户查询某发行方的商品
    		// 从参数获取pUid
        	if(req.getParameter("pUid")!=null)
            	pUid = req.getParameter("pUid");
    	}
    	
    	// 到这里说明不是isUserSearch==0 && p==null
    	System.out.println("["+isUserSearch+","+pUid+","+gType+","+gPubTime+","+gState+","+gName+"]");
//    	System.out.println(name);
    	
    	// 处理请求信息
    	List<Goods> lg = new ArrayList<Goods>();
    	try {
			lg = pd.searchGoods(pUid, gType, gPubTime, gState, gName, req.getSession());
			// new：操作日志记录
			if(isUserSearch == 0) {
				String webRoot = this.getServletContext().getRealPath("");		// 项目根目录
				String IPAddr = Utils.getIPAddr(req);		// 获取请求IP
				String operationStr = "pd.searchGoods("
										+"\""+pUid+"\","
										+gType+","
										+"\""+gPubTime+"\","
										+gState+","
										+"\""+gName+"\""
										+")";
				int result = (lg==null ? -1 : lg.size() );
				pd.recordOperation(pUid, operationStr, result, IPAddr, webRoot);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    	// 响应处理结果
    	String resp_str = "{\"isLogin\":"+isLogin+",\"data\":"+new Gson().toJson(lg)+"}";
    	resp.getWriter().write(resp_str);
	}
	
	
}
