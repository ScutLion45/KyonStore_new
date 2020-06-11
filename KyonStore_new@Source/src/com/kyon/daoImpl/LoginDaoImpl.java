package com.kyon.daoImpl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.kyon.dao.LoginDao;
import com.kyon.pojo.Publisher;
import com.kyon.pojo.User;
import com.kyon.tools.DBCPUtil;

public class LoginDaoImpl implements LoginDao {

	@Override
	public int checkUName(String uname) {
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;
		User u = null;
		int exist = 0;
		
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String sql = "select * from user where uname=?";
			// 创建params参数对象
				Object[] params = {uname};
			// 调用query方法
				u = (User) runner.query(sql, new BeanHandler<User>(User.class), params);
			// 处理结果
				System.out.println(u);
				if(u!=null) {
					exist = 1;
				}
			// test procedure
//				String call = "call testProcedure(?)";
//				Object[] callParams = {"lalala"};
//				int res = runner.execute(call, callParams);
//				System.out.println("call result="+res);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return exist;
	}

	@Override
	public User checkUPwd(String uname, String upwd) {
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;
		User u = null;
		
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String sql = "select * from user where uname=? and upwd=?";
			// 创建params参数对象
				Object[] params = {uname,upwd};
			// 调用query方法
				u = (User) runner.query(sql, new BeanHandler<User>(User.class), params);
			// 处理结果
				System.out.println(u);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return u;
	}
	
	@Override
	public int checkUMail(String umail) {
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;
		User u = null;
		int exist = 0;
		
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String sql = "select * from user where umail=?";
			// 创建params参数对象
				Object[] params = {umail};
			// 调用query方法
				u = (User) runner.query(sql, new BeanHandler<User>(User.class), params);
			// 处理结果
				System.out.println(u);
				if(u!=null) {
					exist = 1;
				}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return exist;
	}
	
	@Override
	public int checkPName(String pname) {
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;
		Publisher p = null;
		int exist = 0;
		
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String sql = "select * from publisher where pname=?";
			// 创建params参数对象
				Object[] params = {pname};
			// 调用query方法
				p = (Publisher) runner.query(sql, new BeanHandler<Publisher>(Publisher.class), params);
			// 处理结果
				System.out.println(p);
				if(p!=null) {
					exist = 1;
				}
			
		} catch(Exception e) {
			exist = -1;
			e.printStackTrace();
		}
		return exist;
	}

	@Override
	public Publisher checkPPwd(String pname, String ppwd) {
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;
		Publisher p = null;
		
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String sql = "select * from publisher where pname=? and ppwd=?";
			// 创建params参数对象
				Object[] params = {pname,ppwd};
			// 调用query方法
				p = (Publisher) runner.query(sql, new BeanHandler<Publisher>(Publisher.class), params);
			// 处理结果
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return p;
	}


}
