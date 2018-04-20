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
        <% out.print(Time.date);%>
        ${registeredPeriod}
        <% if (Time.date.before(((Period)(pageContext.findAttribute("registeredPeriod"))).getLimit())) { %>
            <div class="alert alert-warning" role="alert">
                Moulinette � venir... Attendez le ${registeredPeriod.limit}
            </div>
        <% } else if (Time.date.after(((Period)(pageContext.findAttribute("registeredPeriod"))).getEnd())) { %>
            <div class="alert alert-secondary" role="alert">
                P�riode pass�e, vous pouvez maintenant acc�der � votre facture...
            </div>
        <% } else { %>
            <div class="alert alert-primary" role="alert">
                P�riode courrante.
            </div>
        <% } %>
        <p>Cliquez <a href="<%= request.getContextPath()%>/admin?view=period&period=">ici</a> pour voir les d�tails de l'inscription. (�a n'est pas encore fait !)</p>
    </c:forEach>
    <% }%>
    <hr>

</c:forEach>