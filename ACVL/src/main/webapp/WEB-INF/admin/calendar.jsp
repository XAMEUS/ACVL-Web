<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/date.js"></script>

<%@page import="ensimag.acvl.models.Child"%>
<%@page import="ensimag.acvl.models.Period"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% Period p = (Period) (request.getAttribute("period")); %>
<% List<Child> inscrits = (List<Child>) (request.getAttribute("inscrits")); %>
<% List<Integer> stats = (List<Integer>) (request.getAttribute("stats")); %>
<% if (p == null) {%>
<h2>Gestion des périodes</h2>

<div class="table-responsive">
    <h3>Créer une période</h3>
    <div>
        <form method="post" action="admin?view=calendar" accept-charset="UTF-8" onsubmit="return checkPeriod();">
            <input type="hidden" name="action" value="period">
            <div class="form-group">
                <label for="limitDate">Date limite des inscriptions</label>
                <input class="form-control" type="date" name="limitDate"
                       placeholder="YYYY-MM-DD" required=""
                       oninvalid="this.setCustomValidity('Ce champ est requis.')"
                       oninput="setCustomValidity('')">
            </div
            <div class="form-group">
                <label for="startDate">Date de début de la période</label>
                <input class="form-control" type="date" name="startDate"
                       placeholder="YYYY-MM-DD" required=""
                       oninvalid="this.setCustomValidity('Ce champ est requis.')"
                       oninput="setCustomValidity('')">
            </div>
            <div class="form-group">
                <label for="endDate">Date de fin de la période</label>
                <input class="form-control" type="date" name="endDate"
                       placeholder="YYYY-MM-DD" required=""
                       oninvalid="this.setCustomValidity('Ce champ est requis.')"
                       oninput="setCustomValidity('')">
            </div>
            <input type="submit" value="Ajouter">
        </form>
    </div>

    <hr>

    <h3>Liste des périodes</h3>
    <table class="table table-striped table-sm">
        <thead>
            <tr>
                <th></th>
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${periods}" var="period">
                <tr>
                    <td><a href="<%= request.getContextPath()%>/admin?view=calendar&period=${period.id}">${period}</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<% } else {%>
<h2>Détail de la période du ${period.start} au ${period.end}</h2>

<h3>Inscriptions</h3>
<p>Nombre d'inscrits : <%= inscrits.size()%></p>

<table class="table table-striped table-sm">
    <thead>
        <tr>
            <th></th>
            <th>Lundi</th>
            <th>Mardi</th>
            <th>Mercredi</th>
            <th>Jeudi</th>
            <th>Vendredi</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>Enfants mangeant à la cantine</td>
            <td><%= stats.get(0)%></td>
            <td><%= stats.get(1)%></td>
            <td><%= stats.get(2)%></td>
            <td><%= stats.get(3)%></td>
            <td><%= stats.get(4)%></td>
        </tr>
    </tbody>
</table>

<p>Enfants à la garderie du matin : <%= stats.get(5)%></p>
<p>Enfants à la garderie1 : <%= stats.get(6)%></p>
<p>Enfants à la garderie2 : <%= stats.get(7)%></p>
<p>Enfants à la garderie3 : <%= stats.get(8)%></p>

<h3>Description</h3>        
<table class="table table-striped table-sm">
    <tbody>
        <tr>
            <td>Date limite des inscriptions</td>
            <td>${period.limit}</td>
        </tr>
        <tr>
            <td>Date de début</td>
            <td>${period.start}</td>
        </tr>
        <tr>
            <td>Date de fin</td>
            <td>${period.end}</td>
        </tr>
    </tbody>
</table>


<br>
<h3>Activitées sur la période</h3>
<table class="table table-striped table-sm">
    <thead>
        <tr>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${activities}" var="activity">
            <tr>
                <td>${activity.title} <a href="<%= request.getContextPath()%>/admin?view=activity&activity=${activity.id}&period=${period.id}">plus d'informations</a></td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<% }%>