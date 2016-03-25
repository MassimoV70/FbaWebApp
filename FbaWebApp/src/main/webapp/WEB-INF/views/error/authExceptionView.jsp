<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <title>Authentication Exception</title>
    </head>
     
    <body>
 
        <h2>Exception occured at: </h2><fmt:formatDate value="${exception.date}" pattern="yyyy-MM-dd" />
        <h2>Exception Message   : </h2>${exception.message}
    </body>

</html>