
<%@ include file="header.jsp" %>


	
	<div id="formDiv">
		<c:url var="url" value="/adminModifyUser" />
		
			<form:form action="${url}" method="post" modelAttribute="userFormModify" id="userFormModify" commandName="userFormModify">
			 	<div id="inputDiv">	
					<label for="nome">Nome:</label>
					<br>
					<form:input path="nome" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="nome" cssClass="error" />
					<br>
					<label for="cognome">Cognome:</label>
					<br>
					<form:input path="cognome" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="cognome" cssClass="error" />
					<br>
					<label for="username">Username:</label>
					<br>
					<form:input path="username" disabled="true" id="inputUser" cssClass="inputUser"/>
					<form:errors path="username" cssClass="error" />
					<form:hidden path="username"  id="idModulo" />
					<br>
					<label for="password">Password:</label>
					<br>
					<form:input path="password" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="password" cssClass="error" />
					<br>
					<label for="email">Email:</label>
					<br>
					<form:input path="email" disabled="${disabled}" id="inputUser" cssClass="inputUser"/>
					<form:errors path="email" cssClass="error" />
					<br>
					<label for="enabled">Stato:</label>
					<br>
					<form:select path="enabled" disabled="${disabled}" id="inputUser" cssClass="inputUser">
						<form:options items="${listaStati}"/>
					</form:select>
					<form:errors path="enabled" cssClass="error" />
					<br>
					<label for="dataInizioStr">Data Inizio Attivit&agrave:</label>
					<br>
					<form:input path="dataInizioStr" disabled="${disabled}" id="datepicker" cssClass="inputUser"/>
					<form:errors path="dataInizioStr" cssClass="error" />
					<br>
					<label for="dataFineStr">Data Fine Attivit&agrave:</label>
					<br>
					<form:input path="dataFineStr" disabled="${disabled}" id="datepicker1" cssClass="inputUser"/>
					<form:errors path="dataFineStr" cssClass="error" />
					<br>
				</div>
					<c:if test="${disabled !=  true}">
					  <input type="submit" />
					</c:if>
			</form:form>
		
		</div>
		<div id="bottoniDiv">
				
				<input type="button"  onclick="location.href='/FbaWebApp/admingetAllUser'" value="Indietro" >
				
				<c:if test="${disabled ==  false}">
				  <input type="button"  onclick="location.href='/FbaWebApp/welcome'" value="Annulla" >
				</c:if>
		</div>
		</div>	
          <script type="text/javascript">
	           function cancellaForm(){
	        	   $('#nome').val("");
	        	   $('#cognome').val("");
	        	   $('#username').val("");
	        	   $('#password').val("");
	        	   $('#email').val("");
	        	   $('#datepicker1').val("");
	        	       
	           }
	           
	           $(function() {
	        	    $( "#datepicker" ).datepicker();
	        	    $( "#datepicker1" ).datepicker();
	        	  });
          </script>
</body>
</html>