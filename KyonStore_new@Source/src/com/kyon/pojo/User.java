package com.kyon.pojo;

public class User {
	private String uId="";
	private String uName="";
	private String uPwd="";
	private String uMail="";
	private double uBalance = 0.0;
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuPwd() {
		return uPwd;
	}
	public void setuPwd(String uPwd) {
		this.uPwd = uPwd;
	}
	public String getuMail() {
		return uMail;
	}
	public void setuMail(String uMail) {
		this.uMail = uMail;
	}
	public double getuBalance() {
		return uBalance;
	}
	public void setuBalance(double uBalance) {
		this.uBalance = uBalance;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(uBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((uId == null) ? 0 : uId.hashCode());
		result = prime * result + ((uMail == null) ? 0 : uMail.hashCode());
		result = prime * result + ((uName == null) ? 0 : uName.hashCode());
		result = prime * result + ((uPwd == null) ? 0 : uPwd.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (Double.doubleToLongBits(uBalance) != Double.doubleToLongBits(other.uBalance))
			return false;
		if (uId == null) {
			if (other.uId != null)
				return false;
		} else if (!uId.equals(other.uId))
			return false;
		if (uMail == null) {
			if (other.uMail != null)
				return false;
		} else if (!uMail.equals(other.uMail))
			return false;
		if (uName == null) {
			if (other.uName != null)
				return false;
		} else if (!uName.equals(other.uName))
			return false;
		if (uPwd == null) {
			if (other.uPwd != null)
				return false;
		} else if (!uPwd.equals(other.uPwd))
			return false;
		return true;
	}
	public User(String uId, String uName, String uPwd, String uMail, double uBalance) {
		super();
		this.uId = uId;
		this.uName = uName;
		this.uPwd = uPwd;
		this.uMail = uMail;
		this.uBalance = uBalance;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "User [uId=" + uId + ", uName=" + uName + ", uPwd=" + uPwd + ", uMail=" + uMail + ", uBalance="
				+ uBalance + "]";
	}
	
	

}
