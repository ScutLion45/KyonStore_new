package com.kyon.dao;

import java.util.*;
import com.kyon.pojo.*;

public interface UserProDao {

	// �����û����� -----------------------------------------------
	// map('arg|gType',pos_set)��arg0��lb��1��lo
	public Map<String, Set<ScoreTuple>> get_gt(List<Browse> lb, List<Order> lo);
	public Map<String, Set<ScoreTuple>> get_pu(List<Browse> lb, List<Order> lo);
	
	// ��ȡlo��gId-uBoughtӳ��
	public Map<String, Integer> lo_get_gId_uBought(List<Order> lo);
	
	// �����gType��score��ȡ���ֵ������score��������
	public List<ScoreTuple> cal_gtScore(Map<String, Set<ScoreTuple>> map_gt, List<Browse> lb, List<Order> lo, Map<String, Integer> lo_gId_map);
	// �����pUid��score��ȡ���ֵ������score��������
	public List<ScoreTuple> cal_puScore(Map<String, Set<ScoreTuple>> map_pu, List<Browse> lb, List<Order> lo, Map<String, Integer> lo_gId_map);
	
	// д���ļ�
	public void recordScore(String uId, List<ScoreTuple> score_gt, List<ScoreTuple> score_pu, String webRoot);
	
	// �����û�����
	public void buildUserPortrait(String uId, String webRoot);
	// ----------------------------------------------------------
	
	// �����û����񣺼�recordScore�ķ�����
	// ��ȡ��һ��Map<String, List<ScoreTuple>>��
	/*
	 * {
	 * 		'score_gt': List<ScoreTuple> score_gt,
	 * 		'score_pu': List<ScoreTuple> score_pu,
	 * }
	 */
	// ���ⴴ��һ��Map<String, Set<String>>����¼gType��pUid�ļ��ϣ�
	/*
	 * {
	 * 		'set_gt': Set<String> set_gt,	// score_gt�������gType
	 * 		'set_pu': Set<String> set_pu,	// score_pu�������pUid
	 * }
	 */
	 public void loadUserPortrait(String uId, Map<String, List<ScoreTuple>> uPortrait, Map<String, Set<String>> uP_set, String webRoot);
//	 public Map<String, List<ScoreTuple>> getUserPortraitSet(String uId);
	 
	 // ��¼�û�/���з� ��¼/�ǳ�ʱ���IP ----------------------------------------------
	 /*
	  * arg=1���û���arg=2�����з���
	  * type=0����¼��type=1���ǳ���
	  * webRoot����Ŀ��Ŀ¼����Servlet����
	  */
	 public void recordLogin_out(String id, int arg, int type, String IPAddr, String webRoot);
	
}
