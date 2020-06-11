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
		// ����QueryRunner������������
		QueryRunner runner = null;
		User u = null;
		int exist = 0;
		
		try {
			// ����QueryRunner����
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// ����sql���
				String sql = "select * from user where uname=?";
			// ����params��������
				Object[] params = {uname};
			// ����query����
				u = (User) runner.query(sql, new BeanHandler<User>(User.class), params);
			// ������
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
		// ����QueryRunner������������
		QueryRunner runner = null;
		User u = null;
		
		try {
			// ����QueryRunner����
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// ����sql���
				String sql = "select * from user where uname=? and upwd=?";
			// ����params��������
				Object[] params = {uname,upwd};
			// ����query����
				u = (User) runner.query(sql, new BeanHandler<User>(User.class), params);
			// ������
				System.out.println(u);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return u;
	}
	
	@Override
	public int checkUMail(String umail) {
		// ����QueryRunner������������
		QueryRunner runner = null;
		User u = null;
		int exist = 0;
		
		try {
			// ����QueryRunner����
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// ����sql���
				String sql = "select * from user where umail=?";
			// ����params��������
				Object[] params = {umail};
			// ����query����
				u = (User) runner.query(sql, new BeanHandler<User>(User.class), params);
			// ������
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
		// ����QueryRunner������������
		QueryRunner runner = null;
		Publisher p = null;
		int exist = 0;
		
		try {
			// ����QueryRunner����
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// ����sql���
				String sql = "select * from publisher where pname=?";
			// ����params��������
				Object[] params = {pname};
			// ����query����
				p = (Publisher) runner.query(sql, new BeanHandler<Publisher>(Publisher.class), params);
			// ������
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
		// ����QueryRunner������������
		QueryRunner runner = null;
		Publisher p = null;
		
		try {
			// ����QueryRunner����
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// ����sql���
				String sql = "select * from publisher where pname=? and ppwd=?";
			// ����params��������
				Object[] params = {pname,ppwd};
			// ����query����
				p = (Publisher) runner.query(sql, new BeanHandler<Publisher>(Publisher.class), params);
			// ������
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return p;
	}


}
