<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>Ma famille</title>
        <%@include file="style/style.jsp" %>
        <link href="${pageContext.request.contextPath}/style/homepage.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/style/navbar.css" rel="stylesheet">
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
                        <li class="nav-item <c:if test="${view eq 'home'}">active</c:if>">
                            <a class="nav-link" href="<%= request.getContextPath()%>/family?view=home">Mes enfants <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item <c:if test="${view eq 'register'}">active</c:if>">
                            <a class="nav-link" href="<%= request.getContextPath()%>/family?view=register">Inscriptions</a>
                        </li>
                        <li class="nav-item <c:if test="${view eq 'calendar'}">active</c:if>">
                            <a class="nav-link" href="<%= request.getContextPath()%>/family?view=calendar">Emploi du temps</a>
                        </li>
                    </ul>
                    <a class="btn btn-primary" href="<%= request.getContextPath()%>/account?action=logout">Se déconnecter</a>
                </div>
            </nav>

            <c:if test="${view eq 'home'}"><%@include file="family/home.jsp" %></c:if>
            <c:if test="${view eq 'editChild'}"><%@include file="family/home.jsp" %></c:if>
            <c:if test="${view eq 'register'}"><%@include file="family/register.jsp" %></c:if>
            <c:if test="${view eq 'calendar'}"><%@include file="family/calendar.jsp" %></c:if>
            <c:if test="${view eq 'period'}"><%@include file="family/period.jsp" %></c:if>

            </main>
        </body>
    <%@include file="style/js.jsp" %>
</html>
