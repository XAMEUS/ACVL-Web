<%@page import="ensimag.acvl.models.Activity"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="ensimag.acvl.models.Child"%>
<%@page import="ensimag.acvl.models.Period"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<h1>Inscriptions</h1>
<% int c = 0; %>
<c:forEach items="${children}" var="child">
    <h2>${child.firstname} ${child.lastname}</h2>
    <% if (((Child) (pageContext.findAttribute("child"))).getUnregisteredPeriods().size() == 0) {%>
    <div class="alert alert-primary" role="alert">
        ${child.firstname} ${child.lastname} est inscrit partout, c'est bon.
    </div>
    <%} else {%>
    <div class="alert alert-warning" role="alert">
        ${child.firstname} ${child.lastname} doit être inscrit.e au(x) période(s) suivante(s):
    </div>
    <c:forEach items="${child.unregisteredPeriods}" var="unregisteredPeriod">
        <% c++; %>
        ${unregisteredPeriod}
        <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#period<%= c%>" aria-expanded="false">Inscrire</button>
        <div class="collapse" id="period<%=c %>">
            <form method="post" action="family" accept-charset="UTF-8">
                <input type="hidden" name="action" value="register">
                <input type="hidden" name="child" value="${child.id}">
                <input type="hidden" name="period" value="${unregisteredPeriod.id}">
                <div class="form-group">
                    <label>Sélectionnez les jours de cantine : </label>
                    <table class="table">
                        <thead>
                            <tr>
                                <th></th>
                                <th>Lundi</th>
                                <th>Mardi</th>
                                <th>Mercredi</th>
                                <th>Jeudi</th>
                                <th>Vendredi</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <th>Cantine</th>
                                <td>
                                    <input type="checkbox" class="form-check-input" name="cantine1" value="1">
                                </td>
                                <td>
                                    <input type="checkbox" class="form-check-input" name="cantine2" value="2">
                                </td>
                                <td>
                                    <input type="checkbox" class="form-check-input" name="cantine3" value="3">
                                </td>
                                <td>
                                    <input type="checkbox" class="form-check-input" name="cantine4" value="4">
                                </td>
                                <td>
                                    <input type="checkbox" class="form-check-input" name="cantine5" value="5">
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div class="form-group">
                    <input id="garderie0${period.id}" type="checkbox" class="form-check-input" name="garderie1" value="1">
                    <label for="garderie1${period.id}">Garderie Matin : 7h00 - 8h30 </label>
                </div>
                <div class="form-group">
                    <input id="garderie1${period.id}" type="checkbox" class="form-check-input" name="garderie2" value="2">
                    <label for="garderie2${period.id}">Garderie du soir 1 : 15h45 - 16h30 </label>
                </div>
                <div class="form-group">
                    <input id="garderie2${period.id}" type="checkbox" class="form-check-input" name="garderie3" value="4">
                    <label for="garderie3${period.id}">Garderie du soir 2 : 16h30 - 17h15 </label>
                </div>
                <div class="form-group">
                    <input id="garderie3${period.id}" type="checkbox" class="form-check-input" name="garderie4" value="8">
                    <label for="garderie4${period.id}">Garderie du soir 3 : 17h15 - 18h00 </label>
                </div>

                <%
                    int day = 0;
                    String[] days = {"lundi", "mardi", "mercredi", "jeudi", "vendredi"};
                %>

                <c:forEach items="${unregisteredPeriod.activities}" var="activities">
                    <% int n = ((List<Activity>)(pageContext.getAttribute("activities"))).size(); %>
                    <c:if test="${not empty activities}">
                        <div class="form-group">
                            Le <% out.print(days[day]);%>, souhaitez-vous inscrire votre enfant :
                            <table class="table">
                            <c:forEach items="${activities}" var="activity">
                                <tr>
                                    <td>
                                        ${activity}
                                    </td>
                                    <td>
                                        <select class="form-control" name="activity-${activity.id}-<%=day%>}">
                                            <option value="0">Non</option>
                                            <% for (int i = 1; i <= n; i++) {%>
                                            <option value="<%=i%>}">Voeux <%=i%></option>
                                            <% } %>
                                        </select>
                                    </td>
                                </tr>
                            </c:forEach>
                            </table>
                        </div>
                    </c:if>
                    <% day++; %>
                </c:forEach>
                <div class="form-group">
                    <label for="infos">Informations supplémentaires</label>
                    <textarea class="form-control" rows="3" name='infos'></textarea>
                    <small class="form-text text-muted">Ce que vous souhaitez transmettre en plus à propos de votre enfant. Maximum : 500 charactères</small>
                </div>
                <button type="submit" class="btn btn-primary">Envoyer</button>
            </form>
        </div>
    </c:forEach>
    <% }%>
    <hr>
</c:forEach>