<%@page import="ensimag.acvl.config.Config"%>

<h2>Options de d�boguage</h2>
<% if(ensimag.acvl.config.Config.debug) {
    %>
    <p><a href="admin?view=debugOff">Masquer les informations de d�boguage</a></p>
    <%
} else {
    %>
        <p><a href="admin?view=debugOn">Afficher les informations de d�boguage</a></p>
    <%
}
%>

<% if(ensimag.acvl.config.Config.autoMoulinette) {
    %>
        <p><a href="admin?view=autoMoulinetteOff">Passer � la moulinette manuelle</a></p>
    <%
} else {
    %>
        <p><a href="admin?view=autoMoulinetteOn">Activer le passage p�riodique de la moulinette</a></p>
<p><span data-feather="play-circle"></span><a href="admin?view=moulinette"> Lancer manuellement la moulinette</a></p>
    <%
}
%>

<p><span data-feather="calendar"></span><a href="<%= request.getContextPath()%>/time"> Configurer la date du serveur.</a></p>