<%@ include file="header.jsp" %>
	
<div id="formDiv">
<h1 align="center">Accesso Negato</h1>
	<c:choose>
		<c:when test="${empty username}">
		  <h2>Non hai i permessi per accedere a questa pagina.</h2>
		</c:when>
		<c:otherwise>
		  <h2>Username : ${username} <br/>
                   Non hai i permessi per accedere a questa pagina.</h2>
		</c:otherwise>
	</c:choose>
	
</div>
<div id="funzioniDiv">
        <input type="button"  onclick="location.href='/FbaWebApp/login'" value="Torna indietro" >
     </div>
</body>
</html>