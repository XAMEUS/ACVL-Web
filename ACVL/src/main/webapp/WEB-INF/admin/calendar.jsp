<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/date.js"></script>

<h2>Gestion des périodes</h2>
<div class="table-responsive">
    <h3>Créer une période</h3>
    <div>
        <form method="post" action="admin?view=calendar" accept-charset="UTF-8" onsubmit="return checkPeriod();">
            <input type="hidden" name="action" value="period">
            <div class="form-group">
                <label for="limitDate">Date limite des inscriptions</label>
                <input class="form-control" type="date" name="limitDate"
                       placeholder="YYYY-MM-DD" required=""
                       oninvalid="this.setCustomValidity('Ce champ est requis.')"
                       oninput="setCustomValidity('')">
            </div
            <div class="form-group">
                <label for="startDate">Date de début de la période</label>
                <input class="form-control" type="date" name="startDate"
                       placeholder="YYYY-MM-DD" required=""
                       oninvalid="this.setCustomValidity('Ce champ est requis.')"
                       oninput="setCustomValidity('')">
            </div>
            <div class="form-group">
                <label for="endDate">Date de fin de la période</label>
                <input class="form-control" type="date" name="endDate"
                       placeholder="YYYY-MM-DD" required=""
                       oninvalid="this.setCustomValidity('Ce champ est requis.')"
                       oninput="setCustomValidity('')">
            </div>
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
