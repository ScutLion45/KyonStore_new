package com.kyon.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import javax.mail.MessagingException;
//import javax.mail.internet.AddressException;

import org.apache.commons.dbutils.QueryRunner;

import com.kyon.dao.OrderDao;
import com.kyon.pojo.*;
import com.kyon.tools.*;

public class OrderDaoImpl implements OrderDao {

	// ---------- user_order_dao ----------------------------------------------------
	@Override
	public List<Order> userLoadOrder(String uId, int arg) { // 1-购物车 | 2-历史订单
		// 调用存储过程
		// call user_load_browse(uid)
		System.out.println("call user_load_order('"+uId+"',"+arg+")");
	
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Order> lo = null;
		
		try {
			// 获取数据库连接对象
				conn = DBCPUtil.getConnection();
			// 创建sql命令（占位符）
				String sql = "call user_load_order(?,?)";
			// 创建sql命令对象
				ps = conn.prepareStatement(sql);
			// 占位符赋值
				ps.setString(1, uId);
				ps.setInt(2, arg);
			// 执行
				rs = ps.executeQuery();
			// lo初始化
				lo = new ArrayList<Order>();
			// 遍历执行结果
				while(rs.next()) {
					// 读取数据到Order对象中
						Order o = new Order();
						o.setoId(rs.getString("oid"));
						o.setoState(rs.getInt("ostate"));
						o.setoTime(rs.getString("otime"));
						o.setoNum(rs.getInt("onum"));
						o.setGoodsPrice(rs.getDouble("goodsprice"));
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
//						g.setgVolume(rs.getDouble("gvolume"));
						g.getPub().setpUid(rs.getString("puid"));
						g.getPub().setpName(rs.getString("pname"));
//						g.getPub().setpPwd(rs.getString("ppwd"));
						g.getPub().setpInfo(rs.getString("pinfo"));
					// 读取数据到User对象中
						// User u = new User();
						// u.setuId(rs.getString("uid"));
						// u.setuName(rs.getString("uname"));
						// u.setuBalance(rs.getDouble("ubalance"));
					
					// 将对象存储到lo中
						o.setGoods(g);
						lo.add(o);
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
		
		return lo;
	}

	@Override
	public int userCreateOrder(int oNum, String gId, double gPrice, String uId, int arg) {
		// 根据arg（1-购物车，2-购买）判断需不需要继续update
		
		 // 手动生成oId和oTime
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;
		int flag1 = 0;  // 创建order是否成功
		// int flag2 = 0;  // update是否成功
		int flag = 0;   // 总的操作结果
		
		String oId = Utils.genRandID();
		String oTime = Utils.localeDateTime();
		System.out.println("call order_create('"+oId+"','"+oTime+"',"+oNum+",'"+gId+"',"+gPrice+",'"+uId+"');");
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String call = "call order_create(?,?,?,?,?,?)";
			// 创建params参数对象
				Object[] callParams = { oId, oTime, oNum, gId, gPrice, uId };
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
				String sql = "select * from `order` where oid=?";
			// 创建sql命令对象
				ps = conn.prepareStatement(sql);
			// 占位符赋值
				ps.setString(1, oId);
			// 执行
				rs = ps.executeQuery();
			// 遍历执行结果
				while(rs.next()) {
					String ooid = rs.getString("oid");
					if(ooid.equals(oId)) {
						flag1 = 1;      // 创建order成功
						if(arg==1) {
							flag = 1;   // 不需要update
						}
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
		
		if(arg==1) {
			return flag; // =1成功
		}
		
	// --------------------------- update --------------------------------------------	
		
		if(arg==2 && flag1==1) {
			System.out.println("call order_update('"+oId+"','"+oTime+"');");
			try {
				// 创建QueryRunner对象
					runner = new QueryRunner(DBCPUtil.getDataSource());
				// 创建sql语句
					String call2 = "call order_update(?,?)";
				// 创建params参数对象
					Object[] callParams2 = { oId, oTime };
				// 执行存储过程
					runner.execute(call2, callParams2);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			// 执行一遍查询
			Connection conn2 = null;
			PreparedStatement ps2 = null;
			ResultSet rs2 = null;
			try {
				// 获取数据库连接对象
					conn2 = DBCPUtil.getConnection();
				// 创建sql命令（占位符）
					String sql2 = "select ostate from `order` where oid=?";
				// 创建sql命令对象
					ps2 = conn2.prepareStatement(sql2);
				// 占位符赋值
					ps2.setString(1, oId);
				// 执行
					rs2 = ps2.executeQuery();
				// 遍历执行结果
					while(rs2.next()) {
						int os = rs2.getInt("ostate");
						if( os==2 || os==3 ) {
							flag = 1;      // update成功
						}
					}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
				rs2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				ps2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return flag; // =1成功
	}

	@Override
	public int userEditOrder(String oId, int oNum) {
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;

		System.out.println("call order_edit('"+oId+"',"+oNum+");");
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String call = "call order_edit(?,?)";
			// 创建params参数对象
				Object[] callParams = { oId, oNum };
			// 执行存储过程
				runner.execute(call, callParams);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 执行一遍查询
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int flag = 0;
		try {
			// 获取数据库连接对象
				conn = DBCPUtil.getConnection();
			// 创建sql命令（占位符）
				String sql = "select onum from `order` where oid=?";
			// 创建sql命令对象
				ps = conn.prepareStatement(sql);
			// 占位符赋值
				ps.setString(1, oId);
			// 执行
				rs = ps.executeQuery();
			// 遍历执行结果
				while(rs.next()) {
					int on = rs.getInt("onum");
					if(on==oNum)
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
		
		return flag; // =1成功
	}

	@Override
	public int userRemoveOrder(String oId) {
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;

		System.out.println("call order_remove('"+oId+"');");
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String call = "call order_remove(?)";
			// 创建params参数对象
				Object[] callParams = { oId };
			// 执行存储过程
				runner.execute(call, callParams);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 执行一遍查询
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int flag = 1;
		try {
			// 获取数据库连接对象
				conn = DBCPUtil.getConnection();
			// 创建sql命令（占位符）
				String sql = "select * from `order` where oid=?";
			// 创建sql命令对象
				ps = conn.prepareStatement(sql);
			// 占位符赋值
				ps.setString(1, oId);
			// 执行
				rs = ps.executeQuery();
			// 遍历执行结果
				while(rs.next()) {
					// 如果有结果说明删除不成功
					flag = 0;
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
		
		return flag; // =1成功
	}

	// ----------- update_order ----------------------------------------------------
	@Override
	public int updateOrder(String oId, int arg) {  // 2-用户从购物车结算，3-发行方发货
		
		QueryRunner runner = null;
		int flag = 0;
		String oTime = Utils.localeDateTime();

		System.out.println("call order_update('"+oId+"','"+oTime+"'); [arg="+arg+"]");
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String call2 = "call order_update(?,?)";
			// 创建params参数对象
				Object[] callParams2 = { oId, oTime };
			// 执行存储过程
				runner.execute(call2, callParams2);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 执行一遍查询
		Connection conn2 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		try {
			// 获取数据库连接对象
				conn2 = DBCPUtil.getConnection();
			// 创建sql命令（占位符）
				String sql2 = "select ostate from `order` where oid=?";
			// 创建sql命令对象
				ps2 = conn2.prepareStatement(sql2);
			// 占位符赋值
				ps2.setString(1, oId);
			// 执行
				rs2 = ps2.executeQuery();
			// 遍历执行结果
				while(rs2.next()) {
					int os = rs2.getInt("ostate");
					if(os == arg) {
						flag = 1;      // update成功（用户或发行方）
					}
				}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			rs2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			ps2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(flag==1 && arg==3) {
			// 发送邮件
			String TO="";
			String emailMsg = "";
			
			// 查询uMail
			Connection conn3 = null;
			PreparedStatement ps3 = null;
			ResultSet rs3 = null;
			try {
				// 获取数据库连接对象
					conn3 = DBCPUtil.getConnection();
				// 创建sql命令（占位符）
					String sql3 = "select u.uMail, g.gname, o.onum "
								 +"FROM `order` o LEFT JOIN `user` u ON o.userid=u.uid "
								 +"LEFT JOIN `goods` g ON o.goodsid=g.gid "
								 +"WHERE oid=?";
				// 创建sql命令对象
					ps3 = conn3.prepareStatement(sql3);
				// 占位符赋值
					ps3.setString(1, oId);
				// 执行
					rs3 = ps3.executeQuery();
				// 遍历执行结果
					while(rs3.next()) {
						TO = rs3.getString("umail");
						String oon = rs3.getString("onum");
						String gn = rs3.getString("gname");
						emailMsg = "订单编号："+oId
								  +"\n\r["+gn+"] × "+oon
								  +"\n\r出货成功";
					}
				if(!"".equals(TO))
					MailUtil.sendMail(TO, emailMsg);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
				rs3.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				ps3.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn3.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			

		}
		
		
		return flag;
	}

	// ----------- pub_order_dao ----------------------------------------------------
	
	@Override
	public List<Order> pubLoadOrder(String pUid) {
		// 调用存储过程
				// call user_load_browse(uid)
				System.out.println("call pub_load_order('"+pUid+"')");
			
				Connection conn = null;
				PreparedStatement ps = null;
				ResultSet rs = null;
				List<Order> lo = null;
				
				try {
					// 获取数据库连接对象
						conn = DBCPUtil.getConnection();
					// 创建sql命令（占位符）
						String sql = "call pub_load_order(?)";
					// 创建sql命令对象
						ps = conn.prepareStatement(sql);
					// 占位符赋值
						ps.setString(1, pUid);
					// 执行
						rs = ps.executeQuery();
					// lo初始化
						lo = new ArrayList<Order>();
					// 遍历执行结果
						while(rs.next()) {
							// 读取数据到Order对象中
								Order o = new Order();
								o.setoId(rs.getString("oid"));
								o.setoState(rs.getInt("ostate"));
								o.setoTime(rs.getString("otime"));
								o.setoNum(rs.getInt("onum"));
								o.setGoodsPrice(rs.getDouble("goodsprice"));
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
//								g.getPub().setpUid(rs.getString("puid"));
//								g.getPub().setpName(rs.getString("pname"));
//								g.getPub().setpPwd(rs.getString("ppwd"));
//								g.getPub().setpInfo(rs.getString("pinfo"));
							// 读取数据到User对象中
								 User u = new User();
								 u.setuId(rs.getString("uid"));
								 u.setuName(rs.getString("uname"));
								 // u.setuMail(rs.getString("umail"));
								 u.setuBalance(rs.getDouble("ubalance"));
							
							// 将对象存储到lo中
								o.setGoods(g);
								o.setUser(u);
								lo.add(o);
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
				
				return lo;
	}


}
