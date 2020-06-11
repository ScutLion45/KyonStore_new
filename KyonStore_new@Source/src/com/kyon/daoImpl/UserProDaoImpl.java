package com.kyon.daoImpl;

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Map.Entry;

import com.kyon.dao.*;
import com.kyon.pojo.*;
import com.kyon.tools.Utils;


public class UserProDaoImpl implements UserProDao {
	private SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat formatter2 = new SimpleDateFormat("yyMMdd.HHmm");

	// �����û����� -----------------------------------------------
	@Override
	public Map<String, Set<ScoreTuple>> get_gt(List<Browse> lb, List<Order> lo) {
		// map('arg|gType',pos_list)��arg0��lb��1��lo

		// map('arg|gType',pos_list)��arg0��lb��1��lo
		Map<String, Set<ScoreTuple>> map = new HashMap<>();
		try {
			// ��ȡlb������pUid��pos����
			for(int i=0; i<lb.size(); i++) {
				String key = "0|"+lb.get(i).getGoods().getgType();
				String gId = lb.get(i).getGoods().getgId();
				Set<ScoreTuple> s_pos = map.get(key);
				if(s_pos == null) s_pos = new HashSet<ScoreTuple>();
				
				// ����s_pos
				s_pos.add(new ScoreTuple(gId, i));
				map.put(key, s_pos);
			}
			/*
			// ��ȡlo������pUid��pos����
			for(int i=0; i<lo.size(); i++) {
				String key = "1|"+lo.get(i).getGoods().getPub().getpUid();
				String gId = lb.get(i).getGoods().getgId();
				Set<ScoreTuple> s_pos = map.get(key);
				if(s_pos == null) s_pos = new HashSet<ScoreTuple>();
				
				// ����s_pos
				s_pos.add(new ScoreTuple(gId, i));
			}
			*/
			
			return map;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Map<String, Set<ScoreTuple>> get_pu(List<Browse> lb, List<Order> lo) {
		// map('arg|pUid',pos_list)��arg0��lb��1��lo
		Map<String, Set<ScoreTuple>> map = new HashMap<>();
		try {
			// ��ȡlb������pUid��pos����
			for(int i=0; i<lb.size(); i++) {
				String key = "0|"+lb.get(i).getGoods().getPub().getpUid();
				String gId = lb.get(i).getGoods().getgId();
				Set<ScoreTuple> s_pos = map.get(key);
				if(s_pos == null) s_pos = new HashSet<ScoreTuple>();
				
				// ����s_pos
				s_pos.add(new ScoreTuple(gId, i));
				map.put(key, s_pos);
			}
			/*
			// ��ȡlo������pUid��pos����
			for(int i=0; i<lo.size(); i++) {
				String key = "1|"+lo.get(i).getGoods().getPub().getpUid();
				String gId = lb.get(i).getGoods().getgId();
				Set<ScoreTuple> s_pos = map.get(key);
				if(s_pos == null) s_pos = new HashSet<ScoreTuple>();
				
				// ����s_pos
				s_pos.add(new ScoreTuple(gId, i));
			}
			*/
			
			return map;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Map<String, Integer> lo_get_gId_uBought(List<Order> lo) {
		// ��ȡlo��gId-uBoughtӳ��
		Map<String, Integer> lo_gId_map = new HashMap<>();
		for(int i=0; i<lo.size(); i++) {
			String gId = lo.get(i).getGoods().getgId();
			Integer oSum = lo_gId_map.get(gId);
			if(oSum == null) {
				oSum = new Integer(0);
			}
			oSum += lo.get(i).getoNum();	// �ۼ��û�������uBought
			lo_gId_map.put(gId, oSum);
		}
		return lo_gId_map;
	}
	
	@Override
	public List<ScoreTuple> cal_gtScore(Map<String, Set<ScoreTuple>> map_gt, List<Browse> lb, List<Order> lo, Map<String, Integer> lo_gId_map) {
		// �����gType��score��ȡ���ֵ������score��������
		Iterator<Entry<String, Set<ScoreTuple>>> it = map_gt.entrySet().iterator();
		List<ScoreTuple> score_gt = new ArrayList<>();
		try {
			// ����map_gt��gType
			while(it.hasNext()) {
				Map.Entry<String, Set<ScoreTuple>> entry = it.next();
				String key = entry.getKey();
				// ��ȡarg��gType��arg=0��lb��1��lo��continue��
				String[] args = key.split("\\|");
				String arg = args[0];
				String gType = args[1];
				System.out.println("in cal_gtScore(), key: "+key+";;; gType: "+gType);
				if("1".equals(arg)) {	// lo�ģ�continue
					continue;
				}
				Set<ScoreTuple> s_pos = entry.getValue();
				// ����s_pos������score��ȡ���ֵ
				double max_score = 0.0;
				Iterator<ScoreTuple> it_s = s_pos.iterator();
				while(it_s.hasNext()) {
					// ����score
					double score = 0.0;
					// ��ȡgId, pos_lb
					ScoreTuple sc = it_s.next();
					String gId = sc.getKey();
					int pos_lb = (int)(sc.getScore());

					// bTimeתDouble: "yyMMdd.HHmm"
					Date date = formatter1.parse(lb.get(pos_lb).getbTime());
					score = new Double(formatter2.format(date)).doubleValue();
					
					// bstay
					int bstay = lb.get(pos_lb).getbStay();
					
					// ��Ʒת����
					double gConvert = 0.0;
					if(lb.get(pos_lb).getGoods().getgSell() > 0) {
						gConvert = 1.0*lb.get(pos_lb).getGoods().getgBrowse()/lb.get(pos_lb).getGoods().getgSell();
					}

					// ��ȡgId��lo�е����û�������
					double ub = 0.05;
					if(lo_gId_map.containsKey(gId)) {
						ub = lo_gId_map.get(gId);
					}
					
					// ��������score = w(������ʱ��) + w(�ۼ����ʱ��) * ��Ʒת���� * w(�û�������)
					score += bstay * gConvert * ub;
					
					// ����max_score
					max_score = score > max_score ? score : max_score;
				}
				score_gt.add(new ScoreTuple("g|"+gType, max_score));
			}
			return score_gt;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ScoreTuple> cal_puScore(Map<String, Set<ScoreTuple>> map_pu, List<Browse> lb, List<Order> lo, Map<String, Integer> lo_gId_map) {
		// �����pUid��score��ȡ���ֵ������score��������
		Iterator<Entry<String, Set<ScoreTuple>>> it = map_pu.entrySet().iterator();
		List<ScoreTuple> score_pu = new ArrayList<>();
		try {
			// ����map_pu��pUid
			while(it.hasNext()) {
				Map.Entry<String, Set<ScoreTuple>> entry = it.next();
				String key = entry.getKey();
				// ��ȡarg��pUid��arg=0��lb��1��lo��continue��
				String[] args = key.split("\\|");
				String arg = args[0];
				String pUid = args[1];
				System.out.println("in cal_puScore(), key: "+key+";;; pUid: "+pUid);
				if("1".equals(arg)) {	// lo��pUid��continue
					continue;
				}
				Set<ScoreTuple> s_pos = entry.getValue();
				// ����s_pos������score��ȡ���ֵ
				double max_score = 0.0;
				Iterator<ScoreTuple> it_s = s_pos.iterator();
				while(it_s.hasNext()) {
					// ����score
					double score = 0.0;
					// ��ȡgId, pos_lb
					ScoreTuple sc = it_s.next();
					String gId = sc.getKey();
					int pos_lb = (int)(sc.getScore());

					// bTimeתDouble: "yyMMdd.HHmm"
					Date date = formatter1.parse(lb.get(pos_lb).getbTime());
					score = new Double(formatter2.format(date)).doubleValue();
					
					// bstay
					int bstay = lb.get(pos_lb).getbStay();
					
					// ��Ʒת����
					double gConvert = 0.0;
					if(lb.get(pos_lb).getGoods().getgSell() > 0) {
						gConvert = 1.0*lb.get(pos_lb).getGoods().getgBrowse()/lb.get(pos_lb).getGoods().getgSell();
					}

					// ��ȡgId��lo�е����û�������
					double ub = 0.05;
					if(lo_gId_map.containsKey(gId)) {
						ub = lo_gId_map.get(gId);
					}
					
					// ��������score = w(������ʱ��) + w(�ۼ����ʱ��) * ��Ʒת���� * w(�û�������)
					score += bstay * gConvert * ub;
					
					// ����max_score
					max_score = score > max_score ? score : max_score;
				}
				score_pu.add(new ScoreTuple("p|"+pUid, max_score));
			}
			return score_pu;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void recordScore(String uId,
							List<ScoreTuple> score_gt,
							List<ScoreTuple> score_pu,
							String webRoot) {
		// д���ļ�
		try {
			// �û������ļ�·����{webRoot}/data/user/portrait/{uId}
			String filePath = webRoot+File.separator
								+"data"+File.separator
								+"user"+File.separator
								+"portrait"+File.separator+uId;

			System.out.println("[UserProDaoImpl.recordScore()] filePath={"+filePath+"}");
			
			// �����ļ�д������true��ʾappend��ʽ��charset='UTF-8'
			BufferedWriter out = 
					new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath),"UTF-8"));
//					new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true),"UTF-8"));
			// ��дscore_gt
			for(int i=0; i<score_gt.size(); i++) {
				// g|{gType}|{score}
				String content = score_gt.get(i).getKey()+"|"+score_gt.get(i).getScore()+"\r\n";
				out.write(content);
			}
			// ��дscore_pu
			for(int i=0; i<score_pu.size(); i++) {
				// p|{pUid}|{score}
				String content = score_pu.get(i).getKey()+"|"+score_pu.get(i).getScore()+"\r\n";
				out.write(content);
			}
			// �ر���
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	// �����û�����
	@Override
	public void buildUserPortrait(String uId, String webRoot) {
		// ��ȡ�û������¼����ʷ�������ѹ���ģ�
		List<Browse> lb = new UserDaoImpl().loadBrowse(uId);
		List<Order> lo = new OrderDaoImpl().userLoadOrder(uId, 2);
		
		// ����gType��pos���ϵ�ӳ��
		Map<String, Set<ScoreTuple>> map_gt = get_gt(lb, lo);
		// ����pUid��pos���ϵ�ӳ��
		Map<String, Set<ScoreTuple>> map_pu = get_pu(lb, lo);
		
		// ��ȡlo��gId-uBoughtӳ��
		Map<String, Integer> lo_gId_map = lo_get_gId_uBought(lo);
		
		// �����gType��score��ȡ���ֵ������score��������
		List<ScoreTuple> score_gt = cal_gtScore(map_gt, lb, lo, lo_gId_map);

		// �����pUid��score��ȡ���ֵ������score��������
		List<ScoreTuple> score_pu = cal_puScore(map_pu, lb, lo, lo_gId_map);
		
		// д���ļ�
		recordScore(uId, score_gt, score_pu, webRoot);
		
	}
	// ----------------------------------------------------------
	@Override
	 public void loadUserPortrait(String uId,
			 					  Map<String, List<ScoreTuple>> uPortrait,
			 					  Map<String, Set<String>> uP_set, String webRoot) {
		if(uPortrait == null || uP_set == null)
			return;
		
		try {
//			Map<String, List<ScoreTuple>> uProtrait = new HashMap<>();
			List<ScoreTuple> score_gt = new ArrayList<>();
			List<ScoreTuple> score_pu = new ArrayList<>();

//			Map<String, Set<String>> uP_set = new HashMap<>();
			Set<String> set_gt = new HashSet<>();	// score_gt�������gType
			Set<String> set_pu = new HashSet<>();	// score_pu�������pUid

			// ��ȡ�û������ļ���{webRoot}/data/user/portrait/{uId}
			String filePath = webRoot+File.separator
								+"data"+File.separator
								+"user"+File.separator
								+"portrait"+File.separator+uId;

			System.out.println("[UserProDaoImpl.loadUserPortrait()] filePath={"+filePath+"}");
			if(Utils.filePathExists(filePath) == false) {
				System.out.println("!!! �û������ļ�������");
				return;
			}
			
			BufferedReader in = 
					new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"UTF-8"));
			String line = null;
			while((line = in.readLine()) != null) {
				// ��'|'�ָ�
				String[] cus = line.split("\\|");
//				String arg = cus[0];	// g��ʾscore_gt��p��ʾscore_pu
				
				String key = cus[1];	// gType��pUid
				String score = cus[2];
				
				if("g".equals(cus[0])) {
					score_gt.add(new ScoreTuple(key, Double.parseDouble(score)));
					set_gt.add(key);
				} else if("p".equals(cus[0])) {
					score_pu.add(new ScoreTuple(key, Double.parseDouble(score)));
					set_pu.add(key);
				}
				
			}
			
			uPortrait.put("score_gt", score_gt);
			uPortrait.put("score_pu", score_pu);
			
			uP_set.put("set_gt", set_gt);
			uP_set.put("set_pu", set_pu);

			// �ر���
			in.close();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void recordLogin_out(String id, int arg, int type, String IPAddr, String webRoot) {
		// ����ʱ��
		String timeStr = Utils.localeDateTime();
		
		// ��¼�ǳ���־�ļ���{webRoot}/data/{user|pub}/log/{uId|pUid}
		String filePath = null;	
		if(arg == 1) {
			filePath = webRoot+File.separator
						+"data"+File.separator
						+"user"+File.separator
						+"log"+File.separator+id;
		} else if(arg == 2) {
			filePath = webRoot+File.separator
						+"data"+File.separator
						+"pub"+File.separator
						+"log"+File.separator+id;
		}
		
		System.out.println("[UserProDaoImpl.recordLogin_out()] Record into {"+filePath+"}");
		System.out.println(">>> "
							+(arg==1?"[ uid= ":"[puid= ") + id + "] "
							+(type==0?"login.":"logout."));
		
		try {
			// �����ļ�д������true��ʾappend��ʽ��charset='UTF-8'
			BufferedWriter out = 
					new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true),"UTF-8"));
			
			// ��־��¼��ʽ��{type} | {time} | {IPAddr}
			String content = ""+type+"|"+timeStr+"|"+IPAddr+"\r\n";
			out.write(content);
			
			// �ر���
			out.close();
			return;			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
	}

}
