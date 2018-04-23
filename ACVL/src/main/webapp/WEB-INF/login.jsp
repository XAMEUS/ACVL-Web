<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Se connecter</title>
        <%@include file="style/style.jsp" %>
        <link href="style/homepage.css" rel="stylesheet">
        <link href="style/signin.css" rel="stylesheet">
    </head>
    <body class="text-center">
        <main role="main" class="container">
            <%@include file="debug/debug.jsp" %>
            <form class="form-signin" method="post" action="account" accept-charset="UTF-8">
                <h1 class="h3 mb-3 font-weight-normal">Se connecter</h1>
                <input type="hidden" name="action" value="signin">
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
                <button type="submit" class="btn btn-lg btn-primary btn-block" value="login">Connexion</button>
            </form>      
        </main>
            <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    </body>
</html>
