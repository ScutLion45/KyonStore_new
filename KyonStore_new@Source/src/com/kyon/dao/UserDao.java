package com.kyon.dao;

import java.util.*;

import javax.servlet.http.HttpSession;

import com.kyon.pojo.*;

public interface UserDao {
	// new：添加用户推荐
	public List<Goods> loadLatestGoods(int gType, HttpSession hs);
	public List<Goods> searchGoods(int gType, String gPubTime, String gName, HttpSession hs);
	
	public int browseGoods(String uId, String gId);
	public int editProfile(String uId, String uMail, String uPwd, double uBalance);
	public List<Browse> loadBrowse(String uId);
	
	// new
	public int browseGoodsStay(String uId, String gId, int stay_add);
	
	/*
	// new: user_recommend
	public Map<String, Integer> getAll_gId_lb(List<Browse> lb);
	public Map<String, Integer> getAll_gId_lo(List<Order> lo);
	public List<Goods> loadUserRecommendedGoods(String uId);
	*/
	
}
