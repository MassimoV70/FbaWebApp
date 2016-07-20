<%@ include file="header.jsp" %>




	<sec:authorize access="hasRole('ROLE_USER')">
		<c:url var="url" value="./userLavoratoriUpload?" />
		<c:url var="urlCancellaForm" value="./userCancellaLavoratoriFile" />
	 </sec:authorize>	
	 <sec:authorize access="hasRole('ROLE_ADMIN')">
	 	<c:url var="url" value="/adminLavoratoriUpload?" />
	 	<c:url var="urlCancellaForm" value="/adminCancellaLavoratoriFile" />
	 </sec:authorize>
<div id="contenitore">
	<div id="formDivInputFile">
	   
	 	<form:form id="UploadForm" modelAttribute="uploadForm" action="${url}${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data" >
	 	    
	 	     <div id="inputDiv">
	 	     <input id="addFile" type="button" value="Aggiungi File" />
				<table id="fileTable">
					<tr>
						<td><input id="index0" name="listaFile[0]" type="file" /></td>
						<td><input id="indeximg0" type="image"  onclick="pulisciFile('0'); return false;" value="pulisci" src= "resources/images/pulisci.png"  alt="pulisci" title="pulisci"/></td>
						<td><input id="indeximgEl0" type="image"  onclick="cancellaFile('0'); return false;" value="elimina" src= "resources/images/elimina.png"  alt="elimina" title="elimina"/></td>
					    
					</tr>
				</table>
				 <br/><input id="blockButton" type="button"  onclick="upload();" value="Upload" /> 
	 	         
	 	         
	 	     </div>
	 	</form:form>
	  
	  </div>
	  <div id="tabellaFile">
	  <c:choose>
	  	 <c:when test="${not empty listaFileLavoratori}">
				<div  >
				<table id="table_id" class="display">
				<thead>
							<tr>
								<td>Nome File</td>
								<td>Azioni</td>
							</tr>
				</thead>
				<tbody>
							<c:forEach var="listValue" items="${listaFileLavoratori}">
							  <tr>
								<td>${listValue.nomeAllegato}</td>
								<td>
									<input type="image"  onclick="cancella(${listValue.id});" value="Cancella" src= "resources/images/elimina.png"  alt="Elimina file" title="Elimina file">
								</td>					
						
							   </tr>
							</c:forEach>
				</tbody>
				</table> 
				</div>
			 </c:when>
			 <c:otherwise>
				  <div id="formDiv">
				  	<p align="center">Non ci sono elementi da visualizzare</p>
				  </div>
			  </c:otherwise>
		</c:choose>
	  </div>
	 
 </div>	
	<div id="bottoniDivInputFile">
   <input type="button"  onclick="location.href='/FbaWebApp/welcome'" value="Indietro" />
  </div>
  <form:form action="${urlCancellaForm}" method="POST" modelAttribute="lavoratoriFileBean" id="cancellaFile" >
		<form:hidden path="id"  id="idFile" />
		
	</form:form>
 

<script type="text/javascript">
$(document).ready(function() {
	 //$('#uploadAvviso').hide();
	//add more file components if Add is clicked
	$('#addFile').click(function() {
		var fileIndex = $('#fileTable tr').children().length - 1;
		$('#fileTable').append(
				'<tr><td>'+
				'<input id = "index'+ fileIndex +'" type="file" name="listaFile['+ fileIndex +']"/>'+
				'</td><td>'+'<input  id = "indeximg'+ fileIndex +'" type="image"  onclick="pulisciFile('+ fileIndex +'); return false;" value="pulisci" src= "resources/images/pulisci.png"  alt="pulisci" title="pulisci"/>'+
				'</td><td>'+'<input  id = "indeximgEl'+ fileIndex +'" type="image"  onclick="cancellaFile('+ fileIndex +'); return false;" value="elimina" src= "resources/images/elimina.png"  alt="elimina" title="elimina"/>'+'</td></tr>');
	});
	
	$(document).ready(function() {
		
		$("#table_id").DataTable({
			"language":{
				"lengthMenu":"Mostra _MENU_ righe",
				"info": "Pagina _PAGE_ di _PAGES_ ",
				"infoFiltered" : "filtrate da _MAX_ pagine totali",
				"search": "Cerca",
				
				"oPaginate":{
					"sFirst": "Primo",
					"sLast": "Ultimo",
					"sNext": "Avanti",
					"sPrevious": "Indietro"
				}
			}	
		
		});
	})
		
	
});

function cancellaFile(indice){
	   
	   $('#index'+indice+'').val("");
	   $('#index'+indice+'').hide();
	   $('#indeximg'+indice+'').hide();
	   $('#indeximgEl'+indice+'').hide();
	   
	  
}

function pulisciFile(indice){
	  
	   $('#index'+indice+'').val("");
}



function upload(){
	
	
	 $.blockUI({ message: '<h3><img src="resources/images/waiting.gif" align="bottom" />  Attendere prego...</h3>' });
	   $('formDiv').block({ 
            message: '<h1>Elaborazione</h1>', 
            css: { border: '3px solid #a00' } 
            
        });  
   
	  
	   $("#UploadForm").submit();
}

function cancella(idfile){
	
	 $('#idFile').val(idfile);
	 $('#cancellaFile').submit();
}

</script>


</body>
</html>

