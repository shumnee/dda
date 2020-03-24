var mar = null; // marker 객체 배열
var con = []; // html context 객체 배열
var ovrck= null; // overlay 객체 on-off 체크 배열
var ovr= null; // overlay 객체 배열
var cnt=0;
var map= null;
var markerPosition  = null;
var marker =null;
var mi = 'pp'; // m_id 나중에 session 넘기기 
//String m_id = session.getParameter("m_id");


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
	
	$.each(data,function(i,v){
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
        if(v.num<3){ // stock 말고 남은거 마이너스?
        	context+= '<h1 class="red">'+v.stock + '</h1></div>'
        }else if(v.num<10){
        	context+= '<h1 class="yellow">'+v.stock + '</h1></div>'
        }else{
        	context+= '<h1 class="green">'+v.stock+ '</h1></div>'
        }
        
        context+=
        '            <div class="desc">' + 
        '                <div class="ellipsis">위치: '+v.s_addr+'</div>' +
        '                <div class="jibun ellipsis">총 거치대 수: '+v.s_all+'</div>' + 
        '                <div><button type="button" class="btnsend" data-st="'+v.s_name+'" data-si="'+v.s_id+'" >이 대여소에서 따릉이 찜하기</button></form></div>' + 
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

/* 첫 지도 그리기, id 확인 */
$(function(){
	
	$.getJSON('rstst', rcvLatLngData )

	overlay=new kakao.maps.CustomOverlay({
			content: null,
			map: map,
			position: null      
	});
	console.log(mi)

})


/* 찜하기 버튼 */
$(document).on('click',".btnsend",function(){
	console.log("찜하기 버튼누름")
	var si = $(this).attr('data-si');
	var st = $(this).attr('data-st');
	console.log(si)
	$.ajax({ 
		url: "selectedInfo",
		type: "POST",
		data : {'s_id':si} ,
		datatype: "JSON",
		success : function (data) {
			$("#bicycle").empty()
			$("#bicycle").append('<h3>'+st+'</h3><p>대여 가능 자전거</p>')

			$("#bicycle").append('<select id="bc"></select>')
			$.each(JSON.parse(data), function(i,v){
				$("#bc").append('<option data-b="'+v.b_id+'" data-s="'+si+'">'+v.b_id+'</option>')
				console.log(v.s_id);
			})
			console.log(data)
		}
	}).then(function() {
			$.ajax({
			url: "ticketInfo",
			type: "POST",
			data : {'m_id':mi} , // id request 필요
			datatype: "JSON",
			success : function (tdata) {
				alert(tdata);
				$.each(JSON.parse(tdata), function(i,v){
					//console.log("왜 갑자기 안붙")
					alert(v.t_id)
					if(tdata == null){
						alert("유효한 이용권이 없습니다.")
						location.href = 'buyticketview'
						
					}
					if((v.r_use ==2 || v.r_use ==3)){ // 2: 사용완료 3: 환불
						alert(v.r_use+"대여 가능")
					}else if(v.r_use==1){ // 1: 미사용
						alert("반납이 완료되지 않은 내역이 존재합니다.")
						location.href = 'userecordview'
						
					}else{
						alert("r_use가 1,2,3이 아니면서 이용권 유효 = 0, 예약가능")
					}
					$("#ticket").append('<p data-type_i="'+v.type_id+'" data-t="'+v.t_id+'">이용권: '+v.ticket_type+'</p>')
				})			
			}
		})        


	})
	
})

/* 대여 버튼  */
$(document).on('click',"#rental",function(){
	console.log("대여 버튼누름")
	$("#rental").attr('data-type_id',$("#ticket").attr('data-type_i'))
	$("#rental").attr('data-ti',$("#ticket").attr('data-t'))
	$("#rental").attr('data-si',$("#bc option:selected").attr('data-s'))
	$("#rental").attr('data-bi',$("#bc option:selected").val())

	var si = $(this).attr('data-si');
	var bi = $(this).attr('data-bi');
	var ti = $(this).attr('data-ti');
	$.ajax({
		url: "ticketCheckInfo", // 대여하기 누르는 순간에 다시 한번 업데이트
		type: "POST",
		data : {'mi':mi,'ti':ti,'si':si,'bi':bi} ,
		datatype: "JSON",
		success : function (data) {
			alert(data+"checkRental");
			if(data == null){
				alert("유효한 이용권이 없습니다. 구매 페이지로 이동합니다.")
				location.href = 'buyticketview'
				//break;
			}
		}
	}).then(function(){
			$.ajax({
			url: "insertRentalInfo",
			type: "POST",
			data : {'mi':mi,'ti':ti,'si':si,'bi':bi} ,
			datatype: "JSON",
			success : function (data) {
				alert(data+"insertRental");
				$.each(JSON.parse(data), function(i,v){
					if(v.ti == null){
						alert("(3. insert 직전) 이용권이 만료되었습니다. 구매 페이지로 이동합니다.")
						location.href = 'buyticketview'
						//break;
					}else if(v.bi == null){
							alert("(3. insert 직전) 다른 사용자에 의해 자전거가 대여되었습니다. 다른 자전거를 선택해주십시오.")
							location.href = 'map'
					}else{
						$("#map1").append('<p>(3. insert 직전) 예약이 완료 되었습니다.</p>')
						alert(data+"예약 행 추가 완료");
						location.href = 'rsuccessview'
					}	
				})		
			}
		})
	})
})


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


$(document).on('click',"#bt_t_use_check",function(){
	console.log("예약내역 체크 버튼누름")

	$.ajax({
		url: "selectUseTicketInfo", // 대여하기 누르는 순간에 다시 한번 end_yn 업데이트
		type: "POST",
		data : {} ,
		datatype: "JSON",
		success : function (data) {
			alert("예약 완료 확인");
			if(data == null){
				alert("r_id 내역 없음")
			}else{
				$.each(JSON.parse(data), function(i,v){
				    $("#ck_r_id").append('<tr><td>'+v.r_id+'</td><td>'+v.m_id+'</td></tr>')
				})
                
			}
		}
	})
})


$(document).on('click',"#bt_t_station_check",function(){
	console.log("자전거 재고 버튼누름")

	$.ajax({
		url: "selectStationInfo", // 대여하기 누르는 순간에 다시 한번 업데이트
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



