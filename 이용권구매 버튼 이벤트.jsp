<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="buy"><button id="btnbuy" type="button">이용권 구매</button></div>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

<script>
var m_id = '112';
//var m_id = 'min';
$(document).on('click',"#btnbuy",function(){
	console.log("이용권 구매 가능 체크 시작")

	$.ajax({
		url: "checkBuyTicketInfo", // 대여하기 누르는 순간에 다시 한번 업데이트
		type: "POST",
		data : {"m_id":m_id} ,
		datatype: "JSON",
		success : function (data) {
			if(data == null){
				alert("arr JSON m_id 정보 없음")
			}else{
				alert("end_yn 내역 조회");
				$.each(JSON.parse(data), function(i,v){
				    if(v.cnt_end){
				    	alert("만료되지 않은 이용권이 존재합니다. 대여 서비스를 이용해주십시오.")
				    	location.href = 'map'
				    }else{
				    	alert("end_yn = 1 존재하지 않음, 이용권 구매 페이지로 이동합니다.")
				    	location.href = 'buyticketview'
				    }
				})
			}
		}
	})
})
</script>
</body>
</html>
