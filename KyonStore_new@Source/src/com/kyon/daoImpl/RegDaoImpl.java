package com.kyon.daoImpl;

import org.apache.commons.dbutils.QueryRunner;

import com.kyon.dao.RegDao;
import com.kyon.tools.DBCPUtil;
import com.kyon.tools.Utils;

public class RegDaoImpl implements RegDao {

	@Override
	public int userReg(String umail, String uname, String upwd) {
		// ����QueryRunner������������
		QueryRunner runner = null;
		int flag = 0;
		
		try {
			// ����QueryRunner����
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// ����sql���
				String sql = "insert into user values(?,?,?,?,?)";
			// ����params��������
				String uid = Utils.genRandID();
				Object[] params = { uid, umail, uname, upwd, 450.45 };
			// ����query����
				flag = runner.update(sql, params);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return flag; //=1�ɹ�
	}

	@Override
	public int pubReg(String pname, String ppwd, String pinfo) {
		// ����QueryRunner������������
		QueryRunner runner = null;
		int flag = 0;
		
		try {
			// ����QueryRunner����
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// ����sql���
				String sql = "insert into publisher values(?,?,?,?)";
			// ����params��������
				String puid = Utils.genRandID();
				Object[] params = { puid, pname, ppwd, pinfo };
			// ����query����
				flag = runner.update(sql, params);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
