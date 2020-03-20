		
		
		google.charts.load('current', {'packages':['corechart']});
	    /* google.charts.setOnLoadCallback(readyChart);
	    
	    
	    function readyChart(){
    		myid.innerText="따릉이 현황"
    	}
	     */
	    function drawChart1(cdata) {
        	var data = google.visualization.arrayToDataTable(cdata);

        	var options = {
        	    title: '서울특별시 구 별 따릉이 대여소 현황',
            	subtitle: '출처: 서울 열린데이터 광장, 2019.11월 기준'
        	};

            var chart = new google.visualization.PieChart(document.getElementById('chartframe'));

        	chart.draw(data, options);
        
    	}
	    function drawChart2(cdata) {
        	var data = google.visualization.arrayToDataTable(cdata);

        	var options = {
        	    title: '서울특별시 구 별 따릉이 총 거치대 현황',
            	subtitle: '출처: 서울 열린데이터 광장, 2019.11월 기준'

        	};

            var chart = new google.visualization.PieChart(document.getElementById('chartframe'));

        	chart.draw(data, options);
        
    	}
	    
	    function drawChart3(cdata) {
        	var data = google.visualization.arrayToDataTable(cdata);

        	var options = {
        	    title: '서울특별시 따릉이 연령대 별 신규가입자 현황',
        	    subtitle: '출처: 서울 열린데이터 광장, 2019.11월 기준',
        	    isStacked: true,
        	    
        	};

            var chart = new google.visualization.BarChart(document.getElementById('chartframe'));

        	chart.draw(data, options);
        	
    	}
	    
	    function recvFn(data){
	    	aRental=[]
	    	aRental.push(["구","대여소"])
			
			$.each(data,function(i,v)
			{
				aRental.push([v.gu,v.station])
			})
	    	drawChart1(aRental)
	    	console.log(aRental)
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
	    
	  
	    function recvFn3(data){
			var uarr = []
	    	uarr.push(["성별","10대","20대","30대","40대","50대","60대","70대"])
	    	arr1 = new Array(8);
	    	arr1[0] = "여"
	    	cnt1=1
	    	cnt2=1
	    	arr2 = new Array(8);
	    	arr2[0] = "남"
			$.each(data,function(i,v)
			{
				console.log(i)
				if(i%2==0){
					arr1[cnt1] = v.cnt
					cnt1+=1
				}else{
					arr2[cnt2] = v.cnt
					cnt2+=1
				}
				
			})
			uarr.push(arr1)
	    	console.log(arr1)
			uarr.push(arr2)
	    	console.log(arr2)
	    	drawChart3(uarr)
	    	console.log(uarr)
	    }
	    
	    function btNum(){
	    	$.getJSON("rstStation", recvFn2)
	    }
	
	    function btGu(){
	    	$.getJSON("rstStation", recvFn)
	    }
		function btAge(){
	    	$.getJSON("rstUser", recvFn3)
	    }
