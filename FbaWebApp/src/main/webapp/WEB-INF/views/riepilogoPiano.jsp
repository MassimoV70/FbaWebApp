<%@ include file="header.jsp" %>

<div id="tabs">
 <ul>
    <li><a href="#tabs-1">Dati Piano</a></li>
    <li><a href="#tabs-2">Modulo 1</a></li>
    <li><a href="#tabs-3">Modulo 2</a></li>
    <li><a href="#tabs-4">Rendicontazione</a></li>
  </ul>
  <div id="tabs-1">
               
                    <p><span  class="label1">Numero protocollo:</span>
				    ${pianoFormazioneForm.nuemroProtocollo}</p>
                
					
					<p><span  class="label1">Titolo progetto:</span>
				    ${pianoFormazioneForm.nomeProgetto}</p>
				
					
					<p><span  class="label1">Tipologia corso:</span>
					${pianoFormazioneForm.tipoCorsoPiano}</p>
					
					<p><span  class="label1">Tematica formativa:</span>
					${pianoFormazioneForm.tematicaFormativa}</p>
					
					<p><span  class="label1">Numero partecipanti:</span>
					${pianoFormazioneForm.numeroPartecipanti}</p>
					<br>
					<p><span  class="label1">Titolo modulo1:</span>
					${pianoFormazioneForm.modulo1}</p>
					
					<p><span  class="label1">Modalita formativa modulo 1:</span>
					${pianoFormazioneForm.fadMod1}</p>
					
					<p><span  class="label1">Durata modulo1:</span>
					${pianoFormazioneForm.durataModulo1}</p>
					<br>
					<p><span  class="label1">Titolo modulo2:</span>
					${pianoFormazioneForm.modulo2}</p>
					
					<p><span  class="label1">Modalita formativa modulo 2:</span>
					${pianoFormazioneForm.fadMod2}</p>
					
					<p><span  class="label1">Durata modulo2:</span>
					${pianoFormazioneForm.durataModulo2}</p>
					<br>
					<p><span  class="label1">Forme di aiuti:</span>
					${pianoFormazioneForm.formeAiuti}</p>
					
					<p><span  class="label1">Categorie svantaggiate:</span>
					<c:if test="${pianoFormazioneForm.categSvantagg=='1'}">
									  Si
									</c:if>
									<c:if test="${pianoFormazioneForm.categSvantagg=='0'}">
									  No
									</c:if>
					</p>
					
					<p><span  class="label1">Partita IVA attuatore:</span>
					${pianoFormazioneForm.attuatorePIVA}</p>
				
     	
  
  
  
  
  </div>
  <div id="tabs-2">
   <c:choose>
	  	 <c:when test="${not empty listaLavoratori1}">
			<div>
				<table id="table_id1a" class="display">
				<caption>Dati Lavoratore</caption>
					<thead>
						<tr>
							<td>Numero Matricola</td>
							<td>Ore Presenza</td>
							<td>Esito test</td>
							<td>Allegato</td>
						</tr>
				 </thead>
			 	 <tbody>
						<c:forEach var="listValue" items="${listaLavoratori1}">
						  <tr>
							<td>${listValue.matricola}</td>
							<td>${listValue.orePresenza}</td>
							<td>${listValue.esitoTest}</td>
							<td>${listValue.nomeAllegato}</td>
						  </tr>
						</c:forEach>
				 </tbody>
				</table> 
			</div>
		</c:when>
		<c:otherwise>
				  <div id="formDiv">
				  	<p align="center">Non ci sono lavoratori associati al modulo <br></p>
				  </div>
		</c:otherwise>
	</c:choose>
     
	 <c:choose>
	  	 <c:when test="${not empty listaCalendario1}">
			<div>
			<table id="table_id1b" class="display">
			<caption>Calendario</caption>
				<thead>
				<tr>
					<td>Data</td>
					<td>Inizio Mattina</td>
					<td>Fine Mattina</td>
					<td>Inizio Pomeriggio</td>
					<td>Fine Pomeriggio</td>
					
				</tr>
				</thead>
			<tbody>
				<c:forEach var="listValue" items="${listaCalendario1}">
				  <tr>
					<td>${listValue.dataStr}</td>
					<td>${listValue.inizioMattina}</td>
					<td>${listValue.fineMattina}</td>
					<td>${listValue.inizioPomeriggio}</td>
					<td>${listValue.finePomeriggio}</td>
				 </tr>
				</c:forEach>
			</tbody>		
			</table> 
			</div>
		</c:when>
		<c:otherwise>
				  <div id="formDiv">
				  	<p align="center">Il calendario non è presente <br> </p>
				  </div>
		</c:otherwise>
	</c:choose>
