
<h2>Personnes</h2>

<div class="table-responsive">
    <h3>Liste des utilisateurs</h3>
    <table class="table table-striped table-sm">
        <thead>
            <tr>
                <th></th>
                <th>Utilisateur</th>
            </tr>
        </thead>
        <tbody>
            <% int c = 0; %>
        <c:forEach items="${users}" var="user">
            <% c++; %>
            <tr>
                <td>#<% out.print(c);%></td>
                <td>${user}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="table-responsive">
    <h3>Liste des enfants</h3>
    <table class="table table-striped table-sm">
        <thead>
            <tr>
                <th>Prénom</th>
                <th>Nom</th>
                <th>Niveau</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <% c = 0;%>
        <c:forEach items="${children}" var="child">
            <tr>
                <td>${child.firstname}</td>
                <td>${child.lastname}</td>
                <td>${child.grade}</td>
                <td><a href="">détails</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
