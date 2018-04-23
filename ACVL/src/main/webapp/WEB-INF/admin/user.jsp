<h2>D�tail du parent : ${user.name}</h2>
<p>Username : ${user.name} </p>
<p>Adresse : ${user.addr} </p>

<h3>Enfants</h3>

<table class="table table-striped">
    <thead>
        <tr>
            <th>Pr�nom</th>
            <th>Nom</th>
            <th>Genre</th>
            <th>Classe</th>
            <th>Date de naissance</th>
            <th>R�gime</th>
            <th></th>
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
            <td>${child.diet}</td>
            <td><a href="<%= request.getContextPath()%>/admin?view=child&child=${child.id}">d�tails</a></td>
        </tr>
    </c:forEach>
</tbody>
</table>