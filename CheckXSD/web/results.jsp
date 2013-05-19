<%-- 
    Document   : results
    Created on : Apr 27, 2012, 11:58:58 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.ArrayList" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script language="javascript">
    window.history.forward(1);
    function noBack() { window.history.forward(1); }
      </script>
    </head>

    <body onload="noBack();"
    onpageshow="if (event.persisted) noBack();" onunload="">
        <center>
        <h1><%= session.getAttribute("theName")%></h1>
        </center>
        <center>
            <p>XPath not found count:<%=session.getAttribute("CountMismatch")%></p>
            <p>XPath found count: <%=session.getAttribute("CountMatch")%></p>
        </center>
        <center>
        <table bgcolor="#00FFFF" border="1" cellpadding="5" cellspacing="5" width="100%">
           <tr>
                <th bgcolor="#9966FF">XPaths</th>
                <th bgcolor="#9966FF">Check Result</th>
                <th bgcolor="#9966FF">Sample Node Value</th>
            </tr>
            <%
                    ArrayList ar_xpaths=(ArrayList)session.getAttribute("XPaths");
                    ArrayList ar_checking=(ArrayList)session.getAttribute("CkeckResult");
                     ArrayList nodeValue=(ArrayList)session.getAttribute("nodeValue");
                        if (session.getAttribute("XPaths")!=null) {
                            if (true) {
                                // use for loop
                                for (int i = 0; i
                                        <ar_xpaths.size() ; i++) {
            %>
            
            <tr align="center">
                <td><%= ""+ar_xpaths.get(i)%></td>
                <td><%= ""+ar_checking.get(i)%> </td>
                <td><%= ""+nodeValue.get(i)%> </td>
            </tr>
            <% }
                            }
                        }
        session.invalidate(); %>
        </table>
        </center>
       
    </body>

</html>
