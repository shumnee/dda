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
  
  <script>
		function sumAll() {
			$("select #ticket option:selected").val();
		}
	</script>
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

<h1>이용권구매</h1> <br>

		<div id = "box1" style = "padding: 5px 1px 2px 3px;">
		 <img src="resources/image/ticket.png" width="50" height = "50"><h2>정기권,일일권 구매</h2>
		<div>일일권 :1시간, 2시간<br> 정기권 : 7일권,30일권,180일권,365일권</div><br>
			이용권 선택: <select id='ticket' name='ticket'></select>
			  <script>
			  function fn(data){
				  $.each(data,function(i,v){
					  $("#ticket").append('<option id="'+v.t_id+'" value="'+v.t_price+'">'+v.ticket_type+'</option>')
				  })
					 
				  
			  }
			  $.getJSON('rstTicket',fn)
			  
			  </script>
			  <p></p>
			  <!-- <option id='1' value='1000'>일일권 1시간 : 1,000원</option>
			  <option value='2000'>일일권 2시간 : 2,000원</option>
			  <option value='6000'>정기권 7일 : 6,000원</option>
			  <option value='25000'>정기권 30일 : 25,000원</option>
			  <option value='140000'>정기권 180일 : 140,000원</option>
			  <option value='270000'>정기권 365일 : 270,000원</option> -->
			<br>
			
			보험여부 선택: 
		  <input type="radio" name="ticket" checked="checked" /> <span class="up">선택안함</span>
		  &nbsp;&nbsp;
		  <input type="radio"name="ticket"/> <span class="up">보험선택</span>
			<br><br>
		  <input type="submit" class="btn btn-danger" value="확인">
		</div>
		
			
			
		<div id = "box2" style = "padding: 5px 1px 2px 3px;">
			<h2>결제</h2>
		<div>
 			<img src="resources/image/bill.png" width="50" height = "50"></div><br>
			<div id="price">결제금액 : </div>
			<button type = "button"class = "btn btn-primary">결제하기</button>
		</div>
				
	
</body>
</html>