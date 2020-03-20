<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="<c:url value="/resources/js/chart.js" />" type="text/javascript" charset="UTF-8"></script>
	</head>
  		
    <body>
    	
  	<div id="chartframe"></div>

    <button class="btn btn-default" onclick="btGu()">대여소 현황</button>
    <button class="btn btn-default" onclick="btNum()">자전거 현황</button>
    <button class="btn btn-default" onclick="btAge()">신규 가입자 현황</button>
  </body>
</html>
