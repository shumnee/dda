package com.three.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TicketHandle {
	@Autowired
	DataSource dataSource;

	Connection conn = null;
	PreparedStatement pstmt = null;
	
	public String makeJson() {
		JSONArray arr = new JSONArray();
		String sql="select * from t_type_ticket";
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String ticket_type = rs.getString("TICKET_TYPE");
				int t_id = rs.getInt("TYPE_ID");
				int t_price = rs.getInt("t_price");

				JSONObject o = new JSONObject();
				
				o.put("ticket_type",ticket_type);
				o.put("t_id",t_id);
				o.put("t_price",t_price);

				arr.add(o);
			}
			rs.close();
			return arr.toJSONString();
			
		}catch( Exception ex ) {
			return null;
		}
	}
	
	
	public String makeJJson() {
		JSONArray arr = new JSONArray();
		String sql="select * from t_insurance";
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String I_ID = rs.getString("I_ID");
				String I_NAME = rs.getString("I_NAME");
				int I_PRICE = rs.getInt("I_PRICE");
				
				JSONObject o = new JSONObject();
				
				o.put("I_ID",I_ID);
				o.put("I_NAME",I_NAME);
				o.put("I_PRICE",I_PRICE);
				
				arr.add(o);
			}
			rs.close();
			return arr.toJSONString();
			
		}catch( Exception ex ) {
			return null;
		}
	}
}