<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Annulations</h2>

<table class="table">
    <thead>
        <tr>
            <th>Date</th>
            <th>Enfant</th>
            <th>TAP</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${cancels}" var="cancel">
            <tr>
                <td>${cancel.date}</td>
                <td>${cancel.child.firstname} ${cancel.child.lastname}</td>
                <td>
                    <c:if test="${cancel.codeType eq 1}">
                        ${cancel.descr}
                    </c:if>
                    <c:if test="${cancel.codeType eq 2}">
                        ${cancel.descr}
                    </c:if>
                    <c:if test="${cancel.codeType eq 3}">
                        <a href="<%= request.getContextPath()%>/admin?view=activity&activity=${cancel.descr.id}&period=${cancel.period.id}">${cancel.descr.title}</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>