<%@ include file="header.jsp" %>
 <c:choose>
  	 <c:when test="${not empty listaDatiFiles}">
		<div class="CSSTableGenerator" >
			<table>
						<tr>
							<td>Nome File</td>
							<td>Dimensione File</td>
							<td>Errore</td>
							<td>Stato</td>
						</tr>
						<c:forEach var="listValue" items="${listaDatiFiles}">
						  <tr>
							<td>${listValue.nomeFile}</td>
							<td>${listValue.sizeFile}</td>
							<td>${listValue.errore}</td>
							
							<td>
								<c:choose>
								    <c:when test="${not empty listValue.errore}"><img src= "resources/images/notOK.png" alt="Errore" title="Errore"/></c:when>
									<c:otherwise> <img src= "resources/images/ok.png" alt="Ok" title="Ok"/></c:otherwise>
								</c:choose> 
							</td>					
					
						   </tr>
						</c:forEach>
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
				  
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
				  <input type="button"  onclick="location.href='/FbaWebApp/welcome'" value="Indietro" >
				   
				</sec:authorize>
	</div>
	
	
	
	
	
</body>
</html>