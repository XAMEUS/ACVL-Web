<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>S'inscrire</title>
        <%@include file="style/style.jsp" %>
        <link href="style/homepage.css" rel="stylesheet">
        <link href="style/signin.css" rel="stylesheet">
    </head>
    <body class="text-center">
        <main role="main" class="container">
            <%@include file="debug/debug.jsp" %>

            <form class="form-signin" method="post" action="account" accept-charset="UTF-8">
                <h1 class="h3 mb-3 font-weight-normal">S'inscrire</h1>          
                <input type="hidden" name="action" value="create">
                <div class="form-group">
                    <label for="username">Nom d'utilisateur</label>
                    <input class="form-control" type="text" name="username"
                           placeholder="Nom d'utilisateur" required="" autofocus
                           oninvalid="this.setCustomValidity('Ce champ est requis.')"
                           oninput="setCustomValidity('')">
                </div>
                <div class="form-group">
                    <label for="password">Mot de passe</label>
                    <input class="form-control" type="password" name="password"
                           placeholder="Mot de passe" required=""
                           oninvalid="this.setCustomValidity('Ce champ est requis.')"
                           oninput="setCustomValidity('')">
                </div>
                <div class="form-group">
                    <label for="password">Coordonnées</label>
                    <input class="form-control" type="text" name="address"
                           placeholder="Coordonéees" required=""
                           oninvalid="this.setCustomValidity('Ce champ est requis.')"
                           oninput="setCustomValidity('')">
                </div>
                <button type="submit" class="btn btn-lg btn-primary btn-block" value="create">Créer mon compte</button>
            </form>

        </main>
    </body>
        
    </body>
    <%@include file="style/js.jsp" %>
</html>
