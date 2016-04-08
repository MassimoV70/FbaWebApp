
<%@ include file="header.jsp" %>


	
	<div id="formDiv">
	
	 <sec:authorize access="hasRole('ROLE_USER')">
		<c:url var="url" value="/userModifyPiano" />
	 </sec:authorize>	
	 <sec:authorize access="hasRole('ROLE_ADMIN')">
	 	<c:url var="url" value="/adminModifyPiano" />
	 </sec:authorize>
			<form:form action="${url}" method="post" modelAttribute="pianoFormazioneForm" id="pianoFormModify">
			 	<div id="inputDiv">	
			 		<form:hidden path="username" value="${pageContext.request.userPrincipal.name}"/>
			 		<form:hidden path="id" />
					<label for="pianoDiFormazione">Titolo piano di formazione:</label>
					<br>
					<form:input path="pianoDiFormazione" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="pianoDiFormazione" cssClass="error" />
					<br>
					<label for="modulo1">Titolo modulo1:</label>
					<br>
					<form:input path="modulo1" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="modulo1" cssClass="error" />
					<br>
					<label for="modulo2">Titolo modulo2</label>
					<br>
					<form:input path="modulo2" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="modulo2" cssClass="error" />
					<br>
					<label for="attuatorePIVA">Partita IVA attuatore:</label>
					<br>
					<form:input path="attuatorePIVA" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="attuatorePIVA" cssClass="error" />
					
					
				</div>
					<c:if test="${disabled !=  true}">
					  <input type="submit" />
					</c:if>
			</form:form>
		
		</div>
		<div id="bottoniDiv">
				<sec:authorize access="hasRole('ROLE_USER')">
					<input type="button"  onclick="location.href='/FbaWebApp/userMostraPiani'" value="Indietro" >
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<input type="button"  onclick="location.href='/FbaWebApp/adminMostraPiani'" value="Indietro" >
				</sec:authorize>
				
				<c:if test="${disabled ==  false}">
				  <input type="button"  onclick="location.href='/FbaWebApp/userMostraPiani'" value="Annulla" >
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