package com.kyon.daoImpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.QueryRunner;
//import org.apache.commons.dbutils.handlers.BeanHandler;

import com.kyon.dao.PubDao;
import com.kyon.pojo.Goods;
import com.kyon.pojo.ScoreTuple;
import com.kyon.tools.DBCPUtil;
import com.kyon.tools.Utils;

public class PubDaoImpl implements PubDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Goods> searchGoods(String pUid, int gType, String gPubTime, int gState, String gName, HttpSession hs) {
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
			
			System.out.println("call pub_search_goods('"+pUid+"',"+gType+",'"+gpt_begin+"','"+gpt_end+"',"+gState+",'"+gn+"');");
		
		// 调用存储过程
			// call pub_search_goods(puid,gtp,gpt_begin,gpt_end,gs,gn)
		
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List<Goods> lg = null;
			
			try {
				// 获取数据库连接对象
					conn = DBCPUtil.getConnection();
				// 创建sql命令（占位符）
					String sql = "call pub_search_goods(?,?,?,?,?,?)";
				// 创建sql命令对象
					ps = conn.prepareStatement(sql);
				// 占位符赋值
					ps.setString(1, pUid);
					ps.setInt(2, gType);
					ps.setString(3, gpt_begin);
					ps.setString(4, gpt_end);
					ps.setInt(5, gState);
					ps.setString(6, gn);
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
			
			// new：用户推荐 -----------------------------------------------------
			// 无用户画像map，直接返回
			if(hs.getAttribute("uPortrait") == null) return lg;
			if(hs.getAttribute("uP_set") == null) return lg;
			
			Map<String, List<ScoreTuple>> uPortrait = (Map<String, List<ScoreTuple>>)hs.getAttribute("uPortrait");
			Map<String, Set<String>> uP_set = (Map<String, Set<String>>)hs.getAttribute("uP_set");

			// 取出score_gt和set_gt
			List<ScoreTuple> score_gt = null;
			Set<String> set_gt = null;
			// 取不到score_gt，直接返回
			if((score_gt = uPortrait.get("score_gt")) == null)
				return lg;
			// 取不到set_gt，直接返回
			if((set_gt = uP_set.get("set_gt")) == null)
				return lg;
			
			try {
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
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		return lg;
	}


	@Override
	public int editGoods(String gId, String gName, String gInfo, int gType, double gPrice, String gImg) {
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;
		int flag = 0;
		
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String call = "call goods_edit(?,?,?,?,?,?)";
			// 创建params参数对象	
				Object[] callParams = { gId, gName, gInfo, gType, gPrice,gImg };
			// 执行存储过程
				flag = runner.execute(call, callParams);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return flag;  // 0成功，-1失败
	}


	@Override
	public int offGoods(String gId) {
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;
		
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String call = "call goods_off(?)";
			// 创建params参数对象	
				Object[] callParams = { gId };
			// 执行存储过程
				runner.execute(call, callParams);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 重新查询一遍gState
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int gs = 0;
		try {
			// 获取数据库连接对象
				conn = DBCPUtil.getConnection();
			// 创建sql命令（占位符）
				String sql = "select gstate from goods where gid=?";
			// 创建sql命令对象
				ps = conn.prepareStatement(sql);
			// 占位符赋值
				ps.setString(1, gId);
			// 执行
				rs = ps.executeQuery();
			// 遍历执行结果
				while(rs.next()) {
					gs = rs.getInt("gstate");
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
		
		return gs;  // gState
	}


	@Override
	public int createGoods(String gId, String gName, String gInfo, int gType, double gPrice, String gImg, String pUid) {
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;
		// String gId = Utils.genRandID();
		
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String call = "call goods_create(?,?,?,?,?,?,?,?)";
			// 创建params参数对象
				String gPubTime = Utils.localeDateTime();
				Object[] callParams = { gId, gName, gInfo, gType, gPubTime, gPrice, gImg, pUid };
			// 执行存储过程
				runner.execute(call, callParams);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 查询是否插入成功
		int success = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// 获取数据库连接对象
				conn = DBCPUtil.getConnection();
			// 创建sql命令（占位符）
				String sql = "select * from goods where gid=?";
			// 创建sql命令对象
				ps = conn.prepareStatement(sql);
			// 占位符赋值
				ps.setString(1, gId);
			// 执行
				rs = ps.executeQuery();
			// 遍历执行结果
				while(rs.next()) {
					if(!"".equals(rs.getString("gid"))) {
						success = 1;
					}
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
		
		return success;
	}


	@Override
	public int editProfile(String pUid, String pPwd, String pInfo) {
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;
		int flag = 0;
		
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String sql = "update publisher set ppwd=?,pinfo=? where puid=?";
			// 创建params参数对象
				Object[] params = { pPwd, pInfo, pUid };
			// 调用query方法
				flag = runner.update(sql, params);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}


	@Override
	public void recordOperation(String pUid,
								String operationStr,
								int result,
								String IPAddr,
								String webRoot) {
		// 操作时间
		String timeStr = Utils.localeDateTime();
		
		// 发行方操作日志文件：{webRoot}/data/pub/operation/{pUid}
		String filePath = webRoot+File.separator
							+"data"+File.separator
							+"pub"+File.separator
							+"operation"+File.separator+pUid;
		System.out.println("[PubDaoImpl.recordOperation()] Record into {"+filePath+"}");
		try {
			// 声明文件写入流，true表示append方式，charset='UTF-8'
			BufferedWriter out = 
					new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true),"UTF-8"));
			
			// 日志记录格式：{time} | {operation(args)} | {result} | {IPAddr}
			String content = timeStr+"|"+operationStr+"|"+result+"|"+IPAddr+"\r\n";
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
