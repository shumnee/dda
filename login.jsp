<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<style type="text/css">
		a:link { color: red; text-decoration: none;}
		a:visited { color: black; text-decoration: none;}
		a:hover { color: blue; text-decoration: underline;}
	</style>
</head>
<body>
	<form method="get" id="loginForm">
		<h3>로그인</h3><hr>
	    <input type="text" placeholder="아이디 입력" id="userId" name="userId"><br>
	    <input type="text" placeholder="비밀번호 입력" id="userPwd" name="userPwd"><br>
	    <button class="btn btn-default" onclick="javascript: form.action='loginPost';">로그인</button>
	    <button class="btn btn-default" onclick="javascript: form.action='addMember';">회원가입</button>
	</form>
</body>
</html>