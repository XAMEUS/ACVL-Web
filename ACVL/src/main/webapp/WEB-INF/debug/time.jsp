<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
        <%@include file="../style/style.jsp" %>
        <link href="style/homepage.css" rel="stylesheet">
    </head>
    <body>
        <main role="main" class="container">

            <form action="time" method="post">
                <div class="form-group">
                    <label for="date">System current Date:</label>
                    <input type="date" class="form-control" name="date" id="date" value="${date}">
                </div>
                <button type="submit" class="btn btn-primary">Changer</button>
            </form>
            <hr>
            <%@include file="debug.jsp" %>
        </main>
    </body>

    <%@include file="../style/js.jsp" %>
</html>
