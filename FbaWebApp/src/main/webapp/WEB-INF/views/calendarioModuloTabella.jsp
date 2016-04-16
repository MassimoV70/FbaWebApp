<%@ include file="header.jsp" %>
<div class="CSSTableGenerator" >
	<table>
		<c:if test="${not empty listaCalendari}">
	
				<tr>
					<td>Data</td>
					<td>Inizio Mattin</td>
					<td>Fine Mattina</td>
					<td>Inizio Pomeriggio</td>
					<td>Fine Pomeriggio</td>
					<td>Stato</td>
					<td>Azioni</td>
				</tr>
				<c:forEach var="listValue" items="${listaCalendari}">
				  <tr>
					<td>${listValue.dataStr}</td>
					<td>${listValue.inizioMattina}</td>
					<td>${listValue.fineMattina}</td>
					<td>${listValue.inizioPomeriggio}</td>
					<td>${listValue.finePomeriggio}</td>
					<td>
						<c:choose>
						    <c:when test="${listValue.stato==1}"><img src= "resources/images/ok.png" alt="abilitato" title="abilitato"/></c:when>
							<c:otherwise> <img src= "resources/images/notOK.png" alt="disabilitato" title="disabilitato"/></c:otherwise>
						</c:choose> 
					</td>					
					<td>
						
						<input type="image"  onclick="elaboraGiorno('${listValue.id}','${listValue.idPiano}','${listValue.nomeModulo}','modifica');" value="Indietro" src= "resources/images/settings.png" alt="Modificia giorno" title="Modificia giorno">
						<input type="image"  onclick="elaboraGiorno('${listValue.id}','${listValue.idPiano}','${listValue.nomeModulo}','cencella');" value="Indietro" src= "resources/images/elimina.png"  alt="Elimina giorno" title="Elimina giorno">
				
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
				    <input type="button"  onclick="location.href='/FbaWebApp/adminCancellaTuttiPiani'" value="Annulla Upload" >
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
				  <input type="button"  onclick="location.href='/FbaWebApp/userGestioneModulo'" value="Indietro" >
				    <!-- <input type="button"  onclick="location.href='/FbaWebApp/userCancellaTuttiPiani'" value="Annulla Upload" > -->
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
	</form:form>
	
	<form:form action="" method="POST" modelAttribute="calendarioBeanForm" id="idCalendarioBeanForm" >
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
			function elaboraGiorno(id,idPiano,nomeModulo,operazione){
				
				$('#id').val(id);
			    $('#idPiano').val(idPiano);
				$('#idModulo').val(nomeModulo); 
				
				if (operazione=='modifica'){
					
					$("#idCalendarioBeanForm").attr('action','/FbaWebApp/adminModificaGiornoForm');
			   	 	$("#idCalendarioBeanForm").submit();
				
				}else{
					
					$("#idCalendarioBeanForm").attr('action','/FbaWebApp/adminEliminaGiornoForm');
					$("#idCalendarioBeanForm").submit();
				}
			}
		</script>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_USER')">
		<script type="text/javascript">
			function elaboraGiorno(id,operazione){
				
				$('#idPiano').val(id);
				$('#idModulo').val(modulo);
				
				if (operazione=='modifica'){
					$("#idCalendarioBeanForm").attr('action','/FbaWebApp/userModificaGiornoForm');
			   	 	$("#idCalendarioBeanForm").submit();
				
				}else{
					$("#idCalendarioBeanForm").attr('action','/FbaWebApp/userEliminaGiornoForm');
					$("#idCalendarioBeanForm").submit();
				}
			}
		</script>
	</sec:authorize>
	
</body>
</html>