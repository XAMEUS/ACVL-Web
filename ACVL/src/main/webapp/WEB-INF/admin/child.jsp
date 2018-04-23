<h2>Détail de l'enfant #${child.id}</h2>
<ul>
    <li>Prénom : ${child.firstname}</li>
    <li>Nom : ${child.lastname}</li>
    <li>Genre : ${child.gender}</li>
    <li>Niveau : ${child.grade}</li>
    <li>Date de naissance : ${child.birthdate}</li>
    <li>Régime : ${child.diet}</li>
</ul>
    <p>Parent : <a href="<%= request.getContextPath()%>/admin?view=user&user=${parent.name}">${parent.name} </a></p>
<p>Adresse : ${parent.addr} </p>