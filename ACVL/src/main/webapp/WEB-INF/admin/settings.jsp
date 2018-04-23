<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>R�gimes alimentaires</h2>

<h3>Par p�riode :</h3>
<c:forEach items="${stats}" var="stat">
    <h4>Parmis les enfants inscrits du ${stat.key.start} au ${stat.key.end}</h4>
    <ul>
        <c:forEach items="${stat.value}" var="diet">
            <li>r�gimes ${diet.key} : ${diet.value}</li>
            </c:forEach>
    </ul>
    <hr>
</c:forEach>
<hr>

<h3>Liste des r�gimes sp�ciaux</h3>
<div class="table-responsive">
    <table class="table table-striped table-sm">
        <thead>
            <tr>
                <th></th>
                <th>R�gime</th>
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
                    <td><a href="<%= request.getContextPath()%>/admin?view=settings"><span data-feather="trash"></span></a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>


<hr>

<h3>Ajouter un r�gime</h3>
<form method="post" action="admin?view=settings" accept-charset="UTF-8">
    <input type="hidden" name="action" value="diet">
    <div class="form-row align-items-center">
        <div class="col-auto">
            <label class="sr-only" for="deit">R�gime</label>
            <input type="text" id="diet" name="diet">
        </div>
        <div class="col-auto">
            <button type="submit" class="btn btn-primary mb-2">Ajouter</button>
        </div>
    </div>
</form>
