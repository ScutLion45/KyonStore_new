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
		// 调用存储过程
			// call user_load_latest_goods(gType)
			
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List<Goods> lg = null;
			
			try {
				// 获取数据库连接对象
				conn = DBCPUtil.getConnection();
				// 创建sql命令（占位符）
					String sql = "call user_load_latest_goods(?)";
				// 创建sql命令对象
					ps = conn.prepareStatement(sql);
				// 占位符赋值
					ps.setInt(1, gType);
				// 执行
					rs = ps.executeQuery();
				// lg初始化
					lg = new ArrayList<Goods>();
				// 遍历执行结果
					while(rs.next()) {
						// 读取数据到Goods对象中
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

						// 将对象存储到lg中
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
		
		// new：添加用户推荐 portrait --------------------------------------------------------------
		// 无用户画像map，直接返回
		if(hs.getAttribute("uPortrait") == null) return lg;
		if(hs.getAttribute("uP_set") == null) return lg;
		
		Map<String, List<ScoreTuple>> uPortrait = (Map<String, List<ScoreTuple>>)hs.getAttribute("uPortrait");
		Map<String, Set<String>> uP_set = (Map<String, Set<String>>)hs.getAttribute("uP_set");

		// 取出score_pu和set_pu
		List<ScoreTuple> score_pu = null;
		Set<String> set_pu = null;
		// 取不到score_pu，直接返回
		if((score_pu = uPortrait.get("score_pu")) == null)
			return lg;
		// 取不到set_pu，直接返回
		if((set_pu = uP_set.get("set_pu")) == null)
			return lg;
		
		try {
			Map<String, List<Goods>> tmp_lg_map = new HashMap<>();	// 临时存放取出的符合条件的goods_item
			// 从lg取出pUid在set_pu里的item
			for(int i=0; i<lg.size(); i++) {
				String pUid = lg.get(i).getPub().getpUid();
				if(set_pu.contains(pUid)) {
					Goods item = lg.remove(i);
					List<Goods> tmp_lg = tmp_lg_map.get(pUid);
					if(tmp_lg == null)
						tmp_lg = new ArrayList<>();
					tmp_lg.add(item);
					tmp_lg_map.put(pUid, tmp_lg);
					
					i--;				// ！！！指针回退
				}
			}
			// 按照score_pu[pUid]的score，对取出的item降序排序
			// 注意：一个pUid可能对应多个goods
			for(int i=score_pu.size()-1; i>=0; i--) {
				// score_pu按score降序，所以反序遍历
				ScoreTuple tuple = score_pu.get(i);
				List<Goods> tmp_lg = tmp_lg_map.get(tuple.getKey());
				if(tmp_lg != null) {
					for(int g=0; g<tmp_lg.size(); g++)
						// 插入lg队首
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
		// 处理参数
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
	
		// 调用存储过程
			// call user_search_goods(gtp,gpt_begin,gpt_end,gn)
		
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List<Goods> lg = null;
			
			try {
				// 获取数据库连接对象
					conn = DBCPUtil.getConnection();
				// 创建sql命令（占位符）
					String sql = "call user_search_goods(?,?,?,?)";
				// 创建sql命令对象
					ps = conn.prepareStatement(sql);
				// 占位符赋值
					ps.setInt(1, gType);
					ps.setString(2, gpt_begin);
					ps.setString(3, gpt_end);
					ps.setString(4, gn);
				// 执行
					rs = ps.executeQuery();
				// lg初始化
					lg = new ArrayList<Goods>();
				// 遍历执行结果
					while(rs.next()) {
						// 读取数据到Goods对象中
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

						// 将对象存储到lg中
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
			
			// new：用户推荐 -----------------------------------------------------
			// 无用户画像map，直接返回
			if(hs.getAttribute("uPortrait") == null) return lg;
			if(hs.getAttribute("uP_set") == null) return lg;
			
			Map<String, List<ScoreTuple>> uPortrait = (Map<String, List<ScoreTuple>>)hs.getAttribute("uPortrait");
			Map<String, Set<String>> uP_set = (Map<String, Set<String>>)hs.getAttribute("uP_set");
			
			try {
				if(gType == 0) { 	// if gType == 0：查所有类型：根据gtype推荐
					// 取出score_gt和set_gt
					List<ScoreTuple> score_gt = null;
					Set<String> set_gt = null;
					// 取不到score_gt，直接返回
					if((score_gt = uPortrait.get("score_gt")) == null)
						return lg;
					// 取不到set_gt，直接返回
					if((set_gt = uP_set.get("set_gt")) == null)
						return lg;
					// 临时存放取出的符合条件的goods_item
					Map<String, List<Goods>> tmp_lg_map = new HashMap<>();	
					// 从lg取出gType在set_gt里的item
					for(int i=0; i<lg.size(); i++) {
						String gTypeStr = ""+lg.get(i).getgType();
						if(set_gt.contains(gTypeStr)) {
							Goods item = lg.remove(i);
							List<Goods> tmp_lg = tmp_lg_map.get(gTypeStr);
							if(tmp_lg == null)
								tmp_lg = new ArrayList<>();
							tmp_lg.add(item);
							tmp_lg_map.put(gTypeStr, tmp_lg);
							
							i--;				// ！！！指针回退
						}
					}
					// 按照score_gt[gType]的score，对取出的item降序排序
					// 注意：一个gType可能对应多个goods
					for(int i=score_gt.size()-1; i>=0; i--) {
						// score_gt按score降序，所以反序遍历
						ScoreTuple tuple = score_gt.get(i);
						List<Goods> tmp_lg = tmp_lg_map.get(tuple.getKey());
						if(tmp_lg != null) {
							for(int g=0; g<tmp_lg.size(); g++)
								// 插入lg队首
								lg.add(0, tmp_lg.get(g));
						}
					}
					
				} else {   			// else：根据pub推荐[即与loadLatestGoods一样处理]
					// 取出score_pu和set_pu
					List<ScoreTuple> score_pu = null;
					Set<String> set_pu = null;
					// 取不到score_pu，直接返回
					if((score_pu = uPortrait.get("score_pu")) == null)
						return lg;
					// 取不到set_pu，直接返回
					if((set_pu = uP_set.get("set_pu")) == null)
						return lg;
					// 临时存放取出的符合条件的goods_item
					Map<String, List<Goods>> tmp_lg_map = new HashMap<>();	
					// 从lg取出pUid在set_pu里的item
					for(int i=0; i<lg.size(); i++) {
						String pUid = lg.get(i).getPub().getpUid();
						if(set_pu.contains(pUid)) {
							Goods item = lg.remove(i);
							List<Goods> tmp_lg = tmp_lg_map.get(pUid);
							if(tmp_lg == null)
								tmp_lg = new ArrayList<>();
							tmp_lg.add(item);
							tmp_lg_map.put(pUid, tmp_lg);
							
							i--;				// ！！！指针回退
						}
					}
					// 按照score_pu[pUid]的score，对取出的item降序排序
					// 注意：一个pUid可能对应多个goods
					for(int i=score_pu.size()-1; i>=0; i--) {
						// score_pu按score降序，所以反序遍历
						ScoreTuple tuple = score_pu.get(i);
						List<Goods> tmp_lg = tmp_lg_map.get(tuple.getKey());
						if(tmp_lg != null) {
							for(int g=0; g<tmp_lg.size(); g++)
								// 插入lg队首
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
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;
		int flag = 0;
		
		String bTime = Utils.localeDateTime();
		System.out.println("call browse_update('"+bTime+"','"+gId+"','"+uId+"')");
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String call = "call browse_update(?,?,?)";
			// 创建params参数对象	
				Object[] callParams = { bTime, gId, uId };
			// 执行存储过程
				runner.execute(call, callParams);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 执行一遍查询
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// 获取数据库连接对象
				conn = DBCPUtil.getConnection();
			// 创建sql命令（占位符）
				String sql = "select bid, btime from browse where goodsid=? and userid=?";
			// 创建sql命令对象
				ps = conn.prepareStatement(sql);
			// 占位符赋值
				ps.setString(1, gId);
				ps.setString(2, uId);
			// 执行
				rs = ps.executeQuery();
			// 遍历执行结果
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
		
		return flag;  // 1成功，0失败

	}

	@Override
	public int editProfile(String uId, String uMail, String uPwd, double uBalance) {
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;
		int flag = 0;
		
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String sql = "update user set umail=?,upwd=?,ubalance=? where uid=?";
			// 创建params参数对象
				Object[] params = { uMail, uPwd, uBalance, uId };
			// 调用query方法
				flag = runner.update(sql, params);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}

	@Override
	public List<Browse> loadBrowse(String uId) {
		// 调用存储过程
			// call user_load_browse(uid)
			System.out.println("call user_load_browse('"+uId+"')");
		
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List<Browse> lb = null;

			try {
				// 获取数据库连接对象
					conn = DBCPUtil.getConnection();
				// 创建sql命令（占位符）
					String sql = "call user_load_browse(?)";
				// 创建sql命令对象
					ps = conn.prepareStatement(sql);
				// 占位符赋值
					ps.setString(1, uId);
				// 执行
					rs = ps.executeQuery();
				// lb初始化 
					lb = new ArrayList<Browse>();
				// 遍历执行结果
					while(rs.next()) {
						// 读取数据到Browse对象中
							Browse b = new Browse();
							b.setbId(rs.getString("bid"));
							b.setbTime(rs.getString("btime"));
							b.setbStay(rs.getInt("bstay"));
						// 读取数据到Goods对象中
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

						// 将对象存储到lg中
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
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;
		int flag = 0;
		
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String sql = "update browse set bstay=bstay+? where userid=? and goodsid=?";
			// 创建params参数对象
				Object[] params = { stay_add, uId, gId };
			// 调用query方法
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
		
		// 获取用户浏览记录和历史订单（已购买的）
		List<Browse> lb = loadBrowse(uId);
		List<Order> lo = od.userLoadOrder(uId, 2);
		
		// 获取lb的所有gId，lb中gId不可能重复，但lo中可以
		Map<String, Integer> map1 = getAll_gId_lb(lb);
		
		// 计算lo中每个gId总的用户购买量（区别于gsell）
		Map<String, Integer> map2 = getAll_gId_lo(lo);
		
		// 计算map中所有gId的score
		// score(gId) = 商品最后浏览时间 + 商品累计停留时间 × (商品总浏览数/商品总成交量) × 用户总购买数量
		Iterator<Entry<String, Integer>> it1 = map1.entrySet().iterator();
		List<GoodsScore> lgs = new ArrayList<>();
		try {
			while(it1.hasNext()) {
				Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>)it1.next();
				String gId = (String)entry.getKey();
				int pos = entry.getValue().intValue();
				
				// bTime转Double: "yyMMdd.HHmm"
				Date date = formatter1.parse(lb.get(pos).getbTime());
				double score = new Double(formatter2.format(date)).doubleValue();
				
				// bstay
				int bstay = lb.get(pos).getbStay();
				
				// 商品转化率
				double gConvert = 0.0;
				if(lb.get(pos).getGoods().getgSell() > 0) {
					gConvert = 1.0*lb.get(pos).getGoods().getgBrowse()/lb.get(pos).getGoods().getgSell();
				}
				
				// 用户购买量
				Integer uBought = map2.get(gId);
				double ub = 0.0;
				if(null == uBought) {
					ub = 0.05;
				} else {
					ub = uBought;
				}
				
				// 计算最终score
				score += bstay * gConvert * ub;
				lgs.add(new GoodsScore(gId, score));

			}
			lgs.sort(Comparator.naturalOrder());	// 按反序排序
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 根据score排序结果，返回商品列表
		List<Goods> lg = new ArrayList<>();
		for(int i=0; i<lgs.size(); i++) {
			String gId = lgs.get(i).getgId();
			int pos = map1.get(gId).intValue();
			Goods g = lb.get(pos).getGoods();
			lg.add(g);
		}
		
		// 返回最多前8个结果
		if(lg.size()<8)
			return lg;
		else
			return lg.subList(0, 8);
	}
*/
}
