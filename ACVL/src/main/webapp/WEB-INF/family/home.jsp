<table class="table table-striped">
    <thead>
        <tr>
            <th>Firstname</th>
            <th>Lastname</th>
            <th>Gender</th>
            <th>Grade</th>
            <th>Birthdate</th>
            <th>Diet</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${children}" var="child">
        <tr>
            <td>${child.firstname}</td>
            <td>${child.lastname}</td>
            <td>${child.gender}</td>
            <td>${child.grade}</td>
            <td>${child.birthdate}</td>
            <td>
        <c:forEach items="${child.diet}" var="diet">
            ${diet},
        </c:forEach>
        </td>
        </tr>
    </c:forEach>
</tbody>
</table>

<div>
    <a class="btn btn-primary" data-toggle="collapse" href="#add" role="button" aria-expanded="false" aria-controls="add">Ajouter un enfant</a>
</div>
<div class="row">
    <div class="col">
        <div class="collapse multi-collapse" id="add">
            <div class="card card-body">
                <form method="post" action="family" accept-charset="UTF-8">
                    <input type="hidden" name="action" value="create">
                    <div class="form-group">
                        <label for="lastname">Nom</label>
                        <input type="text" name="lastname" class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="firstname">Prénom</label>
                        <input type="text" name="firstname" class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="firstname">Date de naissance</label>
                        <input type="date" name="birthdate" placeholder="AAAA-MM-JJ" class="form-control">
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="gender" id="male" value="M">
                        <label class="form-check-label" for="male">H</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="gender" id="female" value="F">
                        <label class="form-check-label" for="female">F</label>
                    </div>
                    <div class="form-group">
                        <label for="grade">Classe</label>
                        <select name="grade" id="grade" class="form-control">
                            <option value="PS">PS</option>
                            <option value="MS">MS</option>
                            <option value="GS">GS</option>
                            <option value="CP">CP</option>
                            <option value="CE1">CE1</option>
                            <option value="CE2">CE2</option>
                            <option value="CM1">CM1</option>
                            <option value="CM2">CM2</option>
                        </select>
                    </div>
                    <% int c = 0; %>
                    <c:forEach items="${diets}" var="diet">
                        <% c++; %>
                        <div class="form-check">
                            <input type="checkbox" class="form-check-input" id="diet<% out.print(c); %>" name="diet<%out.print(c);%>" value="${diet}">
                            <label for="diet<% out.print(c);%>">${diet}</label>
                        </div>
                    </c:forEach>
                    <button type="submit" class="btn btn-primary" value="add">Ajouter</button>
                </form>

            </div>
        </div>
    </div>
</div>
