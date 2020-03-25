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
public class ReturnHandle {
	@Autowired
	DataSource dataSource;

	
	PreparedStatement pstmt = null;
	
	public String makeRestStock() {
		JSONArray arr = new JSONArray();
		String sql="select * from t_station";
		ResultSet rs = null;
		System.out.println("남은 거치대 개수 추가");
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String s_id = rs.getString("s_id");
				String city = rs.getString("city");
				String s_name = rs.getString("s_name");
				float lat = rs.getFloat("latitude");
				float lon = rs.getFloat("longitude");
				int stock = rs.getInt("stock");
				String s_addr = rs.getString("s_addr");
				int s_all = rs.getInt("s_all");
				
				JSONObject o = new JSONObject();
				int rest = s_all - stock;
				o.put("s_id",s_id);
				o.put("city",city);
				o.put("s_name",s_name);
				o.put("lat",lat);
				o.put("lon",lon);
				o.put("stock",stock);
				o.put("s_addr",s_addr);
				o.put("s_all",s_all);
				o.put("rest", rest);


				arr.add(o);
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
	
	
	
	public String getRental(String mi) {
		Connection conn = null;
		

		JSONArray arr = new JSONArray();
		ResultSet rs = null;
		
		System.out.println("getRental - returnCheckInfo 실행");

		String sql_send = "select r_id, m_id, s_id, t_id, b_id, to_char(rental_time, 'yyyy-mm-dd hh24:mi') rental_time from t_use_ticket where m_id = '"+mi+"' and r_use = 2 and rs_id = '0'";
		// r_use가 사용이면서(대여 비밀번호 입력 완료, 취소 불가 상태) rs_id 가 null인 것(아직 반납 전): 한 행만 반환됨 -> 미반납내역 존재하면 여러개 대여 불가
	
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql_send); //end_yn 업데이트 됐는데도 1인 것들 중 선택한 자전거가 아직 대여되지 않았는지 확인
			rs = pstmt.executeQuery();
			while(rs.next()) {
				//SimpleDateFormat f = new SimpleDateFormat("yyyy-mm-dd HH:mi", Locale.KOREA);
				// e_date not null 중에 현재시간 > e_date 이면 end_yn update
				
				JSONObject o = new JSONObject();
				String r_id = rs.getString("r_id");
				String m_id = rs.getString("m_id");
				String s_id = rs.getString("s_id");
				String t_id = rs.getString("t_id");
				String b_id = rs.getString("b_id");
				String rental_time = rs.getString("rental_time");
				//SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				//Date rental_time = rs.getDate("rental_time");
				System.out.println(rental_time);
				o.put("r_id",r_id);
				o.put("m_id",m_id);
				o.put("s_id",s_id);
				o.put("t_id",t_id);
				o.put("b_id",b_id); 
				//o.put("rental_time",transFormat.format(rental_time)); 
				o.put("rental_time",rental_time); 
				arr.add(o);
				System.out.println("반납 업데이트 시 필요 데이터"+arr);
				//pstmt = conn.prepareStatement(set_date); // s_date null이면 사용 시작으로 바뀜(s_date, e_date set)
				//pstmt.executeQuery();
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
	
	
	public String insertReturn(String ri, String mi, String ti, String si, String bi) {
		// 대여내역(t_use_ticket)에 [예약id(r_id),m_id,t_id,s_id,b_id,사용 여부 0] 추가 ->insertReturn
		System.out.println("insertReturn - insertReturnInfo 실행");

		JSONArray arr = new JSONArray();
		ResultSet rs = null;
	
		String sql_send = "select count(*) from t_use_ticket where m_id = '"+mi+"' and r_use = 2 and rs_id ='0'";
		
		String update_return_time="update t_use_ticket set return_time = sysdate where m_id = ? and r_use = 2 and rs_id ='0'";
		// t_use_ticket의 r_use 2이면서 rs_id null인 행을 return_time update
		String get_return_time="select r_id, to_char(return_time, 'yyyy-mm-dd hh24:mi') return_time from t_use_ticket where m_id = ? and r_use = 2 and rs_id ='0'";
		
		String update_return_usetime = "update t_use_ticket set use_time = (TRUNC(SYSDATE, 'MI')-trunc(rental_time,'MI')) * 1440 where m_id = '"+mi+"' and r_id = '"+ri+"'";
		// use_time 계산 업데이트
		String get_return_usetime = "select use_time from t_use_ticket where m_id = '"+mi+"' and r_id = '"+ri+"'";
		
		String update_return_rs_id="update t_use_ticket set rs_id = ? where m_id = ? and r_use = 2 and rs_id ='0'";
		// t_use_ticket의 r_use 2이면서 rs_id null인 행을 rs_id = 현재 선택된 si로 변경
		String update_bi_cond = "update t_bicycle set cond_info = 1 where b_id ='"+bi+"'";
		// 반납된 자전거 cond_info = 1으로 업데이트
		String update_bi_si = "update t_bicycle set s_id= '"+si+"' where b_id ='"+bi+"'";
		// 반납된 자전거 반납된 대여소 s_id으로 업데이트
		String update_st = "update t_station set stock = (select count(*) from t_bicycle where t_bicycle.s_id = t_station.s_id and t_bicycle.cond_info = 1)";
		// t_station의 자전거 수 재고 업데이트 (빈 거치대는 증가, s_all -stock)
		
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			
			pstmt = conn.prepareStatement(sql_send);
			rs = pstmt.executeQuery();
			
			rs.next();
			int cnt = rs.getInt("count(*)");
			if(cnt==1) {
				JSONObject o = new JSONObject();
				// 시, 분이 안됨. rs_id 업데이트 안됨
				pstmt = conn.prepareStatement(update_return_time); // t_use_ticket의 return time update
				pstmt.setString(1, mi);
				pstmt.executeQuery();
				
				pstmt = conn.prepareStatement(get_return_time);
				pstmt.setString(1, mi);
				rs = pstmt.executeQuery();
				rs.next();
				String return_time = rs.getString("return_time");
				String r_id = rs.getString("r_id");

				//SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				//Date return_time = rs.getDate("return_time");
				o.put("return_time",return_time);
				o.put("r_id",r_id);
				//to_char(rental_time, 'yyyy-mm-dd hh24:mi')
				
				pstmt = conn.prepareStatement(update_return_usetime); // t_use_ticket의 use time update
				pstmt.executeQuery();
				rs.next();
				
				pstmt = conn.prepareStatement(get_return_usetime); // t_use_ticket의 use time update
				rs = pstmt.executeQuery();
				rs.next();
				int use_time = rs.getInt("use_time");
				o.put("use_time",use_time);
				System.out.println(use_time);
				
				pstmt = conn.prepareStatement(update_return_rs_id); // t_use_ticket의 rs_id update
				pstmt.setString(1, si);
				pstmt.setString(2, mi);
				pstmt.executeQuery();
				//rs.next();

				pstmt = conn.prepareStatement(update_bi_cond); // 자전거 cond 변경
				pstmt.executeQuery();
				//rs.next();
				pstmt = conn.prepareStatement(update_bi_si); // 자전거 s_id 변경
				pstmt.executeQuery();
				//rs.next();
				pstmt = conn.prepareStatement(update_st); // 반납대여소 재고 변경
				pstmt.executeQuery();

				//rs.next();
				arr.add(o);
				System.out.println("반납 업데이트 완료? "+use_time);		
			}else {
				arr.add(null);
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
	
	public String insertPayment(String ri, String mi, String ti, int use_time) {
		// 대여내역(t_use_ticket)에 [예약id(r_id),m_id,t_id,s_id,b_id,사용 여부 0] 추가 ->insertRental
		System.out.println("insertPayment - insertPaymentInfo 실행");

		JSONArray arr = new JSONArray();
		int py_cnt =0;
		ResultSet rs = null;
		// 추가납입: insertReturn에서 계산된 use_time이 >60 이면 alert 창, 바로 결제 처리, p_code = 0, p_type = 1
		
		String insert_payment = "insert into t_payment values(?,?,?,?,0,sysdate,1,?)";
		
		String pay_cnt = "select count(*) from t_payment";
		String select = "select * from t_use_ticket where r_id = '"+ri+"'";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			
			pstmt = conn.prepareStatement(pay_cnt); //새 예약 행 id 생성
			rs = pstmt.executeQuery();
			rs.next();
			py_cnt = rs.getInt("count(*)")+1;
			System.out.println(py_cnt + "새 결제행 번호");
			
			pstmt = conn.prepareStatement(select); 
			rs = pstmt.executeQuery();
			
			while(rs.next()) {

				JSONObject o = new JSONObject();
				//(?,?,?,?,0,sysdate,1,?)
				
				int in_yn = Integer.parseInt(rs.getString("insurance_yn"));
				int add = 0;
				if(in_yn == 1) {
					add = 300;
				}else {
					add = 200;
				}
				int money = ((use_time - 60)/10)*add;
				
				pstmt = conn.prepareStatement(insert_payment); 
				String p_id = "pay"+String.valueOf(py_cnt);
				pstmt.setString(1, p_id);
				pstmt.setString(2, ri);
				pstmt.setString(3, mi);
				pstmt.setInt(4, money);
				pstmt.setString(5, ti);
				pstmt.executeQuery();
				o.put("pi",p_id);
				o.put("ri",ri); 
				o.put("mi",mi); 
				o.put("money",money); 
				o.put("ti",ti); 
				
				arr.add(o);
				System.out.println("결제 라인 추가 완료?"+arr);		
				
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
	
	public String moveStar(String mi) {
		System.out.println("moveStar - moveInfo 실행"+mi);

		JSONArray arr = new JSONArray();
		ResultSet rs = null;
		
		String select_star = "select * from t_station, t_member where t_station.s_id = t_member.s_id and t_member.m_id = '"+mi+"'";
		
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			
			pstmt = conn.prepareStatement(select_star); 
			rs = pstmt.executeQuery();
			rs.next();
			JSONObject o = new JSONObject();
			
			float latitude = rs.getFloat("latitude");
			float longitude = rs.getFloat("longitude");
			
			o.put("latitude",latitude);
			o.put("longitude",longitude);
				
			arr.add(o);
			System.out.println("즐찾"+arr);		
				
			
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
	
