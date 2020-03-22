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
public class StationDBHandle {
	
	@Autowired
	DataSource dataSource;
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	public String searchStation(String keyword) {
		JSONArray arr = new JSONArray();
		String sql= "select s_id, city, s_name, latitude, longitude, s_addr" 
				 +  "  from t_station" 
				 +  " where s_addr like '%' || ? || '%'";
		ResultSet rs = null;
	
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String s_id = rs.getString("s_id");
				String city = rs.getString("city");
				String s_name = rs.getString("s_name");
				int latitude = rs.getInt("latitude");
				int longitude = rs.getInt("longitude");
				String s_addr = rs.getString("s_addr"); 						

				JSONObject o = new JSONObject();
				o.put("s_id",s_id);
				o.put("city",city);
				o.put("s_name",s_name);
				o.put("latitude",latitude);
				o.put("longitude",longitude);
				o.put("s_addr",s_addr);
				arr.add(o);
			}
			
		rs.close();
		
		return arr.toString();
		
		}catch( Exception ex ) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
}

	 