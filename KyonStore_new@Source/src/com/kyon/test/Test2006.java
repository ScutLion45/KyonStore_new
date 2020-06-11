package com.kyon.test;

import java.util.*;
import com.kyon.pojo.*;
import com.kyon.dao.*;
import com.kyon.daoImpl.*;
import com.google.gson.Gson;

public class Test2006 {
	
	public void test_gson() {
		String name = "kyon45";
		String pwd = "12345";
		Object u = (Object)(new LoginDaoImpl()).checkUPwd(name, pwd);
		
		System.out.println("----------------------------------");
		System.out.println(u);
		System.out.println("----------------------------------");
		System.out.println(new Gson().toJson(u));
	}
	
	public void test_stack() {
		Stack<Integer> s = new Stack<>();
		s.push(0);
		System.out.println(s);
		System.out.println(s.size());
		int top = s.pop();
		System.out.println(top);
		System.out.println(s.size());
	}
	
	public void test_convert() {
		String name = "kyon45";
		String pwd = "1234";
		Object obj = (Object)(new LoginDaoImpl()).checkUPwd(name, pwd);
		
		try {
			String uId = ((User)obj).getuId();
			System.out.println("uId: "+uId);
		} catch(Exception e) {
			System.out.println("ERROR `String uId = ((User)obj).getuId()`");
			e.printStackTrace();
		} finally {
			System.out.println("---------------------------------------");
		}
		
		try {
			String pUid = ((Publisher)obj).getpUid();
			System.out.println("pUid: "+pUid);
		} catch(ClassCastException e) {
			System.out.println("Failed to cast to com.kyon.pojo.Publisher");
		} catch(Exception e) {
			System.out.println("ERROR `String pUid = ((Publisher)obj).getpUid()`");
			e.printStackTrace();
		} finally {
			System.out.println("---------------------------------------");
		}
		
	}
	
	// -------------------------------------------------------------------------	
	public void test_move() {
		// �������ж�������Ԫ��ȡ������뵽��һλ������������������Ԫ��֮����Ⱥ�˳��
		
		List<Integer> l = new ArrayList<>();
		l.add(1);
		l.add(2);
		l.add(3);
		l.add(4);
		
		System.out.println("before: "+l);
		
//		Collections.swap(l, 3, 0);
		for(int i=0; i<l.size(); i++) {
			System.out.println(">>> before: i="+i+"; l="+l);
			if(l.get(i) > 2) {
//				Collections.swap(l, i, 0);
				Integer item = l.remove(i);
				System.out.println("    *** after remove: item="+item+"; l="+l);
				l.add(0, item);
			}
			System.out.println(">>> after:  i="+i+"; l="+l);
		}
		
		System.out.println("after:  "+l);
		
		
	}
	public void test_remove() {
		// �������ж�������Ԫ��ȡ�����Ż�
		
		List<Integer> l = new ArrayList<>();
		l.add(1);
		l.add(2);
		l.add(3);
		l.add(4);
		
		System.out.println("before: "+l);
		
//		Collections.swap(l, 3, 0);
		for(int i=0; i<l.size(); i++) {
			System.out.println(">>> before: i="+i+"; l="+l);
			if(l.get(i) > 2) {
//				Collections.swap(l, i, 0);
				Integer item = l.remove(i);
				System.out.println("    *** after remove: item="+item+"; l="+l);
//				l.add(0, item);
				i--;
			}
			System.out.println(">>> after:  i="+i+"; l="+l);
		}
		
		System.out.println("after:  "+l);
		
		
	}
	
	public void test_set(Map<String, Integer> map, Set<String> set) {
		// ����������null�������new Ҳ�޷���ԭ���Ķ�������޸ģ���Ϊ������ǿ�ָ�룬�Ҳ�����
		if(map == null) {
//			map = new HashMap<>();
			return;
		}
		if(set == null) {
//			set = new HashSet<>();
			return;
		}
		for(int i=0; i<10; i++) {
			String id = ""+i;
			map.put(id, i);
			set.add(id);
		}
	}
	
	public void test_buildUP() {
		String uId = "45";
//		String webRoot = "0.0.0.0";
		UserProDao upd = new UserProDaoImpl();
		
		try {
			System.out.println("// ��ȡ�û������¼����ʷ�������ѹ���ģ�");
			List<Browse> lb = new UserDaoImpl().loadBrowse(uId);
			List<Order> lo = new OrderDaoImpl().userLoadOrder(uId, 2);
			System.out.println(lb.size()+"; "+lo.size()+";\n");

			System.out.println("// ����gType��pos���ϵ�ӳ��");
			Map<String, Set<ScoreTuple>> map_gt = upd.get_gt(lb, lo);
			System.out.println(map_gt+";\n");
			System.out.println("// ����pUid��pos���ϵ�ӳ��");
			Map<String, Set<ScoreTuple>> map_pu = upd.get_pu(lb, lo);
			System.out.println(map_pu+";\n");

			System.out.println("// ��ȡlo��gId-uBoughtӳ��");
			Map<String, Integer> lo_gId_map = upd.lo_get_gId_uBought(lo);
			System.out.println(lo_gId_map.size()+";\n");
			
			System.out.println("// �����gType��score��ȡ���ֵ������score��������");
			List<ScoreTuple> score_gt = upd.cal_gtScore(map_gt, lb, lo, lo_gId_map);
			System.out.println((score_gt==null)+";\n");
			
			System.out.println("// �����pUid��score��ȡ���ֵ������score��������");
			List<ScoreTuple> score_pu = upd.cal_puScore(map_pu, lb, lo, lo_gId_map);
			System.out.println((score_pu==null)+";\n");		
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		Test2006 t = new Test2006();
		
//		t.test_gson();
//		t.test_stack();
//		t.test_convert();
//		t.test_move();
		
//		t.test_remove();
		
		t.test_buildUP();
		
/*
		// test_set_ver1
		Map<String, Integer> map = null;
		Set<String> set = null;
		t.test_set(map, set);
		System.out.println(map);	// output: null
		System.out.println(set);	// output: null
		// ---------------------------------------------------
*/	
/*
		// test_set_ver2 ��
		Map<String, Integer> map = new HashMap<>();
		Set<String> set = new HashSet<>();
		t.test_set(map, set);
		System.out.println(map);	// output: {0=0, 1=1, 2=2, 3=3, 4=4, 5=5, 6=6, 7=7, 8=8, 9=9}
		System.out.println(set);	// output: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
		// ---------------------------------------------------
*/	
		

	}

}
