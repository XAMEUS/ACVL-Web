
<h2>Gestion des activités</h2>
<div class="table-responsive">
    <h3>Créer une activité</h3>

    <form method="post" action="admin?view=activities" accept-charset="UTF-8">
        <div class="form-group">
            <label for="activityTitle">Titre de l'activité</label>
            <input id="activityTitle" class="form-control" type="text" name="title" placeholder="">
            <small class="form-text text-muted">Des infos en + sur le champs</small>
        </div>
        <div class="form-group">
            <label for="activityPeriod">Période</label>
            <select id="activityPeriod" name="period" class="form-control">
                <c:forEach items="${periods}" var="period">
                    <option value="${period.id}">${period.start} -> ${period.start}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="description">Description</label>
            <textarea class="form-control" id="description" name="description" rows="3"></textarea>
        </div>
        <div class="form-group">
            <label for="description">Niveaux</label>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="PS" value="PS" id="PS">
                <label class="form-check-label" for="PS">
                    PS
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="MS" value="MS" id="MS">
                <label class="form-check-label" for="MS">
                    MS
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="GS" value="GS" id="GS">
                <label class="form-check-label" for="GS">
                    GS
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="CP" value="CP" id="CP">
                <label class="form-check-label" for="CP">
                    CP
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="CE1" value="CE1" id="CE1">
                <label class="form-check-label" for="CE1">
                    CE1
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="CE2" value="CE2" id="CE2">
                <label class="form-check-label" for="CE2">
                    CE2
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="CM1" value="CM1" id="CM1">
                <label class="form-check-label" for="CM1">
                    CM1
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="CM2" value="CM2" id="CM2">
                <label class="form-check-label" for="CM2">
                    CM2
                </label>
            </div>
        </div>
        <div class="form-group">
            <label>Jours</label>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="Lundi" value="Lundi" id="Lundi">
                <label class="form-check-label" for="Lundi">
                    Lundi
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="Mardi" value="Mardi" id="Mardi">
                <label class="form-check-label" for="Mardi">
                    Mardi
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="Mercredi" value="Mercredi" id="Mercredi">
                <label class="form-check-label" for="Mercredi">
                    Mercredi
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="Jeudi" value="Jeudi" id="Jeudi">
                <label class="form-check-label" for="Lundi">
                    Jeudi
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="Vendredi" value="Vendredi" id="Vendredi">
                <label class="form-check-label" for="Lundi">
                    Vendredi
                </label>
            </div>
        </div>
        <div class="form-group">
            <label for="activityAnimators">Animateurs</label>
            <input id="activityAnimators" class="form-control" type="text" name="animators" placeholder="">
            <small class="form-text text-muted">Animateurs séparés par des ','. Par exemple : "Jean, Lucien"</pre></small>
        </div>
        <div class="form-group">
            <label for="activityCapacity">Capacité</label>
            <input id="activityCapacity" class="form-control" type="number" name="capacity" placeholder="">
            <small class="form-text text-muted">Capacité par groupe (1 groupe / animateur).</pre></small>
        </div>
        <input type="hidden" name="action" value="create-activity">
        <button type="submit" class="btn btn-primary">Créer</button>
    </form>
</div>

<hr>

<h3>Liste des activités</h3>
<table class="table table-striped table-sm">
    <thead>
        <tr>
            <th></th>
            <th></th>
            <th></th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${activities}" var="activity">
        <tr>
            <td>${activity}</td>
        </tr>
    </c:forEach>
</tbody>
</table>
</div>
