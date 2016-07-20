
<%@ include file="header.jsp" %>
 
 <div id="formDiv">
 	<sec:authorize access="hasRole('ROLE_USER')">
		<c:url var="url" value="./userImplementaPiano?" />
		
	 </sec:authorize>	
	 <sec:authorize access="hasRole('ROLE_ADMIN')">
	 	<c:url var="url" value="./adminImplementaPiano?" />
	 	
	 </sec:authorize>
    
 	<form:form modelAttribute="implementaPianoFormBean"  action="${url}${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data" id="calendarioForm">
 	     <div id="inputDiv">	
 	       
 	       
 	      
 	         
 	         <form:hidden path="id"/>
 	         <form:hidden path="modulo1"/>
 	         <form:hidden path="modulo2"/>
 	         <c:if test="${not empty calendario}">
 	         	<div class="msg"> ${calendario}</div>
 	         </c:if>
 	         <h3> ${calendariTitolo}</h3>
	 	     <label for="fileData">Seleziona il file xls o xlsx dei calendari moduli da caricare:</label>
			 <br>
			 <br>
			 <form:input  type="file"  path="fileData1" id="fileData1"/>
			 <br>
			 <br>
			 <c:if test="${not empty lavoratori}">
			   	<div class="msg"> ${lavoratori}</div>
			  </c:if>
			 <h3> ${lavoratoriTitolo}</h3>
			 <label for="fileData">Seleziona il file xls o xlsx dei lavoratori moduli da caricare:</label>
			 <br>
			 <br>
			 <form:input  type="file"  path="fileData2" id="fileData2"/>
			  <br>
			  <br>
			   <c:if test="${not empty rendicontazione}">
			 <div class="msg"> ${rendicontazione}</div>
			 </c:if>
			  <h3> ${rendicontazioneTitolo}</h3>
			  <label for="fileData">Seleziona il file xls o xlsx di rendicontazione da caricare:</label>
			 <br>
			 <br>
			 <form:input  type="file"  path="fileData3" id="fileData3" />
			  <br>
		   
		 </div> 
		     <br>
		     <input type="submit" value="Upload"/>
	</form:form >
</div>

<sec:authorize access="hasRole('ROLE_ADMIN')">
	<div id="bottoniDiv">
		<input type="button"  onclick="location.href='/FbaWebApp/adminMostraPiani'" value="Indietro" >
		<input type="button"  onclick="cancellaForm();" value="Cancella" >
	</div>
</sec:authorize>
 <sec:authorize access="hasRole('ROLE_USER')">
	<div id="bottoniDiv">
		
				<input type="button"  onclick="location.href='/FbaWebApp/userMostraPiani'" value="Indietro" >
				<input type="button"  onclick="cancellaForm();" value="Cancella" >
					
	</div> 
</sec:authorize>
<script type="text/javascript">
	           function cancellaForm(){
	        	
	        	   $('#fileData1').val("");
	        	   $('#fileData2').val("");
	        	   $('#fileData3').val("");
	        	       
	           }
	           
</script>    
    
 
 

</body>
</html>