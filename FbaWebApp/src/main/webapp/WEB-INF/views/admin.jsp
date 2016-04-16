<%@ include file="header.jsp" %>


	
	<div id="funzioniDiv">
		<c:if test="${pageContext.request.userPrincipal.name != null}">
			
			
			<input type="button"  onclick="location.href='/FbaWebApp/adminAddUserForm'" value="Aggiungi Utente" >
			<br>
			<br>
			<input type="button"  onclick="location.href='/FbaWebApp/admingetAllUser'" value="Gestisci Utenti" >
			<br>
			<br>
			<input type="button"  onclick="location.href='/FbaWebApp/welcome'" value="Indietro" >
		</c:if>
	</div>
	</body>
</html>