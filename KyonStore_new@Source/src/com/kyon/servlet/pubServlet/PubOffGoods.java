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

@WebServlet("/pub-off-goods")
public class PubOffGoods extends HttpServlet {
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
    	if(req.getParameter("gId")!=null)
        	gId = req.getParameter("gId"); 

    	// �жϵ�ǰ�Ƿ��¼���з�
    	Object obj = req.getSession().getAttribute("pub");
    	if(obj == null) {
    		// �û�δ��¼��ֱ����Ӧ
        	String str="{\"gState\":-1,\"isLogin\":0}";
        	resp.getWriter().write(str);
    		return;
    	}
    	
    	System.out.println("pub_off_goods["+gId+"]");
    	
    	// ����������Ϣ
    	int gs = 0;
    	try {
    		gs = pd.offGoods(gId);
    		System.out.println("gState: "+gs);
    		
    		// new��������־��¼
			String webRoot = this.getServletContext().getRealPath("");		// ��Ŀ��Ŀ¼
    		String pUid = ((Publisher)obj).getpUid();	// ��ȡpUid
			String IPAddr = Utils.getIPAddr(req);		// ��ȡ����IP
			String operationStr = "pd.offGoods("
									+"\""+gId+"\""
									+")";
			int result = gs;
			pd.recordOperation(pUid, operationStr, result, IPAddr, webRoot);
    		
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	// ��Ӧ������
    	String str="{\"gState\":"+gs+",\"isLogin\":2}";
    	resp.getWriter().write(str);
	}

}
