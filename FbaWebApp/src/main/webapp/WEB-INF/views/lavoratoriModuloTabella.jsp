<%@ include file="header.jsp" %>
<div class="CSSTableGenerator" >
	<table>
		<c:if test="${not empty listaLavoratori}">
	
				<tr>
					<td>Numero Matricola</td>
					<td>Ore Presenza</td>
					<td>Esito test</td>
					<td>Allegato</td>
					<td>Stato</td>
					<td>Azioni</td>
				</tr>
				<c:forEach var="listValue" items="${listaLavoratori}">
				  <tr>
					<td>${listValue.matricola}</td>
					<td>${listValue.orePresenza}</td>
					<td>${listValue.esitoTest}</td>
					<td>${listValue.nomeAllegato}</td>
				   <td>
						<c:choose>
						    <c:when test="${listValue.stato==1}"><img src= "resources/images/ok.png" alt="abilitato" title="abilitato"/></c:when>
							<c:otherwise> <img src= "resources/images/notOK.png" alt="disabilitato" title="disabilitato"/></c:otherwise>
						</c:choose> 
					</td>					
					<td>
						<input type="image"  onclick="elaboraLavoratore('${listValue.id}','${listValue.idPiano}','${listValue.nomeModulo}','allega');" value="Allega" src= "resources/images/pdf.png" alt="Allegato Lavoratore" title="Allegati Lavoratore">
						<input type="image"  onclick="elaboraLavoratore('${listValue.id}','${listValue.idPiano}','${listValue.nomeModulo}','modifica');" value="Indietro" src= "resources/images/settings.png" alt="Modificia lavoratore" title="Modificia lavoratore">
						<input type="image"  onclick="elaboraLavoratore('${listValue.id}','${listValue.idPiano}','${listValue.nomeModulo}','cancella');" value="Indietro" src= "resources/images/elimina.png"  alt="Elimina lavoratore" title="Elimina lavoratore">
				
					</td>
				   </tr>
				</c:forEach>
				
			   
			</c:if>
	</table> 
	</div>
	<div id="bottoniDiv">
	            <br>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
				  <input type="button"  onclick="indietro();" value="Indietro" >

				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
				  <input type="button"  onclick="indietro();" value="Indietro" >

				 </sec:authorize>
				  
				
	</div>
				  
	<sec:authorize access="hasRole('ROLE_USER')">
		<c:url var="urlIndietro" value="/userGestioneModulo" />
	 </sec:authorize>	
	 <sec:authorize access="hasRole('ROLE_ADMIN')">
	 	<c:url var="urlIndietro" value="/adminGestioneModulo" />
	 </sec:authorize>
	
	<form:form action="${urlIndietro}" method="POST" modelAttribute="pianoFormazioneForm" id="idPianoFormazoneForm" >
		<form:hidden path="id"  id="idPiano" />
		<form:hidden path="modulo1"  id="idModulo" />
		<form:hidden path="fadMod1"  id="idTipoModulo" />
	</form:form>
	
	<form:form action="" method="POST" modelAttribute="lavoratoreBeanForm" id="idLavoratoreBeanForm" >
		<form:hidden path="id"  id="id" />
		<form:hidden path="nomeModulo"  id="idModulo" />
		<form:hidden path="idPiano"  id="idPiano"/>
	</form:form>
	<script type="text/javascript">
			function indietro(){
				$("#idPianoFormazoneForm").submit();
				
			}
		</script>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<script type="text/javascript">
			function elaboraLavoratore(id,idPiano,nomeModulo,operazione){
				
				$('#id').val(id);
			    $('#idPiano').val(idPiano);
				$('#idModulo').val(nomeModulo); 
				
				if (operazione=='modifica'){
					
					$("#idLavoratoreBeanForm").attr('action','/FbaWebApp/adminModificaLavoratoreForm');
			   	 	$("#idLavoratoreBeanForm").submit();
				
				}else{
				
					$("#idLavoratoreBeanForm").attr('action','/FbaWebApp/adminEliminaLavoratore');
					$("#idLavoratoreBeanForm").submit();
				}
			}
		</script>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_USER')">
		<script type="text/javascript">
			function elaboraLavoratore(id,idPiano,nomeModulo,operazione){
				
				$('#id').val(id);
			    $('#idPiano').val(idPiano);
				$('#idModulo').val(nomeModulo); 
				
				if (operazione=='modifica'){
					$("#idLavoratoreBeanForm").attr('action','/FbaWebApp/userModificaLavoratoreForm');
			   	 	$("#idLavoratoreBeanForm").submit();
				
				}else{
					$("#idLavoratoreBeanForm").attr('action','/FbaWebApp/userEliminaLavoratore');
					$("#idLavoratoreBeanForm").submit();
				}
			}
		</script>
	</sec:authorize>
</body>
</html>