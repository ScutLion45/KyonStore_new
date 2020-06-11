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
	public List<Order> userLoadOrder(String uId, int arg) { // 1-���ﳵ | 2-��ʷ����
		// ���ô洢����
		// call user_load_browse(uid)
		System.out.println("call user_load_order('"+uId+"',"+arg+")");
	
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Order> lo = null;
		
		try {
			// ��ȡ���ݿ����Ӷ���
				conn = DBCPUtil.getConnection();
			// ����sql���ռλ����
				String sql = "call user_load_order(?,?)";
			// ����sql�������
				ps = conn.prepareStatement(sql);
			// ռλ����ֵ
				ps.setString(1, uId);
				ps.setInt(2, arg);
			// ִ��
				rs = ps.executeQuery();
			// lo��ʼ��
				lo = new ArrayList<Order>();
			// ����ִ�н��
				while(rs.next()) {
					// ��ȡ���ݵ�Order������
						Order o = new Order();
						o.setoId(rs.getString("oid"));
						o.setoState(rs.getInt("ostate"));
						o.setoTime(rs.getString("otime"));
						o.setoNum(rs.getInt("onum"));
						o.setGoodsPrice(rs.getDouble("goodsprice"));
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
//						g.setgVolume(rs.getDouble("gvolume"));
						g.getPub().setpUid(rs.getString("puid"));
						g.getPub().setpName(rs.getString("pname"));
//						g.getPub().setpPwd(rs.getString("ppwd"));
						g.getPub().setpInfo(rs.getString("pinfo"));
					// ��ȡ���ݵ�User������
						// User u = new User();
						// u.setuId(rs.getString("uid"));
						// u.setuName(rs.getString("uname"));
						// u.setuBalance(rs.getDouble("ubalance"));
					
					// ������洢��lo��
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
		// ����arg��1-���ﳵ��2-�����ж��費��Ҫ����update
		
		 // �ֶ�����oId��oTime
		// ����QueryRunner������������
		QueryRunner runner = null;
		int flag1 = 0;  // ����order�Ƿ�ɹ�
		// int flag2 = 0;  // update�Ƿ�ɹ�
		int flag = 0;   // �ܵĲ������
		
		String oId = Utils.genRandID();
		String oTime = Utils.localeDateTime();
		System.out.println("call order_create('"+oId+"','"+oTime+"',"+oNum+",'"+gId+"',"+gPrice+",'"+uId+"');");
		try {
			// ����QueryRunner����
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// ����sql���
				String call = "call order_create(?,?,?,?,?,?)";
			// ����params��������
				Object[] callParams = { oId, oTime, oNum, gId, gPrice, uId };
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
				String sql = "select * from `order` where oid=?";
			// ����sql�������
				ps = conn.prepareStatement(sql);
			// ռλ����ֵ
				ps.setString(1, oId);
			// ִ��
				rs = ps.executeQuery();
			// ����ִ�н��
				while(rs.next()) {
					String ooid = rs.getString("oid");
					if(ooid.equals(oId)) {
						flag1 = 1;      // ����order�ɹ�
						if(arg==1) {
							flag = 1;   // ����Ҫupdate
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
			return flag; // =1�ɹ�
		}
		
	// --------------------------- update --------------------------------------------	
		
		if(arg==2 && flag1==1) {
			System.out.println("call order_update('"+oId+"','"+oTime+"');");
			try {
				// ����QueryRunner����
					runner = new QueryRunner(DBCPUtil.getDataSource());
				// ����sql���
					String call2 = "call order_update(?,?)";
				// ����params��������
					Object[] callParams2 = { oId, oTime };
				// ִ�д洢����
					runner.execute(call2, callParams2);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			// ִ��һ���ѯ
			Connection conn2 = null;
			PreparedStatement ps2 = null;
			ResultSet rs2 = null;
			try {
				// ��ȡ���ݿ����Ӷ���
					conn2 = DBCPUtil.getConnection();
				// ����sql���ռλ����
					String sql2 = "select ostate from `order` where oid=?";
				// ����sql�������
					ps2 = conn2.prepareStatement(sql2);
				// ռλ����ֵ
					ps2.setString(1, oId);
				// ִ��
					rs2 = ps2.executeQuery();
				// ����ִ�н��
					while(rs2.next()) {
						int os = rs2.getInt("ostate");
						if( os==2 || os==3 ) {
							flag = 1;      // update�ɹ�
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
		
		return flag; // =1�ɹ�
	}

	@Override
	public int userEditOrder(String oId, int oNum) {
		// ����QueryRunner������������
		QueryRunner runner = null;

		System.out.println("call order_edit('"+oId+"',"+oNum+");");
		try {
			// ����QueryRunner����
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// ����sql���
				String call = "call order_edit(?,?)";
			// ����params��������
				Object[] callParams = { oId, oNum };
			// ִ�д洢����
				runner.execute(call, callParams);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// ִ��һ���ѯ
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int flag = 0;
		try {
			// ��ȡ���ݿ����Ӷ���
				conn = DBCPUtil.getConnection();
			// ����sql���ռλ����
				String sql = "select onum from `order` where oid=?";
			// ����sql�������
				ps = conn.prepareStatement(sql);
			// ռλ����ֵ
				ps.setString(1, oId);
			// ִ��
				rs = ps.executeQuery();
			// ����ִ�н��
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
		
		return flag; // =1�ɹ�
	}

	@Override
	public int userRemoveOrder(String oId) {
		// ����QueryRunner������������
		QueryRunner runner = null;

		System.out.println("call order_remove('"+oId+"');");
		try {
			// ����QueryRunner����
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// ����sql���
				String call = "call order_remove(?)";
			// ����params��������
				Object[] callParams = { oId };
			// ִ�д洢����
				runner.execute(call, callParams);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// ִ��һ���ѯ
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int flag = 1;
		try {
			// ��ȡ���ݿ����Ӷ���
				conn = DBCPUtil.getConnection();
			// ����sql���ռλ����
				String sql = "select * from `order` where oid=?";
			// ����sql�������
				ps = conn.prepareStatement(sql);
			// ռλ����ֵ
				ps.setString(1, oId);
			// ִ��
				rs = ps.executeQuery();
			// ����ִ�н��
				while(rs.next()) {
					// ����н��˵��ɾ�����ɹ�
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
		
		return flag; // =1�ɹ�
	}

	// ----------- update_order ----------------------------------------------------
	@Override
	public int updateOrder(String oId, int arg) {  // 2-�û��ӹ��ﳵ���㣬3-���з�����
		
		QueryRunner runner = null;
		int flag = 0;
		String oTime = Utils.localeDateTime();

		System.out.println("call order_update('"+oId+"','"+oTime+"'); [arg="+arg+"]");
		try {
			// ����QueryRunner����
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// ����sql���
				String call2 = "call order_update(?,?)";
			// ����params��������
				Object[] callParams2 = { oId, oTime };
			// ִ�д洢����
				runner.execute(call2, callParams2);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// ִ��һ���ѯ
		Connection conn2 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		try {
			// ��ȡ���ݿ����Ӷ���
				conn2 = DBCPUtil.getConnection();
			// ����sql���ռλ����
				String sql2 = "select ostate from `order` where oid=?";
			// ����sql�������
				ps2 = conn2.prepareStatement(sql2);
			// ռλ����ֵ
				ps2.setString(1, oId);
			// ִ��
				rs2 = ps2.executeQuery();
			// ����ִ�н��
				while(rs2.next()) {
					int os = rs2.getInt("ostate");
					if(os == arg) {
						flag = 1;      // update�ɹ����û����з���
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
			// �����ʼ�
			String TO="";
			String emailMsg = "";
			
			// ��ѯuMail
			Connection conn3 = null;
			PreparedStatement ps3 = null;
			ResultSet rs3 = null;
			try {
				// ��ȡ���ݿ����Ӷ���
					conn3 = DBCPUtil.getConnection();
				// ����sql���ռλ����
					String sql3 = "select u.uMail, g.gname, o.onum "
								 +"FROM `order` o LEFT JOIN `user` u ON o.userid=u.uid "
								 +"LEFT JOIN `goods` g ON o.goodsid=g.gid "
								 +"WHERE oid=?";
				// ����sql�������
					ps3 = conn3.prepareStatement(sql3);
				// ռλ����ֵ
					ps3.setString(1, oId);
				// ִ��
					rs3 = ps3.executeQuery();
				// ����ִ�н��
					while(rs3.next()) {
						TO = rs3.getString("umail");
						String oon = rs3.getString("onum");
						String gn = rs3.getString("gname");
						emailMsg = "������ţ�"+oId
								  +"\n\r["+gn+"] �� "+oon
								  +"\n\r�����ɹ�";
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
		// ���ô洢����
				// call user_load_browse(uid)
				System.out.println("call pub_load_order('"+pUid+"')");
			
				Connection conn = null;
				PreparedStatement ps = null;
				ResultSet rs = null;
				List<Order> lo = null;
				
				try {
					// ��ȡ���ݿ����Ӷ���
						conn = DBCPUtil.getConnection();
					// ����sql���ռλ����
						String sql = "call pub_load_order(?)";
					// ����sql�������
						ps = conn.prepareStatement(sql);
					// ռλ����ֵ
						ps.setString(1, pUid);
					// ִ��
						rs = ps.executeQuery();
					// lo��ʼ��
						lo = new ArrayList<Order>();
					// ����ִ�н��
						while(rs.next()) {
							// ��ȡ���ݵ�Order������
								Order o = new Order();
								o.setoId(rs.getString("oid"));
								o.setoState(rs.getInt("ostate"));
								o.setoTime(rs.getString("otime"));
								o.setoNum(rs.getInt("onum"));
								o.setGoodsPrice(rs.getDouble("goodsprice"));
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
//								g.getPub().setpUid(rs.getString("puid"));
//								g.getPub().setpName(rs.getString("pname"));
//								g.getPub().setpPwd(rs.getString("ppwd"));
//								g.getPub().setpInfo(rs.getString("pinfo"));
							// ��ȡ���ݵ�User������
								 User u = new User();
								 u.setuId(rs.getString("uid"));
								 u.setuName(rs.getString("uname"));
								 // u.setuMail(rs.getString("umail"));
								 u.setuBalance(rs.getDouble("ubalance"));
							
							// ������洢��lo��
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
