
<%@ include file="header.jsp" %>


	
	<div id="formDiv">
		
			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<c:url var="url" value="/adminModificaRendicontazione" />
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_USER')">
				<c:url var="url" value="/userModificaRendicontazione" />
			</sec:authorize>
		
		
			<form:form action="${url}" method="post" modelAttribute="rendicontazioneBeanForm" id="idRendicontazioneBeanForm" >
			 	<div id="inputDiv">	
			 		<form:hidden path="id"  id="id" />
			 		<form:hidden path="idPiano"  id="idPiano"/>
		           
		            <form:hidden path="stato"  id="stato" />
		            
		            <label for="tipologiaGiustificativo">Tipologia giustificativo:</label>
					<br>
					<form:input path="tipologiaGiustificativo" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="tipologiaGiustificativo" cssClass="error" />
					<br>
		            <label for="codice">Codice:</label>
					<br>
					<form:input path="codice" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="codice" cssClass="error" />
					<br>
					<label for="dataGiustificativoStr">Data giustificativo:</label>
					<br>
					<form:input path="dataGiustificativoStr" disabled="${disabled}" id="datepicker" cssClass="inputUser"/>
					<form:errors path="dataGiustificativoStr" cssClass="error" />
					<br>
					<label for="fornitoreNominativo">Fornitore nominativo:</label>
					<br>
					<form:input path="fornitoreNominativo" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="fornitoreNominativo" cssClass="error" />
					<br>
					<label for="valoreComplessivo">Valore complessivo:</label>
					<br>
					<form:input path="valoreComplessivo" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="valoreComplessivo" cssClass="error" />
					<br>
					<label for="contributoFBA">Contributo FBA:</label>
					<br>
					<form:input path="contributoFBA" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="contributoFBA" cssClass="error" />
					<br>
					<label for="contributoPrivato">Contributo privato:</label>
					<br>
					<form:input path="contributoPrivato" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="contributoPrivato" cssClass="error" />
					<br>
					<label for="nomeAllegato">Nome allegato:</label>
					<br>
					<form:input path="nomeAllegato" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="nomeAllegato" cssClass="error" />
					<br>
					
				</div>
					<c:if test="${disabled !=  true}">
					  <input type="submit" value="Salva"/>
					</c:if>
			</form:form>
		
		</div>
		<div id="bottoniDiv">
		
				<sec:authorize access="hasRole('ROLE_USER')">
					<c:url var="urlIndietro" value="/userMostraRendicontazione"/>
				 </sec:authorize>	
				 <sec:authorize access="hasRole('ROLE_ADMIN')">
				 	<c:url var="urlIndietro" value="/adminMostraRendicontazione" />
				 </sec:authorize>
				
				<form:form action="${urlIndietro}" method="POST" modelAttribute="pianoFormazioneForm" id="idPianoFormazoneForm" >
					<form:hidden path="id"  id="idPiano"/>
		        </form:form>
		         <input type="button"  onclick="indietro();" value="indietro" >
				<c:if test="${disabled ==  false}">
				  <input type="button"  onclick="location.href='/FbaWebApp/welcome'" value="Annulla" >
				</c:if>
		</div>
			
          <script type="text/javascript">
	           
	           
	           $(function() {
	        	    $( "#datepicker" ).datepicker();
	        	   
	        	  });
          </script>
    
		<script type="text/javascript">
			function indietro(){
			   	$("#idPianoFormazoneForm").submit();
				
				
			}
		</script>
	
		
</body>
</html>