
<%@ include file="header.jsp" %>


	
	<div id="formDiv">
		
			<c:url var="url" value="/adminModificaGiorno" />
		
		
			<form:form action="${url}" method="post" modelAttribute="calendarioBeanForm" id="idCalendarioBeanForm" >
			 	<div id="inputDiv">	
			 		<form:hidden path="id"  id="id" />
			 		<form:hidden path="idPiano"  id="idPiano"/>
		            <form:hidden path="nomeModulo"  id="idModulo" />
		            <form:hidden path="stato"  id="stato" />
		            
					<label for="dataStr">Data:</label>
					<br>
					<form:input path="dataStr" disabled="${disabled}" id="datepicker" cssClass="inputUser"/>
					<form:errors path="dataStr" cssClass="error" />
					<br>
					<label for="inizioMattina">Inizio mattina:</label>
					<br>
					<form:input path="inizioMattina" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="inizioMattina" cssClass="error" />
					<br>
					<label for="fineMattina">Fine mattina:</label>
					<br>
					<form:input path="fineMattina" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="fineMattina" cssClass="error" />
					<br>
					<label for="inizioPomeriggio">Inizio pomeriggio:</label>
					<br>
					<form:input path="inizioPomeriggio" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="inizioPomeriggio" cssClass="error" />
					<br>
					<label for="finePomeriggio">Fine pomeriggio:</label>
					<br>
					<form:input path="finePomeriggio" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="finePomeriggio" cssClass="error" />
					<br>
					
				</div>
					<c:if test="${disabled !=  true}">
					  <input type="submit" />
					</c:if>
			</form:form>
		
		</div>
		<div id="bottoniDiv">
				
				<input type="button"  onclick="indietro();" value="Indietro" >
				<c:if test="${disabled ==  false}">
				  <input type="button"  onclick="location.href='/FbaWebApp/welcome'" value="Annulla" >
				</c:if>
		</div>
			
          <script type="text/javascript">
	           
	           
	           $(function() {
	        	    $( "#datepicker" ).datepicker();
	        	    $( "#datepicker1" ).datepicker();
	        	  });
          </script>
     <sec:authorize access="hasRole('ROLE_ADMIN')">
		<script type="text/javascript">
			function indietro(){
				
				$("#idCalendarioBeanForm").attr('action','/FbaWebApp/adminMostraCalendario');
			   	$("#idCalendarioBeanForm").submit();
				
				
			}
		</script>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_USER')">
		<script type="text/javascript">
			function indietro(){
					$("#idCalendarioBeanForm").attr('action','/FbaWebApp/userMostraCalendario');
			   	 	$("#idCalendarioBeanForm").submit();
				
				
			}
		</script>
	</sec:authorize>
</body>
</html>