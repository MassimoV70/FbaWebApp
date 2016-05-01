<%@ include file="../header.jsp" %>


       <div id="formDiv">
        <h2>Exception occured at: </h2><fmt:formatDate value="${exception.date}" pattern="yyyy-MM-dd" />
        <h2>Exception Message   : </h2>${exception.message}
        </div>
         <div id="funzioniDiv">
        	<input type="button"  onclick="location.href='/FbaWebApp/welcome'" value="Torna indietro" >
        </div>
    </body>

</html>