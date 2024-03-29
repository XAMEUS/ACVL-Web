<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>Admin</title>
        <link rel="stylesheet" href="style/dashboard.css">
        <%@include file="style/style.jsp" %>
         <style>
            .error-message {
                color: red;
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
            <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">Admin Dashboard</a>
            <!--<input class="form-control form-control-dark w-100" type="text" placeholder="Search" aria-label="Search">-->
            <ul class="navbar-nav px-3">
                <li class="nav-item text-nowrap">
                    <a class="nav-link" href="account?action=logout">Se déconnecter</a>
                </li>
            </ul>
        </nav>

        <div class="container-fluid">
            <div class="row">
                <nav class="col-md-2 d-none d-md-block bg-light sidebar">
                    <div class="sidebar-sticky">
                        <ul class="nav flex-column">
                            <li class="nav-item">
                                <a class="nav-link <c:if test="${view eq 'main'}">active</c:if>" href="<%= request.getContextPath()%>/admin?view=main">
                                        <span data-feather="home"></span>
                                        Dashboard <span class="sr-only">(current)</span>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link <c:if test="${view eq 'users'}">active</c:if><c:if test="${view eq 'user'}">active</c:if><c:if test="${view eq 'child'}">active</c:if>" href="<%= request.getContextPath()%>/admin?view=users">
                                        <span data-feather="users"></span>
                                        Personnes
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link <c:if test="${view eq 'calendar'}">active</c:if>" href="<%= request.getContextPath()%>/admin?view=calendar">
                                        <span data-feather="calendar"></span>
                                        Périodes
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link <c:if test="${view eq 'activities'}">active</c:if> <c:if test="${view eq 'activity'}">active</c:if>" href="<%= request.getContextPath()%>/admin?view=activities">
                                        <span data-feather="sun"></span>
                                        Activités
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link <c:if test="${view eq 'cancel'}">active</c:if>" href="<%= request.getContextPath()%>/admin?view=cancel">
                                        <span data-feather="x-circle"></span>
                                        Annulations
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link <c:if test="${view eq 'settings'}">active</c:if>" href="<%= request.getContextPath()%>/admin?view=settings">
                                        <span data-feather="settings"></span>
                                        Configuration
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="<%= request.getContextPath()%>/admin?view=debug">
                                        <span data-feather="terminal"></span>
                                        Déboguage
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </nav>

                    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
                        <div class="row">
                        <%@include file="debug/debug.jsp" %>
                    </div>
                    <c:if test="${view eq 'main'}"><%@include file="admin/home.jsp" %></c:if>
                    <c:if test="${view eq 'users'}"><%@include file="admin/users.jsp" %></c:if>
                    <c:if test="${view eq 'calendar'}"><%@include file="admin/calendar.jsp" %></c:if>
                    <c:if test="${view eq 'activities'}"><%@include file="admin/activities.jsp" %></c:if>
                    <c:if test="${view eq 'activity'}"><%@include file="admin/activity.jsp" %></c:if>
                    <c:if test="${view eq 'child'}"><%@include file="admin/child.jsp" %></c:if>
                    <c:if test="${view eq 'user'}"><%@include file="admin/user.jsp" %></c:if>
                    <c:if test="${view eq 'cancel'}"><%@include file="admin/cancel.jsp" %></c:if>
                    <c:if test="${view eq 'settings'}"><%@include file="admin/settings.jsp" %></c:if>
                    <c:if test="${view eq 'debug'}"><%@include file="admin/debug.jsp" %></c:if>
                    </main>

                </div>
            </div>
        </body>
        <!-- Icons -->
        <script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
        <script>
            feather.replace();
        </script>
        <%@include file="style/js.jsp" %>
    </body>
</html>
