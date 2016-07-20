
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
			 		<form:hidden path="enabled" />
			 		<label for="nuemroProtocollo">Numero protocollo:</label>
					<br>
					<form:input path="nuemroProtocollo" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="nuemroProtocollo" cssClass="error" />
					<br>
					<label for="nomeProgetto">Titolo progetto:</label>
					<br>
					<form:input path="nomeProgetto" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="nomeProgetto" cssClass="error" />
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
					<label for="tematicaFormativa">Numero partecipanti:</label>
					<br>
					<form:input path="numeroPartecipanti" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="numeroPartecipanti" cssClass="error" />
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
					<label for="durataModulo1">Durata modulo1</label>
					<br>
					<form:input path="durataModulo1" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="durataModulo1" cssClass="error" />
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
					<label for="durataModulo2">Durata modulo2</label>
					<br>
					<form:input path="durataModulo2" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="durataModulo2" cssClass="error" />
					<br>
					<label for="formeAiuti">Forme di aiuti</label>
					<br>
					<form:input path="formeAiuti" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="formeAiuti" cssClass="error" />
					<br>
					<label for="categSvantagg">Categorie svantaggiate</label>
					<br>
					<form:select path="categSvantagg" disabled="${disabled}" id="inputUser" cssClass="inputUser">
						<form:options items="${listaSelezione}"/>
					</form:select>
					<form:errors path="categSvantagg" cssClass="error" />
					<br>
					<label for="attuatorePIVA">Partita IVA attuatore:</label>
					<br>
					<form:input path="attuatorePIVA" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="attuatorePIVA" cssClass="error" />
					
					
				</div>
					<c:if test="${disabled !=  true}">
					  <input type="submit" value="Salva"/>
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