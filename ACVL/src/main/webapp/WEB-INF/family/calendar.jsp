<%@page import="ensimag.acvl.time.Time"%>
<%@page import="ensimag.acvl.models.Period"%>
<%@page import="ensimag.acvl.models.Child"%>
<%@page import="ensimag.acvl.models.Activity"%>
<%@page import="ensimag.acvl.models.Activity"%>
<%@page import="java.util.List"%>
<h1>Emplois du temps</h1>
<% int c = 0; %>
<c:forEach items="${children}" var="child">
    <h2>${child.firstname} ${child.lastname}</h2>
    <% if (((Child) (pageContext.findAttribute("child"))).getRegisteredPeriods().size() == 0) {%>
    <div class="alert alert-danger" role="alert">
        ${child.firstname} ${child.lastname} n'a aucune inscription !
    </div>
    <%} else {%>
    <div>
        <p>${child.firstname} ${child.lastname} est inscrit.e au(x) p�riode(s) suivante(s):</p>
    </div>
    <c:forEach items="${child.registeredPeriods}" var="registeredPeriod">
        <% c++;%>
        ${registeredPeriod}
        <% if (Time.getDate().before(((Period)(pageContext.findAttribute("registeredPeriod"))).getLimit())) { %>
            <div class="alert alert-warning" role="alert">
                Moulinette � venir... Attendez le ${registeredPeriod.limit}
            </div>
        <% } else if (Time.getDate().after(((Period)(pageContext.findAttribute("registeredPeriod"))).getEnd())) { %>
            <div class="alert alert-secondary" role="alert">
                P�riode pass�e, vous pouvez maintenant acc�der � votre facture <a href="<%= request.getContextPath()%>/family?view=facture&period=${registeredPeriod.id}&child=${child.id}">ici</a>.
            </div>
        <% } else { %>
            <div class="alert alert-primary" role="alert">
                P�riode courrante.
            </div>
        <% } %>
        <p>Cliquez <a href="<%= request.getContextPath()%>/family?view=period&period=${registeredPeriod.id}&child=${child.id}">ici</a> pour voir les d�tails de l'inscription.</p>
    </c:forEach>
    <% }%>
    <hr>

</c:forEach>