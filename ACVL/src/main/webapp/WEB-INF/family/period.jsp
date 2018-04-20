<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="ensimag.acvl.time.Time"%>
<%@page import="ensimag.acvl.models.Period"%>
<%@page import="ensimag.acvl.models.Child"%>
<%@page import="ensimag.acvl.models.Activity"%>
<%@page import="ensimag.acvl.models.Activity"%>
<%@page import="java.util.List"%>
<h1>Détail de la période</h1>
<h2 class="alert alert-danger">Il faut finir cet affichage (cantine, etc.)</h2>
${period}<br>
${registration}<br>
Votre enfant est inscrit pour la période ${period.id}.<br>
<% String[] days = {"lundi", "mardi", "mercredi", "jeudi", "vendredi"};%>
<% for (int i = 0; i < 5; i++) { %>
<% 
    List<Activity> activities = ((Period)(request.getAttribute("period"))).getActivities().get(i);
    if (activities.size() > 0) { 
%>
Le <%out.print(days[i]);%> : <%out.print(activities.get(0).getTitle());%> <br>

<% } %>
<% } %>
