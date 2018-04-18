
<h2>Personnes</h2>

<form method="post" action="account" accept-charset="UTF-8">
    <input class="form-control" type="hidden" name="action" value="create">
    <div class="form-row align-items-center">
        <div class="col-auto">
            <label class="sr-only" for="inlineFormInput">Username</label>
            <input type="text" class="form-control mb-2" id="inlineFormInput" placeholder="Username">
        </div>
        <div class="col-auto">
            <label class="sr-only" for="inlineFormInputGroup">Mot de passe</label>
            <input type="password" class="form-control mb-2" id="inlineFormInputGroup" placeholder="Password">
        </div>
        <div class="col-auto">
            <button type="submit" class="btn btn-primary mb-2">Créer</button>
        </div>
    </div>
</form>
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
                <th></th>
                <th>Enfant</th>
            </tr>
        </thead>
        <tbody>
            <% c = 0; %>
        <c:forEach items="${children}" var="child">
            <% c++; %>
            <tr>
                <td>#<% out.print(c);%></td>
                <td>${child}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
