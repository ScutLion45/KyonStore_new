package com.kyon.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.kyon.pojo.*;

public interface PubDao {
	// new������û��Ƽ�
	public List<Goods> searchGoods(String pUid, int gType, String gPubTime, int gState, String gName, HttpSession hs);
	
	public int editGoods(String gId, String gName, String gInfo, int gType, double gPrice, String gImg);
	public int offGoods(String gId);
	public int createGoods(String gId, String gName, String gInfo, int gType, double gPrice, String gImg, String pUid);
	public int editProfile(String pUid, String pPwd, String pInfo);
	
	// new����ӷ��з�������־
	/*
	 * [/pub-search-goods]:	List<Goods> PubDao.searchGoods(pUid, gType, gPubTime, gState, gName)
	 * [/pub-edit-goods-1]:	int PubDao.editGoods(gId, gName, gInfo, gType, gPrice, gImg)
	 * [/pub-off-goods]:	int PubDao.offGoods(gId)
	 * [/pub-create-goods]:	int PubDao.createGoods(gId, gName, gInfo, gType, gPrice, gImg, pUid)
	 * [/pub-edit-profile]:	int PubDao.editProfile(pUid, pPwd, pInfo)
	 * 
	 * [/update-order]:		int OrderDao.updateOrder(oId, arg=3)
	 * [/pub-load-order]:	List<Order> OrderDao.pubLoadOrder(pUid)
	 * 
	 * ��־��¼��ʽ��{time} | {operation(args)} | {result} | {IPAddr}
	 * ˵��:	- args��������ַ����������""������
	 * 		- ���operation����ֵΪint�������ֱ�Ӹ���result��
	 * 		- ���operation����ֵΪList����result����List�ĳ��ȣ� 
	 * 		- ���operation����ֵΪnull����result����-1��
	 */
	public void recordOperation(String pUid, String operationStr, int result, String IPAddr, String webRoot);
}
