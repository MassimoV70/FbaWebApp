
<%@ include file="header.jsp" %>


	
	<div id="formDiv">
		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<c:url var="url" value="/adminModificaLavoratore" />
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_USER')">
			<c:url var="url" value="/userModificaLavoratore" />
		</sec:authorize>
		
			<form:form action="${url}" method="post" modelAttribute="lavoratoriBean" id="idLavoratoriBean" >
			 	<div id="inputDiv">	
			 		<form:hidden path="id"  id="id" />
			 		<form:hidden path="idPiano"  id="idPiano"/>
		            <form:hidden path="nomeModulo"  id="idModulo" />
		            <form:hidden path="modalitaFormatvia"  id="idTipoModulo" />
		            <form:hidden path="stato"  id="stato" />
		            
					<label for="matricola">Matricola:</label>
					<br>
					<form:input path="matricola" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="matricola" cssClass="error" />
					<br>
					<label for="orePresenza">Ore presenza:</label>
					<br>
					<form:input path="orePresenza" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="orePresenza" cssClass="error" />
					<br>
					<label for="esitoTest">Esito test:</label>
					<br>
					<form:input path="esitoTest" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="esitoTest" cssClass="error" />
					<br>
					<label for="esitoTest">Nome allegato:</label>
					<br>
					<form:input path="nomeAllegato" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="nomeAllegato" cssClass="error" />
					<br>
					
					
				</div>
					<c:if test="${disabled !=  true}">
					  <input type="submit" value="Salva"/>
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
				
				$("#idLavoratoriBean").attr('action','/FbaWebApp/adminMostraLavoratori');
			   	$("#idLavoratoriBean").submit();
				
				
			}
		</script>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_USER')">
		<script type="text/javascript">
			function indietro(){
					$("#idLavoratoriBean").attr('action','/FbaWebApp/userMostraLavoratori');
			   	 	$("#idLavoratoriBean").submit();
				
				
			}
		</script>
	</sec:authorize>
</body>
</html>