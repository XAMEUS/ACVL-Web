<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>Users Management</title>
        <script src="formularise.js"></script>
    </head>
    <body>
        <h2> Users list </h2>


        <form method="post" action="account" accept-charset="UTF-8">
            <input type="hidden" name="action" value="create">
            <input type="text" name="username" placeholder="username">
            <input type="password" name="password" placeholder="password">
            <input type="submit" value="Create">
        </form>

        <table>
            <tr>
                <th>Username</th>
            </tr>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.name}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
