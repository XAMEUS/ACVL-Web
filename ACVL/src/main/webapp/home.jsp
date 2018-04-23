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
        <main class="container">
            <%@include file="WEB-INF/debug/debug.jsp" %>
                       
            <% if (session.getAttribute("username") != null) {
                if(session.getAttribute("username").equals("admin")) {
                    response.sendRedirect(request.getContextPath() + "/admin?view=main");
                }
                else {
                    response.sendRedirect(request.getContextPath() + "/family");
                }
             }
                    
            else { %>

            <div>
                <h1>Bienvenue !</h1>
                <p class="lead">Chaque trimestre choisissez les activités périscolaires de vos enfants.
                    <br>Gérez facilement leur inscription à la cantine, à la garderie et annulez jusqu'à 48 heures avant.</p>
            </div>
            <% if (request.getAttribute("message") != null) { %>
                <div class="alert alert-danger">
                    <strong><%out.print(request.getAttribute("title"));%> :</strong> <%out.print(request.getAttribute("message"));%>.
                </div>
                <%}%>
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
                                    <input class="form-control" type="text" name="username" id="username"
                                           placeholder="Nom d'utilisateur" required="" autofocus
                                           oninvalid="this.setCustomValidity('Ce champ est requis.')"
                                           oninput="setCustomValidity('')">
                                </div>
                                <div class="form-group">
                                    <label for="password">Mot de passe</label>
                                    <input class="form-control" type="password" name="password" id="username"
                                           placeholder="Mot de passe" required=""
                                           oninvalid="this.setCustomValidity('Ce champ est requis.')"
                                           oninput="setCustomValidity('')">
                                </div>
                                <button type="submit" class="btn btn-primary" value="login">Connexion</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            
            <%}%>
            
        </main>
        <%@include file="WEB-INF/style/js.jsp" %>
    </body>
</html>
