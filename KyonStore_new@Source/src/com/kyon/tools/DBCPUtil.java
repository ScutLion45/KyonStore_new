package com.kyon.tools;

//import java.io.File;
//import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
//import java.util.Properties;

import javax.sql.DataSource;

//import java.sql.Connection;

import org.apache.commons.dbcp2.BasicDataSource;
//import org.apache.commons.dbcp2.BasicDataSourceFactory;


public class DBCPUtil {
	private static DataSource ds = null;
	static {
//		// ����������Ϣ
//		Properties prop = new Properties();
//		try {
//			//prop.load(DBCPTest.class.getClassLoader().getResourceAsStream("config/dbcp.properties"));
//			FileInputStream is = new FileInputStream("config"+File.separator+"dbcp.properties");
//			prop.load(is);
//			ds = BasicDataSourceFactory.createDataSource(prop);
//			
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
		
		// ��ȡDBCP����Դʵ�������
		BasicDataSource bds = new BasicDataSource();
		// �����������ݿ���Ҫ��������Ϣ
		bds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		bds.setUrl("jdbc:mysql://localhost:3306/kyonstore");
		bds.setUsername("root");
//		bds.setPassword("1234");
//		bds.setPassword("root");
		bds.setPassword("Mysql@@0513121002");
		
		
		// �������ӳصĲ���
		bds.setInitialSize(10);
		bds.setMaxTotal(100);      // �������������maxActive=100
		bds.setMaxIdle(20);
		bds.setMinIdle(5);
		bds.setMaxWaitMillis(60000);  // maxWait=60000
		bds.setConnectionProperties("useUnicode=true;characterEncoding=UTF8;useSSL=false;serverTimezone=GMT");
		bds.setDefaultAutoCommit(true);
		bds.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED); // READ_UNCOMMITED
		ds = bds;
	}
	
	public static DataSource getDataSource() {
		return ds;
	}
	
	public static Connection getConnection() {
		// ��ȡ���Ӷ���
		Connection conn = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}
