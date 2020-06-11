package com.kyon.tools;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

public class Utils {
	/* 获取yyyy-MM-dd HH:mm:ss格式时间 
	 * HH: 24小时制，与MySQL的datetime格式一致
	 */
	public static String localeDateTime() {
		Date now = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatDT = f.format(now);
		return formatDT;
	}
	
	/* 生成随机ID */
	public static String genRandID() {
		UUID uuid=UUID.randomUUID();
		
		/* 形成MMddHHmm前缀 */
		Date now = new Date();
		SimpleDateFormat f = new SimpleDateFormat("MMdd-HHmm");
		String ldt = f.format(now);
		
        String randID = ldt + "-" + uuid.toString();
        return randID;
	}
	
	/* 获取某月最大的日期数 
	 * 输入：yyyy-MM字符串
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
				flag = 1;  // 判断是否为闰年
			
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
	 * 从request获取客户端IP地址，如果通过代理进来，则透过防火墙获取真实IP地址;
	 * reference: https://www.cnblogs.com/Mauno/p/Mauno.html
	 */
	public static String getIPAddr(HttpServletRequest req) throws IOException {
		
		String ip = req.getHeader("X-Forwarded-For");	// Squid 服务代理
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");		// apache 服务代理
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");	// weblogic 服务代理
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_CLIENT_IP");		// 某些代理服务器
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("X-Real-IP");			// nginx服务代理
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_X_FORWARDED_FOR");
		}
		
		// 有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
	    if (ip != null && ip.length() != 0) {
	        ip = ip.split(",")[0];
	    }
	    
	    // 还是不能获取到，最后再通过request.getRemoteAddr();获取
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		
		return ip;
	}
	
	// 判断文件是否存在
	public static boolean filePathExists(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}
	
}
