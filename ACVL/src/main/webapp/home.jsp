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
            
            <main role="main" class="container">

                <div class="starter-template">
                    <h1>Bootstrap starter template</h1>
                    <p class="lead">Use this document as a way to quickly start any new project.<br> All you get is this text and a mostly barebones HTML document.</p>
                     <a class="btn btn-primary" data-toggle="collapse" href="#multiCollapseExample1" role="button" aria-expanded="false" aria-controls="multiCollapseExample1">Se connecter</a>
                      <a class="btn btn-primary" href="account?action=register">S'inscrire</a>
                </div>
                <div class="row">
                    <div class="col">
                        <div class="collapse multi-collapse" id="multiCollapseExample1">
                            <div class="card card-body">
                                <form method="post" action="account" accept-charset="UTF-8">
                                    <input type="hidden" name="action" value="signin">
                                    <div class="form-group">
                                        <label for="inputUsername1">Nom d'utilisateur</label>
                                        <input type="text" name="username" class="form-control">
                                    </div>
                                    <div class="form-group">
                                        <label for="inputPassword1">Mot de passe</label>
                                        <input type="password" name="password" class="form-control">
                                    </div>
                                    <button type="submit" class="btn btn-primary" value="login">Connexion</button>
                                </form>
                            </div>
                        </div>
                    </div>
            </main>
            <%}%>
    </body>

    <%@include file="WEB-INF/style/js.jsp" %>
</html>
