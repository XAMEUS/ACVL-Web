<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>Admin</title>
        <link rel="stylesheet" href="style/dashboard.css">
        <%@include file="style/style.jsp" %>
    </head>
    <body>
        <nav class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
            <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">Admin Dashboard</a>
            <!--<input class="form-control form-control-dark w-100" type="text" placeholder="Search" aria-label="Search">-->
            <ul class="navbar-nav px-3">
                <li class="nav-item text-nowrap">
                    <a class="nav-link" href="#">Sign out</a>
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
                                    <a class="nav-link <c:if test="${view eq 'users'}">active</c:if>" href="<%= request.getContextPath()%>/admin?view=users">
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
                                    <a class="nav-link <c:if test="${view eq 'activities'}">active</c:if>" href="<%= request.getContextPath()%>/admin?view=activities">
                                        <span data-feather="sun"></span>
                                        Activités
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link <c:if test="${view eq 'settings'}">active</c:if>" href="<%= request.getContextPath()%>/admin?view=settings">
                                        <span data-feather="settings"></span>
                                        Configuration
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </nav>

                    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
                        <div class="row">
                        <%@include file="debug/debug.jsp" %>
                    </div>
                    <c:if test="${view eq 'calendar'}"><%@include file="admin/calendar.jsp" %></c:if>
                    <c:if test="${view eq 'activities'}"><%@include file="admin/activities.jsp" %></c:if>
                    <c:if test="${view eq 'settings'}"><%@include file="admin/settings.jsp" %></c:if>
                    </main>

                </div>
            </div>
        </body>
        <!-- Icons -->
        <script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
        <script>
            feather.replace()
        </script>

        <!-- Graphs -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.1/Chart.min.js"></script>
        <script>
            var ctx = document.getElementById("myChart");
            var myChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
                    datasets: [{
                            data: [15339, 21345, 18483, 24003, 23489, 24092, 12034],
                            lineTension: 0,
                            backgroundColor: 'transparent',
                            borderColor: '#007bff',
                            borderWidth: 4,
                            pointBackgroundColor: '#007bff'
                        }]
                },
                options: {
                    scales: {
                        yAxes: [{
                                ticks: {
                                    beginAtZero: false
                                }
                            }]
                    },
                    legend: {
                        display: false,
                    }
                }
            });
        </script>
    </body>
<%@include file="style/js.jsp" %>
</html>
