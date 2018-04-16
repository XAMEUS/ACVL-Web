
<h2>Gestion des régimes alimentaires</h2>
<div class="table-responsive">
    <div>
        <form method="post" action="admin?view=settings" accept-charset="UTF-8">
            <input type="hidden" name="action" value="diet">
            <input type="text" name="diet">
            <input type="submit" value="Ajouter">
        </form>
    </div>
    <table class="table table-striped table-sm">
        <thead>
            <tr>
                <th></th>
                <th>Régime</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <% int c = 0; %>
        <c:forEach items="${diets}" var="diet">
            <% c++; %>
            <tr>
                <td>#<% out.print(c);%></td>
                <td>${diet}</td>
                <td><span data-feather="trash"></span><span data-feather="edit"></span></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
