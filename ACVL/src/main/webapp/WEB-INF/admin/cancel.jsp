<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Annulations</h2>

<table class="table">
    <c:forEach items="${cancels}" var="cancel">
        <tr>
            <td>${cancel.date}</td>
        </tr>
    </c:forEach>
</table>