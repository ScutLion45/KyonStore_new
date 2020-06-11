package com.kyon.pojo;

public class Order {
	private String oId="";
	private int oState=0;
	private String oTime="";
	private int oNum=0;
	private double goodsPrice=0.00;
	private Goods goods=new Goods();
	private User user=new User();
	
	public String getoId() {
		return oId;
	}
	public void setoId(String oId) {
		this.oId = oId;
	}
	public int getoState() {
		return oState;
	}
	public void setoState(int oState) {
		this.oState = oState;
	}
	public String getoTime() {
		return oTime;
	}
	public void setoTime(String oTime) {
		this.oTime = oTime;
	}
	public int getoNum() {
		return oNum;
	}
	public void setoNum(int oNum) {
		this.oNum = oNum;
	}
	public double getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(double goodsPrice) {
		this.goodsPrice = goodsPrice;
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
		result = prime * result + ((goods == null) ? 0 : goods.hashCode());
		long temp;
		temp = Double.doubleToLongBits(goodsPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((oId == null) ? 0 : oId.hashCode());
		result = prime * result + oNum;
		result = prime * result + oState;
		result = prime * result + ((oTime == null) ? 0 : oTime.hashCode());
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
		Order other = (Order) obj;
		if (goods == null) {
			if (other.goods != null)
				return false;
		} else if (!goods.equals(other.goods))
			return false;
		if (Double.doubleToLongBits(goodsPrice) != Double.doubleToLongBits(other.goodsPrice))
			return false;
		if (oId == null) {
			if (other.oId != null)
				return false;
		} else if (!oId.equals(other.oId))
			return false;
		if (oNum != other.oNum)
			return false;
		if (oState != other.oState)
			return false;
		if (oTime == null) {
			if (other.oTime != null)
				return false;
		} else if (!oTime.equals(other.oTime))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Order(String oId, int oState, String oTime, int oNum, double goodsPrice, Goods goods, User user) {
		super();
		this.oId = oId;
		this.oState = oState;
		this.oTime = oTime;
		this.oNum = oNum;
		this.goodsPrice = goodsPrice;
		this.goods = goods;
		this.user = user;
	}
	@Override
	public String toString() {
		return "Order [oId=" + oId + ", oState=" + oState + ", oTime=" + oTime + ", oNum=" + oNum + ", goodsPrice="
				+ goodsPrice + ", goods=" + goods + ", user=" + user + "]";
	}
	
	
}
