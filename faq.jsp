<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" rel="stylesheet">
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
<link href="bootstrap-accordions-faqs.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>

<section class="accordion-section clearfix mt-3" aria-label="Question Accordions">
  <div class="container">
  
	  <h2>Frequently Asked Questions </h2>
	  <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
		<!--  -->
		<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
		
		<script>
		$.getJSON('rstPost',rfn)
		function rfn(data){
			$.each(data, function(i,v){
				context = '<div class="panel panel-default"><div class="panel-heading p-3 mb-3" role="tab" id="heading"><h3 class="panel-title">'+
				'<a  role="button" title="" data-toggle="collapse" data-parent="#accordion" href="#collapse'+i+'">'+
				v.p_text+
				'</a></h3></div><div id="collapse'+i+'" class="panel-collapse collapse" role="tabpanel" ><div class="panel-body px-3 mb-4"><pre>'+
				v.r_text+'</pre></div></div></div>'		
				$(".panel-group").append(context)
			})
		}
		
		</script>
	</div>
	</div>
	
</section>
