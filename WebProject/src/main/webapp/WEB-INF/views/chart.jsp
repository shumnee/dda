<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<html>
	<head>
	   <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	
    <script type="text/javascript">
	    google.charts.load('current', {'packages':['corechart']});
	    google.charts.setOnLoadCallback(readyChart);
	    
	    function readyChart(){
    		myid.innerText="차트 준비됨"
    	}
	    
	    function drawChart1(cdata) {
        	var data = google.visualization.arrayToDataTable(cdata);

        	var options = {
        	    title: '서울특별시 구 별 대여소 현황'
        	};

            var chart = new google.visualization.PieChart(document.getElementById('piechart1'));

        	chart.draw(data, options);
        
    	}
	    function drawChart2(cdata) {
        	var data = google.visualization.arrayToDataTable(cdata);

        	var options = {
        	    title: '서울특별시 구 별 거치대 현황'
        	};

            var chart = new google.visualization.PieChart(document.getElementById('piechart2'));

        	chart.draw(data, options);
        
    	}
	    function recvFn(data){
	    	arr=[]
			arr.push(["구","대여소"])
			
			$.each(data,function(i,v)
			{
				arr.push([v.gu,v.station])
			})
	    	drawChart1(arr)
	    	console.log(arr)
	    }
	    
	    function recvFn2(data){
	    	arr=[]
			arr.push(["구","거치대"])
			
			$.each(data,function(i,v)
			{
				arr.push([v.gu,v.rest])
			})
	    	drawChart2(arr)
	    	console.log(arr)
	    }
	    
	    function fn(){
	    	$.getJSON("rstRest", recvFn)
	    	$.getJSON("rstRest", recvFn2)
	    }
	    

	
	
    
    </script>
	</head>
  	
    <body>
    <div id="piechart1" style="width: 900px; height: 500px;"></div>
    <div id="piechart2" style="width: 900px; height: 500px;"></div>
    <p>여기</p>
    <button onclick="fn()">버튼</button>
    

	<div id="myid"></div>
  </body>
</html>
