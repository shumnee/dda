<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("utf-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</head>
<body>
<div id="ck_r_id"><button id="bt_t_use_check">예약내역 체크하기</button><a>
<table>
<thead><th>예약ID</th><th>예약회원ID</th><th>이용권ID</th><th>대여(예약)대여소ID</th><th>자전거ID</th><th>이용상태</th><th>대여시작시간</th><th>반납대여소ID</th><th>반납시간</th><th>보험여부(1-신청)</th><th>이용시간</th></thead>
<tbody id="t_use"></tbody>
</table>

</a></div>
<div id="ck_cond"><button id="bt_t_bicycle_check">자전거 체크하기</button>
<p>자전거 상태: 0 - 대여/예약 중, 1 - 대여 가능</p>
<table>
<thead><th>자전거ID</th><th>현재 위치 대여소ID</th><th>자전거 상태</th></thead>
<tbody id="t_cond"></tbody>
</table>

</div>
<div id="ck_stock"><button id="bt_t_station_check">자전거 재고 체크하기</button>
<table>
<thead><th>대여소ID</th><th>대여소명</th><th>자전거 재고</th></thead>
<tbody id="t_stock"></tbody>
</table>

</div>
			
</body>
<script>
/* 관리자 체크 버튼 */


$(document).on('click',"#bt_t_bicycle_check",function(){
	console.log("자전거 체크 버튼누름")

	$.ajax({
		url: "selectBicycleInfo", // 대여하기 누르는 순간에 다시 한번 업데이트
		type: "POST",
		data : {} ,
		datatype: "JSON",
		success : function (data) {
			alert("자전거 확인");
			if(data == null){
				alert("자전거 없음")
			}else{
				$.each(JSON.parse(data), function(i,v){
				    $("#ck_cond").append('<tr><td>'+v.b_id+'</td><td>'+v.s_id+'</td><td>'+v.cond_info+'</td></tr>')
				})
			}
		}
	})
})


$(document).on('click',"#bt_t_use_check",function(){
	console.log("예약내역 체크 버튼누름")

	$.ajax({
		url: "selectUseTicketInfo", // 대여하기 누르는 순간에 다시 한번 end_yn 업데이트
		type: "POST",
		data : {} ,
		datatype: "JSON",
		success : function (data) {
			alert("예약 완료 확인");
			if(data == null){
				alert("r_id 내역 없음")
			}else{
				$.each(JSON.parse(data), function(i,v){
				    $("#ck_r_id").append('<tr><td>'+v.r_id+'</td><td>'+v.m_id+'</td><td>'+v.t_id+'</td><td>'+v.s_id+'</td><td>'+v.b_id+'</td><td>'+v.r_use+'</td><td>'+v.rental_time+'</td><td>'+v.rs_id+'</td><td>'+v.return_time+'</td><td>'+v.insurance_yn+'</td><td>'+v.use_time+'</td></tr>')
				})
                
			}
		}
	})
})


$(document).on('click',"#bt_t_station_check",function(){
	console.log("자전거 재고 버튼누름")

	$.ajax({
		url: "selectStationInfo", // 대여하기 누르는 순간에 다시 한번 업데이트
		type: "POST",
		data : {} ,
		datatype: "JSON",
		success : function (data) {
			alert("재고 확인");
			if(data == null){
				alert("s_id 내역 없음")
			}else{
				$.each(JSON.parse(data), function(i,v){
				    $("#ck_stock").append('<tr><td>'+v.s_id+'</td><td>'+v.s_name+'</td><td>'+v.stock+'</td></tr>')
				})
                
			}
		}
	})
})


</script>
</html>
