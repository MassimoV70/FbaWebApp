
<%@ include file="header.jsp" %>


	<div id="formDiv">
		<c:url var="url" value="/adminAddUser" />
			<form:form action="${url}" method="post" modelAttribute="userForm" id="userForm" commandName="userForm">
			<div id="inputDiv">	
				
				
				<label>Nome:</label>
				<br>
				<form:input path="nome" disabled="${disabled}" id="nome" cssClass="inputUser"/>
				<form:errors path="nome" cssClass="error" />
				<br>
				<label>Cognome:</label>
				<br>
				<form:input path="cognome" disabled="${disabled}" id="cognome" cssClass="inputUser"/>
				<form:errors path="cognome" cssClass="error" />
				<br>
				<label>Username:</label>
				<br>
				<form:input path="username" disabled="${disabled}" id="username" cssClass="inputUser"/>
				<form:errors path="username" cssClass="error" />
				<br>
				<label>Password:</label>
				<br>
				<form:input path="password" disabled="${disabled}" id="password" cssClass="inputUser"/>
				<form:errors path="password" cssClass="error" />
				<br>
				<label>Email:</label>
				<br>
				<form:input path="email" disabled="${disabled}" id="email" cssClass="inputUser"/>
				<form:errors path="email" cssClass="error" />
				<br>
				<label>Data Inizio Attivit&agrave</label>
				<br>
				<form:input path="dataInizioStr" disabled="true" id="dataInizio" cssClass="inputUser"/>
				<form:errors path="dataInizioStr" cssClass="error" />
				<br>
				<label>Data Fine Attivit&agrave</label>
				<br>
				<form:input path="dataFineStr" disabled="${disabled}" id="datepicker1" cssClass="inputUser"/>
				<form:errors path="dataFineStr" cssClass="error" />
				<br>
			</div>
			<c:if test="${disabled !=  true}">
			  <input type="submit" />
			</c:if>
			</form:form>
		</div>
		<br>
		<div id="funzioniDiv">
			<input type="button"  onclick="location.href='/FbaWebApp/admin'" value="Indietro" >
			
			<c:if test="${disabled ==  false}">
			  <input type="button"  onclick="cancellaForm();" value="Cancella" >
			  <input type="button"  onclick="location.href='/FbaWebApp/welcome'" value="Annulla" >
			</c:if>
		</div>
          <script type="text/javascript">
	           function cancellaForm(){
	        	  
	        	   $('#nome').val("");
	        	   $('#cognome').val("");
	        	   $('#username').val("");
	        	   $('#password').val("");
	        	   $('#email').val("");
	        	   $('#datepicker1').val("");
	        	       
	           }
	           
	           $(function() {
	        	    $( "#datepicker" ).datepicker();
	        	    $( "#datepicker1" ).datepicker();
	        	  });
          </script>
</body>
</html>