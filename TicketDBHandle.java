package com.three.web;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDBHandle {
	@Autowired
	DataSource dataSource;

	Connection conn = null;
	PreparedStatement pstmt = null;
	
	
	public String selectTicket(String m_id) {
		JSONArray arr = new JSONArray();
		String sql= "select t_id, buy_date, s_date, e_date, end_yn" 
				 +  "  from t_buy_ticket" 
				 +  " where m_id = ? ";
		ResultSet rs = null;
	
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m_id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String t_id = rs.getString("t_id");
				Date buy_date = rs.getDate("buy_date");
				Date s_date = rs.getDate("s_date");
				Date e_date = rs.getDate("e_date");
				String end_yn = rs.getString("end_yn");						
				
	
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
				
				JSONObject o = new JSONObject();
				o.put("t_id",t_id);
				o.put("buy_date",transFormat.format(buy_date));
				if(s_date != null) {
					o.put("s_date",transFormat.format(s_date));
				}
				o.put("e_date",transFormat.format(e_date));
				o.put("end_yn",end_yn);
				arr.add(o);
			}
			
		rs.close();
		
		return arr.toString();
		
		}catch( Exception ex ) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	public String changeEndImg(String end_yn) {
		
	}
}
