<%@ include file="header.jsp" %>

<div id="funzioniDiv">
    <sec:authorize access="hasRole('ROLE_USER')">
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
		 <c:url var="url" value="/userMostraPiani" />  
        <form:form action="${url}" method="post" modelAttribute="user" id="user" > 
        <form:hidden path="username" value="${pageContext.request.userPrincipal.name}"/>
        <P>Vai alla sezionone piani di formazione caricati</P>
        <input type="submit"   value="Carica Piani Di Formazione" >  
        </form:form>
        <br>

		<P>Vai alla sezione caricamento piani di formazione </P>
        <input type="button"  onclick="location.href='/FbaWebApp/userformUploadPiani'" value="Carica Piani Di Formazione" > 

	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
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

		
		<P>Vai alle funzioalità di amministratore</P>
        <input type="button"  onclick="location.href='/FbaWebApp/admin'" value="Funzionalità Amministratore" >
          <br>
        <c:url var="url" value="/adminMostraPiani" />  
        <form:form action="${url}" method="post" modelAttribute="user" id="user" > 
        <form:hidden path="username" value="${pageContext.request.userPrincipal.name}"/>
        <P>Vai alla sezionone piani di formazione caricati</P>
        <input type="submit"   value="Carica Piani Di Formazione" >  
        </form:form>
        <br>
        <P>Vai alla sezione caricamento piani di formazione</P>
        <input type="button"  onclick="location.href='/FbaWebApp/adminformUploadPiani'" value="Carica Piani Di Formazione" >
        
	</sec:authorize>
</div>
</body>
</html>