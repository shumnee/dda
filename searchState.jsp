<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>즐겨찾는 대여소 검색</title>
	<script src=https://code.jquery.com/jquery-3.4.1.min.js></script>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
	<h3>즐겨찾는 대여소 검색하기</h3><hr>
	<form class="form-inline">
		<input type="text" placeholder="검색어를 입력해주세요 (구이름)" id="keyword" name="keyword" style="width:30%">
		<button id="search" type="button">검색</button>
	</form><br>
	
	<table id="mytable" class="table">
		<thead>
			<tr>
				<th>대여소 이름</th>
				<th>주소</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	
 </div>	
	<script>
	$(function(){
		//키워드 검색
		$("#search").on("click", function(e){
			keyword=$("#keyword").val()
		        
			$.ajax({ 
	        	url: "searchPro", 
	        	data: {"keyword":keyword}, 
	        	method: "GET", 
	        	dataType: "json" 
	        	}).done(function(data) {
		        	$('tbody > tr').remove();
		        	
		        	$.each(data, function(i,v){
		        		$("#mytable > tbody").append("<tr><td>"+v.s_name+"</td><td>"+v.s_addr+"</td></tr>")
		        	})
				})
			})	
				
			//대여소 선택
			$("tbody").on("click","tr",function() {
				var tr = $(this).children()   
				var addr = tr.eq(1).text()
				console.log(tr.eq(0).text()+"("+tr.eq(1).text()+")")
//				var theURL = "searchState?state="+; // 전송 URL
//				opener.window.location = theURL;
				//window.opener.setChildValue(tr.eq(0).text());
				$(opener.location).attr("href","javascript:setChildValue('"+addr+"');");
				window.close();
			})			
		})
	</script>
</body>
</html>