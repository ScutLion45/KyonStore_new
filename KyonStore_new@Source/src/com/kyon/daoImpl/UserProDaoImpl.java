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

	// 构建用户画像 -----------------------------------------------
	@Override
	public Map<String, Set<ScoreTuple>> get_gt(List<Browse> lb, List<Order> lo) {
		// map('arg|gType',pos_list)，arg0是lb，1是lo

		// map('arg|gType',pos_list)，arg0是lb，1是lo
		Map<String, Set<ScoreTuple>> map = new HashMap<>();
		try {
			// 获取lb中所有pUid的pos集合
			for(int i=0; i<lb.size(); i++) {
				String key = "0|"+lb.get(i).getGoods().getgType();
				String gId = lb.get(i).getGoods().getgId();
				Set<ScoreTuple> s_pos = map.get(key);
				if(s_pos == null) s_pos = new HashSet<ScoreTuple>();
				
				// 插入s_pos
				s_pos.add(new ScoreTuple(gId, i));
				map.put(key, s_pos);
			}
			/*
			// 获取lo中所有pUid的pos集合
			for(int i=0; i<lo.size(); i++) {
				String key = "1|"+lo.get(i).getGoods().getPub().getpUid();
				String gId = lb.get(i).getGoods().getgId();
				Set<ScoreTuple> s_pos = map.get(key);
				if(s_pos == null) s_pos = new HashSet<ScoreTuple>();
				
				// 插入s_pos
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
		// map('arg|pUid',pos_list)，arg0是lb，1是lo
		Map<String, Set<ScoreTuple>> map = new HashMap<>();
		try {
			// 获取lb中所有pUid的pos集合
			for(int i=0; i<lb.size(); i++) {
				String key = "0|"+lb.get(i).getGoods().getPub().getpUid();
				String gId = lb.get(i).getGoods().getgId();
				Set<ScoreTuple> s_pos = map.get(key);
				if(s_pos == null) s_pos = new HashSet<ScoreTuple>();
				
				// 插入s_pos
				s_pos.add(new ScoreTuple(gId, i));
				map.put(key, s_pos);
			}
			/*
			// 获取lo中所有pUid的pos集合
			for(int i=0; i<lo.size(); i++) {
				String key = "1|"+lo.get(i).getGoods().getPub().getpUid();
				String gId = lb.get(i).getGoods().getgId();
				Set<ScoreTuple> s_pos = map.get(key);
				if(s_pos == null) s_pos = new HashSet<ScoreTuple>();
				
				// 插入s_pos
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
		// 获取lo的gId-uBought映射
		Map<String, Integer> lo_gId_map = new HashMap<>();
		for(int i=0; i<lo.size(); i++) {
			String gId = lo.get(i).getGoods().getgId();
			Integer oSum = lo_gId_map.get(gId);
			if(oSum == null) {
				oSum = new Integer(0);
			}
			oSum += lo.get(i).getoNum();	// 累加用户购买量uBought
			lo_gId_map.put(gId, oSum);
		}
		return lo_gId_map;
	}
	
	@Override
	public List<ScoreTuple> cal_gtScore(Map<String, Set<ScoreTuple>> map_gt, List<Browse> lb, List<Order> lo, Map<String, Integer> lo_gId_map) {
		// 计算各gType的score（取最大值），按score降序排序
		Iterator<Entry<String, Set<ScoreTuple>>> it = map_gt.entrySet().iterator();
		List<ScoreTuple> score_gt = new ArrayList<>();
		try {
			// 遍历map_gt的gType
			while(it.hasNext()) {
				Map.Entry<String, Set<ScoreTuple>> entry = it.next();
				String key = entry.getKey();
				// 获取arg和gType；arg=0是lb，1是lo（continue）
				String[] args = key.split("\\|");
				String arg = args[0];
				String gType = args[1];
				System.out.println("in cal_gtScore(), key: "+key+";;; gType: "+gType);
				if("1".equals(arg)) {	// lo的，continue
					continue;
				}
				Set<ScoreTuple> s_pos = entry.getValue();
				// 遍历s_pos，计算score并取最大值
				double max_score = 0.0;
				Iterator<ScoreTuple> it_s = s_pos.iterator();
				while(it_s.hasNext()) {
					// 计算score
					double score = 0.0;
					// 获取gId, pos_lb
					ScoreTuple sc = it_s.next();
					String gId = sc.getKey();
					int pos_lb = (int)(sc.getScore());

					// bTime转Double: "yyMMdd.HHmm"
					Date date = formatter1.parse(lb.get(pos_lb).getbTime());
					score = new Double(formatter2.format(date)).doubleValue();
					
					// bstay
					int bstay = lb.get(pos_lb).getbStay();
					
					// 商品转化率
					double gConvert = 0.0;
					if(lb.get(pos_lb).getGoods().getgSell() > 0) {
						gConvert = 1.0*lb.get(pos_lb).getGoods().getgBrowse()/lb.get(pos_lb).getGoods().getgSell();
					}

					// 获取gId在lo中的总用户购买量
					double ub = 0.05;
					if(lo_gId_map.containsKey(gId)) {
						ub = lo_gId_map.get(gId);
					}
					
					// 计算最终score = w(最近浏览时间) + w(累计浏览时间) * 商品转化率 * w(用户购买量)
					score += bstay * gConvert * ub;
					
					// 更新max_score
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
		// 计算各pUid的score（取最大值），按score降序排序
		Iterator<Entry<String, Set<ScoreTuple>>> it = map_pu.entrySet().iterator();
		List<ScoreTuple> score_pu = new ArrayList<>();
		try {
			// 遍历map_pu的pUid
			while(it.hasNext()) {
				Map.Entry<String, Set<ScoreTuple>> entry = it.next();
				String key = entry.getKey();
				// 获取arg和pUid；arg=0是lb，1是lo（continue）
				String[] args = key.split("\\|");
				String arg = args[0];
				String pUid = args[1];
				System.out.println("in cal_puScore(), key: "+key+";;; pUid: "+pUid);
				if("1".equals(arg)) {	// lo的pUid，continue
					continue;
				}
				Set<ScoreTuple> s_pos = entry.getValue();
				// 遍历s_pos，计算score并取最大值
				double max_score = 0.0;
				Iterator<ScoreTuple> it_s = s_pos.iterator();
				while(it_s.hasNext()) {
					// 计算score
					double score = 0.0;
					// 获取gId, pos_lb
					ScoreTuple sc = it_s.next();
					String gId = sc.getKey();
					int pos_lb = (int)(sc.getScore());

					// bTime转Double: "yyMMdd.HHmm"
					Date date = formatter1.parse(lb.get(pos_lb).getbTime());
					score = new Double(formatter2.format(date)).doubleValue();
					
					// bstay
					int bstay = lb.get(pos_lb).getbStay();
					
					// 商品转化率
					double gConvert = 0.0;
					if(lb.get(pos_lb).getGoods().getgSell() > 0) {
						gConvert = 1.0*lb.get(pos_lb).getGoods().getgBrowse()/lb.get(pos_lb).getGoods().getgSell();
					}

					// 获取gId在lo中的总用户购买量
					double ub = 0.05;
					if(lo_gId_map.containsKey(gId)) {
						ub = lo_gId_map.get(gId);
					}
					
					// 计算最终score = w(最近浏览时间) + w(累计浏览时间) * 商品转化率 * w(用户购买量)
					score += bstay * gConvert * ub;
					
					// 更新max_score
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
		// 写入文件
		try {
			// 用户画像文件路径：{webRoot}/data/user/portrait/{uId}
			String filePath = webRoot+File.separator
								+"data"+File.separator
								+"user"+File.separator
								+"portrait"+File.separator+uId;

			System.out.println("[UserProDaoImpl.recordScore()] filePath={"+filePath+"}");
			
			// 声明文件写入流，true表示append方式，charset='UTF-8'
			BufferedWriter out = 
					new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath),"UTF-8"));
//					new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true),"UTF-8"));
			// 先写score_gt
			for(int i=0; i<score_gt.size(); i++) {
				// g|{gType}|{score}
				String content = score_gt.get(i).getKey()+"|"+score_gt.get(i).getScore()+"\r\n";
				out.write(content);
			}
			// 再写score_pu
			for(int i=0; i<score_pu.size(); i++) {
				// p|{pUid}|{score}
				String content = score_pu.get(i).getKey()+"|"+score_pu.get(i).getScore()+"\r\n";
				out.write(content);
			}
			// 关闭流
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	// 构建用户画像
	@Override
	public void buildUserPortrait(String uId, String webRoot) {
		// 获取用户浏览记录和历史订单（已购买的）
		List<Browse> lb = new UserDaoImpl().loadBrowse(uId);
		List<Order> lo = new OrderDaoImpl().userLoadOrder(uId, 2);
		
		// 构建gType和pos集合的映射
		Map<String, Set<ScoreTuple>> map_gt = get_gt(lb, lo);
		// 构建pUid和pos集合的映射
		Map<String, Set<ScoreTuple>> map_pu = get_pu(lb, lo);
		
		// 获取lo的gId-uBought映射
		Map<String, Integer> lo_gId_map = lo_get_gId_uBought(lo);
		
		// 计算各gType的score（取最大值），按score降序排序
		List<ScoreTuple> score_gt = cal_gtScore(map_gt, lb, lo, lo_gId_map);

		// 计算各pUid的score（取最大值），按score降序排序
		List<ScoreTuple> score_pu = cal_puScore(map_pu, lb, lo, lo_gId_map);
		
		// 写入文件
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
			Set<String> set_gt = new HashSet<>();	// score_gt里的所有gType
			Set<String> set_pu = new HashSet<>();	// score_pu里的所有pUid

			// 读取用户画像文件：{webRoot}/data/user/portrait/{uId}
			String filePath = webRoot+File.separator
								+"data"+File.separator
								+"user"+File.separator
								+"portrait"+File.separator+uId;

			System.out.println("[UserProDaoImpl.loadUserPortrait()] filePath={"+filePath+"}");
			if(Utils.filePathExists(filePath) == false) {
				System.out.println("!!! 用户画像文件不存在");
				return;
			}
			
			BufferedReader in = 
					new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"UTF-8"));
			String line = null;
			while((line = in.readLine()) != null) {
				// 以'|'分割
				String[] cus = line.split("\\|");
//				String arg = cus[0];	// g表示score_gt；p表示score_pu
				
				String key = cus[1];	// gType或pUid
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

			// 关闭流
			in.close();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void recordLogin_out(String id, int arg, int type, String IPAddr, String webRoot) {
		// 操作时间
		String timeStr = Utils.localeDateTime();
		
		// 登录登出日志文件：{webRoot}/data/{user|pub}/log/{uId|pUid}
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
			// 声明文件写入流，true表示append方式，charset='UTF-8'
			BufferedWriter out = 
					new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true),"UTF-8"));
			
			// 日志记录格式：{type} | {time} | {IPAddr}
			String content = ""+type+"|"+timeStr+"|"+IPAddr+"\r\n";
			out.write(content);
			
			// 关闭流
			out.close();
			return;			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
	}

}
