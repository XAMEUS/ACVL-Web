<%@page import="ensimag.acvl.models.Child"%>
<%@page import="java.util.List"%>
<%@page import="ensimag.acvl.models.Period"%>
<%@page import="ensimag.acvl.models.Activity"%>
<h2>Détails activité</h2>
${activity}
<% Activity activity = (Activity) request.getAttribute("activity"); %>
<% List<Child>[] subscribers = (List<Child>[]) request.getAttribute("subscribers");%>

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
                    <tr><td><%=c.getFirstname()%> <%=c.getLastname()%></td></tr>
                    <% }%>
                </table>
            </td>
            <td>
                <table class="table">
                    <% for (Child c : subscribers[1]) { %>
                    <tr><td><%=c.getFirstname()%> <%=c.getLastname()%></td></tr>
                    <% }%>
                </table>
            </td>
            <td>
                <table class="table">
                    <% for (Child c : subscribers[2]) { %>
                    <tr><td><%=c.getFirstname()%> <%=c.getLastname()%></td></tr>
                    <% }%>
                </table>
            </td>
            <td>
                <table class="table">
                    <% for (Child c : subscribers[3]) { %>
                    <tr><td><%=c.getFirstname()%> <%=c.getLastname()%></td></tr>
                    <% }%>
                </table>
            </td>
            <td>
                <table class="table">
                    <% for (Child c : subscribers[4]) { %>
                    <tr><td><%=c.getFirstname()%> <%=c.getLastname()%></td></tr>
                    <% }%>
                </table>
            </td>
        </tr>
    </tbody>
</table>

<h3>Disponible sur ces périodes</h3>
<table class="table table-striped table-sm">
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
            <td><a href="<%= request.getContextPath()%>/admin?view=calendar&period=<%=p.getId()%>"><%=p%></a></td>
        </tr>
        <% }%>
    </tbody>
</table>