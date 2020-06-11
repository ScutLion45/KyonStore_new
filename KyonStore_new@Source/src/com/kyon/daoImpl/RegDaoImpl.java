package com.kyon.daoImpl;

import org.apache.commons.dbutils.QueryRunner;

import com.kyon.dao.RegDao;
import com.kyon.tools.DBCPUtil;
import com.kyon.tools.Utils;

public class RegDaoImpl implements RegDao {

	@Override
	public int userReg(String umail, String uname, String upwd) {
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;
		int flag = 0;
		
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String sql = "insert into user values(?,?,?,?,?)";
			// 创建params参数对象
				String uid = Utils.genRandID();
				Object[] params = { uid, umail, uname, upwd, 450.45 };
			// 调用query方法
				flag = runner.update(sql, params);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return flag; //=1成功
	}

	@Override
	public int pubReg(String pname, String ppwd, String pinfo) {
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;
		int flag = 0;
		
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String sql = "insert into publisher values(?,?,?,?)";
			// 创建params参数对象
				String puid = Utils.genRandID();
				Object[] params = { puid, pname, ppwd, pinfo };
			// 调用query方法
				flag = runner.update(sql, params);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
