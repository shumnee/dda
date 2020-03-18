<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
  <head>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<style>
	.panel {
	  padding: 0 18px;
	  background-color: white;
	  max-height: 0;
	  overflow: hidden;
	  transition: max-height 0.2s ease-out;
	}
	</style>
	<script>
    	
    	function recvFn(data){

    		$.each(data,function(i,v)
    		{
    			$("#post").append("<button class = \"accordion\">"+v.p_text+"</button>")
    			$("#post").append("<div class=\"panel\">"+v.r_text+"</div>")
    		})
    	    	
    	}
    	/* 	
	    function fn(){
	    	$.getJSON("rstPost", recvFn)
	    } */
	    $(function(){
	    	$.getJSON("rstPost", recvFn)
	    	var acc = document.getElementsByClassName("accordion");
		    var i;

		    for (i = 0; i < acc.length; i++) {
		      acc[i].addEventListener("click", function() {
		        this.classList.toggle("active");
		        var panel = this.nextElementSibling;
		        if (panel.style.maxHeight) {
		          panel.style.maxHeight = null;
		        } else {
		          panel.style.maxHeight = panel.scrollHeight + "px";
		        } 
		      });
		    }
		})
	    
    	</script>
  </head>
  <body>
<!--     <div id="piechart" style="width: 900px; height: 500px;"></div>
 -->    
 		
 		<div id="post">
 			<!--  <th>글</th><th>답글</th>-->
 		</div>
    	
   
  </body>
</html>