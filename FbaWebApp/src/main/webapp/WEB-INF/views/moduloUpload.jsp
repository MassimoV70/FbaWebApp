
<%@ include file="header.jsp" %>
 
 <div id="formDiv">
 	<sec:authorize access="hasRole('ROLE_USER')">
		<c:url var="url" value="./userElaboraUploadModuloCalendario?" />
		<c:url var="urlLavoratori" value="./userElaboraUploadModuloLavoratori?" />
	 </sec:authorize>	
	 <sec:authorize access="hasRole('ROLE_ADMIN')">
	 	<c:url var="url" value="./adminElaboraUploadModuloCalendario?" />
	 	<c:url var="urlLavoratori" value="./adminElaboraUploadModuloLavoratori?" />
	 </sec:authorize>
    
 	<form:form modelAttribute="calendarioModuloBean"  action="${url}${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data" id="calendarioForm">
 	     <div id="inputDiv">	
 	        <h3> ${calendario}</h3>
 	         <form:hidden path="nomeModulo"/>
 	         <form:hidden path="idPiano"/>
 	           <c:if test="${!disabled}">
 	          <P>Vai alla sezione calendario caricato</P>
        		<input type="button"  onclick="mostraCalendario();" value="Visualizza calendario associato"  >
        		<br>
       
	 	     <label for="fileData">Seleziona il file xls o xlsx da caricare:</label>
			 <br>
			 <br>
			 <form:input  type="file"  path="fileData" id="fileData"/>
			  <br>
		    </c:if>
		 </div> 
		     <br>
		     <c:if test="${!disabled}">
 	    	 <input type="submit" />
			 </c:if>
 	   
 	</form:form >
</div>
<div id="formDiv"> 	
 	<form:form modelAttribute="lavoratoriBean"  action="${urlLavoratori}${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data" id="lavoratoriForm" >
 	     <div id="inputDiv">	
 	        <h3> ${lavoratori}</h3>
 	         <form:hidden path="nomeModulo"/>
 	         <form:hidden path="idPiano"/>
 	          <P>Vai alla sezione lavoratori caricati</P>
        		<input type="button"  onclick="mostraLavoratori();" value="Visualizza lavoratori associati" >
        	 <br>
	 	     <label for="fileData">Seleziona il file xls o xlsx da caricare:</label>
			 <br>
			 <br>
			 <form:input  type="file"  path="fileData" id="fileData"/>
			  <br>
		</div>	
		     <br>
 	    	 <input type="submit" />
			
 	    
 	     
 	</form:form >
</div>
    
<sec:authorize access="hasRole('ROLE_ADMIN')">
	<div id="bottoniDiv">
		
				<input type="button"  onclick="location.href='/FbaWebApp/adminMostraPiani'" value="Indietro" >
					
	</div>
	<script type="text/javascript">
		
			function mostraCalendario(){
				 
					$("#calendarioForm").attr('action','/FbaWebApp/adminMostraCalendario');
			   	 	$("#calendarioForm").submit();
				
			}
			
			function mostraLavoratori(){
				 
				$("#lavoratoriForm").attr('action','/FbaWebApp/adminMostraLavoratori');
		   	 	$("#lavoratoriForm").submit();
			
		}
	</script>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<div id="bottoniDiv">
		
				<input type="button"  onclick="location.href='/FbaWebApp/userMostraPiani'" value="Indietro" >
					
	</div>
	<script type="text/javascript">
		
			function mostraCalendario(){
					$("#calendarioForm").attr('action','/FbaWebApp/userMostraCalendario');
			   	 	$("#calendarioForm").submit();
				
			}
			
			function mostraLavoratori(){
				 
				$("#lavoratoriForm").attr('action','/FbaWebApp/userMostraLavoratori');
		   	 	$("#lavoratoriForm").submit();
			
		}
	</script>
</sec:authorize>        
    
 
 

</body>
</html>