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
public class memberDBHandle {
	
	@Autowired
	DataSource dataSource;
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	public String selectMemberId(String m_id) {
		JSONArray arr = new JSONArray();
		String sql= "select count(*) as cnt from t_member where m_id = ?";
		ResultSet rs = null;
		String rst = "0"; //중복
	
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m_id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(rs.getInt("cnt") > 0) {
					rst = "0"; //사용 불가능					
				} else { 
					rst = "1"; //사용 가능
				}

				JSONObject o = new JSONObject();
				o.put("rst",rst);
				arr.add(o);
			}
			
		rs.close();
		
		return arr.toString();
		
		}catch( Exception ex ) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	 
	
	public boolean checkPassword(String m_id, String m_pwd) {
		String sql= "select m_id, m_pwd from t_member where m_id = ?";
		ResultSet rs = null;
		boolean rst = false;
	
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m_id);
			
			rs = pstmt.executeQuery();
		
			while(rs.next()) {
				String id = rs.getString("m_id");
				String pwd = rs.getString("m_pwd");
						
				if(pwd.equals(m_pwd)) {
					rst = true;	
				}else {
					rst = false;		
				}
			}
			
		rs.close();	
		
		return rst;
		
		}catch( Exception ex ) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	public void insertMember(String m_id, String m_name, String m_age, String m_pwd, String r_pwd, String m_phone, String s_id) {
		String sql="insert into t_member(m_id, m_name, m_age, m_pwd, r_pwd, m_phone, s_id) " + 
					"values (?,?,?,?,?,?,?)";
	
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
	
			pstmt.setString(1, m_id);
			pstmt.setString(2, m_name);
			pstmt.setString(3, m_age); 
			pstmt.setString(4, m_pwd);
			pstmt.setString(5, r_pwd);
			pstmt.setString(6, m_phone);
			pstmt.setString(7, s_id);
		
			pstmt.executeUpdate();
		
			System.out.println("insert 완료");
			
		}catch( Exception ex ) {
			System.out.println("insert 실패 : "+ex.getMessage());
		}
	}

}

	 