<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  

<title>Insert title here</title>
<style>
#box1 {
/* float : left; */
height: auto; width: 25%;
text-align : center;
border:2px solid black;
}
#box2 {
/* float : left; */
height: auto; width: 25%;
text-align:center;
border:2px solid black; padding 10px;
}
</style>
</head>
<body>
<script>
var m_id = '0'

</script>
<h1>이용권구매</h1><br>

<div id = "box1" style = "padding: 5px 1px 2px 3px;">
	<img src="resources/img/ticket.png" width="50" height = "50">
	<h2>정기권,일일권 구매</h2>
<div>일일권 :1시간, 2시간<br> 정기권 : 7일권, 30일권, 180일권, 365일권</div><br>
	이용권 선택: <select id='ticket' name='ticket'></select>
<script>
	$.getJSON('rstTicket',fn)
		function fn(data){
		$.each(data,function(i,v){
			$("#ticket").append('<option id='+v.type_id+' data-ty="'+v.type_id+'" data-d="'+v.t_day+'" data-tp="'+v.t_price+'" data-ip="'+v.i_price+'">'+v.ticket_type+'</option>')
			}) 
		}
</script>
<br>
			
	보험여부 선택: 
	<input type="radio" name="ticket" checked="checked" value="0"/> <span class="up">선택안함</span>
	&nbsp;&nbsp;
	<input type="radio" name="ticket" value="1"/> <span class="up">보험선택</span>
	<br><br>
	<button class="btn btn-danger" id="sum" clicked = "0">확인</button>
</div>
		<!--체크 됐는지 안됐는지 보고 insert 행에 insurance_yn 유무 ...  -->
<script>
	$(document).on('click',"#sum",function(){
		console.log("이용권, 보험 선택 후 확인 누름")
		var tp =0;
		var ip =0;
		$(this).attr("clicked",1);
		var insu = $('input[name=ticket]:checked').val()
		
		$("#pay").attr("insurance",insu);
		if(insu ==1){
			
			tp = $("#ticket option:selected").attr('data-tp');
			ip = $("#ticket option:selected").attr('data-ip');
			console.log(tp)
			console.log(ip)
		}else{
			tp = $("#ticket option:selected").attr('data-tp');
			ip = 0;
			console.log(tp)
			console.log(ip)
		}
			
			
		var sum = Number(tp)+Number(ip);
		console.log(sum)
		$("#pay").attr("money",sum)
		$("#price").empty()
		
		$("#price").append("<p><h4>결제 내역</h4><br>이용권 가격: "+Number(tp)+"원 <br>보험금 가격: "+Number(ip)+" 원<br>총 결제 금액: "+sum+" 원</p>")
	})
		
</script>
		
<div id = "box2" style = "padding: 5px 1px 2px 3px;">
	<h2>결제</h2>
	<div><img src="resources/img/bill.png" width="50" height = "50"></div><br>
	<div id="price">결제금액 : </div>
	<button id="pay" type = "button" class = "btn btn-primary">결제하기</button>
</div>
		
<script>
	$(document).on('click',"#pay",function(){
	console.log("가격 확인 후 결제 누름")
	if($("#sum").attr('clicked')!=1){
		alert("상품 선택 후 확인을 눌러주십시오")
	}else{
		var money = $(this).attr("money");
		var t_day =	$("#ticket option:selected").attr('data-d')
		var type_id = $("#ticket option:selected").attr('data-ty')
		var insurance_yn = $(this).attr("insurance");
		
		$.ajax({
			url: "insertBuyTicketInfo", // 구매 
			type: "POST",
			data : {"m_id":m_id,"t_day":t_day,"type_id":type_id,"insurance_yn":insurance_yn,"money":money} ,
			datatype: "JSON",
			success : function (data) {
				alert("결제 진행");
				if(data == null){
					alert("결제 JSON 오류")
				}else{
					$.each(JSON.parse(data), function(i,v){
						alert("이용권 id: "+v.t_id+",결제 id: "+v.p_id)
						})
					}
				}
			})
		}
			
	})	
</script>
	
</body>
</html>
