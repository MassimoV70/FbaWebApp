
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
    
 	<form:form modelAttribute="fileBean"  action="${url}${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">
 	     <div id="inputDiv">	
 	        
 	         <form:hidden path="nomeModulo"/>
 	         <form:hidden path="idProgetto"/>
	 	     <label for="fileData">Seleziona il file xls o xlsx da caricare:</label>
			 <br>
			 <br>
			 <form:input  type="file"  path="fileData" id="fileData"/>
			  <br>
		</div>	
		     <br>
 	    	 <input type="submit" />
			
 	    
 	     
 	</form:form >
 	
 	<form:form modelAttribute="fileBean"  action="${urlLavoratori}${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">
 	     <div id="inputDiv">	
 	        
 	         <form:hidden path="nomeModulo"/>
 	         <form:hidden path="idProgetto"/>
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
    
<div id="bottoniDiv">
	
			<input type="button"  onclick="location.href='/FbaWebApp/welcome'" value="Indietro" >
				
</div>
    
    
 
 

</body>
</html>