package com.three.web;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;


@Repository
public class RecordHandle {
	@Autowired
	DataSource dataSource;

	
	PreparedStatement pstmt = null;
	
	
	public String insertBuy(String mi, int t_day, String type_id, String insurance_yn, int money) {
		
		JSONArray arr = new JSONArray();
		ResultSet rs = null;
	
		String check_buy = "select count(*) from t_buy_ticket";
		String check_pay = "select count(*) from t_payment";
		String insert_buy = "insert into t_buy_ticket values(?,?,?,null,null,?,1,?,sysdate)";
		String insert_pay = "insert into t_payment values(?,null,?,?,0,sysdate,1,?)";
		
		String pay_cnt = "select count(*) from t_payment";

		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			
			
			JSONObject o = new JSONObject();
				
			pstmt = conn.prepareStatement(check_buy);
			rs = pstmt.executeQuery();
			rs.next();
			int cnt_buy = rs.getInt("count(*)");
			
			
			pstmt = conn.prepareStatement(check_pay);
			rs = pstmt.executeQuery();
			rs.next();
			int cnt_pay = rs.getInt("count(*)");
			// 미반납 내역(r_use=2면서 rs_id=0: 대여(사용)했으나 반납 대여소가 찍히지 않은 내역) 행 체크
			//t_id, m_id, t_day, s_date, e_date, type_id, end_yn, insurance_yn
			//insert into t_buy_ticket values(?,?,?,null,null,?,1,?,sysdate)
			pstmt = conn.prepareStatement(insert_buy);					
			String t_id = "tk"+String.valueOf(cnt_buy);
			
			pstmt.setString(1, t_id);
			pstmt.setString(2, mi);
			pstmt.setInt(3, t_day);
			pstmt.setString(4, type_id);
			pstmt.setString(5, insurance_yn);
			pstmt.executeQuery();
			
			//insert into t_payment values(?,null,?,?,0,sysdate,1,?)
			pstmt = conn.prepareStatement(insert_pay);					
			String p_id = "paym"+String.valueOf(cnt_pay);
			pstmt.setString(1, p_id);
			pstmt.setString(2, mi);
			pstmt.setInt(3, money);
			pstmt.setString(4, t_id);
			pstmt.executeQuery();
			
			o.put("t_id",t_id);
			o.put("p_id",p_id);
			arr.add(o);
			
			System.out.println("구매 및 결제 행 삽입 완료? :"+arr);		
			
			rs.close();
			return arr.toJSONString();
		}catch( Exception ex ) {
			return null;
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// 구매 메뉴 누를 때 사용 end_yn 체크
	public String checkBuyTicket( String mi) {
		// 이용권 구매 내역(t_buy_ticket)에 [t_id,m_id,t_id,s_id,b_id,사용 여부 0] 추가 ->insertRental
		System.out.println("checkBuyTicket - checkBuyTicketInfo 실행");

		JSONArray arr = new JSONArray();
		int buy_cnt =0;
		ResultSet rs = null;
		String check_endyn = "select count(*) from t_buy_ticket where m_id = '"+mi+"' and end_yn = 1";
		
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			
			pstmt = conn.prepareStatement(check_endyn);
			rs = pstmt.executeQuery();
			rs.next();
			int cnt_end = rs.getInt("count(*)"); // end_yn =1 인 count ==0이여야 구매 가능
			
			
			JSONObject o = new JSONObject();
			o.put("cnt_end",cnt_end);
			arr.add(o);
			
			
			rs.close();
			return arr.toJSONString();
		}catch( Exception ex ) {
			return null;
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	public String checkRuse(String mi) {
		// t_use_ticket에서 r_use가 2면서 rs_id가 0인 데이터 유무 검색
		System.out.println("checkBuyTicket - checkBuyTicketInfo 실행");

		JSONArray arr = new JSONArray();
		ResultSet rs = null;
		String check_ruse = "select count(*) from t_use_ticket where m_id = '"+mi+"' and r_use=2 and rs_id = '0'";
		
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			
			pstmt = conn.prepareStatement(check_ruse);
			rs = pstmt.executeQuery();
			rs.next();
			int cnt_ruse = rs.getInt("count(*)"); // end_yn =1 인 count ==0이여야 구매 가능
			
			
			JSONObject o = new JSONObject();
			o.put("cnt_ruse",cnt_ruse);
			arr.add(o);
			
			
			rs.close();
			return arr.toJSONString();
		}catch( Exception ex ) {
			return null;
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	public String selectTicket() { // 체크용
		System.out.println("이용권 정보 불러오기, select * from t_ticket 실행");

		JSONArray arr = new JSONArray();
		ResultSet rs = null;
		
		String send_sql="select * from t_ticket where t_day is not null";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			
			pstmt = conn.prepareStatement(send_sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				JSONObject o = new JSONObject();
				
				String type_id = rs.getString("type_id");
				String ticket_type = rs.getString("ticket_type");
				int t_price = rs.getInt("t_price");
				int i_price = rs.getInt("i_price");
				int t_day = rs.getInt("t_day");
				
				o.put("type_id",type_id);
				o.put("ticket_type",ticket_type);
				o.put("t_price",t_price);
				o.put("i_price",i_price);
				o.put("t_day",t_day);

				arr.add(o);
				System.out.println("t_ticket 불러오기 "+arr);	
			}
			rs.close();
			return arr.toJSONString();
		}catch( Exception ex ) {
			return null;
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public String selectTuse_ticket() { // 체크용
		System.out.println("확인 - 예약 행 삽입 완료 체크, select * from t_use_ticket 실행");

		JSONArray arr = new JSONArray();
		ResultSet rs = null;
		
		String send_sql="select * from t_use_ticket";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			
			pstmt = conn.prepareStatement(send_sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				JSONObject o = new JSONObject();
				
				String r_id = rs.getString("r_id");
				String m_id = rs.getString("m_id");
				String t_id = rs.getString("t_id");
				String s_id = rs.getString("s_id");
				String b_id = rs.getString("b_id");
				int r_use = rs.getInt("r_use");
				String rental_time = rs.getString("rental_time");
				String rs_id = rs.getString("rs_id");
				String return_time = rs.getString("return_time");
				String insurance_yn = rs.getString("insurance_yn");
				int use_time = rs.getInt("use_time");
				o.put("r_id",r_id);
				o.put("m_id",m_id);
				o.put("t_id",t_id);
				o.put("s_id",s_id);
				o.put("b_id",b_id);
				o.put("r_use",r_use);
				o.put("rental_time",rental_time);
				o.put("rs_id",rs_id);
				o.put("return_time",return_time);
				o.put("insurance_yn",insurance_yn);
				o.put("use_time",use_time); 
				arr.add(o);
				System.out.println("r_use 내역 체크"+arr);	
			}
			rs.close();
			return arr.toJSONString();
		}catch( Exception ex ) {
			return null;
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String selectTbicycle() { // 체크용
		System.out.println("확인 - 자전거 cond_info 변화 체크, select * from t_bicycle 실행");

		JSONArray arr = new JSONArray();
		ResultSet rs = null;
		
		String send_sql="select * from t_bicycle";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			
			pstmt = conn.prepareStatement(send_sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				JSONObject o = new JSONObject();
				
				String b_id = rs.getString("b_id");
				String s_id = rs.getString("s_id");
				String cond_info = rs.getString("cond_info");
				String reg_date = rs.getString("reg_date");
				
				o.put("b_id",b_id);
				o.put("s_id",s_id);
				o.put("cond_info",cond_info);
				o.put("reg_date",reg_date);
				
				arr.add(o);
				System.out.println("t_bicycle cond_info 내역 체크"+arr);	
			}
			rs.close();
			return arr.toJSONString();
		}catch( Exception ex ) {
			return null;
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String selectTstation() { // 체크용
		System.out.println("확인 - 자전거 재고 변화 체크, select * from t_station 실행");

		JSONArray arr = new JSONArray();
		ResultSet rs = null;
		
		String send_sql="select * from t_station";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			
			pstmt = conn.prepareStatement(send_sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				JSONObject o = new JSONObject();
				
				String s_id = rs.getString("s_id");
				String s_name = rs.getString("s_name");
				String stock = rs.getString("stock");
				
				o.put("s_id",s_id);
				o.put("s_name",s_name);
				o.put("stock",stock);
				
				arr.add(o);
				System.out.println("t_station stock 내역 체크"+arr);	
			}
			rs.close();
			return arr.toJSONString();
		}catch( Exception ex ) {
			return null;
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
	
