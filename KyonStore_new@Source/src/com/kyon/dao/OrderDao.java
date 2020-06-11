package com.kyon.dao;

import java.util.List;

import com.kyon.pojo.*;

public interface OrderDao {
	// user_order_dao
	public List<Order> userLoadOrder(String uId, int arg); // 1-购物车(ostate=1/4) | 2-历史订单(ostate=2/3)
	public int userCreateOrder(int oNum, String gId, double gPrice, String uId, int arg);
		// 手动生成oId和oTime，根据arg（1-购物车，2-购买）判断需不需要继续update
	public int userEditOrder(String oId, int oNum);
	public int userRemoveOrder(String oId);
	
	// update_order
	public int updateOrder(String oId, int arg); // 2-用户从购物车结算，3-发行方发货
	
	// pub_order_dao
	public List<Order> pubLoadOrder(String pUid); // ostate=2/3，取到的订单可能有ostate=2且商品已下架的
	
}
