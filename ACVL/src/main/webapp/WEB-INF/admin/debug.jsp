<%@page import="ensimag.acvl.config.Config"%>

<h2>Options de d�boguage</h2>
<% if(ensimag.acvl.config.Config.debug) {
    %>
        <a href="admin?view=debugOff">Masquer les informations de d�boguage</a>
    <%
} else {
    %>
        <a href="admin?view=debugOn">Afficher les informations de d�boguage</a>
    <%
}
%>
<br/>
<% if(ensimag.acvl.config.Config.autoMoulinette) {
    %>
        <a href="admin?view=autoMoulinetteOff">Passer � la moulinette manuelle</a>
    <%
} else {
    %>
        <a href="admin?view=moulinette">Lancer manuellement la moulinette</a><br />
        <a href="admin?view=autoMoulinetteOn">Activer le passage p�riodique de la moulinette</a>
    <%
}
%>