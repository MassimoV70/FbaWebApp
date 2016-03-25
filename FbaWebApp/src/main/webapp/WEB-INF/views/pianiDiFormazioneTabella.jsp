<%@ include file="header.jsp" %>
<div class="CSSTableGenerator" >
	<table>
		<c:if test="${not empty listaPiani}">
	
				<tr>
					<td>Piano Di Formazione</td>
					<td>Modulo 1</td>
					<td>Modulo 2</td>
					<td>Attuatore P.IVA</td>
					<td>Azioni</td>
				</tr>
				<c:forEach var="listValue" items="${listaPiani}">
				  <tr>
					<td>${listValue.pianoDiFormazione}</td>
					<td>${listValue.modulo1}</td>
					<td>${listValue.modulo2}</td>
					<td>${listValue.attuatorePIVA}</td>
					<td>
						<input type="image"  onclick="gestisciUtente('${listValue.modulo1}','abilita');" value="Indietro" src="resources/images/enable.png" alt="abilit&grave">
						<input type="image"  onclick="gestisciUtente('${listValue.modulo2}','modifica');" value="Indietro" src= "resources/images/settings.png" alt="modifica">
						<input type="image"  onclick="gestisciUtente('${listValue.attuatorePIVA}','disabilita');" value="Indietro" src= "resources/images/disable.png"  alt="disabilit&grave">
				
					</td>
				   </tr>
				</c:forEach>
			   
			</c:if>
	</table> 
	</div>
</body>
</html>