package com.three.web;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BycHandle {
	@Autowired
	DataSource dataSource;

	Connection conn = null;
	PreparedStatement pstmt = null;

	
	public String makeJson() {
		JSONArray arr = new JSONArray();
		String sql="select * from s_num";
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String gu = rs.getString("구");
				int station = rs.getInt("대여소");
				int rest = rs.getInt("거치대");

				JSONObject o = new JSONObject();
				o.put("gu",gu);
				o.put("station",station);
				o.put("rest",rest);

				arr.add(o);
			}
			rs.close();
			return arr.toJSONString();
			
		}catch( Exception ex ) {
			return null;
		}
	}
	
	public String makePostJson() {
		JSONArray arr = new JSONArray();
		String sql="select p_text, r_text from post";
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String p_text = rs.getString("p_text");
				String r_text = rs.getString("r_text");

				JSONObject o = new JSONObject();
				o.put("p_text",p_text);
				o.put("r_text",r_text);

				arr.add(o);
			}
			rs.close();
			return arr.toJSONString();
			
		}catch( Exception ex ) {
			return null;
		}
	}
}
