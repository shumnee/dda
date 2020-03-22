<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<script src=https://code.jquery.com/jquery-3.4.1.min.js></script>

</head>
<body>
	<form class="form-group" id="addMemberForm" action="addMemberPro" method="post">
		<h3>회원가입</h3><hr>
		아이디(ID) : <input type="text" id="id" name="id">
	    		   <button type="button" id="idChk" value="N">중복체크</button><br>
		회원 명	  : <input type="text" id="name" name="name"><br>
		나이   	  : <input type="text" id="age" name="age"><br>
		비밀번호	  : <input type="password" id="pwd" name="pwd"><br>
		비밀번호 확인   : <input type="password" id="pwdChk" name="pwdChk">
					<span id="spanPwd" value="N"></span><br>
		대여번호	  : <input type="password" id="r_pwd" name="r_pwd"><br>
		대여번호 확인   : <input type="password" id="r_pwdChk" name="r_pwdChk">
					<span id="spanRPwd" value="N"></span><br>
		전화번호	  : <input type="text" id="phone" name="phone" placeholder="000-0000-0000"><br>
		즐겨찾는 대여소 : <input type="text" id="state" name="state"><br>
		<!-- 팝업창 > 검색 > 사용자가 원하는 주소 클릭  -->
	    <button class="btn btn-success" id="enroll" type="submit" onSubmit="return false;">회원가입</button>
	    <button class="cancle btn btn-danger" type="button" onclick="javascript: form.action='index';">취소</button>  
	</form><br>
  	    
  
	<script>
		$(function() {
			$("#enroll").on("click", function(){
				var idChkVal = $("#idChk").val();
				var spanPwd = $("#spanPwd").text();
				var spanRPwd = $("#spanRPwd").text();
				if(idChkVal == "N" ){
					alert("중복확인 버튼을 눌러주세요.");
					return false
				}else if(spanPwd != " 확인" ){
					alert("비밀번호 확인");
					return false
				}else if(spanRPwd != " 확인" ){
					alert("대여 비밀번호 확인");
					return false
				}
				return true
			});
			
			//아이디 중복 체크
			$("#idChk").on('click',function() {
		        
		        var userid =  $("#id").val(); 
		        
		        if(userid == ""){
					alert("아이디 입력 부탁드립니다.");
					$("#idChk").attr("value","N");
					return
        		}
		        
		        $.getJSON('dulCheck', {'id':userid}, function(data){
		        	console.log("0 = 중복o / 1 = 중복x : "+ data);								
		        	$.each(data, function(i,v){
						if (v.rst =="0") {
							alert("중복된 아이디입니다. :p");
							$("#idChk").attr("value","N");
						}else {
							alert("사용가능한 아이디입니다.")
							$("#idChk").attr("value","Y");
						}
					})
				})
			})
			
			//비밀번호 확인
			$("#pwdChk").on('blur',function() {
				var pwd = $("#pwd").val();
				var pwdChk = $("#pwdChk").val();
				
				if(pwd!=null && pwd != pwdChk){
					$("#spanPwd").text(" 입력한 비밀번호가 일치하지 않습니다.");
				}else{
					$("#spanPwd").text(" 확인");
				}
			})

			//대여 비밀번호 확인
			$("#r_pwdChk").on('blur',function() {
				var pwd = $("#r_pwd").val();
				var pwdChk = $("#r_pwdChk").val();
				
				if(pwd!=null && pwd != pwdChk){
					$("#spanRPwd").text(" 입력한 비밀번호가 일치하지 않습니다.");
				}else{
					$("#spanRPwd").text(" 확인");
				}
			})
			
			//대여소 조회 팝업
			$("#state").on('click', function() {				
                var newWindow = window.open("searchState", 'TEST', 'height=700,width=1000,top=0,left=0, fullscreen=yes');
                newWindow.focus();
			})
			
		})
	
		//주소값 가져오기
		function setChildValue(data){
			$("#state").val(data);
		}
		
	</script>
</body>
</html>