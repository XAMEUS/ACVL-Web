<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
        <%@include file="WEB-INF/style/style.jsp" %>
        <link href="style/homepage.css" rel="stylesheet">
    </head>
    <body>
        <main role="main" class="container">
            <%@include file="WEB-INF/debug/debug.jsp" %>
            
            <% if (session.getAttribute("username") != null) { %>
            
            <div class="text-center">
                <h1 class="h3 mb-3 font-weight-normal">Connexion réussie</h1>
                <p class="lead">Bonjour <strong><% out.print(session.getAttribute("username")); %></strong> !
                    Vous pouvez maintenant consulter votre <a href="family"><strong>page famille</strong></a>.</p>
            </div>
                    
            <% } else { %>

            <div>
                <h1>Bienvenue !</h1>
                <p class="lead">Chaque trimestre choisissez les activités périscolaires de vos enfants.
                    <br>Gérez facilement leur inscription à la cantine, à la garderie et annulez jusqu'à 48 heures avant.</p>
            </div>
            <div>
                <a class="btn btn-primary" data-toggle="collapse" href="#connect" role="button" aria-expanded="false" aria-controls="connect">Se connecter</a>
                <a class="btn btn-primary" href="account?action=register">S'inscrire</a>
            </div>
            <div class="row">
                <div class="col">
                    <div class="collapse multi-collapse" id="connect">
                        <div class="card card-body">
                            <form method="post" action="account" accept-charset="UTF-8">
                                <input type="hidden" name="action" value="signin">
                                <div class="form-group">
                                    <label for="username">Nom d'utilisateur</label>
                                    <input type="text" name="username" placeholder="Nom d'utilisateur" class="form-control" autofocus>
                                </div>
                                <div class="form-group">
                                    <label for="password">Mot de passe</label>
                                    <input type="password" name="password" placeholder="Mot de passe" class="form-control">
                                </div>
                                <button type="submit" class="btn btn-primary" value="login">Connexion</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            
            <%}%>
            
        </main>
    </body>

    <%@include file="WEB-INF/style/js.jsp" %>
</html>
