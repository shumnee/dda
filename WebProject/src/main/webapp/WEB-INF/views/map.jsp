<!-- 닫기 -->


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<!--    	<script src="map.js"></script>
 -->	
<body>
	<div id="map" style="width:100%;height:350px;"></div>

<script>
var mar = null; // marker 객체 배열
var con = []; // html context 객체 배열
var ovrck= null; // overlay 객체 on-off 체크 배열
var ovr= null; // overlay 객체 배열
var cnt=0;
var map= null;
var markerPosition  = null;
var marker =null;


function drawMap(lat, lng){
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
  		mapOption = { 
        	center: new kakao.maps.LatLng(lat, lng), // 지도의 중심좌표
        	level: 5 // 지도의 확대 레벨
   		};
	map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
}
function drawMarker(lat, lng)
{
		var markerPosition  = new kakao.maps.LatLng(lat, lng); 
		var marker = new kakao.maps.Marker({
		position: markerPosition
		});
		console.log(marker.getPosition());
		mar.push(marker);
		
		
}
function rcvLatLngData(data){
	lat_avg=0
	lng_avg=0
	
	$.each(data,function(i,v){
		lat_avg += v.lat
		lng_avg += v.lon
		cnt++
		console.log(v.lat+","+v.lon);
		context = '<div class="wrap">' + 
        '    <div class="info">' + 
        '        <div class="title">' 
        			+ v.name + 
        '        </div>' + 
        '        <div class="body">' + 
        '            <div class="img">'
        if(v.num<3){
        	context+= '<h1 class="red">'+v.num + '</h1></div>'
        }else if(v.num<10){
        	context+= '<h1 class="yellow">'+v.num + '</h1></div>'
        }else{
        	context+= '<h1 class="green">'+v.num + '</h1></div>'
        }
        
        context+=
        '            <div class="desc">' + 
        '                <div class="ellipsis">위치: '+v.addr+'</div>' +
        '                <div class="jibun ellipsis">등록일자: '+v.date+'</div>' + 
        '                <div><a href="http://www.kakaocorp.com/main" target="myinfo" class="link">이 대여소에서 따릉이 찜하기</a></div>' + 
        '            </div>' + 
        '        </div>' + 
        '    </div>' +    
        '</div>';
		con.push(context)
		
	}) 
	
	lat_avg /=cnt
	lng_avg /=cnt
	drawMap(lat_avg, lng_avg)
	mar = new Array(cnt)
	$.each(data, function(i,v){
		markerPosition  = new kakao.maps.LatLng(v.lat, v.lon);
		console.log(v.lat+">>"+v.lon)
		marker = new kakao.maps.Marker({
			map:map,
			position: markerPosition
		});
		console.log(marker)
		mar[i]=marker
		console.log(mar);
	})
	
	ovr = new Array(cnt);
	ovrck = new Array(cnt);
	console.log(cnt)
	for(var i=0; i<cnt; i++){
		ovrck[i] = -1;
		console.log(mar[i].getPosition())
		mar[i].setMap(map);
	}
	
	checkOverlay()
}
$(function(){
	$.getJSON('rstTest', rcvLatLngData )
	/*
	drawMap(33.450701, 126.570667)
	drawMarker(33.450701, 126.570667)
	 */
	 overlay=new kakao.maps.CustomOverlay({
			content: null,
			map: map,
			position: null      
	});
})

function checkOverlay(){
	mar.forEach(function(element, index){
		kakao.maps.event.addListener(element, 'click', function() {
			console.log(element)
			console.log(index+"번 마커 객체 위치: "+element.getPosition())
			
			if(ovrck[index]==-1){
				console.log(index+"번 오버레이 첫 생성")
				ovr[index]=new kakao.maps.CustomOverlay({
					content: con[index],
					map: map,
					position: element.getPosition()       
				});
				ovr[index].setMap(map);
				ovrck[index] = 1
			}else if(ovrck[index]==0){
				console.log(index+"번 오버레이 on")
				ovr[index].setMap(map);
				ovrck[index] = 1
			}else{
				console.log(index+"번 오버레이 off")
				ovr[index].setMap(null);
				ovrck[index] = 0
			}

		});
	})
}
</script>
</body>