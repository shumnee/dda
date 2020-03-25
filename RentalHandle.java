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
public class RentalHandle {
	@Autowired
	DataSource dataSource;

	Connection conn = null;
	PreparedStatement pstmt = null;
	
	public String checkRental(String mi, String si, String bi) {
		// 대여내역(t_use_ticket)에 [예약id(r_id),m_id,t_id,s_id,b_id,사용 여부 0] 추가 ->insertRental

		JSONArray arr = new JSONArray();
		ResultSet rs = null;
		// 1) 대여소 조회는 로그인된 회원만 가능. m_id 전달 받음
		// # 활성화된 이용권 있으면 더 구매 못하므로(그렇게 만들예정) end_yn =1 인 행은 하나뿐임.
		// 2) 대여소 정보보기 선택 후 자전거 선택
		// 3) 대여하기 클릭되면 회원이 구매한 이용권 내역 중 
		//	   현재 시간이 만료시간 보다 이후이면 end_yn == 0(finish) 해줌
		// 4) 유효한 기간 내에 있는 end_yn == 1(active) 인것 찾음
		
		System.out.println("checkRental - ticketCheckInfo 실행");

		String check_e_date = "update t_buy_ticket set end_yn = 0 where sysdate > e_date";
		String send_sql="select * from t_buy_ticket, t_bicycle where t_buy_ticket.m_id='"+mi+"' and t_buy_ticket.end_yn = 1 and t_bicycle.cond_info=1 and t_bicycle.b_id ='"+bi+"' and t_bicycle.s_id ='"+si+"'";

		// id 
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(check_e_date);
			pstmt.executeQuery();
			// 모든 e_date 확인 후 end_yn 업데이트
			
			pstmt = conn.prepareStatement(send_sql); //end_yn 업데이트 됐는데도 1인 것들 중 선택한 자전거가 아직 대여되지 않았는지 확인
			rs = pstmt.executeQuery();
			while(rs.next()) {
				// SimpleDateFormat f = new SimpleDateFormat("yyyy-mm-dd HH:mm", Locale.KOREA);
				// e_date not null 중에 현재시간 > e_date 이면 end_yn update
				
				JSONObject o = new JSONObject();
				
				String s_id = rs.getString("s_id");
				String t_id = rs.getString("t_id");
				String b_id = rs.getString("b_id");
				String type_id = rs.getString("type_id");
				
				o.put("mi",mi); //para
				o.put("si",s_id);
				o.put("ti",t_id);
				o.put("bi",b_id); 
				o.put("type_id",type_id); 

				arr.add(o);
				System.out.println("예약 라인 추가 시 필요 데이터"+arr);
				//pstmt = conn.prepareStatement(set_date); // s_date null이면 사용 시작으로 바뀜(s_date, e_date set)
				//pstmt.executeQuery();
			}
			rs.close();
			return arr.toJSONString();
		}catch( Exception ex ) {
			return null;
		}
	}
	
	
	public String insertRental(String mi, String ti, String si, String bi) {
		// 대여내역(t_use_ticket)에 [예약id(r_id),m_id,t_id,s_id,b_id,사용 여부 0] 추가 ->insertRental
		System.out.println("insertRental - insertRentalInfo 실행");

		JSONArray arr = new JSONArray();
		int tk_cnt =0;
		ResultSet rs = null;
		// 1) 대여소 조회는 로그인된 회원만 가능. m_id 전달 받음
		// # 활성화된 이용권 있으면 더 구매 못하므로(그렇게 만들예정) end_yn =1 인 행은 하나뿐임.
		// 2) 대여소 정보보기 선택 후 자전거 선택
		// 3) 대여하기 클릭되면 회원이 구매한 이용권 내역 중 
		//	   현재 시간이 만료시간 보다 이후이면 end_yn == 0(finish) 해줌
		// 4) 유효한 기간 내에 있는 end_yn == 1(active) 인것 찾음
		
		
		String send_sql="select * from t_buy_ticket, t_bicycle where t_buy_ticket.m_id='"+mi+"' and t_buy_ticket.end_yn = 1 and t_bicycle.cond_info=1 and t_bicycle.b_id ='"+bi+"' and t_bicycle.s_id ='"+si+"'";
		String sqlcnt = "select count(*) from t_use_ticket";
		
		String insert_rental="insert into t_use_ticket(r_id, m_id, t_id,s_id,b_id,r_use,insurance_yn,rs_id) values(?, ?, ?, ?, ?, ?, ?,?)";
		// t_use_ticket에 새 예약 행 삽입
		String update_bi = "update t_bicycle set cond_info = 0 where b_id ='"+bi+"'"; // 자전거 cond_info =0
		// 예약된 자전거 cond_info = 0으로 업데이트
		String update_st = "update t_station set stock = (select count(*) from t_bicycle where t_bicycle.s_id = t_station.s_id and t_bicycle.cond_info = 1)";
		// t_station의 자전거 수 재고 업데이트 (빈 거치대는 증가, s_all -stock)
		// id 
		try {
			conn = dataSource.getConnection();
			
			pstmt = conn.prepareStatement(sqlcnt); //새 예약 행 id 생성
			rs = pstmt.executeQuery();
			rs.next();
			tk_cnt = rs.getInt("count(*)")+1;
			System.out.println(tk_cnt + "새 예약행 번호");
			
			pstmt = conn.prepareStatement(send_sql); //end_yn 업데이트 됐는데도 1인 것들 중 선택한 자전거가 아직 대여되지 않았는지 두번째 확인
			rs = pstmt.executeQuery();
			
			// 조건에 맞지 않는 상황이면 어차피 rs.next 없어서 arr에 null 들어감? 그리고 애초에 대여소 조회 못함
			while(rs.next()) {

				pstmt = conn.prepareStatement(insert_rental);//대여 예약 처리
				JSONObject o = new JSONObject();
				
				String r_id = "rent"+String.valueOf(tk_cnt);
				String t_id = rs.getString("t_id");
				int inyn = rs.getInt("insurance_yn");
				pstmt.setString(1, r_id);
				pstmt.setString(2, mi);
				pstmt.setString(3, t_id);
				pstmt.setString(4, si);
				pstmt.setString(5, bi);
				pstmt.setInt(6, 1);
				pstmt.setInt(7, inyn);
				pstmt.setString(8, "0");
				pstmt.executeQuery();
				o.put("r_id",r_id);
				o.put("mi",mi); //para
				o.put("ti",t_id);
				o.put("si",si); //para
				o.put("bi",bi); //para
				arr.add(o);
				System.out.println("예약 라인 추가 완료?"+arr);		
				
				pstmt = conn.prepareStatement(update_bi); // 대여한 자전거 cond_info =0 처리
				pstmt.executeQuery();
				pstmt = conn.prepareStatement(update_st); // 대여소 자전거 수 감소 처리
				pstmt.executeQuery();
				
			}
			rs.close();
			return arr.toJSONString();
		}catch( Exception ex ) {
			return null;
		}
	}
	
	
	public String checkReserve(String mi) {
		// 찜하기 눌렀을 때 이미 예약 중인 내역이 있는 지 확인 r_use 상태 1(사용중)이면 예약불가, 0(완료)와 2(취소-환불)이면 가능
		System.out.println("checkReserve - checkReserveInfo 실행");

		JSONArray arr = new JSONArray();
		ResultSet rs = null;
		
		
		try {
			
			conn = dataSource.getConnection();

			// end_yn 접근 전 update end_yn
			String check_e_date = "update t_buy_ticket set end_yn = 0 where sysdate > e_date";
			pstmt = conn.prepareStatement(check_e_date);
			pstmt.executeQuery();
			
			String check_sql = "select ut.rs_id rs_id, ut.r_use r_use, ut.r_id r_id, bt.t_id t_id, tt.type_id type_id, tt.ticket_type ticket_type from t_use_ticket ut right outer join t_buy_ticket bt on ut.m_id = bt.m_id join t_ticket tt on bt.type_id = tt.type_id where bt.end_yn=1 and bt.m_id='" + mi+"'";

			// 유효한 구매권이 없는 회원은 구매 페이지로, 사용중인 내역이 있는 회원은 마이페이지- 대여 내역조회로(반납) 보내기 위해
			// t_use_ticket의 r_use 컬럼을 조회: mi로 예약한 내역 중 이용권이 아직 유효(end_yn=1)한 데이터
			
			pstmt = conn.prepareStatement(check_sql); //end_yn 업데이트 된 후 유효한 구매권있는 회원이라면 - 유효한 구매권으로 사용중인 내역이 있는지
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				JSONObject o = new JSONObject();
				
				String rs_id = rs.getString("rs_id");
				int r_use = rs.getInt("r_use");
				String r_id = rs.getString("r_id");
				String t_id = rs.getString("t_id");
				String type_id = rs.getString("type_id");
				String ticket_type = rs.getString("ticket_type");
				o.put("t_id",t_id);
				o.put("type_id",type_id);
				o.put("ticket_type",ticket_type);
				o.put("r_id",r_id);
				o.put("rs_id",rs_id);
				o.put("r_use",r_use); // js에서 alert 후 페이지 전환(구매 or 반납)
				
				arr.add(o);
				System.out.println("r_use 내역 체크"+arr);		
				
			}
			System.out.println("rs get row size:"+rs.getRow());		
			rs.close();
			return arr.toJSONString();
		}catch( Exception ex ) {
			return null;
		}
	}
}
	
