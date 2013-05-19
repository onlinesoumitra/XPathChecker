<%-- 
    Document   : FronPage
    Created on : Aug 5, 2011, 4:57:15 PM
    Author     : 245071
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="com.google.appengine.api.blobstore.BlobstoreService.*" %>

<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%
            BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>
<html>
    <head>
        <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="0">
        <script language="javascript">
            function Checkfiles()
            {
                var fup = document.getElementById('myFile');
                var fileName = fup.value;

                var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
                if(ext == "xml")
                {
                    return true;
                }
                else
                {
                    alert("Upload an XML File");
                    fup.focus();
                    return false;
                }
            }
        </script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>XSD Checking Tool</title>

        <style rel="stylesheet" type="text/css">
            body {
                color: #000000;
                background-color: #ffffff;
                font-family: arial, verdana, sans-serif;
                font-size: 14pt;
            }

            fieldset {
                font-size: 14px;
                width: 600px;
            }

            .formPrompt {
                text-align: right;
            }
            .footer, .push {
                background-color: #ffffff;
                height: 4em;
                font-size: 8pt;

            }

            input {
                border-style: solid;
                border-color: #000000;
                border-width: 1px;
                background-color: #f2f2f2;
            }

        </style>
    </head>
    <body  bgcolor="Silver">
        <center>
            <h2 style="background-color:skyblue; bottom:20">XPath Checking Tool</h2>
        </center>

        <form action="<%= blobstoreService.createUploadUrl("/FileUpload")%>" onsubmit="return Checkfiles();" method="post" enctype="multipart/form-data">
            <center>
                Browse The File to Check XPaths:
                <input type="file" name="myFile" id="myFile">

                <hr />

                <div align="center">
                    <fieldset>
                    <legend>Plese Select</legend>
                    <br>
                    <input type="radio" name="group1" value="All"> All Values<br>
                    <input type="radio" name="group1" value="First" checked> Only first Value<br>

                    </fieldset>
                </div>

     <center>
             <div align="center">
                    <fieldset>
                    <legend>Please Select the xpath type</legend>
                    <br>
                    <input type="radio" name="group2" value="StandardXPath" checked>Standard XPath<br>
                    <input type="radio" name="group2" value="DotFormat" > Dot Format<br>

                    </fieldset>
                </div>
                </center>

                <div style="font-size:12pt; color: black">
                    <fieldset>
                        <legend>Paste the xpaths Here</legend>

                        <table>
                            <td>
                                <TEXTAREA NAME="xpath" id="xpath" ROWS="10" cols="40"></TEXTAREA><br>
                            </td>

                        </table>

                    </fieldset>
                </div>
            </center>
       
            <center>
                <p><input type=submit value="Submit"></p>
            </center>
            <hr />

        </form>
        <center>
            <div class="footer">
                <p style="background-color:skyblue">Copyright (c) 2012 Developed by Soumitra Chatterjee,Arpish Chatterjee & Dwaipayan Mukherjee</p>
            </div>
        </center>
    </body>


</html>
