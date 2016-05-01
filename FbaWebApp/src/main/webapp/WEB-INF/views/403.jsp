<%@ include file="header.jsp" %>
	<h1>HTTP Status 403 - Access is denied</h1>
<div id="formDiv">
	<c:choose>
		<c:when test="${empty username}">
		  <h2>Non hai i permessi per accedere a questa pagina!k</h2>
		</c:when>
		<c:otherwise>
		  <h2>Username : ${username} <br/>
                   Non hai i permessi per accedere a questa pagina!</h2>
		</c:otherwise>
	</c:choose>
	
</div>
<div id="funzioniDiv">
        <input type="button"  onclick="location.href='/FbaWebApp/welcome'" value="Torna indietro" >
     </div>
</body>
</html>