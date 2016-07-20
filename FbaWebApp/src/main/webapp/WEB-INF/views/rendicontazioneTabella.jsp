<%@ include file="header.jsp" %>
 <c:choose>
  	 <c:when test="${not empty listaRendicontazione}">
		<div >
			<table id="table_id" class="display">
					
				<thead>
						<tr>
							<td>Tipologia Giustificativo</td>
							<td>Codice</td>
							<td>Data Giustificativo</td>
							<td>Fornitore Nominativo</td>
							<td>Valore Complessivo</td>
							<td>Contributo FBA</td>
							<td>Contributo Privato</td>
							<td>Nome Allegato</td>
							<td>Stato</td>
							<td>Azioni</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="listValue" items="${listaRendicontazione}">
						  <tr>
							<td>${listValue.tipologiaGiustificativo}</td>
							<td>${listValue.codice}</td>
							<td>${listValue.dataGiustificativoStr}</td>
							<td>${listValue.fornitoreNominativo}</td>
							<td>${listValue.valoreComplessivo}</td>
							<td>${listValue.contributoFBA}</td>
							<td>${listValue.contributoPrivato}</td>
							<td>${listValue.nomeAllegato}</td>
							<td>
								<c:choose>
								    <c:when test="${listValue.stato==1}"><img src= "resources/images/ok.png" alt="abilitato" title="abilitato"/></c:when>
									<c:otherwise> <img src= "resources/images/notOK.png" alt="disabilitato" title="disabilitato"/></c:otherwise>
								</c:choose> 
							</td>					
							<td>
								
								<input type="image"  onclick="elaboraRendicontazione('${listValue.id}','${listValue.idPiano}','modifica');" value="modifica" src= "resources/images/settings.png" alt="Modificia giorno" title="Modificia giorno">
								<input type="image"  onclick="elaboraRendicontazione('${listValue.id}','${listValue.idPiano}','cancella');" value="cancella" src= "resources/images/elimina.png"  alt="Elimina giorno" title="Elimina giorno">
						
							</td>
						   </tr>
						</c:forEach>
					</tbody>
			</table> 
			</div>
		 </c:when>
		 <c:otherwise>
			  <div id="formDiv">
			  	<p align="center">Non ci sono elementi da visualizzare</p>
			  </div>
		  </c:otherwise>
	</c:choose>
	<div id="bottoniDiv">
	            <br>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
				 <input type="button"  onclick="location.href='/FbaWebApp/adminMostraPiani'" value="Indietro" >
				  
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
				  <input type="button"  onclick="location.href='/FbaWebApp/userMostraPiani'" value="Indietro" >
				   
				</sec:authorize>
	</div>
	
	
	
	<form:form action="${urlIndietro}" method="POST" modelAttribute="pianoFormazioneForm" id="idPianoFormazoneForm" >
		<form:hidden path="id"  id="idPiano"/>
	</form:form>
	
	<form:form action="" method="POST" modelAttribute="rendicontazioneBeanForm" id="idRendicontazioneForm" >
		<form:hidden path="id"  id="id" />
		<form:hidden path="idPiano"  id="idPianoTab"/>
	</form:form>
	
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<script type="text/javascript">
			function elaboraRendicontazione(id,idPiano,operazione){
				
				$('#id').val(id);
			    $('#idPianoTab').val(idPiano);
			    
				
				if (operazione=='modifica'){
					
					$("#idRendicontazioneForm").attr('action','/FbaWebApp/adminModificaRendForm');
			   	 	$("#idRendicontazioneForm").submit();
				
				}else{
					
					$("#idRendicontazioneForm").attr('action','/FbaWebApp/adminEliminaSoggettoRend');
					$("#idRendicontazioneForm").submit();
				}
			}
		</script>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_USER')">
		<script type="text/javascript">
			function elaboraRendicontazione(id,idPiano,operazione){
				
				$('#id').val(id);
			    $('#idPianoTab').val(idPiano);
				
				
				if (operazione=='modifica'){
					$("#idRendicontazioneForm").attr('action','/FbaWebApp/userModificaRendForm');
			   	 	$("#idRendicontazioneForm").submit();
				
				}else{
					$("#idRendicontazioneForm").attr('action','/FbaWebApp/userEliminaSoggettoRend');
					$("#idRendicontazioneForm").submit();
				}
			}
		</script>
	</sec:authorize>
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
		
	</script>
	
</body>
</html>