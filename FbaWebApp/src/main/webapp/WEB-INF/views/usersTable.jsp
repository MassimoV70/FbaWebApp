<%@ include file="header.jsp" %>

	<div class="CSSTableGenerator" >
	<table>
		<c:if test="${not empty usersList}">
	
				<tr>
					<td>Nome</td>
					<td>Cognome</td>
					<td>Email</td>
					<td>Username</td>
					<td>Password</td>
					<td>Abilitato</td>
					<td>Data inizio Attivit&agrave</td>
				 	<td>Data Fine Attivit&agrave</td>
				 	<td>Azioni</td>
				</tr>
				<c:forEach var="listValue" items="${usersList}">
				  <tr>
					<td>${listValue.nome}</td>
					<td>${listValue.cognome}</td>
					<td>${listValue.email}</td>
					<td>${listValue.username}</td>
					<td>${listValue.password}</td>
					<td><c:choose>
						    <c:when test="${listValue.enabled==1}"><img src= "resources/images/ok.png" alt="abilitato"/></c:when>
							<c:otherwise> <img src= "resources/images/notOK.png" alt="disabilitato"/></c:otherwise>
						</c:choose> </td>
					<td>${listValue.dataInizioStr}</td>
					<td>${listValue.dataFineStr}</td>
					<td>
						<input type="image"  onclick="gestisciUtente('${listValue.username}','abilita');" value="Indietro" src="resources/images/enable.png" alt="abilit&grave">
						<input type="image"  onclick="gestisciUtente('${listValue.username}','modifica');" value="Indietro" src= "resources/images/settings.png" alt="modifica">
						<input type="image"  onclick="gestisciUtente('${listValue.username}','disabilita');" value="Indietro" src= "resources/images/disable.png"  alt="disabilit&grave">
				
					</td>
				   </tr>
				</c:forEach>
			   
			</c:if>
	</table> 
	</div>
	
	
	<br>
	<div id="funzioniDiv">
		<input type="button"  onclick="location.href='/FbaWebApp/admin'" value="Indietro" >
		<input type="button"  onclick="location.href='/FbaWebApp/login'" value="Annulla" >
	</div>
	<c:url var="url" value="adminGestisciUtente"></c:url>
	<form:form action="${url}" method="post" modelAttribute="gestioneUserForm" id="gestioneUserForm" commandName="gestioneUserForm">
	 <form:hidden path="username"  id="idUserNamer" />
	 <form:hidden path="azione"  id="idAzione" />
	
	
	</form:form>
	<script type="text/javascript">
		function gestisciUtente(username, operazione){
		
			$('#idUserNamer').val(username);
			$('#idAzione').val(operazione);
		    $("#gestioneUserForm").submit();
		}
	
	
	
	</script>
</body>

</html>