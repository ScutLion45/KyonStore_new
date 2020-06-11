package com.kyon.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.kyon.pojo.*;

public interface PubDao {
	// new：添加用户推荐
	public List<Goods> searchGoods(String pUid, int gType, String gPubTime, int gState, String gName, HttpSession hs);
	
	public int editGoods(String gId, String gName, String gInfo, int gType, double gPrice, String gImg);
	public int offGoods(String gId);
	public int createGoods(String gId, String gName, String gInfo, int gType, double gPrice, String gImg, String pUid);
	public int editProfile(String pUid, String pPwd, String pInfo);
	
	// new：添加发行方操作日志
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
	 * 日志记录格式：{time} | {operation(args)} | {result} | {IPAddr}
	 * 说明:	- args中如果是字符串，则会用""包含；
	 * 		- 如果operation返回值为int，则可以直接赋予result；
	 * 		- 如果operation返回值为List，则result等于List的长度； 
	 * 		- 如果operation返回值为null，则result等于-1；
	 */
	public void recordOperation(String pUid, String operationStr, int result, String IPAddr, String webRoot);
}
