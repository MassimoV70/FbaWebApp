<%@ include file="header.jsp" %>
<c:choose>
  	 <c:when test="${not empty listaPiani}">
			<div >
				<table id="table_id" class="display">
					
				<thead>
							<tr>
								<td>Numero Protocollo</td>
								<td>Nome Piano</td>
								<td>Tipologia corso</td>
								<td>Tematica formativa</td>
								<td>N. part.</td>
								<td>Modulo 1</td>
								<td>Modulo 2</td>
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
							    <td>${listValue.nuemroProtocollo} </td>
								<td>${listValue.nomeProgetto}</td>
								<td>${listValue.tipoCorsoPiano}</td>
								<td>${listValue.tematicaFormativa}</td>
								<td>${listValue.numeroPartecipanti}</td>
								<c:choose>
									 <c:when test="${listValue.modulo1!='assente'}">
										<td>
										<c:choose>
											 <c:when test="${listValue.enabled==2||listValue.enabled==4}">
												 <p><b>Nome</b>
												 	${listValue.modulo1}
												 </p>
											 </c:when>
											 <c:otherwise>
												<p><b>Nome</b>
													<a onclick="elaboraPiano('${listValue.id}','${listValue.modulo1}','${listValue.fadMod1}','modulo','','')" title="vai al modulo">${listValue.modulo1}</a>
												</p>
											 </c:otherwise>
										</c:choose>
												<p><b>Modalita Formativa</b>
													${listValue.fadMod1}
												</p>
												<p ><b>Durata</b>
													${listValue.durataModulo1}
												</p>
											
										</td>
									 </c:when>
									 <c:otherwise>
									  <td>assente</td>
									 </c:otherwise>
								</c:choose>
								
								<c:choose>
									 <c:when test="${(listValue.modulo2!='assente')}">
										<td>
											<c:choose>
											 <c:when test="${listValue.enabled==2||listValue.enabled==4}">
												 <p><b>Nome</b>
												 	${listValue.modulo2}
												 </p>
											 </c:when>
											  <c:otherwise>
											    <p><b>Nome</b>
													<a onclick="elaboraPiano('${listValue.id}','${listValue.modulo2}','${listValue.fadMod2}','modulo','','')" title="vai al modulo">${listValue.modulo2}</a>
												</p>
											 </c:otherwise>
											</c:choose>
											<p><b>Modalita Formativa</b>
												${listValue.fadMod2}
											</p>
											<p ><b>Durata</b>
												${listValue.durataModulo2}
											</p>
										
										</td>
									</c:when>
									<c:otherwise>
									  <td>assente</td>
									 </c:otherwise>
								</c:choose>
								<td>${listValue.formeAiuti}</td>
								<td>
								   
									<c:if test="${listValue.categSvantagg=='1'}">
									  Si
									</c:if>
									<c:if test="${listValue.categSvantagg=='0'}">
									  No
									</c:if>
									<c:if test="${listValue.categSvantagg=='assente'}">
									 assente
									</c:if>
								</td>
								<td>${listValue.attuatorePIVA}</td>
								<td>
									<p>
										<c:if test="${not empty listValue.nomeAllegato1}">
											${listValue.nomeAllegato1}<br>
										</c:if>
									</p>
									<p>
										<c:if test="${not empty listValue.nomeAllegato2}">
											${listValue.nomeAllegato2}<br>
										</c:if>
									</p>
									<p>
										<c:if test="${not empty listValue.nomeAllegato3}">
										    ${listValue.nomeAllegato3}<br>
										</c:if>
									</p>
									<p>
										<c:if test="${not empty listValue.nomeAllegato4}">
										    ${listValue.nomeAllegato4}
										</c:if>
									</p>
								</td>
								<td>
									<c:choose>
											<c:when test="${listValue.enabled==2||listValue.enabled==4}">
											    <input type="image"  value="Rendicontazione" src= "resources/images/rendicontazione.png" alt="Rendicontazione progetto" title="Rendicontazione progetto"> 
											</c:when>
											<c:otherwise>  	
									      		<input type="image"  onclick="elaboraPiano('${listValue.id}','','','rendiconta','','');" value="Rendicontazione" src= "resources/images/rendicontazione.png" alt="Rendicontazione progetto" title="Rendicontazione progetto">
											</c:otherwise>
									</c:choose>	
								</td>
								<td>
									
									<div id="stato">
										  	
										
										
											<c:choose>
										    	<c:when test="${listValue.enabled==1}">
										    		<img src= "resources/images/ok.png" alt="inviabile" title="inviabile"/>
										    		<input type="image"  onclick="elaboraPiano('${listValue.id}','','','valida','','');" value="Valida piano" src= "resources/images/refresh.png" alt="Valida piano" title="Valida piano">
												</c:when>
												<c:when test="${listValue.enabled==2}"><img src= "resources/images/busy.png" alt="in trasmissione" title="in trasmissione"/></c:when>
												<c:when test="${listValue.enabled==3}">
										    		<input type="image"  onclick="elaboraPiano('${listValue.id}','','','erroriTrasmProgetto','','');" value="Mostra errori trasmissione progetto" src= "resources/images/errTrasmissione.png" alt="Mostra errori trasmissione progetto" title="Mostra errori trasmissione progetto">
													<input type="image"  onclick="elaboraPiano('${listValue.id}','','','validaTrasm','','');" value="Valida piano" src= "resources/images/refresh.png" alt="Valida piano" title="Valida piano">
												</c:when>
												<c:when test="${listValue.enabled==4}">
													<img   src= "resources/images/trasmissioneOk.png" alt="Piano trasmesso con successo" title="Piano trasmesso con successo">
												</c:when>
										    	<c:otherwise> 
															<input type="image"  onclick="elaboraPiano('${listValue.id}','','','erroriProgetto','','');" value="Mostra errori progetto" src= "resources/images/notOK.png" alt="Mostra errori progetto" title="Mostra errori progetto">
															<input type="image"  onclick="elaboraPiano('${listValue.id}','','','valida','','');" value="Valida piano" src= "resources/images/refresh.png" alt="Valida piano" title="Valida piano">
												</c:otherwise>
											</c:choose>
										
									  </div>
									
								</td>					
								<td >
									<div class="toggler" onmousedown="mostra('${listValue.id}'); " >
									<c:choose>
									   	<c:when test="${listValue.enabled==2}">Funzioni disabilitate
									   		<input type="image"  onclick="elaboraPiano('${listValue.id}','','','sblocca','','');" value="Sblocca piano" src= "resources/images/sbloccaPiano.png"  alt="Sblocca piano" title="Sblocca piano">
									   	</c:when>
									   	<c:when test="${listValue.enabled==4}">
									   		<div>
									   		  <input type="image"  onclick="elaboraPiano('${listValue.id}','','','cancella','','');" value="Cancella" src= "resources/images/elimina.png"  alt="Elimina piano" title="Elimina piano">
									   		</div>
									   	</c:when>
										<c:otherwise>
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
										 </c:otherwise>
									 </c:choose>
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
	            <sec:authorize access="hasRole('ROLE_ADMIN')">
				  <input type="button"  onclick="trasmissione()" value="Trasmetti Piani" >
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
			}else if(operazione=='valida'){
				$("#modificaPianoForm").attr('action','/FbaWebApp/adminValidaPiano');
				$("#modificaPianoForm").submit();
			}else if(operazione=='validaTrasm'){
				validaPianoTrasm();
			}else if(operazione=='erroriProgetto'){
				$("#modificaPianoForm").attr('action','/FbaWebApp/adminErroriPiano');
				$("#modificaPianoForm").submit();
			}else if(operazione=='erroriTrasmProgetto'){
				$("#modificaPianoForm").attr('action','/FbaWebApp/adminErroriTrasm');
				$("#modificaPianoForm").submit();
			}else if(operazione=='sblocca'){
				sbloccaPiano();
			}else{
				$("#modificaPianoForm").attr('action','/FbaWebApp/adminImplementaPianoForm');
				$("#modificaPianoForm").submit();
			}
		}
		
		
		function validaPianoTrasm(){
			$('<div></div>').appendTo('body')
			  .html('<div><h5>L&#8217;operazione cancella gli errori di trasmissione di FBA e valida il piano.</h5><h5>Procedere?</h5></div>')
			  .dialog({
			      modal: true, title: 'Validazione Piano Trasmesso', zIndex: 10000, autoOpen: true,
			      width: 'auto', resizable: false,
			      buttons: {
			          Si: function () {
			        	  $("#modificaPianoForm").attr('action','/FbaWebApp/adminValidaPiano');
							$("#modificaPianoForm").submit();
			              $(this).dialog("close");
			          },
			          No: function () {
			             // doFunctionForNo();
			              $(this).dialog("close");
			          }
			      },
			      close: function (event, ui) {
			          $(this).remove();
			      }
			});
		
		}
		
		
		function sbloccaPiano(){
			$('<div></div>').appendTo('body')
			  .html('<div><h5>L&#8217;operazione causer&agrave la perdita di coerenza del piano con quello trasmesso in FBA.</h5><h5>Procedere?</h5></div>')
			  .dialog({
			      modal: true, title: 'Sblocca Piano', zIndex: 10000, autoOpen: true,
			      width: 'auto', resizable: false,
			      buttons: {
			          Si: function () {
			        	  $("#modificaPianoForm").attr('action','/FbaWebApp/adminSbloccaPiano');
							$("#modificaPianoForm").submit();
			              $(this).dialog("close");
			          },
			          No: function () {
			             // doFunctionForNo();
			              $(this).dialog("close");
			          }
			      },
			      close: function (event, ui) {
			          $(this).remove();
			      }
			});
		
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
		}else if(operazione=='valida'){
			$("#modificaPianoForm").attr('action','/FbaWebApp/userValidaPiano');
			$("#modificaPianoForm").submit();
		}else if(operazione=='validaTrasm'){
			validaPianoTrasm();
		}else if(operazione=='erroriProgetto'){
			$("#modificaPianoForm").attr('action','/FbaWebApp/userErroriPiano');
			$("#modificaPianoForm").submit();
		}else if(operazione=='erroriTrasmProgetto'){
			$("#modificaPianoForm").attr('action','/FbaWebApp/userErroriTrasm');
			$("#modificaPianoForm").submit();
		}else if(operazione=='sblocca'){
			sbloccaPiano();
		}else{
			$("#modificaPianoForm").attr('action','/FbaWebApp/userImplementaPianoForm');
			$("#modificaPianoForm").submit();
		}
		
		function validaPianoTrasm(){
			$('<div></div>').appendTo('body')
			  .html('<div><h5>L&#8217;operazione cancella gli errori di trasmissione di FBA e valida il piano.</h5><h5>Procedere?</h5></div>')
			  .dialog({
			      modal: true, title: 'Validazione Piano Trasmesso', zIndex: 10000, autoOpen: true,
			      width: 'auto', resizable: false,
			      buttons: {
			          Si: function () {
			        	  $("#modificaPianoForm").attr('action','/FbaWebApp/userValidaPiano');
							$("#modificaPianoForm").submit();
			              $(this).dialog("close");
			          },
			          No: function () {
			             // doFunctionForNo();
			              $(this).dialog("close");
			          }
			      },
			      close: function (event, ui) {
			          $(this).remove();
			      }
			});
		
		}
		
		function sbloccaPiano(){
			$('<div></div>').appendTo('body')
			  .html('<div><h5>L&#8217;operazione causer&agrave la perdita di coerenza del piano con quello trasmesso in FBA.</h5><h5>Procedere?</h5></div>')
			  .dialog({
			      modal: true, title: 'Sblocca Piano', zIndex: 10000, autoOpen: true,
			      width: 'auto', resizable: false,
			      buttons: {
			          Si: function () {
			        	  $("#modificaPianoForm").attr('action','/FbaWebApp/userSbloccaPiano');
							$("#modificaPianoForm").submit();
			              $(this).dialog("close");
			          },
			          No: function () {
			             // doFunctionForNo();
			              $(this).dialog("close");
			          }
			      },
			      close: function (event, ui) {
			          $(this).remove();
			      }
			});
		
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
		
		function trasmissione(){
			$('<div></div>').appendTo('body')
			  .html('<div><h5>L&#8217;operazione tradurr&#224; in pi&#249; file, i piani validati di tutti gli utenti da trasmettere in FBA, disabilitandoli.</h5><h5>Procedere?</h5></div>')
			  .dialog({
			      modal: true, title: 'Trasmissione Piani', zIndex: 10000, autoOpen: true,
			      width: 'auto', resizable: false,
			      buttons: {
			          Si: function () {
			        	  $("#modificaPianoForm").attr('action','/FbaWebApp/adminTrasmettiPiani');
			  	   	 	  $("#modificaPianoForm").submit();
			              $(this).dialog("close");
			          },
			          No: function () {
			             // doFunctionForNo();
			              $(this).dialog("close");
			          }
			      },
			      close: function (event, ui) {
			          $(this).remove();
			      }
			});
		
		}
	</script>
	
</body>
</html>