<%@page import="java.sql.Date"%>
<%@page import="ensimag.acvl.models.Cancel"%>
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
<h1>Détail de la période</h1>
${period.toPrettyString()}<br>
${registration}<br>
<% Period period = (Period) (request.getAttribute("period")); %>
<% ensimag.acvl.models.Registration registration = (ensimag.acvl.models.Registration) (request.getAttribute("registration")); %>
<% LocalDate date = period.getStart().toLocalDate(); %>
<% LocalDate limit = period.getEnd().toLocalDate(); %>
Votre enfant est inscrit pour la période ${period.id}.<br>
<% String[] days = {"lundi", "mardi", "mercredi", "jeudi", "vendredi"};%>
<%out.print(date);%>
<%out.print(limit);%>
<br>
<% while (date.isBefore(limit) || date.equals(limit)) { %>
<%
    int i = 0;
    if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
        i = 0;
    }
    if (date.getDayOfWeek() == DayOfWeek.TUESDAY) {
        i = 1;
    }
    if (date.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
        i = 2;
    }
    if (date.getDayOfWeek() == DayOfWeek.THURSDAY) {
        i = 3;
    }
    if (date.getDayOfWeek() == DayOfWeek.FRIDAY) {
        i = 4;
    }
%>
<% Cancel cancel = (Cancel) request.getAttribute("cancel"); %>
<% for (; i < 5 && !date.isAfter(limit); i++) {%>
<span>Le <%=date.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy", Locale.FRENCH))%> :</span>
<ul>
    <% if (registration.getCodeGarderie() % 2 == 1) {%>
    <li>Garderie Matin : 7h00 - 8h30
        <% if (cancel.isCanceled(date, 1, 0)) { %>
        <span class="alert-secondary">Annulé</span>
        <% } else if (Time.getDate().before(Date.valueOf(date.minusDays(1)))) {%>
        <a href="<%= request.getContextPath()%>/family?view=period&period=${period.id}&child=${child.id}&cancelDate=<%=date%>&codeType=1&code=0" 
           class="alert-warning" role="button">Annuler</a>
        <% } else {%>
            <!--<span class="alert-secondary">Annulation impossible</span>-->
        <% } %>
    </li>
    <% } %>
    <% if ((registration.getCodeCantine() >> i) % 2 == 1) {%>
    <li>
        Cantine
        <% if (cancel.isCanceled(date, 2, i)) { %>
        <span class="alert-secondary">Annulé</span>
        <% } else if (Time.getDate().before(Date.valueOf(date.minusDays(1)))) {%>
        <a href="<%= request.getContextPath()%>/family?view=period&period=${period.id}&child=${child.id}&cancelDate=<%=date%>&codeType=2&code=<%=i%>" 
           class="alert-warning" role="button">Annuler</a>
        <% } %>
    </li>
    <% } %>
    <%
        List<Activity> activities = ((Period) (request.getAttribute("period"))).getActivities().get(i);
        if (activities.size() > 0) {
    %> <li>
        <% Activity a = activities.get(0);%> 
        <%out.print(a.getTitle());%> 

        <% if (cancel.isCanceled(date, 3, a.getId())) { %>
        <span class="alert-secondary">Annulé</span>
        <% } else if (Time.getDate().before(Date.valueOf(date.minusDays(1)))) {%>
        <a href="<%= request.getContextPath()%>/family?view=period&period=${period.id}&child=${child.id}&cancelDate=<%=date%>&codeType=3&code=<%=activities.get(0).getId()%>" 
           class="alert-warning" role="button">Annuler</a>
        <% } %>
    </li>
    <% } else {
        if (i != 2 && (registration.getCodeGarderie() >> 1) % 2 == 1) {%>
    <li>Garderie du soir 1 : 15h45 - 16h30
        <% if (cancel.isCanceled(date, 1, 1)) { %>
        <span class="alert-secondary">Annulé</span>
        <% } else if (Time.getDate().before(Date.valueOf(date.minusDays(1)))) {%>
        <a href="<%= request.getContextPath()%>/family?view=period&period=${period.id}&child=${child.id}&cancelDate=<%=date%>&codeType=1&code=1" 
           class="alert-warning" role="button">Annuler</a>
        <% } %>
    </li>
    <% }
        if (i != 2 && (registration.getCodeGarderie() >> 2) % 2 == 1) {%>
    <li>Garderie du soir 2 : 16h30 - 17h15
        <% if (cancel.isCanceled(date, 1, 2)) { %>
        <span class="alert-secondary">Annulé</span>
        <% } else if (Time.getDate().before(Date.valueOf(date.minusDays(1)))) {%>
        <a href="<%= request.getContextPath()%>/family?view=period&period=${period.id}&child=${child.id}&cancelDate=<%=date%>&codeType=1&code=2" 
           class="alert-warning" role="button">Annuler</a>
        <% } %>
    </li>
    <% }
        if (i != 2 && (registration.getCodeGarderie() >> 3) % 2 == 1) {%>
    <li>Garderie du soir 3 : 17h15 - 18h00
        <% if (cancel.isCanceled(date, 1, 3)) { %>
        <span class="alert-secondary">Annulé</span>
        <% } else if (Time.getDate().before(Date.valueOf(date.minusDays(1)))) {%>
        <a href="<%= request.getContextPath()%>/family?view=period&period=${period.id}&child=${child.id}&cancelDate=<%=date%>&codeType=1&code=3" 
           class="alert-warning" role="button">Annuler</a>
        <% } %>
    </li>
    <% }
            } %>
</ul>
<% date = date.plusDays(1); %>
<% }%>
<% date = date.plusDays(2); %>
<hr>
<% }%>
