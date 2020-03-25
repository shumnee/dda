package com.three.web;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

	Connection conn = null;
	PreparedStatement pstmt = null;
	
	public String selectTuse_ticket() { // 체크용
		System.out.println("확인 - 예약 행 삽입 완료 체크, select * from t_use_ticket 실행");

		JSONArray arr = new JSONArray();
		ResultSet rs = null;
		
		String send_sql="select * from t_use_ticket";
		
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
		}
	}
	
	public String selectTbicycle() { // 체크용
		System.out.println("확인 - 자전거 cond_info 변화 체크, select * from t_bicycle 실행");

		JSONArray arr = new JSONArray();
		ResultSet rs = null;
		
		String send_sql="select * from t_bicycle";
		
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
		}
	}
	
	public String selectTstation() { // 체크용
		System.out.println("확인 - 자전거 재고 변화 체크, select * from t_station 실행");

		JSONArray arr = new JSONArray();
		ResultSet rs = null;
		
		String send_sql="select * from t_station";
		
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
		}
	}
}
	
