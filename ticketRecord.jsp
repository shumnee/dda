<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>이용권 사용 내역 조회</title>
	<script src=https://code.jquery.com/jquery-3.4.1.min.js></script>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<h3>이용권 사용 내역</h3><hr><br>
		<table id="mytable" class="table">
			<thead>
				<tr>
					<!-- 이용권명, 구매일자 컬럼 추가 필요 -->
					<th>이용권id</th><th>구매일자</th><th>사용일자</th><th>만료일자</th><th>사용여부</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
  
	<script>
		$(function() {
			//이용권 구매/사용 내역 조회
			$.getJSON('ticketPro', function(data){
	        	$.each(data, function(i,v){
	        		$("#mytable > tbody").append("<tr><td>"+v.t_id+"</td><td>"+v.buy_date+"</td><td>"+v.s_date+"</td><td>"+v.e_date+"</td><td>"+insertButton(v.end_yn)+"</td></tr>")
	        	})
			})
			
		})

		function insertButton(yn){
			if(yn == '1'){
				return "<button>미사용</button>"
			}else{
				return "<button>사용</button>"
			}
		}
		
	</script>
</body>
</html>