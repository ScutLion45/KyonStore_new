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
		// ������������ʽ
		req.setCharacterEncoding("utf-8");
		// ������Ӧ�����ʽ����Ӧͷ
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// ��ȡ������Ϣ
    	String pUid = "";	// ����isUserSearch�ж�ȡ��
    	int gType = 0;
    	String gPubTime = "";
    	int gState = 0;
    	String gName = "";
    	int isUserSearch = -1;	// 1-�û���ѯ��0-���з���ѯ
    	
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
    		// ��ѯ��¼�еķ��з�����Ʒ
    		// ��Session��ȡpUid
    		Publisher p = (Publisher)req.getSession().getAttribute("pub");
    		if(p != null) {
    			// ���з���¼��
    			pUid = p.getpUid();
    			isLogin = 2;
    		} else {
    			// ���з�δ��¼��ֱ����Ӧ
    	    	String resp_str = "{\"isLogin\":0,\"data\":null}";
    	    	resp.getWriter().write(resp_str);
    			return;
    		}
    	} else {
    		// �û���ѯĳ���з�����Ʒ
    		// �Ӳ�����ȡpUid
        	if(req.getParameter("pUid")!=null)
            	pUid = req.getParameter("pUid");
    	}
    	
    	// ������˵������isUserSearch==0 && p==null
    	System.out.println("["+isUserSearch+","+pUid+","+gType+","+gPubTime+","+gState+","+gName+"]");
//    	System.out.println(name);
    	
    	// ����������Ϣ
    	List<Goods> lg = new ArrayList<Goods>();
    	try {
			lg = pd.searchGoods(pUid, gType, gPubTime, gState, gName, req.getSession());
			// new��������־��¼
			if(isUserSearch == 0) {
				String webRoot = this.getServletContext().getRealPath("");		// ��Ŀ��Ŀ¼
				String IPAddr = Utils.getIPAddr(req);		// ��ȡ����IP
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
    	
    	
    	// ��Ӧ������
    	String resp_str = "{\"isLogin\":"+isLogin+",\"data\":"+new Gson().toJson(lg)+"}";
    	resp.getWriter().write(resp_str);
	}
	
	
}
