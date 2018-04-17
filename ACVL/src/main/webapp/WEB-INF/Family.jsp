<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>Ma famille</title>
        <%@include file="style/style.jsp" %>
        <link href="homepage.css" rel="stylesheet">
        <link href="navbar.css" rel="stylesheet">
    </head>
    <body>

        <main role="main" class="container">
            <%@include file="debug/debug.jsp" %>

            <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
                <a class="navbar-brand" href="#"><strong>Bonjour <%out.print(session.getAttribute("username"));%></strong></a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor02" aria-controls="navbarColor02" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarColor02">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item active">
                            <a class="nav-link" href="#">Mes enfants <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Inscription aux TAP</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Emploi du temps</a>
                        </li>
                    </ul>
                    <a class="btn btn-primary" href="">Se déconnecter</a>
                </div>
            </nav>

            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Firstname</th>
                        <th>Lastname</th>
                        <th>Gender</th>
                        <th>Grade</th>
                        <th>Birthdate</th>
                        <th>Diet</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${children}" var="child">
                        <tr>
                            <td>${child.firstname}</td>
                            <td>${child.lastname}</td>
                            <td>${child.gender}</td>
                            <td>${child.grade}</td>
                            <td>${child.birthdate}</td>
                            <td>
                                <c:forEach items="${child.diet}" var="diet">
                                    ${diet},
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div>
                <a class="btn btn-primary" data-toggle="collapse" href="#add" role="button" aria-expanded="false" aria-controls="add">Ajouter un enfant</a>
            </div>
            <div class="row">
                <div class="col">
                    <div class="collapse multi-collapse" id="add">
                        <div class="card card-body">
                            <form method="post" action="family" accept-charset="UTF-8">
                                <input type="hidden" name="action" value="create">
                                <div class="form-group">
                                    <label for="lastname">Nom</label>
                                    <input type="text" name="lastname" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label for="firstname">Prénom</label>
                                    <input type="text" name="firstname" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label for="firstname">Date de naissance</label>
                                    <input type="date" name="birthdate" placeholder="AAAA-MM-JJ" class="form-control">
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="gender" id="male" value="M">
                                    <label class="form-check-label" for="male">H</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="gender" id="female" value="F">
                                    <label class="form-check-label" for="female">F</label>
                                </div>
                                <div class="form-group">
                                    <label for="grade">Classe</label>
                                    <select name="grade" id="grade" class="form-control">
                                        <option value="PS">PS</option>
                                        <option value="MS">MS</option>
                                        <option value="GS">GS</option>
                                        <option value="CP">CP</option>
                                        <option value="CE1">CE1</option>
                                        <option value="CE2">CE2</option>
                                        <option value="CM1">CM1</option>
                                        <option value="CM2">CM2</option>
                                    </select>
                                </div>
                                <% int c = 0; %>
                                <c:forEach items="${diets}" var="diet">
                                    <% c++; %>
                                    <div class="form-check">
                                        <input type="checkbox" class="form-check-input" id="diet<% out.print(c); %>" name="diet<%out.print(c);%>" value="${diet}">
                                        <label for="diet<% out.print(c);%>">${diet}</label>
                                    </div>
                                </c:forEach>
                                <button type="submit" class="btn btn-primary" value="add">Ajouter</button>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </main>
    </body>
    <%@include file="style/js.jsp" %>
</html>