</div>
 <div id="tabs-3">
   <c:choose>
	  	 <c:when test="${not empty listaLavoratori2}">
			<div>
				<table id="table_id2a" class="display">
				<caption>Dati Lavoratore</caption>
					<thead>
						<tr>
							<td>Numero Matricola</td>
							<td>Ore Presenza</td>
							<td>Esito test</td>
							<td>Allegato</td>
							
						</tr>
				 </thead>
			 	 <tbody>
						<c:forEach var="listValue" items="${listaLavoratori2}">
						  <tr>
							<td>${listValue.matricola}</td>
							<td>${listValue.orePresenza}</td>
							<td>${listValue.esitoTest}</td>
							<td>${listValue.nomeAllegato}</td>
						  </tr>
						</c:forEach>
				 </tbody>
				</table> 
			</div>
		</c:when>
		<c:otherwise>
				  <div id="formDiv">
				  	<p align="center">Non ci sono lavoratori associati al modulo <br></p>
				  </div>
		</c:otherwise>
	</c:choose>
     
	 <c:choose>
	  	 <c:when test="${not empty listaCalendario2}">
			<div>
			<table id="table_id2b" class="display">
			<caption>Calendario</caption>
				<thead>
				<tr>
					<td>Data</td>
					<td>Inizio Mattina</td>
					<td>Fine Mattina</td>
					<td>Inizio Pomeriggio</td>
					<td>Fine Pomeriggio</td>
					
				</tr>
				</thead>
			<tbody>
				<c:forEach var="listValue" items="${listaCalendario2}">
				  <tr>
					<td>${listValue.dataStr}</td>
					<td>${listValue.inizioMattina}</td>
					<td>${listValue.fineMattina}</td>
					<td>${listValue.inizioPomeriggio}</td>
					<td>${listValue.finePomeriggio}</td>
				 </tr>
				</c:forEach>
			</tbody>		
			</table> 
			</div>
		</c:when>
		<c:otherwise>
				  <div id="formDiv">
				  	<p align="center">Il calendario non è presente <br></p>
				  </div>
		</c:otherwise>
	</c:choose>
</div>
 <div id="tabs-4">
	 <c:choose>
	  <c:when test="${not empty listaRendicontazione}">
	  <div>
	   <table id="table_id3" class="display">
				<caption>Rendicontazione</caption>		
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
								
							   </tr>
							</c:forEach>
						</tbody>
				</table>
			</div>
			</c:when>
			<c:otherwise>
				<div id="formDiv">
					  <p align="center">La rendicontazione non è presente. <br></p>
				</div>
			</c:otherwise>
	</c:choose> 
 </div>
<div id="bottoniDiv">
	            <br>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
				 <input type="button"  onclick="location.href='/FbaWebApp/adminMostraPiani'" value="Indietro" >
				  
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
				  <input type="button"  onclick="location.href='/FbaWebApp/userMostraPiani'" value="Indietro" >
				</sec:authorize>
	</div>
	
 <script>
 $(document).ready(function() {
		
		$("#table_id1a,#table_id1b,#table_id2a,#table_id2b,#table_id3").DataTable({
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
  $( function() {
    $( "#tabs" ).tabs();
  } );
  </script>
	
	
</body>
</html>