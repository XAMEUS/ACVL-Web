<%@page import="java.time.DayOfWeek"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.Locale"%>
<%@page import="java.time.LocalDate"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="ensimag.acvl.time.Time"%>
<%@page import="ensimag.acvl.models.Period"%>
<%@page import="ensimag.acvl.models.Child"%>
<%@page import="ensimag.acvl.models.Activity"%>
<%@page import="ensimag.acvl.models.Activity"%>
<%@page import="java.util.List"%>
<h1>D�tail de la p�riode</h1>
<h2 class="alert alert-danger">Il faut finir cet affichage (cantine, etc.)</h2>
${period}<br>
${registration}<br>
<% Period period = (Period) (request.getAttribute("period")); %>
<% ensimag.acvl.models.Registration registration = (ensimag.acvl.models.Registration) (request.getAttribute("registration")); %>
<% LocalDate date = period.getStart().toLocalDate(); %>
<% LocalDate limit = period.getEnd().toLocalDate(); %>
Votre enfant est inscrit pour la p�riode ${period.id}.<br>
<% String[] days = {"lundi", "mardi", "mercredi", "jeudi", "vendredi"};%>
<%out.print(date);%>
<%out.print(limit);%>
<br>
<% while (date.isBefore(limit) || date.equals(limit)) { %>
<%
    int i = 0;
    if (date.getDayOfWeek() == DayOfWeek.MONDAY)
        i = 0;
    if (date.getDayOfWeek() == DayOfWeek.TUESDAY)
        i = 1;
    if (date.getDayOfWeek() == DayOfWeek.WEDNESDAY)
        i = 2;
    if (date.getDayOfWeek() == DayOfWeek.THURSDAY)
        i = 3;
    if (date.getDayOfWeek() == DayOfWeek.FRIDAY)
        i = 4;
%>
<% for (; i < 5 && !date.isAfter(limit); i++) {%>
<span>Le <%=date.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy", Locale.FRENCH))%> :</span>
<ul>
    <% if ((registration.getCodeCantine() / (1)) % 2 == 1) {%>
    <li>Garderie Matin : 7h00 - 8h30</li>
        <% } %>
        <% if ((registration.getCodeCantine() / (i + 1)) % 2 == 1) {%>
    <li>cantine</li>
        <% } %>
        <%
            List<Activity> activities = ((Period) (request.getAttribute("period"))).getActivities().get(i);
            if (activities.size() > 0) {
        %> <li><%out.print(activities.get(0).getTitle());%> </li> <br>
        <% } else if ((registration.getCodeCantine() / (2)) % 2 == 1) {%>
    <li>Garderie du soir 1 : 15h45 - 16h30</li>
        <% } else if ((registration.getCodeCantine() / (3)) % 2 == 1) {%>
    <li>Garderie du soir 2 : 16h30 - 17h15</li>
        <% } else if ((registration.getCodeCantine() / (4)) % 2 == 1) {%>
    <li>Garderie du soir 3 : 17h15 - 18h00</li>
        <% } %>
</ul>
<% date = date.plusDays(1); %>
<% }%>
<% date = date.plusDays(2); %>
<hr>
<% }%>
