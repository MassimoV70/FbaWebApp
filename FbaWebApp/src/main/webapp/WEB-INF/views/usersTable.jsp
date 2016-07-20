<%@ include file="header.jsp" %>

	<div  >
	<table id="table_id" class="display">
		<c:if test="${not empty usersList}">			
		<thead>
		
	
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
				</thead>
			<tbody>
				<c:forEach var="listValue" items="${usersList}">
				  <tr>
					<td>${listValue.nome}</td>
					<td>${listValue.cognome}</td>
					<td>${listValue.email}</td>
					<td>${listValue.username}</td>
					<td>${listValue.password}</td>
					<td><c:choose>
						    <c:when test="${listValue.enabled==1}"><img src= "resources/images/ok.png" alt="abilitato" title="abilitato"/></c:when>
							<c:otherwise> <img src= "resources/images/notOK.png" alt="disabilitato" title="disabilitato"/></c:otherwise>
						</c:choose> </td>
					<td>${listValue.dataInizioStr}</td>
					<td>${listValue.dataFineStr}</td>
					<td>
						<input type="image"  onclick="gestisciUtente('${listValue.username}','abilita');" value="Abilita" src="resources/images/enable.png" alt="abilit&grave" title="abilita">
						<input type="image"  onclick="gestisciUtente('${listValue.username}','modifica');" value="Indietro" src= "resources/images/settings.png" alt="modifica" title="modifica">
						<input type="image"  onclick="gestisciUtente('${listValue.username}','disabilita');" value="disabilita" src= "resources/images/disable.png"  alt="disabilit&grave" title="disabilita">
				
					</td>
				   </tr>
				</c:forEach>
			   
			
			</tbody>
			</c:if>
	</table> 
	</div>
	
	
	<br>
	<div id="funzioniDiv">
		<input type="button"  onclick="location.href='/FbaWebApp/admin'" value="Indietro" >
		<input type="button"  onclick="location.href='/FbaWebApp/login'" value="Annulla" >
	</div>
	<c:url var="url" value="/adminGestisciUtente"></c:url>
	<form:form action="${url}" method="GET" modelAttribute="gestioneUserForm" id="gestioneUserForm" >
	 <form:hidden path="username"  id="idUserNamer" />
	 <form:hidden path="azione"  id="idAzione" />
	
	
	</form:form>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#table_id").DataTable({
				"language":{
					"lengthMenu":"Mostra _MENU_ righe",
					"info": "Pagina _PAGE_ di _PAGES_ ",
					"infoFiltered" : "filtrate da _MAX_ pagine totali",
					"search": "Cerca",
					
					"oPaginate":{
						"sFirst": "Primo",
						"sLast": "Ultimo",
						"sNext": "Avanti",
						"sPrevious": "Indietro"
					}
				}	
			
			});
		})
	
	
		function gestisciUtente(username, operazione){
			
			$('#idUserNamer').val(username);
			$('#idAzione').val(operazione);
		    $("#gestioneUserForm").submit();
		}
	
	
	
	</script>
</body>

</html>