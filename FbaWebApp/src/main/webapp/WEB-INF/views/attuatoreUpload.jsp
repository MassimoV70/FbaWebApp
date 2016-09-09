
<%@ include file="header.jsp" %>
 
 <div id="formDiv">
 	<sec:authorize access="hasRole('ROLE_USER')">
		<c:url var="url" value="./userUploadAttuatore?" />
		
	 </sec:authorize>	
	 <sec:authorize access="hasRole('ROLE_ADMIN')">
	 	<c:url var="url" value="./adminUploadAttuatore?" />
	 	
	 </sec:authorize>
    
 	<form:form modelAttribute="implementaPianoFormBean"  action="${url}${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data" id="calendarioForm">
 	     <div id="inputDiv">	
 	       
 	       
 	       <c:if test="${not empty attuatore}">
 	         <div class="msg"> ${attuatore}</div>
 	       </c:if>
 	         <form:hidden path="id"/>
 	         <form:hidden path="modulo1"/>
 	         <form:hidden path="modulo2"/>
 	         <label for="nuemroProtocollo">Partita IVA:</label>
					<br>
					<form:input path="attuatorePIVA" disabled="true" id="inputUser" cssClass="inputUser"/>
					<form:errors path="attuatorePIVA" cssClass="error" />
					 <form:hidden path="attuatorePIVA"/>
			 <br>
 	         <c:if test="${not empty allegato1}">
 	         	<div class="error"> ${allegato1}</div>
 	         </c:if>
 	         <h3> ${allegato1Titolo}</h3>
	 	     <label for="fileData">Seleziona il primo allegato da caricare:</label>
			 <br>
			 <br>
			 <form:input  type="file"  path="fileData1" id="fileData1"/>
			 <form:errors path="fileData1" cssClass="error" />
			 <br>
			 <br>
			 <c:if test="${not empty allegato2}">
			   	<div class="error"> ${allegato2}</div>
			  </c:if>
			 <h3> ${allegato2Titolo}</h3>
			 <label for="fileData">Seleziona il secondo allegato da caricare:</label>
			 <br>
			 <br>
			 <form:input  type="file"  path="fileData2" id="fileData2"/>
			 <form:errors path="fileData2" cssClass="error" />
			  <br>
			  <br>
			   <c:if test="${not empty allegato3}">
			 <div class="error"> ${allegato3}</div>
			 </c:if>
			  <h3> ${allegato3Titolo}</h3>
			  <label for="fileData">Seleziona il terzo allegato da caricare:</label>
			 <br>
			 <br>
			 <form:input  type="file"  path="fileData3" id="fileData3" />
			 <form:errors path="fileData3" cssClass="error" />
			  <br>
			  <br>
			   <c:if test="${not empty allegato4}">
			 <div class="error"> ${allegato4}</div>
			 </c:if>
			  <h3> ${allegato4Titolo}</h3>
			  <label for="fileData">Seleziona il quarto allegato da caricare:</label>
			 <br>
			 <br>
			 <form:input  type="file"  path="fileData4" id="fileData4" />
			 <form:errors path="fileData4" cssClass="error" />
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
	        	   $('#fileData4').val("");    
	           }
	           
</script>    
    
 
 

</body>
</html>