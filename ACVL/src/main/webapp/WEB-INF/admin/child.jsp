<h2>D�tail de l'enfant #${child.id}</h2>
<ul>
    <li>Pr�nom : ${child.firstname}</li>
    <li>Nom : ${child.lastname}</li>
    <li>Genre : ${child.gender}</li>
    <li>Niveau : ${child.grade}</li>
    <li>Date de naissance : ${child.birthdate}</li>
    <li>R�gime : ${child.diet}</li>
</ul>
    <p>Parent : <a href="<%= request.getContextPath()%>/admin?view=user&user=${parent.name}">${parent.name} </a></p>
<p>Adresse : ${parent.addr} </p>