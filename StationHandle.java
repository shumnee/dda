package com.three.web;


import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class StationHandle {
	@Autowired
	DataSource dataSource;

	Connection conn = null;
	PreparedStatement pstmt = null;
	public String selectST(String s_id) {
		// 지도에서 고른 대여소에 거치되어있는 자전거 반환
		JSONArray arr = new JSONArray();
		//String sql="select * from t_bicycle where s_id='"+s_id+"' and cond_info='1'";
		String sql="select * from t_bicycle where s_id = ? and cond_info = 1";
		ResultSet rs = null;
		System.out.println("selectST - selectInfo 실행");
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, s_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String b_id = rs.getString("b_id");
				JSONObject o = new JSONObject();

				o.put("s_id",s_id);
				o.put("b_id",b_id);


				arr.add(o);
				System.out.println(arr);
			}
			rs.close();
			return arr.toJSONString();
		}catch( Exception ex ) {
			return null;
		}
	}
	
	public String selectTK(String m_id) {
		// 사용자 이용권 종류 찾기
		JSONArray arr = new JSONArray();
		String check_e_date = "update t_buy_ticket set end_yn = 0 where sysdate > e_date";
		String get_type = "select * from t_buy_ticket,t_ticket where t_buy_ticket.end_yn =1 and t_buy_ticket.type_id = t_ticket.type_id and t_buy_ticket.m_id='"+m_id+"'"; // 이용권 타입반환
		ResultSet rs = null;
		System.out.println("selectTK - ticketInfo 실행");

		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(check_e_date);
			pstmt.executeQuery();
			// 모든 e_date 확인 후 end_yn 업데이트
			
			pstmt = conn.prepareStatement(get_type); 
			pstmt.executeQuery();
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String t_id = rs.getString("t_id");
				String type_id = rs.getString("type_id");
				String ticket_type = rs.getString("ticket_type");
				JSONObject o = new JSONObject();
				o.put("t_id",t_id);
				o.put("type_id",type_id);
				o.put("ticket_type",ticket_type);


				arr.add(o);
				System.out.println("사용자 티켓 데이터, 버튼 붙어야함"+arr);

			}
			rs.close();
			return arr.toJSONString();
		}catch( Exception ex ) {
			return null;
		}
	}
	
	public String makeStJson() {
		JSONArray arr = new JSONArray();
		String sql="select * from t_station";
		ResultSet rs = null;
		System.out.println("map selectStation 실행");

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

				o.put("s_id",s_id);
				o.put("city",city);
				o.put("s_name",s_name);
				o.put("lat",lat);
				o.put("lon",lon);
				o.put("stock",stock);
				o.put("s_addr",s_addr);
				o.put("s_all",s_all);


				arr.add(o);
			}
			rs.close();
			return arr.toJSONString();
			
		}catch( Exception ex ) {
			return null;
		}
	}
	
	public String makeJson() { // all data test 용, 지도에 모든 데이터 찍기
		JSONArray arr = new JSONArray();
		String sql="select * from test";
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String gu = rs.getString("대여소_구");
				int id = rs.getInt("대여소ID");
				String name = rs.getString("대여소명");
				String addr = rs.getString("대여소주소");
				float lat = rs.getFloat("위도");
				float lon = rs.getFloat("경도");
				String date = rs.getString("기준시작일자");
				int num = rs.getInt("거치대수");
				
				JSONObject o = new JSONObject();
				String[] token = date.split(" ");
				if(token[0]!=null) {
					date = token[0];
				}
				
				o.put("gu",gu);
				o.put("id",id);
				o.put("name",name);
				o.put("addr",addr);
				o.put("lat",lat);
				o.put("lon",lon);
				o.put("date",date);
				o.put("num",num);

				arr.add(o);
			}
			rs.close();
			return arr.toJSONString();
			
		}catch( Exception ex ) {
			return null;
		}
	}
}
