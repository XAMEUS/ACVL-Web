<%-- 
    Document   : bdErreur
    Created on : Mar 22, 2012, 12:32:53 AM
    Author     : reignier
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>Database Error</title>
    </head>
    <body>
        <h1 style="text-align: center">Database Error</h1>
        <img src="images/scared.png" alt="" />
        <p>Message : </p>
        <pre>${errorMessage}</pre>
</body>
</html>
