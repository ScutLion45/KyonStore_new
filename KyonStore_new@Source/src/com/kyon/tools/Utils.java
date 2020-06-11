package com.kyon.tools;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

public class Utils {
	/* ��ȡyyyy-MM-dd HH:mm:ss��ʽʱ�� 
	 * HH: 24Сʱ�ƣ���MySQL��datetime��ʽһ��
	 */
	public static String localeDateTime() {
		Date now = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatDT = f.format(now);
		return formatDT;
	}
	
	/* �������ID */
	public static String genRandID() {
		UUID uuid=UUID.randomUUID();
		
		/* �γ�MMddHHmmǰ׺ */
		Date now = new Date();
		SimpleDateFormat f = new SimpleDateFormat("MMdd-HHmm");
		String ldt = f.format(now);
		
        String randID = ldt + "-" + uuid.toString();
        return randID;
	}
	
	/* ��ȡĳ������������ 
	 * ���룺yyyy-MM�ַ���
	 */
	public static int getMaxDateOf(String yM) {
		int dates = 0;
		int year = 0;
		int month = 0;
		int flag = 0;
		try {
			year = Integer.parseInt(yM.split("-")[0]);
			month = Integer.parseInt(yM.split("-")[1]);
			
			if(year%4==0 && year%100!=0)
				flag = 1;  // �ж��Ƿ�Ϊ����
			
			switch(month) {
				case 1: case 3: case 5: case 7: case 8: case 10: case 12:
					dates = 31; break;
				case 4: case 6: case 9: case 11:
					dates = 30; break;
				default:
					dates = 0;
			}
			if(month==2) {
				if(flag==1) dates = 29;
				else dates = 28;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return dates;
	}
	
	/*
	 * ��request��ȡ�ͻ���IP��ַ�����ͨ�������������͸������ǽ��ȡ��ʵIP��ַ;
	 * reference: https://www.cnblogs.com/Mauno/p/Mauno.html
	 */
	public static String getIPAddr(HttpServletRequest req) throws IOException {
		
		String ip = req.getHeader("X-Forwarded-For");	// Squid �������
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");		// apache �������
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");	// weblogic �������
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_CLIENT_IP");		// ĳЩ���������
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("X-Real-IP");			// nginx�������
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_X_FORWARDED_FOR");
		}
		
		// ��Щ����ͨ����������ô��ȡ����ip�ͻ��ж����һ�㶼��ͨ�����ţ�,���ָ�������ҵ�һ��ipΪ�ͻ��˵���ʵIP
	    if (ip != null && ip.length() != 0) {
	        ip = ip.split(",")[0];
	    }
	    
	    // ���ǲ��ܻ�ȡ���������ͨ��request.getRemoteAddr();��ȡ
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		
		return ip;
	}
	
	// �ж��ļ��Ƿ����
	public static boolean filePathExists(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}
	
}
