<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>Family</title>
    </head>
    <body>
        <h2> Family </h2>


        <form method="post" action="family" accept-charset="UTF-8">
            <input type="hidden" name="action" value="create">
            <input type="text" name="firstname" placeholder="firstname">
            <input type="text" name="lastname" placeholder="lastname">
            <input type="date" name="birthdate">
            <input type="submit" value="Add">
        </form>

        <table>
            <tr>
                <th>Firstname</th>
                <th>Lastname</th>
                <th>Birthdate</th>
            </tr>
            <c:forEach items="${children}" var="child">
                <tr>
                    <td>${child.firstname}</td>
                    <td>${child.lastname}</td>
                    <td>${child.birthdate}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
