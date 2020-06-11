package com.kyon.dao;

import java.util.*;
import com.kyon.pojo.*;

public interface UserProDao {

	// 构建用户画像 -----------------------------------------------
	// map('arg|gType',pos_set)，arg0是lb，1是lo
	public Map<String, Set<ScoreTuple>> get_gt(List<Browse> lb, List<Order> lo);
	public Map<String, Set<ScoreTuple>> get_pu(List<Browse> lb, List<Order> lo);
	
	// 获取lo的gId-uBought映射
	public Map<String, Integer> lo_get_gId_uBought(List<Order> lo);
	
	// 计算各gType的score（取最大值），按score降序排序
	public List<ScoreTuple> cal_gtScore(Map<String, Set<ScoreTuple>> map_gt, List<Browse> lb, List<Order> lo, Map<String, Integer> lo_gId_map);
	// 计算各pUid的score（取最大值），按score降序排序
	public List<ScoreTuple> cal_puScore(Map<String, Set<ScoreTuple>> map_pu, List<Browse> lb, List<Order> lo, Map<String, Integer> lo_gId_map);
	
	// 写入文件
	public void recordScore(String uId, List<ScoreTuple> score_gt, List<ScoreTuple> score_pu, String webRoot);
	
	// 构建用户画像
	public void buildUserPortrait(String uId, String webRoot);
	// ----------------------------------------------------------
	
	// 加载用户画像：即recordScore的反过程
	// 读取到一个Map<String, List<ScoreTuple>>：
	/*
	 * {
	 * 		'score_gt': List<ScoreTuple> score_gt,
	 * 		'score_pu': List<ScoreTuple> score_pu,
	 * }
	 */
	// 另外创建一个Map<String, Set<String>>，记录gType和pUid的集合：
	/*
	 * {
	 * 		'set_gt': Set<String> set_gt,	// score_gt里的所有gType
	 * 		'set_pu': Set<String> set_pu,	// score_pu里的所有pUid
	 * }
	 */
	 public void loadUserPortrait(String uId, Map<String, List<ScoreTuple>> uPortrait, Map<String, Set<String>> uP_set, String webRoot);
//	 public Map<String, List<ScoreTuple>> getUserPortraitSet(String uId);
	 
	 // 记录用户/发行方 登录/登出时间和IP ----------------------------------------------
	 /*
	  * arg=1，用户；arg=2：发行方；
	  * type=0：登录；type=1：登出；
	  * webRoot：项目根目录，由Servlet传递
	  */
	 public void recordLogin_out(String id, int arg, int type, String IPAddr, String webRoot);
	
}
