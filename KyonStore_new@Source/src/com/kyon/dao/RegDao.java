package com.kyon.dao;

public interface RegDao {
	public int userReg(String umail, String uname, String upwd);
	public int pubReg(String pname, String ppwd, String pinfo);
}
