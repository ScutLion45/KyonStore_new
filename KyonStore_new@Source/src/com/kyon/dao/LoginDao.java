package com.kyon.dao;

import com.kyon.pojo.Publisher;
import com.kyon.pojo.User;

public interface LoginDao {
	public int checkUName(String uname);
	public User checkUPwd(String uname, String upwd);
	public int checkUMail(String umail);
	public int checkPName(String pname);
	public Publisher checkPPwd(String pname, String ppwd);
}
