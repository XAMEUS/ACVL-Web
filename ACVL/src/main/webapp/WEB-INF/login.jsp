<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Se connecter</title>
        <%@include file="style/style.jsp" %>
        <link href="style/login.css" rel="stylesheet">
    </head>
    <body class="text-center">
        <main role="main" class="container">
            <%@include file="debug/debug.jsp" %>

            <% if (session.getAttribute("username") != null) { %>
            
            <div>
                <h1 class="h3 mb-3 font-weight-normal">Connexion réussie</h1>
                <p class="lead">Bonjour <strong><% out.print(session.getAttribute("username")); %></strong> !
                    Vous pouvez maintenant consulter votre <a href="family"><strong>page famille</strong></a>.</p>
            </div>
                    
            <% } else { %>

            <form class="form-signin" method="post" action="account" accept-charset="UTF-8">
                <h1 class="h3 mb-3 font-weight-normal">Se connecter</h1>
                <% if (request.getAttribute("message") != null) { %>
                <div class="alert alert-danger">
                    <strong><%out.print(request.getAttribute("title"));%> :</strong> <%out.print(request.getAttribute("message"));%>.
                </div>
                <%}%>
                <input type="hidden" name="action" value="signin">
                <div class="form-group">
                    <input type="text" name="username" placeholder="Nom d'utilisateur" class="form-control" autofocus>
                </div>
                <div class="form-group">
                    <input type="password" name="password" placeholder="Mot de passe" class="form-control">
                </div>
                <button type="submit" class="btn btn-lg btn-primary btn-block" value="login">Connexion</button>
            </form>
                
            <%}%>
            
        </main>
    </body>

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

</html>
