package com.kyon.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.text.SimpleDateFormat;
import java.util.*;
//import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.QueryRunner;

//import com.kyon.dao.OrderDao;
import com.kyon.dao.UserDao;
import com.kyon.pojo.*;
import com.kyon.tools.DBCPUtil;
import com.kyon.tools.Utils;

public class UserDaoImpl implements UserDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Goods> loadLatestGoods(int gType, HttpSession hs) {
			System.out.println("call user_load_latest_goods("+gType+");");
		// ���ô洢����
			// call user_load_latest_goods(gType)
			
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List<Goods> lg = null;
			
			try {
				// ��ȡ���ݿ����Ӷ���
				conn = DBCPUtil.getConnection();
				// ����sql���ռλ����
					String sql = "call user_load_latest_goods(?)";
				// ����sql�������
					ps = conn.prepareStatement(sql);
				// ռλ����ֵ
					ps.setInt(1, gType);
				// ִ��
					rs = ps.executeQuery();
				// lg��ʼ��
					lg = new ArrayList<Goods>();
				// ����ִ�н��
					while(rs.next()) {
						// ��ȡ���ݵ�Goods������
							Goods g = new Goods();
							g.setgId(rs.getString("gid"));
							g.setgName(rs.getString("gname"));
							g.setgInfo(rs.getString("ginfo"));
							g.setgType(rs.getInt("gtype"));
							g.setgPubTime(rs.getString("gpubtime"));
							g.setgPrice(rs.getDouble("gprice"));
							g.setgBrowse(rs.getInt("gbrowse"));
							g.setgSell(rs.getInt("gsell"));
							g.setgState(rs.getInt("gstate"));
							g.setgImg(rs.getString("gimg"));
							g.setgVolume(rs.getDouble("gvolume"));
							g.getPub().setpUid(rs.getString("puid"));
							g.getPub().setpName(rs.getString("pname"));
//							g.getPub().setpPwd(rs.getString("ppwd"));
							g.getPub().setpInfo(rs.getString("pinfo"));

						// ������洢��lg��
							lg.add(g);
					}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			

			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		// new������û��Ƽ� portrait --------------------------------------------------------------
		// ���û�����map��ֱ�ӷ���
		if(hs.getAttribute("uPortrait") == null) return lg;
		if(hs.getAttribute("uP_set") == null) return lg;
		
		Map<String, List<ScoreTuple>> uPortrait = (Map<String, List<ScoreTuple>>)hs.getAttribute("uPortrait");
		Map<String, Set<String>> uP_set = (Map<String, Set<String>>)hs.getAttribute("uP_set");

		// ȡ��score_pu��set_pu
		List<ScoreTuple> score_pu = null;
		Set<String> set_pu = null;
		// ȡ����score_pu��ֱ�ӷ���
		if((score_pu = uPortrait.get("score_pu")) == null)
			return lg;
		// ȡ����set_pu��ֱ�ӷ���
		if((set_pu = uP_set.get("set_pu")) == null)
			return lg;
		
		try {
			Map<String, List<Goods>> tmp_lg_map = new HashMap<>();	// ��ʱ���ȡ���ķ���������goods_item
			// ��lgȡ��pUid��set_pu���item
			for(int i=0; i<lg.size(); i++) {
				String pUid = lg.get(i).getPub().getpUid();
				if(set_pu.contains(pUid)) {
					Goods item = lg.remove(i);
					List<Goods> tmp_lg = tmp_lg_map.get(pUid);
					if(tmp_lg == null)
						tmp_lg = new ArrayList<>();
					tmp_lg.add(item);
					tmp_lg_map.put(pUid, tmp_lg);
					
					i--;				// ������ָ�����
				}
			}
			// ����score_pu[pUid]��score����ȡ����item��������
			// ע�⣺һ��pUid���ܶ�Ӧ���goods
			for(int i=score_pu.size()-1; i>=0; i--) {
				// score_pu��score�������Է������
				ScoreTuple tuple = score_pu.get(i);
				List<Goods> tmp_lg = tmp_lg_map.get(tuple.getKey());
				if(tmp_lg != null) {
					for(int g=0; g<tmp_lg.size(); g++)
						// ����lg����
						lg.add(0, tmp_lg.get(g));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return lg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Goods> searchGoods(int gType, String gPubTime, String gName, HttpSession hs) {
		// �������
			String gpt_begin = "0000-01-01 00:00:00";
			String gpt_end = "2999-12-31 23:59:59";
			if(!"".equals(gPubTime)) {
				int dates = Utils.getMaxDateOf(gPubTime);
				if(dates>0) {
					gpt_begin = gPubTime+"-01 00:00:00";
					gpt_end = gPubTime+"-"+dates+" 23:59:59";
				}
			}
			
			String gn = "%"+gName+"%";
			
			System.out.println("call user_search_goods("+gType+",'"+gpt_begin+"','"+gpt_end+"','"+gn+"');");
	
		// ���ô洢����
			// call user_search_goods(gtp,gpt_begin,gpt_end,gn)
		
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List<Goods> lg = null;
			
			try {
				// ��ȡ���ݿ����Ӷ���
					conn = DBCPUtil.getConnection();
				// ����sql���ռλ����
					String sql = "call user_search_goods(?,?,?,?)";
				// ����sql�������
					ps = conn.prepareStatement(sql);
				// ռλ����ֵ
					ps.setInt(1, gType);
					ps.setString(2, gpt_begin);
					ps.setString(3, gpt_end);
					ps.setString(4, gn);
				// ִ��
					rs = ps.executeQuery();
				// lg��ʼ��
					lg = new ArrayList<Goods>();
				// ����ִ�н��
					while(rs.next()) {
						// ��ȡ���ݵ�Goods������
							Goods g = new Goods();
							g.setgId(rs.getString("gid"));
							g.setgName(rs.getString("gname"));
							g.setgInfo(rs.getString("ginfo"));
							g.setgType(rs.getInt("gtype"));
							g.setgPubTime(rs.getString("gpubtime"));
							g.setgPrice(rs.getDouble("gprice"));
							g.setgBrowse(rs.getInt("gbrowse"));
							g.setgSell(rs.getInt("gsell"));
							g.setgState(rs.getInt("gstate"));
							g.setgImg(rs.getString("gimg"));
							g.getPub().setpUid(rs.getString("puid"));
							g.getPub().setpName(rs.getString("pname"));
//							g.getPub().setpPwd(rs.getString("ppwd"));
							g.getPub().setpInfo(rs.getString("pinfo"));

						// ������洢��lg��
							lg.add(g);
					}
			} catch(Exception e) {
				e.printStackTrace();
			}
			

			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			// new���û��Ƽ� -----------------------------------------------------
			// ���û�����map��ֱ�ӷ���
			if(hs.getAttribute("uPortrait") == null) return lg;
			if(hs.getAttribute("uP_set") == null) return lg;
			
			Map<String, List<ScoreTuple>> uPortrait = (Map<String, List<ScoreTuple>>)hs.getAttribute("uPortrait");
			Map<String, Set<String>> uP_set = (Map<String, Set<String>>)hs.getAttribute("uP_set");
			
			try {
				if(gType == 0) { 	// if gType == 0�����������ͣ�����gtype�Ƽ�
					// ȡ��score_gt��set_gt
					List<ScoreTuple> score_gt = null;
					Set<String> set_gt = null;
					// ȡ����score_gt��ֱ�ӷ���
					if((score_gt = uPortrait.get("score_gt")) == null)
						return lg;
					// ȡ����set_gt��ֱ�ӷ���
					if((set_gt = uP_set.get("set_gt")) == null)
						return lg;
					// ��ʱ���ȡ���ķ���������goods_item
					Map<String, List<Goods>> tmp_lg_map = new HashMap<>();	
					// ��lgȡ��gType��set_gt���item
					for(int i=0; i<lg.size(); i++) {
						String gTypeStr = ""+lg.get(i).getgType();
						if(set_gt.contains(gTypeStr)) {
							Goods item = lg.remove(i);
							List<Goods> tmp_lg = tmp_lg_map.get(gTypeStr);
							if(tmp_lg == null)
								tmp_lg = new ArrayList<>();
							tmp_lg.add(item);
							tmp_lg_map.put(gTypeStr, tmp_lg);
							
							i--;				// ������ָ�����
						}
					}
					// ����score_gt[gType]��score����ȡ����item��������
					// ע�⣺һ��gType���ܶ�Ӧ���goods
					for(int i=score_gt.size()-1; i>=0; i--) {
						// score_gt��score�������Է������
						ScoreTuple tuple = score_gt.get(i);
						List<Goods> tmp_lg = tmp_lg_map.get(tuple.getKey());
						if(tmp_lg != null) {
							for(int g=0; g<tmp_lg.size(); g++)
								// ����lg����
								lg.add(0, tmp_lg.get(g));
						}
					}
					
				} else {   			// else������pub�Ƽ�[����loadLatestGoodsһ������]
					// ȡ��score_pu��set_pu
					List<ScoreTuple> score_pu = null;
					Set<String> set_pu = null;
					// ȡ����score_pu��ֱ�ӷ���
					if((score_pu = uPortrait.get("score_pu")) == null)
						return lg;
					// ȡ����set_pu��ֱ�ӷ���
					if((set_pu = uP_set.get("set_pu")) == null)
						return lg;
					// ��ʱ���ȡ���ķ���������goods_item
					Map<String, List<Goods>> tmp_lg_map = new HashMap<>();	
					// ��lgȡ��pUid��set_pu���item
					for(int i=0; i<lg.size(); i++) {
						String pUid = lg.get(i).getPub().getpUid();
						if(set_pu.contains(pUid)) {
							Goods item = lg.remove(i);
							List<Goods> tmp_lg = tmp_lg_map.get(pUid);
							if(tmp_lg == null)
								tmp_lg = new ArrayList<>();
							tmp_lg.add(item);
							tmp_lg_map.put(pUid, tmp_lg);
							
							i--;				// ������ָ�����
						}
					}
					// ����score_pu[pUid]��score����ȡ����item��������
					// ע�⣺һ��pUid���ܶ�Ӧ���goods
					for(int i=score_pu.size()-1; i>=0; i--) {
						// score_pu��score�������Է������
						ScoreTuple tuple = score_pu.get(i);
						List<Goods> tmp_lg = tmp_lg_map.get(tuple.getKey());
						if(tmp_lg != null) {
							for(int g=0; g<tmp_lg.size(); g++)
								// ����lg����
								lg.add(0, tmp_lg.get(g));
						}
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		return lg;
	}

	@Override
	public int browseGoods(String uId, String gId) {
		// ����QueryRunner������������
		QueryRunner runner = null;
		int flag = 0;
		
		String bTime = Utils.localeDateTime();
		System.out.println("call browse_update('"+bTime+"','"+gId+"','"+uId+"')");
		try {
			// ����QueryRunner����
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// ����sql���
				String call = "call browse_update(?,?,?)";
			// ����params��������	
				Object[] callParams = { bTime, gId, uId };
			// ִ�д洢����
				runner.execute(call, callParams);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// ִ��һ���ѯ
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// ��ȡ���ݿ����Ӷ���
				conn = DBCPUtil.getConnection();
			// ����sql���ռλ����
				String sql = "select bid, btime from browse where goodsid=? and userid=?";
			// ����sql�������
				ps = conn.prepareStatement(sql);
			// ռλ����ֵ
				ps.setString(1, gId);
				ps.setString(2, uId);
			// ִ��
				rs = ps.executeQuery();
			// ����ִ�н��
				while(rs.next()) {
//					int bid = rs.getInt("bid");
					String bt = rs.getString("btime");
					if(bt.equals(bTime))
						flag = 1;
				}			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag;  // 1�ɹ���0ʧ��

	}

	@Override
	public int editProfile(String uId, String uMail, String uPwd, double uBalance) {
		// ����QueryRunner������������
		QueryRunner runner = null;
		int flag = 0;
		
		try {
			// ����QueryRunner����
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// ����sql���
				String sql = "update user set umail=?,upwd=?,ubalance=? where uid=?";
			// ����params��������
				Object[] params = { uMail, uPwd, uBalance, uId };
			// ����query����
				flag = runner.update(sql, params);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}

	@Override
	public List<Browse> loadBrowse(String uId) {
		// ���ô洢����
			// call user_load_browse(uid)
			System.out.println("call user_load_browse('"+uId+"')");
		
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List<Browse> lb = null;

			try {
				// ��ȡ���ݿ����Ӷ���
					conn = DBCPUtil.getConnection();
				// ����sql���ռλ����
					String sql = "call user_load_browse(?)";
				// ����sql�������
					ps = conn.prepareStatement(sql);
				// ռλ����ֵ
					ps.setString(1, uId);
				// ִ��
					rs = ps.executeQuery();
				// lb��ʼ�� 
					lb = new ArrayList<Browse>();
				// ����ִ�н��
					while(rs.next()) {
						// ��ȡ���ݵ�Browse������
							Browse b = new Browse();
							b.setbId(rs.getString("bid"));
							b.setbTime(rs.getString("btime"));
							b.setbStay(rs.getInt("bstay"));
						// ��ȡ���ݵ�Goods������
							Goods g = new Goods();
							g.setgId(rs.getString("gid"));
							g.setgName(rs.getString("gname"));
							g.setgInfo(rs.getString("ginfo"));
							g.setgType(rs.getInt("gtype"));
							g.setgPubTime(rs.getString("gpubtime"));
							g.setgPrice(rs.getDouble("gprice"));
							g.setgBrowse(rs.getInt("gbrowse"));
							g.setgSell(rs.getInt("gsell"));
							g.setgState(rs.getInt("gstate"));
							g.setgImg(rs.getString("gimg"));
							g.getPub().setpUid(rs.getString("puid"));
							g.getPub().setpName(rs.getString("pname"));
//							g.getPub().setpPwd(rs.getString("ppwd"));
							g.getPub().setpInfo(rs.getString("pinfo"));

						// ������洢��lg��
							b.setGoods(g);
							lb.add(b);
					}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			

			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		return lb;
	}

	
	// new
	@Override
	public int browseGoodsStay(String uId, String gId, int stay_add) {
		// ����QueryRunner������������
		QueryRunner runner = null;
		int flag = 0;
		
		try {
			// ����QueryRunner����
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// ����sql���
				String sql = "update browse set bstay=bstay+? where userid=? and goodsid=?";
			// ����params��������
				Object[] params = { stay_add, uId, gId };
			// ����query����
				flag = runner.update(sql, params);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}

/*
	// new: user_recommend
	@Override
	public Map<String, Integer> getAll_gId_lb(List<Browse> lb) {
		Map<String, Integer> map = new HashMap<>();
		for(int i=0; i<lb.size(); i++) {
			map.put(lb.get(i).getGoods().getgId(), new Integer(i));
		}
		return map;
	}
	
	@Override
	public Map<String, Integer> getAll_gId_lo(List<Order> lo) {
		Map<String, Integer> lo_g = new HashMap<>();
		for(int i=0; i<lo.size(); i++) {
			String gId = lo.get(i).getGoods().getgId();
			Integer osum = lo_g.get(gId);
			if(osum == null) {
				osum = new Integer(0);
			}
			osum += lo.get(i).getoNum();
			lo_g.put(gId, osum);
		}
		return lo_g;
	}
	
	@Override
	public List<Goods> loadUserRecommendedGoods(String uId) {
		OrderDao od = new OrderDaoImpl();
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyMMdd.HHmm");
		
		// ��ȡ�û������¼����ʷ�������ѹ���ģ�
		List<Browse> lb = loadBrowse(uId);
		List<Order> lo = od.userLoadOrder(uId, 2);
		
		// ��ȡlb������gId��lb��gId�������ظ�����lo�п���
		Map<String, Integer> map1 = getAll_gId_lb(lb);
		
		// ����lo��ÿ��gId�ܵ��û���������������gsell��
		Map<String, Integer> map2 = getAll_gId_lo(lo);
		
		// ����map������gId��score
		// score(gId) = ��Ʒ������ʱ�� + ��Ʒ�ۼ�ͣ��ʱ�� �� (��Ʒ�������/��Ʒ�ܳɽ���) �� �û��ܹ�������
		Iterator<Entry<String, Integer>> it1 = map1.entrySet().iterator();
		List<GoodsScore> lgs = new ArrayList<>();
		try {
			while(it1.hasNext()) {
				Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>)it1.next();
				String gId = (String)entry.getKey();
				int pos = entry.getValue().intValue();
				
				// bTimeתDouble: "yyMMdd.HHmm"
				Date date = formatter1.parse(lb.get(pos).getbTime());
				double score = new Double(formatter2.format(date)).doubleValue();
				
				// bstay
				int bstay = lb.get(pos).getbStay();
				
				// ��Ʒת����
				double gConvert = 0.0;
				if(lb.get(pos).getGoods().getgSell() > 0) {
					gConvert = 1.0*lb.get(pos).getGoods().getgBrowse()/lb.get(pos).getGoods().getgSell();
				}
				
				// �û�������
				Integer uBought = map2.get(gId);
				double ub = 0.0;
				if(null == uBought) {
					ub = 0.05;
				} else {
					ub = uBought;
				}
				
				// ��������score
				score += bstay * gConvert * ub;
				lgs.add(new GoodsScore(gId, score));

			}
			lgs.sort(Comparator.naturalOrder());	// ����������
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// ����score��������������Ʒ�б�
		List<Goods> lg = new ArrayList<>();
		for(int i=0; i<lgs.size(); i++) {
			String gId = lgs.get(i).getgId();
			int pos = map1.get(gId).intValue();
			Goods g = lb.get(pos).getGoods();
			lg.add(g);
		}
		
		// �������ǰ8�����
		if(lg.size()<8)
			return lg;
		else
			return lg.subList(0, 8);
	}
*/
}
