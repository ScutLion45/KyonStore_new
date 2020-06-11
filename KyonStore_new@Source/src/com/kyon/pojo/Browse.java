package com.kyon.pojo;

public class Browse {
	String bId="";
	String bTime="";
	int bStay = 0;
	Goods goods = new Goods();
	User user = new User();
	public String getbId() {
		return bId;
	}
	public void setbId(String bId) {
		this.bId = bId;
	}
	public String getbTime() {
		return bTime;
	}
	public void setbTime(String bTime) {
		this.bTime = bTime;
	}
	public int getbStay() {
		return bStay;
	}
	public void setbStay(int bStay) {
		this.bStay = bStay;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bId == null) ? 0 : bId.hashCode());
		result = prime * result + bStay;
		result = prime * result + ((bTime == null) ? 0 : bTime.hashCode());
		result = prime * result + ((goods == null) ? 0 : goods.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Browse other = (Browse) obj;
		if (bId == null) {
			if (other.bId != null)
				return false;
		} else if (!bId.equals(other.bId))
			return false;
		if (bStay != other.bStay)
			return false;
		if (bTime == null) {
			if (other.bTime != null)
				return false;
		} else if (!bTime.equals(other.bTime))
			return false;
		if (goods == null) {
			if (other.goods != null)
				return false;
		} else if (!goods.equals(other.goods))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Browse [bId=" + bId + ", bTime=" + bTime + ", bStay=" + bStay + ", goods=" + goods + ", user=" + user
				+ "]";
	}
	public Browse(String bId, String bTime, int bStay, Goods goods, User user) {
		super();
		this.bId = bId;
		this.bTime = bTime;
		this.bStay = bStay;
		this.goods = goods;
		this.user = user;
	}
	public Browse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
