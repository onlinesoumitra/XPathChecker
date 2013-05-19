<%-- 
    Document   : Error
    Created on : Apr 29, 2012, 12:55:06 AM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <center>
        <h1>Error :</h1>
        <p><%=session.getAttribute("errormessage")%>\Error in xml</p>
        </center>
    </body>
</html>
