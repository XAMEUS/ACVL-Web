
<h2>Gestion des périodes</h2>
<div class="table-responsive">
    <h3>Créer une période</h3>
    <div>
        <form method="post" action="admin?view=calendar" accept-charset="UTF-8">
            <input type="hidden" name="action" value="period">
            <input type="date" name="startDate">
            <input type="date" name="endDate">
            <input type="submit" value="Ajouter">
        </form>
    </div>
    
    <hr>
    
    <h3>Liste des périodes</h3>
    <table class="table table-striped table-sm">
        <thead>
            <tr>
                <th></th>
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${periods}" var="period">
            <tr>
                <td>${period}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
