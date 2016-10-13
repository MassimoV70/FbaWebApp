<%@ include file="header.jsp" %>
 <c:choose>
  	 <c:when test="${not empty listaErroriProgetto}">
		<div class="CSSTableGenerator" >
			<table>
				   		<tr>
							<td>Oggetto errore</td>
							<td>Errore</td>
						</tr>
						<c:forEach var="listValue" items="${listaErroriProgetto}">
						  <tr>
							<td>${listValue.oggettoErrore}</td>
							<td>${listValue.errore}</td>
						  </tr>
						</c:forEach>
			</table> 
		</div>
	</c:when>
	<c:otherwise>
			  <div id="formDiv">
			  	<p align="center">Non ci sono errori da visualizzare <br>
			  	 <img src= "resources/images/ok.png" alt="piano validato" title="piano validato" />
			    </p>
			  </div>
	</c:otherwise>
</c:choose>
<div id="bottoniDiv">
	            <br>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
				 <input type="button"  onclick="location.href='/FbaWebApp/adminMostraPiani'" value="Indietro" >
				  
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
				  <input type="button"  onclick="location.href='/FbaWebApp/userMostraPiani'" value="Indietro" >
				</sec:authorize>
	</div>
	
	
</body>
</html>