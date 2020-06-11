package com.kyon.servlet.pubServlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.kyon.dao.PubDao;
import com.kyon.daoImpl.PubDaoImpl;
import com.kyon.tools.Utils;
import com.kyon.pojo.*;

@WebServlet("/pub-create-goods")
public class PubCreateGoods extends HttpServlet {
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
    	
		// new ----------------------------------------------------------------------------
		// �жϵ�ǰ�Ƿ��¼���з�
    	Object obj = req.getSession().getAttribute("pub");
    	if(obj == null) {
    		// �û�δ��¼��ֱ����Ӧ
        	String str="{\"success\":0,\"isLogin\":0}";
        	resp.getWriter().write(str);
    		return;
    	}
    	// new ----------------------------------------------------------------------------
    	
    	// ������Ϣ������������˵�����з��ѵ�¼
    	String gId = Utils.genRandID();
    	String gImg = "";
    	String gName = ""; // formField
    	String gInfo = ""; // formField
    	int gType = 0; // formField
    	double gPrice = 0.0; // formField
    	
    	String pUid = ""; 		// ��Ϊ��Session��ȡ
    	pUid = ((Publisher)obj).getpUid();
    	
		String webPath="";
		String webRoot=this.getServletContext().getRealPath("");

		//1.��ȡ���ϴ���fileItem
			//����DiskFileItemFactory��������
    	DiskFileItemFactory factory = new DiskFileItemFactory();
    		//�����ļ��Ļ���Ŀ¼��������������´���һ��
    	String tempPath=webRoot+File.separator+"tempFolder";   	
    	//System.out.println(tempPath);
    	File f=new File(tempPath);
    	if(!f.exists()) {
    		f.mkdirs();
    	}
    		//�����ļ�����·��
    	factory.setRepository(f);
    		//����ServletFileUpload����
    	ServletFileUpload fileUpload = new ServletFileUpload(factory);
    		//�����ַ�����
    	fileUpload.setHeaderEncoding("utf-8");
    		//����request���õ��ϴ��ļ���FileItem����
		try {
			List<FileItem> fileItems= fileUpload.parseRequest(req);
			
		//2.���ֿ��ļ�������ͨ�ֶ�
			//�������ϣ���ȡ�����ֶ�
			for(int i=0;i<fileItems.size();i++) {
				if(fileItems.get(i).isFormField()) {
					//���λ�ȡ��������gName��gInfo��gType��gPrice��pUid
					String str = fileItems.get(i).getString();
					String fieldName = fileItems.get(i).getFieldName();
					//�����������iso-8859-1 ��utf-8
					str=new String(str.getBytes("iso8859-1"),"utf-8");
					//��ӡ����
					//System.out.println("FormField["+fieldName+": "+str+"]");
					if ("gName".equals(fieldName)) {
						gName = str;
					} else if ("gInfo".equals(fieldName)) {
						gInfo = str;
					} else if ("gType".equals(fieldName)) {
						if(!"".equals(str))
							gType = Integer.parseInt(str);
					} else if ("gPrice".equals(fieldName)) {
						if(!"".equals(str))
							gPrice = Double.parseDouble(str);
					} 
//					else if ("pUid".equals(fieldName)) {
//						pUid = str;
//					}
					//uNo=s;
				}
			}
			//�������ϣ���ȡ�ļ�
			for(int i=0;i<fileItems.size();i++) {
				if(fileItems.get(i).isFormField()) {//��ͨ�ֶ�FileItem					
					// pass
				}
				else {//�ļ�FileItem
					System.out.println("file: "+fileItems.get(i).getName());					
					
					//�����ȡ���ϴ����ļ�����׺
						String afterDot=fileItems.get(i).getName();
					//��ȡ�ļ�����׺
						afterDot=afterDot.substring(afterDot.lastIndexOf("."));
					//��������
						gImg = afterDot;  // �ļ���׺��ʽ
						String fileName=gId+afterDot;
						webPath=webRoot+File.separator
								+"img"+File.separator
								+"goods"+File.separator;
						String filePath=webPath+fileName;
					//�����ļ�
						File file=new File(filePath);
					//��û����仰��ʾֱ�Ӵ���filePath�ļ��У��������ļ�	
						file.getParentFile().mkdirs();
						file.createNewFile();
					//��ȡ�ϴ����ļ���
						InputStream in = fileItems.get(i).getInputStream();
					//�򿪴����õ��ļ�
						FileOutputStream out =new FileOutputStream(file);
					//���Կ�
						byte[] buffer= new byte[1024];//ÿ�ζ�ȡ1KB
						int len;
					//��ʼ��ȡ
						while((len=in.read(buffer))>0)
							out.write(buffer, 0,len);
					//�ر���
						in.close();
						out.close();
					//ɾ����ʱ�ļ�
						fileItems.get(i).delete();	
						System.out.println("��ƷͼƬ�ϴ��ɹ�");
					
					
				}
				//�ۺ�����ȡ���ֶ�
	
			}
			
		}catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	
    	// System.out.println("pub_create_goods["+gId+","+gName+","+gInfo+","+gType+","+gPrice+","+gImg+","+pUid+"]");
    	
    	// ����������Ϣ
    	int success = 0;
    	try {
    		success = pd.createGoods(gId, gName, gInfo, gType, gPrice, gImg, pUid);

    		// new��������־��¼
//			String webRoot = this.getServletContext().getRealPath("");		// ��Ŀ��Ŀ¼
//    		String pUid = ((Publisher)obj).getpUid();	// ��ȡpUid
			String IPAddr = Utils.getIPAddr(req);		// ��ȡ����IP
			String operationStr = "pd.createGoods("
									+"\""+gId+"\","
									+"\""+gName+"\","
									+"\""+gInfo+"\","
									+gType+","
									+gPrice+","
									+"\""+gImg+"\","
									+"\""+pUid+"\""
									+")";
			int result = success;
			pd.recordOperation(pUid, operationStr, result, IPAddr, webRoot);
    		
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	// ��Ӧ������
    	String str="{\"success\":"+success+",\"isLogin\":2}";
    	resp.getWriter().write(str);
    	
	}

}
