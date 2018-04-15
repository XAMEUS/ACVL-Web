<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <c:if test="${not empty param.title}" >
            <title>${title}</title>
        </c:if>
        <c:if test="${empty param.title}" >
            <title>Error</title>
        </c:if>
        <title>Admin</title>
        <%@include file="../style/style.jsp" %>
    </head>
    <body>
        <h1 style="text-align: center" class="text-danger">Error</h1>
        <c:if test="${not empty param.title}" >
            <h2 style="text-align: center" class="text-danger">${title}</h2>
        </c:if>
        <c:if test="${message != ""}" >
            <p>Message : </p>
            <pre class='bg-danger text-white'>${message}</pre>
        </c:if>
        <c:if test="${not empty param.exception}" >
            <p>Exception : </p>
            <pre class="bg-danger text-white">${exception}</pre>
        </c:if>
        <%@include file="../debug/debug.jsp" %>
    </body>
    <%@include file="../style/js.jsp" %>
</html>
