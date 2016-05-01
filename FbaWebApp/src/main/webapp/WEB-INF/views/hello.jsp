<%@ include file="header.jsp" %>

<div id="formDiv">
    <sec:authorize access="hasRole('ROLE_USER')">
	    <div id="bottoniDiv">	
			<!-- For login user -->
			<c:url value="/j_spring_security_logout" var="logoutUrl" />
			<form action="${logoutUrl}" method="post" id="logoutForm">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
			<script>
				function formSubmit() {
					document.getElementById("logoutForm").submit();
				}
			</script>
			 
	        <P>Vai alla sezionone piani di formazione caricati</P>
	        <input type="button"  onclick="location.href='/FbaWebApp/userMostraPiani'" value="Visualizza piani di formazione" >
	        <br>
	
			<P>Vai alla sezione caricamento piani di formazione </P>
	        <input type="button"  onclick="location.href='/FbaWebApp/userformUploadPiani'" value="Carica Piani Di Formazione" > 
		</div>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
	<div id="bottoniDiv">	
		<!-- For login user -->
		<c:url value="/j_spring_security_logout" var="logoutUrl" />
		<form action="${logoutUrl}" method="post" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>
		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
		</script>

		
		<P>Vai alle funzionalità di amministratore</P>
		
        <input type="button"  onclick="location.href='/FbaWebApp/admin'" value="Funzionalità Amministratore" >
          <br>
        
        <P>Vai alla sezionone piani di formazione caricati</P>
        <input type="button"  onclick="location.href='/FbaWebApp/adminMostraPiani'" value="Visualizza piani di formazione" >
        <br>
        <P>Vai alla sezione caricamento piani di formazione</P>
        <input type="button"  onclick="location.href='/FbaWebApp/adminformUploadPiani'" value="Carica Piani Di Formazione" >
      </div>  
	</sec:authorize>
</div>
</body>
</html>