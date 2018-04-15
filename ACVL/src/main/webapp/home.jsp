<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@include file="WEB-INF/style/style.jsp" %>
    </head>
    <body>
        <%@include file="WEB-INF/debug/debug.jsp" %>
        <%
            if(session.getAttribute("username") != null) {
                out.println("Hi " + session.getAttribute("username"));
            } else { %>
                <form method="post" action="account" accept-charset="UTF-8">
                    <input type="hidden" name="action" value="signin">
                    <input type="text" name="username" placeholder="username">
                    <input type="password" name="password" placeholder="password">
                    <input type="submit" value="login">
                </form>
                <a href="account?action=register">register</a>
            <%}%>
    </body>

    <%@include file="WEB-INF/style/js.jsp" %>
</html>
