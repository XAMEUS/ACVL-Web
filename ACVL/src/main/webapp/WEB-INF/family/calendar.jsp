<%@page import="ensimag.acvl.time.Time"%>
<%@page import="ensimag.acvl.models.Period"%>
<%@page import="ensimag.acvl.models.Child"%>
<%@page import="ensimag.acvl.models.Activity"%>
<%@page import="ensimag.acvl.models.Activity"%>
<%@page import="java.util.List"%>
<h1>Emploi du temps</h1>
<% int c = 0; %>
<c:forEach items="${children}" var="child">
    <h2>${child.firstname} ${child.lastname}</h2>
    <% if (((Child) (pageContext.findAttribute("child"))).getRegisteredPeriods().size() == 0) {%>
    <div class="alert alert-danger" role="alert">
        ${child.firstname} ${child.lastname} n'a aucune inscription !
    </div>
    <%} else {%>
    <div>
        <p>${child.firstname} ${child.lastname} est inscrit.e au(x) période(s) suivante(s):</p>
    <c:forEach items="${child.registeredPeriods}" var="registeredPeriod">
        <% c++;%>
        <p>${registeredPeriod.toPrettyString()} <small class="form-text text-muted">Cliquez <a href="<%= request.getContextPath()%>/family?view=period&period=${registeredPeriod.id}&child=${child.id}">ici</a> pour voir les détails de l'inscription.</small></p>
        <% if (Time.getDate().before(((Period)(pageContext.findAttribute("registeredPeriod"))).getLimit())) { %>
            <div class="alert alert-warning" role="alert">
                Moulinette à venir... Attendez le ${registeredPeriod.limit}
            </div>
        <% } else if (Time.getDate().after(((Period)(pageContext.findAttribute("registeredPeriod"))).getEnd())) { %>
            <div class="alert alert-secondary" role="alert">
                Période passée, vous pouvez maintenant accéder à votre facture <a href="<%= request.getContextPath()%>/family?view=facture&period=${registeredPeriod.id}&child=${child.id}">ici</a>.
            </div>
        <% } else { %>
            <div class="alert alert-primary" role="alert">
                Période courante.
            </div>
        <% } %>
    </c:forEach>
    <% }%>
    </div>
    <hr>

</c:forEach>