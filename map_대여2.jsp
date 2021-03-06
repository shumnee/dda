<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("utf-8"); %>
 
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<link href="<c:url value="/resources/css/map.css" />" rel="stylesheet">
	<title>Insert title here</title>
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=77c6f85103f6e8b3fdb59c9e13612db1"></script>
	<script src="<c:url value="/resources/js/map.js?223" />"></script>
 	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
<body>
	<div class="grid-container">
		<div class="grid-item" id="map" style="width:50%;height:450px;"></div>
		<div class="grid-item" id="map1" style="border:solid;width:50%;height:450px;">
			<div id="star"><button id="bt_star">즐겨찾는 대여소로 이동</button></div>
		
			<%-- <%@ include file="selectedstview.jsp" %> --%>
			<div id="bicycle">
				<h3>대여소</h3>
				<p>대여 가능 자전거</p>
				<select id="bc"></select>
			</div>
			<div id="ticket"></div>
			<div id="div_rental"><button id="rental">대여하기</button></div>
			<div id="ck_r_id"><button id="bt_t_use_check">예약내역 체크하기</button></div>
			<div id="ck_cond"><button id="bt_t_bicycle_check">자전거 체크하기</button></div>
			<div id="ck_stock"><button id="bt_t_station_check">자전거 재고 체크하기</button></div>
			
		</div>
		
		<%-- <p>${s_id }</p> --%>
	</div>
	<script>

	
 	</script>
</body>
