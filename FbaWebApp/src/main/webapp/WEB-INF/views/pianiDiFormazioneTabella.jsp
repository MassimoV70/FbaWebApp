<%@ include file="header.jsp" %>
<div class="CSSTableGenerator" >
	<table>
		<c:if test="${not empty listaPiani}">
	
				<tr>
					<td>Piano Di Formazione</td>
					<td>Tipologia corso</td>
					<td>Tematica formativa</td>
					<td>Inizio attivita</td>
					<td>Fine attivita</td>
					<td>Numero partecipanti</td>
					<td>Comp. impresa\innovazione</td>
					<td>Comp. settoriale</td>
					<td>Deloc.\Intern.</td>
					<td>Form. obbl.</td>
					<td>Form. ingr.</td>
					<td>Mant. occup.</td>
					<td>Manut. agg. comp.</td>
					<td>Mob. est.\outpl.\ricoll.\</td>
					<td>Sviluppo loc.</td>
					<td>Modulo 1</td>
					<td>Mod. form. modulo1</td>
					<td>Modulo 2</td>
					<td>Mod. form. modulo2</td>
					<td>Attuatore P.IVA</td>
					<td>Allegato1</td>
					<td>Allegato2</td>
					<td>Allegato3</td>
					<td>Allegato4</td>
					<td>Stato</td>
					<td>Azioni</td>
				</tr>
				<c:forEach var="listValue" items="${listaPiani}">
				  <tr>
					<td>${listValue.pianoDiFormazione}</td>
					<td>${listValue.tipoCorsoPiano}</td>
					<td>${listValue.tematicaFormativa}</td>
					<td>${listValue.dataInizioAttStr}</td>
					<td>${listValue.dataFineAttStr}</td>
					<td>${listValue.numPartecipanti}</td>
					<td>
						<c:choose>
							    <c:when test="${listValue.compImprInn==1}"><img src= "resources/images/ok.png" alt="abilitato" title="abilitato"/></c:when>
								<c:otherwise> <img src= "resources/images/notOK.png" alt="disabilitato" title="disabilitato"/></c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							    <c:when test="${listValue.compSett==1}"><img src= "resources/images/ok.png" alt="abilitato" title="abilitato"/></c:when>
								<c:otherwise> <img src= "resources/images/notOK.png" alt="disabilitato" title="disabilitato"/></c:otherwise>
						</c:choose>
					</td> 
					<td>
						<c:choose>
							    <c:when test="${listValue.delocInter==1}"><img src= "resources/images/ok.png" alt="abilitato" title="abilitato"/></c:when>
								<c:otherwise> <img src= "resources/images/notOK.png" alt="disabilitato" title="disabilitato"/></c:otherwise>
						</c:choose>
					</td> 
					<td>
						<c:choose>
							    <c:when test="${listValue.formObblExLeg==1}"><img src= "resources/images/ok.png" alt="abilitato" title="abilitato"/></c:when>
								<c:otherwise> <img src= "resources/images/notOK.png" alt="disabilitato" title="disabilitato"/></c:otherwise>
						</c:choose>
					</td>
					<td> 
						<c:choose>
							    <c:when test="${listValue.formInIngresso==1}"><img src= "resources/images/ok.png" alt="abilitato" title="abilitato"/></c:when>
								<c:otherwise> <img src= "resources/images/notOK.png" alt="disabilitato" title="disabilitato"/></c:otherwise>
						</c:choose> 
					</td>
					<td>
						<c:choose>
							    <c:when test="${listValue.mantenimOccup==1}"><img src= "resources/images/ok.png" alt="abilitato" title="abilitato"/></c:when>
								<c:otherwise> <img src= "resources/images/notOK.png" alt="disabilitato" title="disabilitato"/></c:otherwise>
						</c:choose>
					</td>
					<td> 
						<c:choose>
							    <c:when test="${listValue.mobEstOutRic==1}"><img src= "resources/images/ok.png" alt="abilitato" title="abilitato"/></c:when>
								<c:otherwise> <img src= "resources/images/notOK.png" alt="disabilitato" title="disabilitato"/></c:otherwise>
						</c:choose>
					</td> 
					<td>
						<c:choose>
							    <c:when test="${listValue.sviluppoLoc==1}"><img src= "resources/images/ok.png" alt="abilitato" title="abilitato"/></c:when>
								<c:otherwise> <img src= "resources/images/notOK.png" alt="disabilitato" title="disabilitato"/></c:otherwise>
						</c:choose>
					</td>
					<td> 
						<c:choose>
							    <c:when test="${listValue.compImprInn==1}"><img src= "resources/images/ok.png" alt="abilitato" title="abilitato"/></c:when>
								<c:otherwise> <img src= "resources/images/notOK.png" alt="disabilitato" title="disabilitato"/></c:otherwise>
						</c:choose>
					</td> 
					<td><a onclick="elaboraPiano('${listValue.id}','${listValue.modulo1}','modulo')" title="vai al modulo">${listValue.modulo1}</a></td>
					<td>${listValue.fadMod1}</td>
					<td><a onclick="elaboraPiano('${listValue.id}','${listValue.modulo2}','modulo')" title="vai al modulo">${listValue.modulo2}</a></td>
					<td>${listValue.fadMod2}</td>
					<td>${listValue.attuatorePIVA}</td>
					<td>${listValue.nomeAllegato1}</td>
					<td>${listValue.nomeAllegato2}</td>
					<td>${listValue.nomeAllegato3}</td>
					<td>${listValue.nomeAllegato4}</td>
					<td>
						<c:choose>
						    <c:when test="${listValue.enabled==1}"><img src= "resources/images/ok.png" alt="abilitato" title="abilitato"/></c:when>
							<c:otherwise> <img src= "resources/images/notOK.png" alt="disabilitato" title="disabilitato"/></c:otherwise>
						</c:choose> 
					</td>					
					<td>
						<input type="image"  onclick="elaboraPiano('${listValue.id}','','allega');" value="Allega" src= "resources/images/pdf.png" alt="Allegati attuatore" title="Allegati attuatore">
						<input type="image"  onclick="elaboraPiano('${listValue.id}','','rendiconta');" value="Rendiconta" src= "resources/images/rendicontazione.png" alt="Giustificativi spesa" title="Giustificativi spesa">
						<input type="image"  onclick="elaboraPiano('${listValue.id}','','modifica');" value="Modifica" src= "resources/images/settings.png" alt="Modificia piano" title="Modificia piano">
						<input type="image"  onclick="elaboraPiano('${listValue.id}','','cencella');" value="Cancella" src= "resources/images/elimina.png"  alt="Elimina piano" title="Elimina piano">
				
					</td>
				   </tr>
				</c:forEach>
				
			   
			</c:if>
	</table> 
	</div>
	<div id="bottoniDiv">
	            <br>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
				  <input type="button"  onclick="location.href='/FbaWebApp/welcome'" value="Indietro" >
				    <!-- <input type="button"  onclick="location.href='/FbaWebApp/adminCancellaTuttiPiani'" value="Annulla Upload" > -->
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
				  <input type="button"  onclick="location.href='/FbaWebApp/welcome'" value="Indietro" >
				  <!--   <input type="button"  onclick="location.href='/FbaWebApp/userCancellaTuttiPiani'" value="Annulla Upload" > -->
				</sec:authorize>
				  
				
	</div>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<c:url var="url" value="/adminModifyPianoForm"></c:url>
		<c:url var="urlCancella" value="/adminCancellaPiano"></c:url>
		<c:url var="urlRendiconta" value="/adminRendicontaPiano"></c:url>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_USER')">
		<c:url var="url" value="/userModifyPianoForm"></c:url>
		<c:url var="urlCancella" value="/userCancellaPiano"></c:url>
		<c:url var="urlRendiconta" value="/userRendicontaPiano"></c:url>
	</sec:authorize>
	<form:form action="" method="POST" modelAttribute="pianoFormazioneForm" id="modificaPianoForm" >
		<form:hidden path="id"  id="idPiano" />
		<form:hidden path="modulo1"  id="idModulo" />
		<form:hidden path="username" value="${pageContext.request.userPrincipal.name}"/>
	</form:form>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
	
	<script type="text/javascript">
	
		function elaboraPiano(id,modulo, operazione){
		
			$('#idPiano').val(id);
			$('#idModulo').val(modulo);
			
			if (operazione=='modulo'){
				$("#modificaPianoForm").attr('action','/FbaWebApp/adminGestioneModulo');
		   	 	$("#modificaPianoForm").submit();
			}else if(operazione=='modifica'){
				$("#modificaPianoForm").attr('action','/FbaWebApp/adminModifyPianoForm');
		   	 	$("#modificaPianoForm").submit();
			}else if(operazione=='modifica'){
				$("#modificaPianoForm").attr('action','/FbaWebApp/adminModifyPianoForm');
				$("#modificaPianoForm").submit(); 
			}else{
				$("#modificaPianoForm").attr('action','/FbaWebApp/adminModifyPianoForm');
				$("#modificaPianoForm").submit();
			}
		}
		</script>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_USER')">
	
	<script type="text/javascript">
	
	function elaboraPiano(id,modulo, operazione){
		
		$('#idPiano').val(id);
		$('#idModulo').val(modulo);
		
		if (operazione=='modulo'){
			$("#modificaPianoForm").attr('action','/FbaWebApp/userGestioneModulo');
	   	 	$("#modificaPianoForm").submit();
		}else if(operazione=='modifica'){
			$("#modificaPianoForm").attr('action','/FbaWebApp/userModifyPianoForm');
	   	 	$("#modificaPianoForm").submit();
		}else if(operazione=='modifica'){
			$("#modificaPianoForm").attr('action','/FbaWebApp/adminModifyPianoForm');
			$("#modificaPianoForm").submit(); 
		}else{
			$("#modificaPianoForm").attr('action','/FbaWebApp/adminModifyPianoForm');
			$("#modificaPianoForm").submit();
		}
	}
						
		</script>
	</sec:authorize>
</body>
</html>