<%@ include file="header.jsp" %>
 
 
 <div id="formDiv">
    <c:url value="/uploadPiano" var="logoutUrl" />
 	<form:form modelAttribute="fileBean" action="${logoutUrl}" method="post" enctype="multipart/form-data">
 	     <div id="inputDiv">	
	 	     <label>File Excel Piani di Formazione:</label>
			 <br>
			 <form:input type="file" path="fileData" disabled="${disabled}" id="fileData" cssClass="inputUser" />
 	    	 <c:if test="${disabled !=  true}">
			  	<input type="submit" />
			 </c:if>
 	     </div>	
 	     
 	</form:form>
    
 
 
 </div>

</body>
</html>