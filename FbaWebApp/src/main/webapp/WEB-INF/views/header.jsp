<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<head>
    <spring:url value="/resources/css/main.css" var="mainCSS" />
   <spring:url value="/resources/js/jquery-1.12.1.min.js" var="jqueryJs" /> 
    <spring:url value="/resources/js/jquery-ui-1.11.4.custom/jquery-ui.min.js" var="jqueryMin" />
    <spring:url value="/resources/js/jquery-ui-1.11.4.custom/jquery-ui.js" var="jqueryUI" /> 
   
   
    <link href="${mainCSS}" rel="stylesheet" />
   <script src="${jqueryJs}" type="text/javascript"></script>
    <script src="${jqueryMin}" type="text/javascript"></script>
    <script src="${jqueryUI}" type="text/javascript"></script> 
   
	
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Intesa San Paolo FBA</title>
</head>
<body>

      <div id="headerDiv" >
      <img alt="bnner" src="resources/images/testata_sfondo_blu_intesa.jpg">
        <h2> ${title}</h2>
		
		<h3> ${message}</h3>
		<h3> ${errorMessage}</h3>
	   
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
	
		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<h3 id="nomeUser">
				Benevunto : ${pageContext.request.userPrincipal.name} | <a
					href="javascript:formSubmit()"> Logout</a>
			</h3>
			
		</c:if>
	  </div>
