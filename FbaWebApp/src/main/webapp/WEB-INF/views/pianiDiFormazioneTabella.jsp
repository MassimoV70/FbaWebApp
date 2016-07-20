<%@ include file="header.jsp" %>
<c:choose>
  	 <c:when test="${not empty listaPiani}">
			<div >
				<table id="table_id" class="display">
					
				<thead>
							<tr>
								<td>Numero Protocollo</td>
								<td>Nome Progetto</td>
								<td>Tipologia corso</td>
								<td>Tematica formativa</td>
								<td>N. part.</td>
								<td>Modulo 1</td>
								<td>Mod. form. mod1</td>
								<td>Durata Modulo 1</td>
								<td>Modulo 2</td>
								<td>Mod. form. mod2</td>
								<td>Durata Modulo 2</td>
								<td>Regime aiuti</td>
								<td>Cat. svant.</td>
								<td>Att. P.IVA</td>
								<td>Allegati</td>
								<td>Rend.</td>
								<td>Stato</td>
								<td>Azioni</td>
							</tr>
					</thead>
					<tbody>
							<c:forEach var="listValue" items="${listaPiani}">
							  <tr>
							    <td>${listValue.nuemroProtocollo}</td>
								<td>${listValue.nomeProgetto}</td>
								<td>${listValue.tipoCorsoPiano}</td>
								<td>${listValue.tematicaFormativa}</td>
								<td>${listValue.numeroPartecipanti}</td>
								<c:choose>
									 <c:when test="${listValue.modulo1!='assente'}">
										<td><a onclick="elaboraPiano('${listValue.id}','${listValue.modulo1}','${listValue.fadMod1}','modulo','','')" title="vai al modulo">${listValue.modulo1}</a></td>
									 </c:when>
									 <c:otherwise>
									  <td>assente</td>
									 </c:otherwise>
								</c:choose>
								<td>${listValue.fadMod1}</td>
								<td>${listValue.durataModulo1}</td>
								<c:choose>
									 <c:when test="${listValue.modulo2!='assente'}">
										<td><a onclick="elaboraPiano('${listValue.id}','${listValue.modulo2}','${listValue.fadMod2}','modulo','','')" title="vai al modulo">${listValue.modulo2}</a></td>
									</c:when>
									<c:otherwise>
									  <td>assente</td>
									 </c:otherwise>
								</c:choose>
								<td>${listValue.fadMod2}</td>
								<td>${listValue.durataModulo2}</td>
								<td>${listValue.formeAiuti}</td>
								<td>
									<c:if test="${listValue.categSvantagg=='1'}">
									  Si
									</c:if>
									<c:if test="${listValue.categSvantagg=='0'}">
									  No
									</c:if>
								</td>
								<td>${listValue.attuatorePIVA}</td>
								<td>
									<c:if test="${not empty listValue.nomeAllegato1}">
										${listValue.nomeAllegato1}<br>
									</c:if>
									<c:if test="${not empty listValue.nomeAllegato2}">
										${listValue.nomeAllegato2}<br>
									</c:if>
									<c:if test="${not empty listValue.nomeAllegato3}">
									    ${listValue.nomeAllegato3}<br>
									</c:if>
									<c:if test="${not empty listValue.nomeAllegato4}">
									    ${listValue.nomeAllegato4}
									</c:if>
								</td>
								<td>
								   <input type="image"  onclick="elaboraPiano('${listValue.id}','','','rendiconta','','');" value="Rendicontazione" src= "resources/images/rendicontazione.png" alt="Rendicontazione progetto" title="Rendicontazione progetto"> 
									
								</td>
								<td>
									<c:choose>
									    <c:when test="${listValue.enabled==1}"><img src= "resources/images/ok.png" alt="abilitato" title="abilitato"/></c:when>
										<c:otherwise> <img src= "resources/images/notOK.png" alt="disabilitato" title="disabilitato"/></c:otherwise>
									</c:choose> 
								</td>					
								<td >
									<div class="toggler" onmousedown="mostra('${listValue.id}'); " >
									 <div id="azioni${listValue.id}" class="toggle" >
									  	<img alt="azioni" src="resources/images/azioni.png" title="Apri azioni">
									  	
									 </div>
									 <div id="effect${listValue.id}"  class="funzioni" >
									 	<img alt="azioni" src="resources/images/azioniChiudi.png" onclick="nascondi('${listValue.id}');" title="Chiudi azioni">
									    <input type="image"  onclick="elaboraPiano('${listValue.id}','','','allega','','${listValue.attuatorePIVA}');" value="Allega" src= "resources/images/pdf.png" alt="Allegati attuatore" title="Allegati attuatore">
										<input type="image"  onclick="elaboraPiano('${listValue.id}','${listValue.modulo1}','','implementa','${listValue.modulo2}','');" value="Implementa" src= "resources/images/implModulo.png" alt="Implementa piano" title="Implementa piano">
										<input type="image"  onclick="elaboraPiano('${listValue.id}','','','modifica','','');" value="Modifica" src= "resources/images/settings.png" alt="Modificia piano" title="Modificia piano">
										<input type="image"  onclick="elaboraPiano('${listValue.id}','','','cancella','','');" value="Cancella" src= "resources/images/elimina.png"  alt="Elimina piano" title="Elimina piano">
									 </div>
									</div>
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
		<form:hidden path="modulo2"  id="idModulo2" />
		<form:hidden path="fadMod1"  id="idTipoModulo" />
		<form:hidden path="username" value="${pageContext.request.userPrincipal.name}"/>
		<form:hidden path="attuatorePIVA"  id="idAttuatorePIVA" />
	</form:form>
	
	
	
	
	
	<sec:authorize access="hasRole('ROLE_ADMIN')">
	
	<script type="text/javascript">
	
		function elaboraPiano(id,modulo,tipoModulo, operazione, modulo2, attuatoreIVA){
		
			$('#idPiano').val(id);
			$('#idModulo').val(modulo);
			$('#idTipoModulo').val(tipoModulo);
			$('#idModulo2').val(modulo2);
			$('#idAttuatorePIVA').val(attuatoreIVA);
			
			if (operazione=='modulo'){
				$("#modificaPianoForm").attr('action','/FbaWebApp/adminGestioneModulo');
		   	 	$("#modificaPianoForm").submit();
			}else if(operazione=='modifica'){
				$("#modificaPianoForm").attr('action','/FbaWebApp/adminModifyPianoForm');
		   	 	$("#modificaPianoForm").submit();
			}else if(operazione=='cancella'){
				$("#modificaPianoForm").attr('action','/FbaWebApp/adminCancellaPiano');
				$("#modificaPianoForm").submit(); 
			}else if(operazione=='allega'){
				$("#modificaPianoForm").attr('action','/FbaWebApp/adminUploadAttuatoreForm');
				$("#modificaPianoForm").submit(); 
			}else if(operazione=='rendiconta'){
				$("#modificaPianoForm").attr('action','/FbaWebApp/adminMostraRendicontazione');
				$("#modificaPianoForm").submit(); 
			}else{
				$("#modificaPianoForm").attr('action','/FbaWebApp/adminImplementaPianoForm');
				$("#modificaPianoForm").submit();
			}
		}
		</script>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_USER')">
	
	<script type="text/javascript">
	
	function elaboraPiano(id,modulo,tipoModulo, operazione, modulo2, attuatoreIVA){
		
		$('#idPiano').val(id);
		$('#idModulo').val(modulo);
		$('#idTipoModulo').val(tipoModulo);
		$('#idModulo2').val(modulo2);
		$('#idAttuatorePIVA').val(attuatoreIVA);
		
		if (operazione=='modulo'){
			$("#modificaPianoForm").attr('action','/FbaWebApp/userGestioneModulo');
	   	 	$("#modificaPianoForm").submit();
		}else if(operazione=='modifica'){
			$("#modificaPianoForm").attr('action','/FbaWebApp/userModifyPianoForm');
	   	 	$("#modificaPianoForm").submit();
		}else if(operazione=='cancella'){
			$("#modificaPianoForm").attr('action','/FbaWebApp/userCancellaPiano');
			$("#modificaPianoForm").submit(); 
		}else if(operazione=='allega'){
			$("#modificaPianoForm").attr('action','/FbaWebApp/userUploadAttuatoreForm');
			$("#modificaPianoForm").submit(); 
		}else if(operazione=='rendiconta'){
			$("#modificaPianoForm").attr('action','/FbaWebApp/userMostraRendicontazione');
			$("#modificaPianoForm").submit(); 
		}else{
			$("#modificaPianoForm").attr('action','/FbaWebApp/userImplementaPianoForm');
			$("#modificaPianoForm").submit();
		}
	}
	
	
						
		</script>
	</sec:authorize>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$(".toggle").show();
			$(".funzioni").hide();
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
		
		
		function nascondi(id){
			$("#azioni"+id).fadeIn("slow");
			$("#effect"+id).fadeOut();
		}
		
		function mostra(id){
			$("#effect"+id).fadeIn("slow");
			$("#azioni"+id).fadeOut();
		}
	</script>
	
</body>
</html>