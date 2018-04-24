<%@page import="ensimag.acvl.models.Child"%>
<%@page import="java.util.List"%>
<%@page import="ensimag.acvl.models.Period"%>
<%@page import="ensimag.acvl.models.Activity"%>
<h2>Détails de l'activité : ${activity.title}, du ${period.start} au ${period.end}</h2>

<% Activity activity = (Activity) request.getAttribute("activity"); %>
<% List<Child>[] subscribers = (List<Child>[]) request.getAttribute("subscribers");%>

<p>Description : ${activity.description}</p>
<p>Animateurs : ${activity.animators}</p>
<p>Capacité : ${activity.capacity}</p>
<p>Prix : ${activity.price}</p>

<br>
<h3>Enfants inscrits</h3>
<table class="table">
    <thead>
        <tr>
            <th>Lundi : <%=subscribers[0].size()%></th>
            <th>Mardi : <%=subscribers[1].size()%></th>
            <th>Mercredi : <%=subscribers[2].size()%></th>
            <th>Jeudi : <%=subscribers[3].size()%></th>
            <th>Vendredi : <%=subscribers[4].size()%></th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>
                <table class="table">
                    <% for (Child c : subscribers[0]) { %>
                    <tr><td><a href="<%= request.getContextPath()%>/admin?view=child&child=<%=c.getId()%>"><%=c.getFirstname()%> <%=c.getLastname()%></a></td></tr>
                    <% }%>
                </table>
            </td>
            <td>
                <table class="table">
                    <% for (Child c : subscribers[1]) { %>
                    <tr><td><a href="<%= request.getContextPath()%>/admin?view=child&child=<%=c.getId()%>"><%=c.getFirstname()%> <%=c.getLastname()%></a></td></tr>
                    <% }%>
                </table>
            </td>
            <td>
                <table class="table">
                    <% for (Child c : subscribers[2]) { %>
                    <tr><td><a href="<%= request.getContextPath()%>/admin?view=child&child=<%=c.getId()%>"><%=c.getFirstname()%> <%=c.getLastname()%></a></td></tr>
                    <% }%>
                </table>
            </td>
            <td>
                <table class="table">
                    <% for (Child c : subscribers[3]) { %>
                    <tr><td><a href="<%= request.getContextPath()%>/admin?view=child&child=<%=c.getId()%>"><%=c.getFirstname()%> <%=c.getLastname()%></a></td></tr>
                    <% }%>
                </table>
            </td>
            <td>
                <table class="table">
                    <% for (Child c : subscribers[4]) { %>
                    <tr><td><a href="<%= request.getContextPath()%>/admin?view=child&child=<%=c.getId()%>"><%=c.getFirstname()%> <%=c.getLastname()%></a></td></tr>
                    <% }%>
                </table>
            </td>
        </tr>
    </tbody>
</table>

<h3>Disponible sur ces périodes</h3>
<table class="table table-striped">
    <thead>
        <tr>
            <th></th>
            <th></th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <% for (Period p : activity.getPeriod()) {%>
        <tr>
            <td>du <%=p.getStart()%> au <%=p.getEnd()%> <a href="<%= request.getContextPath()%>/admin?view=calendar&period=<%=p.getId()%>">plus d'informations</a></td>
        </tr>
        <% }%>
    </tbody>
</table>