<%@ include file="header.jsp" %>

<div id="formDiv">
    <sec:authorize access="hasRole('ROLE_USER')">
    <div id="bottoniDiv ">	
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
		 
       <P>Vai alla sezione calendario caricato</P>
        <input type="button"  onclick="location.href='/FbaWebApp/adminMostraPiani'" value="Visualizza Calendario Modulo" >
        <br>
        <P>Vai alla sezione di caricamento del calendario</P>
        <input type="button"  onclick="location.href='/FbaWebApp/adminformUploadPiani'" value="Carica Calendario Modulo" >
        <br>
         <P>Vai alla sezione lavoratori associati</P>
        <input type="button"  onclick="location.href='/FbaWebApp/adminMostraPiani'" value="Vissualiza lavoratori" >
        <br>
        <P>Vai alla sezione di caricamento lavoratori associati</P>
        <input type="button"  onclick="location.href='/FbaWebApp/adminformUploadPiani'" value="Carica lavoratori" >
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
        
        <P>Vai alla sezionone calendario caricato</P>
        <input type="button"  onclick="location.href='/FbaWebApp/adminMostraPiani'" value="Visualizza Calendario Modulo" >
        <br>
        <P>Vai alla sezione di caricamento del calendario</P>
        <input type="button"  onclick="location.href='/FbaWebApp/adminformUploadPiani'" value="Carica Calendario Modulo" >
        <br>
        <P>Vai alla sezionone lavoratori associati</P>
        <input type="button"  onclick="location.href='/FbaWebApp/adminMostraPiani'" value="Vissualiza lavoratori" >
        <br>
        <P>Vai alla sezione di caricamento lavoratori associati</P>
        <input type="button"  onclick="location.href='/FbaWebApp/adminformUploadPiani'" value="Carica lavoratori" >
      </div>  
	</sec:authorize>
</div>
</body>
</html>