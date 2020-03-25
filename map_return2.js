var mar = null; // marker 객체 배열
var con = []; // html context 객체 배열
var ovrck= null; // overlay 객체 on-off 체크 배열
var ovr= null; // overlay 객체 배열
var cnt=0;
var map= null;
var markerPosition  = null;
var marker =null;
var lt =0;
var ln =0;
var mi = 'min'; // m_id 나중에 session 넘기기 
//String m_id = session.getParameter("m_id");
var ck = 1;




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

function rcvLatLngData(data){
	lat_avg=0
	lng_avg=0
	console.log("뭐임")
	$.each(data,function(i,v){
		console.log("why")
		lat_avg += v.lat
		lng_avg += v.lon
		cnt++
		console.log(v.lat+","+v.lon);
		context = '<div class="wrap">' + 
        '    <div class="info">' + 
        '        <div class="title">' 
        			+ v.s_name + 
        '        </div>' + 
        '        <div class="body">' + 
        '            <div class="img">'
        if(v.rest<3){ // stock 말고 남은거 마이너스?
        	context+= '<h1 class="red">'+v.rest + '</h1></div>'
        }else if(v.rest<10){
        	context+= '<h1 class="yellow">'+v.rest + '</h1></div>'
        }else{
        	context+= '<h1 class="green">'+v.rest+ '</h1></div>'
        }
        
        context+=
        '            <div class="desc">' + 
        '                <div class="ellipsis">위치: '+v.s_addr+'</div>' +
        '                <div class="jibun ellipsis">총 거치대 수: '+v.rest+'</div>' + 
        '                <div><button type="button" class="btnsend" data-rest = "'+v.rest+'" data-st="'+v.s_name+'" data-si="'+v.s_id+'" >이 대여소에서 따릉이 반납하기</button></form></div>' + 
        '            </div>' + 
        '        </div>' + 
        '    </div>' +    
        '</div>';
		con.push(context)
		
	}) 
	
	lat_avg /=cnt
	lng_avg /=cnt
	if(ck){
		drawMap(lat_avg, lng_avg)
	}
	//gps(lat_avg,lng_avg)
	/* if (navigator.geolocation) {
    // GeoLocation을 이용해서 접속 위치를 얻어옵니다
		navigator.geolocation.getCurrentPosition(function(position) {
			
			var lt = position.coords.latitude, // 위도
				ln = position.coords.longitude; // 경도
					
			drawMap(lt,ln);
			console.log("gps로 얻었음")
		})
    
	} else { // gps 받지 못하면 위도 경도 평균값으로 
    	drawMap(lat_avg, lng_avg)
	} */
	
	console.log("뭐2임")
	mar = new Array(cnt)
	$.each(data, function(i,v){
		markerPosition  = new kakao.maps.LatLng(v.lat, v.lon);
		console.log(v.lat+">>"+v.lon)
		marker = new kakao.maps.Marker({
			map:map,
			position: markerPosition
		});
		console.log("맵"+map)
		console.log("마커 "+marker)
		mar[i]=marker
		console.log(mar);
	})
	
	ovr = new Array(cnt);
	ovrck = new Array(cnt);
	console.log(cnt+"?")
	for(var i=0; i<cnt; i++){
		ovrck[i] = -1;
		console.log(mar[i].getPosition())
		mar[i].setMap(map);
	}
	//gps()
	checkOverlay()
}

/* 첫 지도 그리기, id 확인 */
$(function(){
	if (navigator.geolocation) {
		// GeoLocation을 이용해서 접속 위치를 얻어옵니다
			navigator.geolocation.getCurrentPosition(function(position) {
				ck = 0
				lt = position.coords.latitude, // 위도
				ln = position.coords.longitude; // 경도
						
				
				drawMap(lt,ln)
				console.log("gps로 얻었음")
			})
		
		} else { // gps 받지 못하면 위도 경도 평균값으로 
			//drawMap(0,0)
	}
	$.getJSON('rstst2', rcvLatLngData )
	//setCenter()
	overlay=new kakao.maps.CustomOverlay({
			content: null,
			map: map,
			position: null      
	});
	console.log(mi)

})


function setCenter(a,b) {            
    // 이동할 위도 경도 위치를 생성합니다 
    var moveLatLon = new kakao.maps.LatLng(a,b);
    
    // 지도 중심을 이동 시킵니다
    map.setCenter(moveLatLon);
}
function gps(){
	if (navigator.geolocation) {
		// GeoLocation을 이용해서 접속 위치를 얻어옵니다
			navigator.geolocation.getCurrentPosition(function(position) {
				
				var lt = position.coords.latitude, // 위도
					ln = position.coords.longitude; // 경도
						
				
				setCenter(lt,ln)
				console.log("gps로 얻었음")
			})
		
		} else { // gps 받지 못하면 위도 경도 평균값으로 
	}
}

