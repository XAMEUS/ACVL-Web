<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Annulations</h2>
${cancels}

<table class="table">
    <c:forEach items="${cancels}" var="cancel">
        <tr>
            <td>${cancel.date}</td>
            <td>${cancel.firstname} ${cancel.child.lastname}</td>
            <td>
                <c:if test="${cancel.codeType eq 1}">
                    ${cancel.descr}
                </c:if>
                <c:if test="${cancel.codeType eq 2}">
                    ${cancel.descr}
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>