package com.kyon.dao;

import java.util.List;

import com.kyon.pojo.*;

public interface OrderDao {
	// user_order_dao
	public List<Order> userLoadOrder(String uId, int arg); // 1-���ﳵ(ostate=1/4) | 2-��ʷ����(ostate=2/3)
	public int userCreateOrder(int oNum, String gId, double gPrice, String uId, int arg);
		// �ֶ�����oId��oTime������arg��1-���ﳵ��2-�����ж��費��Ҫ����update
	public int userEditOrder(String oId, int oNum);
	public int userRemoveOrder(String oId);
	
	// update_order
	public int updateOrder(String oId, int arg); // 2-�û��ӹ��ﳵ���㣬3-���з�����
	
	// pub_order_dao
	public List<Order> pubLoadOrder(String pUid); // ostate=2/3��ȡ���Ķ���������ostate=2����Ʒ���¼ܵ�
	
}
