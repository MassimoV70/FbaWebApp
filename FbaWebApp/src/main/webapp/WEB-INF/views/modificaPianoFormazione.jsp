
<%@ include file="header.jsp" %>


	
	<div id="formDiv">
	
	 <sec:authorize access="hasRole('ROLE_USER')">
		<c:url var="url" value="/userModifyPiano" />
	 </sec:authorize>	
	 <sec:authorize access="hasRole('ROLE_ADMIN')">
	 	<c:url var="url" value="/adminModifyPiano" />
	 </sec:authorize>
			<form:form action="${url}" method="post" modelAttribute="pianoFormazioneForm" id="pianoFormModify">
			 	<div id="inputDiv">	
			 		<form:hidden path="username" value="${pageContext.request.userPrincipal.name}"/>
			 		<form:hidden path="id" />
					<label for="pianoDiFormazione">Titolo piano di formazione:</label>
					<br>
					<form:input path="pianoDiFormazione" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="pianoDiFormazione" cssClass="error" />
					<br>
					<label for="tipoCorsoPiano">Tipologia corso:</label>
					<br>
					<form:input path="tipoCorsoPiano" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="tipoCorsoPiano" cssClass="error" />
					<br>
					<label for="tematicaFormativa">Tematica formativa:</label>
					<br>
					<form:input path="tematicaFormativa" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="tematicaFormativa" cssClass="error" />
					<br>
					<label for="dataInizioAttStr">Inizio attivita formativa:</label>
					<br>
					<form:input path="dataInizioAttStr" disabled="${disabled}" id="datepicker" cssClass="inputUser"/>
					<form:errors path="dataInizioAttStr" cssClass="error" />
					<br>
					<label for="dataFineAttStr">Fine attivita formativa:</label>
					<br>
					<form:input path="dataFineAttStr" disabled="${disabled}" id="datepicker1" cssClass="inputUser"/>
					<form:errors path="dataFineAttStr" cssClass="error" />
					<br>
					<label for="numPartecipanti">Numero partecipanti:</label>
					<br>
					<form:input path="numPartecipanti" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="numPartecipanti" cssClass="error" />
					<br>
					<label for="compImprInn">Competitivita impresa innovazione:</label>
					<br>
					<form:select path="compImprInn" disabled="${disabled}" id="inputUser" cssClass="inputUser">
						<form:options items="${listaSelezione}"/>
					</form:select>
					<form:errors path="compImprInn" cssClass="error" />
					<br>
					<label for="compSett">Competitivita settoriale:</label>
					<br>
					<form:select path="compSett" disabled="${disabled}" id="inputUser" cssClass="inputUser">
						<form:options items="${listaSelezione}"/>
					</form:select>
					<form:errors path="compSett" cssClass="error" />
					<br>
					<label for="delocInter">Delocalizzazione internazionalizzazione:</label>
					<br>
					<form:select path="delocInter" disabled="${disabled}" id="inputUser" cssClass="inputUser">
						<form:options items="${listaSelezione}"/>
					</form:select>
					<form:errors path="delocInter" cssClass="error" />
					<br>
					<label for="formObblExLeg">Formazione obbligatoria ex leg:</label>
					<br>
					<form:select path="formObblExLeg" disabled="${disabled}" id="inputUser" cssClass="inputUser">
						<form:options items="${listaSelezione}"/>
					</form:select>
					<form:errors path="formObblExLeg" cssClass="error" />
					<br>
					<label for="formInIngresso">Formazione in ingresso:</label>
					<br>
					<form:select path="formInIngresso" disabled="${disabled}" id="inputUser" cssClass="inputUser">
						<form:options items="${listaSelezione}"/>
					</form:select>
					<form:errors path="formInIngresso" cssClass="error" />
					<br>
					<label for="mantenimOccup">Mantenimento occupazione:</label>
					<br>
					<form:select path="mantenimOccup" disabled="${disabled}" id="inputUser" cssClass="inputUser">
						<form:options items="${listaSelezione}"/>
					</form:select>
					<form:errors path="mantenimOccup" cssClass="error" />
					<br>
					<label for="manutAggComp">Manutenzione aggiornamento delle competenze:</label>
					<br>
					<form:select path="manutAggComp" disabled="${disabled}" id="inputUser" cssClass="inputUser">
						<form:options items="${listaSelezione}"/>
					</form:select>
					<form:errors path="manutAggComp" cssClass="error" />
					<br>
					<label for="mobEstOutRic">Mobilita esterna outplacement ricollocazione:</label>
					<br>
					<form:select path="mobEstOutRic" disabled="${disabled}" id="inputUser" cssClass="inputUser">
						<form:options items="${listaSelezione}"/>
					</form:select>
					<form:errors path="mobEstOutRic" cssClass="error" />
					<br>
					<label for="sviluppoLoc">Sviluppo locale:</label>
					<br>
					<form:select path="sviluppoLoc" disabled="${disabled}" id="inputUser" cssClass="inputUser">
						<form:options items="${listaSelezione}"/>
					</form:select>
					<form:errors path="sviluppoLoc" cssClass="error" />
					<br>
					<label for="modulo1">Titolo modulo1:</label>
					<br>
					<form:input path="modulo1" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="modulo1" cssClass="error" />
					<br>
					<label for="fadMod1">Modalita formativa modulo 1:</label>
					<br>
					<form:select path="fadMod1" disabled="${disabled}" id="inputUser" cssClass="inputUser">
						<form:options items="${listaSelezioneFad}"/>
					</form:select>
					<form:errors path="fadMod1" cssClass="error" />
					<br>
					<label for="modulo2">Titolo modulo2</label>
					<br>
					<form:input path="modulo2" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="modulo2" cssClass="error" />
					<br>
					<label for="fadMod2">Modalita formativa modulo 2:</label>
					<br>
					<form:select path="fadMod2" disabled="${disabled}" id="inputUser" cssClass="inputUser">
						<form:options items="${listaSelezioneFad}"/>
					</form:select>
					<form:errors path="fadMod2" cssClass="error" />
					<br>
					<label for="attuatorePIVA">Partita IVA attuatore:</label>
					<br>
					<form:input path="attuatorePIVA" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="attuatorePIVA" cssClass="error" />
					
					
				</div>
					<c:if test="${disabled !=  true}">
					  <input type="submit" />
					</c:if>
			</form:form>
		
		</div>
		<div id="bottoniDiv">
				<sec:authorize access="hasRole('ROLE_USER')">
					<input type="button"  onclick="location.href='/FbaWebApp/userMostraPiani'" value="Indietro" >
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<input type="button"  onclick="location.href='/FbaWebApp/adminMostraPiani'" value="Indietro" >
				</sec:authorize>
				
				<c:if test="${disabled ==  false}">
				  <input type="button"  onclick="location.href='/FbaWebApp/userMostraPiani'" value="Annulla" >
				</c:if>
		</div>
		
          <script type="text/javascript">
	           
	           $(function() {
	        	    $( "#datepicker" ).datepicker();
	        	    $( "#datepicker1" ).datepicker();
	        	  });
          </script>
</body>
</html>