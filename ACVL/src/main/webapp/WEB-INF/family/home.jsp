
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/date.js"></script>

<c:choose>
    <c:when test="${view eq 'home'}">
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Prénom</th>
                    <th>Nom</th>
                    <th>Genre</th>
                    <th>Classe</th>
                    <th>Date de naissance</th>
                    <th>Régime</th>
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
                        <td>
                            <c:forEach items="${child.diet}" var="diet">
                                ${diet},
                            </c:forEach>
                        </td>
                        <td><a href="<%= request.getContextPath()%>/family?view=editChild&child=${child.id}"><i class="far fa-edit"></i></a></td>
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
                        <form method="post" action="family" accept-charset="UTF-8" onsubmit="return checkBirthdate();">
                            <input type="hidden" name="action" value="create">
                            <div class="form-group">
                                <label for="lastname">Nom</label>
                                <input class="form-control" type="text" name="lastname"
                                       required="" autofocus
                                       oninvalid="this.setCustomValidity('Ce champ est requis.')"
                                       oninput="setCustomValidity('')">
                            </div>
                            <div class="form-group">
                                <label for="firstname">Prénom</label>
                                <input class="form-control" type="text" name="firstname" required=""
                                       oninvalid="this.setCustomValidity('Ce champ est requis.')"
                                       oninput="setCustomValidity('')">
                            </div>
                            <div class="form-group">
                                <label for="birthdate">Date de naissance</label>
                                <input class="form-control" type="date" name="birthdate"
                                       placeholder="AAAA-MM-JJ" required=""
                                       oninvalid="this.setCustomValidity('Ce champ est requis.')"
                                       oninput="setCustomValidity('')">
                            </div>
                            <div class="form-group">
                                <label>Genre</label>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="gender" id="male" value="M" checked>
                                    <label class="form-check-label" for="male">H</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="gender" id="female" value="F">
                                    <label class="form-check-label" for="female">F</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="grade">Classe</label>
                                <select name="grade" id="grade" class="form-control">
                                    <option value="PS" selected>PS</option>
                                    <option value="MS">MS</option>
                                    <option value="GS">GS</option>
                                    <option value="CP">CP</option>
                                    <option value="CE1">CE1</option>
                                    <option value="CE2">CE2</option>
                                    <option value="CM1">CM1</option>
                                    <option value="CM2">CM2</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Régimes alimentaires</label>
                                <% int c = 0; %>
                                <c:forEach items="${diets}" var="diet">
                                    <% c++; %>
                                    <div class="form-check">
                                        <input type="checkbox" class="form-check-input" id="diet<% out.print(c); %>" name="diet<%out.print(c);%>" value="${diet}">
                                        <label for="diet<% out.print(c);%>">${diet}</label>
                                    </div>
                                </c:forEach>
                            </div>
                            <button class="btn btn-primary" type="submit" value="add">Ajouter</button>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    </c:when>
    
    <c:when test="${view eq 'editChild'}">
        <div>
            <h2>Éditez les informations de votre enfant</h2><br>
            ${child}
            <hr>
        </div>
            <form method="post" action="family" accept-charset="UTF-8" onsubmit="return checkBirthdate()">
            <input type="hidden" name="action" value="editChild">
            <input type="hidden" name="child" value="${child.id}">
            <div class="form-group">
                <label for="lastname">Nom</label>
                <input class="form-control" type="text" name="lastname"
                       value='${child.lastname}' required=""
                       oninvalid="this.setCustomValidity('Ce champ est requis.')"
                       oninput="setCustomValidity('')">
            </div>
            <div class="form-group">
                <label for="firstname">Prénom</label>
                <input class="form-control" type="text" name="firstname" 
                       value='${child.firstname}' required=""
                       oninvalid="this.setCustomValidity('Ce champ est requis.')"
                       oninput="setCustomValidity('')">
            </div>
            <div class="form-group">
                <label for="birthdate">Date de naissance</label>
                <input class="form-control" type="date" name="birthdate"
                       value='${child.birthdate}' placeholder="AAAA-MM-JJ" required=""
                       oninvalid="this.setCustomValidity('Ce champ est requis.')"
                       oninput="setCustomValidity('')">
            </div>
            <div class="form-group">
                <label>Genre</label>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="gender" id="male" value="M" <c:if test="${child.gender eq 'M'.charAt(0)}">checked</c:if>>
                        <label class="form-check-label" for="male">H</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="gender" id="female" value="F" <c:if test="${child.gender eq 'F'.charAt(0)}">checked</c:if>>
                    <label class="form-check-label" for="female">F</label>
                </div>
            </div>
            <div class="form-group">
                <label for="grade">Classe</label>
                <select name="grade" id="grade" class="form-control">
                    <option value="PS" <c:if test="${child.grade == 'PS '}">selected="selected"</c:if>>PS</option>
                <option value="MS" <c:if test="${child.grade == 'MS '}">selected="selected"</c:if>>MS</option>
                <option value="GS" <c:if test="${child.grade == 'GS '}">selected="selected"</c:if>>GS</option>
                <option value="CP" <c:if test="${child.grade == 'CP '}">selected="selected"</c:if>>CP</option>
                <option value="CE1" <c:if test="${child.grade == 'CE1'}">selected="selected"</c:if>>CE1</option>
                <option value="CE2" <c:if test="${child.grade == 'CE2'}">selected="selected"</c:if>>CE2</option>
                <option value="CM1" <c:if test="${child.grade == 'CM1'}">selected="selected"</c:if>>CM1</option>
                <option value="CM2" <c:if test="${child.grade == 'CM2'}">selected="selected"</c:if>>CM2</option>
                </select>
            </div>
            <div class="form-group">
                <label>Régimes alimentaires</label>
                <% int c1 = 0; %>
                <c:forEach items="${diets}" var="diet">
                    <% c1++; %>
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input" id="diet<% out.print(c1); %>" name="diet<%out.print(c1);%>" value="${diet}"

                               <c:forEach items="${child.diet}" var="childdiet">
                                   <c:if test="${childdiet eq diet}">
                                       checked
                                   </c:if>
                               </c:forEach>
                               >
                        <label for="diet<% out.print(c1);%>">${diet}</label>
                    </div>
                </c:forEach>
            </div>
                <button class="btn btn-primary" type="submit" value="add">Éditer</button>
        </form>



    </c:when>
    <c:otherwise>
        Vue inconnue...
    </c:otherwise>
</c:choose>

