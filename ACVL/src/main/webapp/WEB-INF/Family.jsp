<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>Family</title>
        <%@include file="style/style.jsp" %>
    </head>
    <body>
        <%@include file="debug/debug.jsp" %>
        <h2> Family </h2>


        <form method="post" action="family" accept-charset="UTF-8">
            <input type="hidden" name="action" value="create">
            <input type="text" name="firstname" placeholder="firstname">
            <input type="text" name="lastname" placeholder="lastname">
            <input type="radio" name="gender" value="M" id="M">
            <label for="M">M</label>
            <input type="radio" name="gender" value="F" id="F">
            <label for="F">F</label>
            <select name="grade">
                <option value="PS">PS</option>
                <option value="PS">MS</option>
                <option value="PS">GS</option>
                <option value="PS">CP</option>
                <option value="PS">CE1</option>
                <option value="PS">CE2</option>
                <option value="PS">CM1</option>
                <option value="PS">CM2</option>
            </select>
            <input type="date" name="birthdate">
            <% int c = 0; %>
            <c:forEach items="${diets}" var="diet">
                <% c++; %>
                <input type="checkbox" id="diet<% out.print(c); %>" name="diet<%out.print(c);%>" value="${diet}">
                <label for="diet<% out.print(c); %>">${diet}</label>
            </c:forEach>
            <input type="submit" value="Add">
        </form>

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
    </body>
    <%@include file="style/js.jsp" %>
</html>
