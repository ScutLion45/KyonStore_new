package com.kyon.pojo;

public class Goods {
	private String gId="";
	private String gName="";
	private String gInfo="";
	private int gType=0;
	private String gPubTime="";
	private double gPrice=0.00;
	private int gBrowse=0;
	private int gSell=0;
	private int gState=0;
	private String gImg="";
	private double gVolume=0.00;
	private Publisher pub=new Publisher();
	public String getgId() {
		return gId;
	}
	public void setgId(String gId) {
		this.gId = gId;
	}
	public String getgName() {
		return gName;
	}
	public void setgName(String gName) {
		this.gName = gName;
	}
	public String getgInfo() {
		return gInfo;
	}
	public void setgInfo(String gInfo) {
		this.gInfo = gInfo;
	}
	public int getgType() {
		return gType;
	}
	public void setgType(int gType) {
		this.gType = gType;
	}
	public String getgPubTime() {
		return gPubTime;
	}
	public void setgPubTime(String gPubTime) {
		this.gPubTime = gPubTime;
	}
	public double getgPrice() {
		return gPrice;
	}
	public void setgPrice(double gPrice) {
		this.gPrice = gPrice;
	}
	public int getgBrowse() {
		return gBrowse;
	}
	public void setgBrowse(int gBrowse) {
		this.gBrowse = gBrowse;
	}
	public int getgSell() {
		return gSell;
	}
	public void setgSell(int gSell) {
		this.gSell = gSell;
	}
	public int getgState() {
		return gState;
	}
	public void setgState(int gState) {
		this.gState = gState;
	}
	public String getgImg() {
		return gImg;
	}
	public void setgImg(String gImg) {
		this.gImg = gImg;
	}
	public double getgVolume() {
		return gVolume;
	}
	public void setgVolume(double gVolume) {
		this.gVolume = gVolume;
	}
	public Publisher getPub() {
		return pub;
	}
	public void setPub(Publisher pub) {
		this.pub = pub;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + gBrowse;
		result = prime * result + ((gId == null) ? 0 : gId.hashCode());
		result = prime * result + ((gImg == null) ? 0 : gImg.hashCode());
		result = prime * result + ((gInfo == null) ? 0 : gInfo.hashCode());
		result = prime * result + ((gName == null) ? 0 : gName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(gPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((gPubTime == null) ? 0 : gPubTime.hashCode());
		result = prime * result + gSell;
		result = prime * result + gState;
		result = prime * result + gType;
		temp = Double.doubleToLongBits(gVolume);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((pub == null) ? 0 : pub.hashCode());
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
		Goods other = (Goods) obj;
		if (gBrowse != other.gBrowse)
			return false;
		if (gId == null) {
			if (other.gId != null)
				return false;
		} else if (!gId.equals(other.gId))
			return false;
		if (gImg == null) {
			if (other.gImg != null)
				return false;
		} else if (!gImg.equals(other.gImg))
			return false;
		if (gInfo == null) {
			if (other.gInfo != null)
				return false;
		} else if (!gInfo.equals(other.gInfo))
			return false;
		if (gName == null) {
			if (other.gName != null)
				return false;
		} else if (!gName.equals(other.gName))
			return false;
		if (Double.doubleToLongBits(gPrice) != Double.doubleToLongBits(other.gPrice))
			return false;
		if (gPubTime == null) {
			if (other.gPubTime != null)
				return false;
		} else if (!gPubTime.equals(other.gPubTime))
			return false;
		if (gSell != other.gSell)
			return false;
		if (gState != other.gState)
			return false;
		if (gType != other.gType)
			return false;
		if (Double.doubleToLongBits(gVolume) != Double.doubleToLongBits(other.gVolume))
			return false;
		if (pub == null) {
			if (other.pub != null)
				return false;
		} else if (!pub.equals(other.pub))
			return false;
		return true;
	}
	public Goods(String gId, String gName, String gInfo, int gType, String gPubTime, double gPrice, int gBrowse,
			int gSell, int gState, String gImg, double gVolume, Publisher pub) {
		super();
		this.gId = gId;
		this.gName = gName;
		this.gInfo = gInfo;
		this.gType = gType;
		this.gPubTime = gPubTime;
		this.gPrice = gPrice;
		this.gBrowse = gBrowse;
		this.gSell = gSell;
		this.gState = gState;
		this.gImg = gImg;
		this.gVolume = gVolume;
		this.pub = pub;
	}
	public Goods() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Goods [gId=" + gId + ", gName=" + gName + ", gInfo=" + gInfo + ", gType=" + gType + ", gPubTime="
				+ gPubTime + ", gPrice=" + gPrice + ", gBrowse=" + gBrowse + ", gSell=" + gSell + ", gState=" + gState
				+ ", gImg=" + gImg + ", gVolume=" + gVolume + ", pub=" + pub + "]";
	}
	
	
}
