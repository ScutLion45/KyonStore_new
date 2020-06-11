package com.kyon.pojo;

public class Publisher {
	private String pUid="";
	private String pName="";
	private String pPwd="";
	private String pInfo="";
	public String getpUid() {
		return pUid;
	}
	public void setpUid(String pUid) {
		this.pUid = pUid;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getpPwd() {
		return pPwd;
	}
	public void setpPwd(String pPwd) {
		this.pPwd = pPwd;
	}
	public String getpInfo() {
		return pInfo;
	}
	public void setpInfo(String pInfo) {
		this.pInfo = pInfo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pInfo == null) ? 0 : pInfo.hashCode());
		result = prime * result + ((pName == null) ? 0 : pName.hashCode());
		result = prime * result + ((pPwd == null) ? 0 : pPwd.hashCode());
		result = prime * result + ((pUid == null) ? 0 : pUid.hashCode());
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
		Publisher other = (Publisher) obj;
		if (pInfo == null) {
			if (other.pInfo != null)
				return false;
		} else if (!pInfo.equals(other.pInfo))
			return false;
		if (pName == null) {
			if (other.pName != null)
				return false;
		} else if (!pName.equals(other.pName))
			return false;
		if (pPwd == null) {
			if (other.pPwd != null)
				return false;
		} else if (!pPwd.equals(other.pPwd))
			return false;
		if (pUid == null) {
			if (other.pUid != null)
				return false;
		} else if (!pUid.equals(other.pUid))
			return false;
		return true;
	}
	public Publisher(String pUid, String pName, String pPwd, String pInfo) {
		super();
		this.pUid = pUid;
		this.pName = pName;
		this.pPwd = pPwd;
		this.pInfo = pInfo;
	}
	public Publisher() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Publisher [pUid=" + pUid + ", pName=" + pName + ", pPwd=" + pPwd + ", pInfo=" + pInfo + "]";
	}
	
	
}
