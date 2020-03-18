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
	
	public String makeJson() {
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