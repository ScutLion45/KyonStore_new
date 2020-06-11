package com.kyon.servlet.pubServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kyon.dao.PubDao;
import com.kyon.daoImpl.PubDaoImpl;

@WebServlet("/pub-edit-goods")
public class PubEditGoods extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PubDao pd = new PubDaoImpl();
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// ������������ʽ
		req.setCharacterEncoding("utf-8");
		// ������Ӧ�����ʽ����Ӧͷ
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// ��ȡ������Ϣ
    	String gId = "";
    	String gName = "";
    	String gInfo = "";
    	int gType = 0;
    	double gPrice = 0.0;
    	
    	if(req.getParameter("gId")!=null)
        	gId = req.getParameter("gId");    	
    	if(req.getParameter("gName")!=null)
        	gName = req.getParameter("gName");    	
    	if(req.getParameter("gInfo")!=null)
        	gInfo = req.getParameter("gInfo");    	
    	if(req.getParameter("gType")!=null && !"".equals(req.getParameter("gType")))
        	gType = Integer.parseInt(req.getParameter("gType"));    	
    	if(req.getParameter("gPrice")!=null && !"".equals(req.getParameter("gPrice")))
        	gPrice = Double.parseDouble(req.getParameter("gPrice"));

    	// �жϵ�ǰ�Ƿ��¼���з�
    	Object obj = req.getSession().getAttribute("pub");
    	if(obj == null) {
    		// �û�δ��¼��ֱ����Ӧ
        	String str="{\"success\":0,\"isLogin\":0}";
        	resp.getWriter().write(str);
    		return;
    	}
    	
    	System.out.println("pub_edit_goods["+gId+","+gName+","+gInfo+","+gType+","+gPrice+"]");
    	
    	// ����������Ϣ
    	int success = 0;
    	try {
    		int flag = pd.editGoods(gId, gName, gInfo, gType, gPrice, "");
    		System.out.println(flag);
    		if(flag==0) success = 1;
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	// ��Ӧ������
    	String str="{\"success\":"+success+",\"isLogin\":2}";
    	resp.getWriter().write(str);
	}

}