/* 이 대여소에서 반납하기 버튼 */
$(document).on('click',".btnsend",function(){
	console.log("이 대여소에서 반납하기 버튼누름")
	var si = $(this).attr('data-si');
	var st = $(this).attr('data-st');
	var rest = $(this).attr('data-rest');

	console.log(si+": "+rest)
	$("#bicycle").empty()
	$("#bicycle").append('<h3>'+st+'</h3><p>반납 가능 거치대 수</p>')
			
	if(rest<1){
		$("#bicycle").append('<p>반납 가능한 거치대가 없습니다.</p><p>다른 대여소를 이용해 주세요.</p>')
	}else{
		$("#bicycle").append('<h3>'+st+'</h3><p>반납 가능 거치대 수</p><p>'+rest+'</p>')
		$("#ticket").append('<p>대여 내역</p>')
		$.ajax({
			url: "returnCheckInfo", // mi이면서 r_use = 2 && rs_id = 0 (사용중 미반납)인 내역 한개 받아 옴.
			type: "POST",
			data : {'mi':mi} ,
			datatype: "JSON",
			success : function (data) {
				alert(data+"getRental");
				if(data == null){
					alert("반납 가능한 내역이 없습니다. 대여 페이지로 이동합니다.")
					location.href = 'map'
				}else{
					$("#ticket").empty()
					$("#ticket").append('<p>미반납 내역</p>')
					$.each(JSON.parse(data), function(i,v){
						$("#ticket").attr('data-ri',v.r_id)
						$("#ticket").append('<p>'+v.m_id+'님의 미반납 내역입니다.</p>')
						$("#ticket").append('<p>대여한 대여소: '+v.s_id+'</p>')
						$("#ticket").append('<p>대여한 자전거: '+v.b_id+'</p>')
						$("#ticket").append('<p>대여 시간: '+v.rental_time+'</p>')
						$("#ticket").append('<button type="button" id="return" data-ri="'+v.r_id+'" data-si="'+si+'" data-bi="'+v.b_id+'" data-ti="'+v.t_id+'" >반납하기</button>')
					})
				}
			}
		})
	}
})

/* 반납 버튼  */
$(document).on('click',"#return",function(){
	console.log("반납 버튼누름")
	var ri = $(this).attr('data-ri');
	var ti = $(this).attr('data-ti');
	var si = $(this).attr('data-si');
	var bi = $(this).attr('data-bi');

	$.ajax({
		url: "insertReturnInfo", // 반납하기로 use_ticket에는 rs_id return time 추가, station의 stock 추가, bicycle s_id 변경
		type: "POST",
		data : {'ri':ri,'mi':mi,'ti':ti,'si':si,'bi':bi} ,
		datatype: "JSON",
		success : function (data) {
			alert(data+"insertRental 반납 업데이트");
			$.each(JSON.parse(data), function(i,v){
				$("#ticket").attr("use_time", v.use_time)
				if(v.use_time>60){
					insertpay(v.use_time)
				}
				$("#ticket").append("예약번호: "+v.r_id+"반납시간: "+v.return_time)
				
			})
		}
	})
})

function insertpay(use_time){
	var ri = $("#return").attr('data-ri');
	var ti = $("#return").attr('data-ti');
	var use_time = $("#ticket").attr('use_time');
	$.ajax({
		url: "insertPaymentInfo", // use_time>60이면 추가 납입 행 추가
		type: "POST",
		data : {'ri':ri,'mi':mi,'ti':ti,'use_time':use_time} ,
		datatype: "JSON",
		success : function (data) {
			alert(data+"payRental 결제 업데이트");
			
		}
	})
}

/* 관리자 체크 버튼 */


$(document).on('click',"#bt_t_bicycle_check",function(){
	console.log("자전거 체크 버튼누름")

	$.ajax({
		url: "selectBicycleInfo", // 대여하기 누르는 순간에 다시 한번 업데이트
		type: "POST",
		data : {} ,
		datatype: "JSON",
		success : function (data) {
			alert("자전거 확인");
			if(data == null){
				alert("자전거 없음")
			}else{
				$.each(JSON.parse(data), function(i,v){
				    $("#ck_cond").append('<tr><td>'+v.b_id+'</td><td>'+v.cond_info+'</td></tr>')
				})
			}
		}
	})
})

$(document).on('click',"#bt_t_bicycle_check",function(){
	console.log("자전거 체크 버튼누름")

	$.ajax({
		url: "selectBicycleInfo", // 대여하기 누르는 순간에 다시 한번 업데이트
		type: "POST",
		data : {} ,
		datatype: "JSON",
		success : function (data) {
			alert("자전거 확인");
			if(data == null){
				alert("자전거 없음")
			}else{
				$.each(JSON.parse(data), function(i,v){
				    $("#ck_cond").append('<tr><td>'+v.b_id+'</td><td>'+v.cond_info+'</td></tr>')
				})
			}
		}
	})
})

//////////
$(document).on('click',"#star",function(){
	console.log("즐겨찾는 대여소 이동 버튼누름")

	$.ajax({
		url: "moveInfo",
		type: "POST",
		data : {"m_id":mi} ,
		datatype: "JSON",
		success : function (data) {
			alert("즐겨찾는 대여소 이동");
			if(data == null){
				alert("즐겨찾는 내역 없음")
			}else{
				$.each(JSON.parse(data), function(i,v){
					setCenter(v.latitude,v.longitude)
				})
                
			}
		}
	})
})


$(document).on('click',"#bt_t_station_check",function(){
	console.log("자전거 재고 버튼누름")

	$.ajax({
		url: "selectStationInfo",
		type: "POST",
		data : {} ,
		datatype: "JSON",
		success : function (data) {
			alert("재고 확인");
			if(data == null){
				alert("s_id 내역 없음")
			}else{
				$.each(JSON.parse(data), function(i,v){
				    $("#ck_stock").append('<tr><td>'+v.s_id+'</td><td>'+v.stock+'</td></tr>')
				})
                
			}
		}
	})
})



