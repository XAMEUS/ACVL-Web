<%@page import="ensimag.acvl.config.Config"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.Date"%>
<%@page import="ensimag.acvl.models.Cancel"%>
<%@page import="java.time.DayOfWeek"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.Locale"%>
<%@page import="java.time.LocalDate"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="ensimag.acvl.time.Time"%>
<%@page import="ensimag.acvl.models.Period"%>
<%@page import="ensimag.acvl.models.Child"%>
<%@page import="ensimag.acvl.models.Activity"%>
<%@page import="ensimag.acvl.models.Activity"%>
<%@page import="java.util.List"%>
<% Period period = (Period) (request.getAttribute("period")); %>
<% ensimag.acvl.models.Registration registration = (ensimag.acvl.models.Registration) (request.getAttribute("registration")); %>
<% LocalDate date = period.getStart().toLocalDate(); %>
<% LocalDate limit = period.getEnd().toLocalDate(); %>
<% Cancel cancel = (Cancel) request.getAttribute("cancel"); %>
<%
    int cantine = 0;
    int garderie0 = 0;
    int garderie1 = 0;
    int garderie2 = 0;
    int garderie3 = 0;
    HashMap<Activity, Integer> activitiesMap = new HashMap<>();
%>
<br>
<%
    while (date.isBefore(limit) || date.equals(limit)) {
        int i = 0;
        if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
            i = 0;
        }
        if (date.getDayOfWeek() == DayOfWeek.TUESDAY) {
            i = 1;
        }
        if (date.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
            i = 2;
        }
        if (date.getDayOfWeek() == DayOfWeek.THURSDAY) {
            i = 3;
        }
        if (date.getDayOfWeek() == DayOfWeek.FRIDAY) {
            i = 4;
        }
        for (; i < 5 && !date.isAfter(limit); i++) {
            if (registration.getCodeGarderie() % 2 == 1 && !cancel.isCanceled(date, 1, 0)) {
                garderie0++;
            }
            if ((registration.getCodeCantine() >> i) % 2 == 1 && !cancel.isCanceled(date, 2, i)) {
                cantine++;
            }

            List<Activity> activities = ((Period) (request.getAttribute("period"))).getActivities().get(i);
            if (activities.size() > 0) {
                Activity a = activities.get(0);
                if (!cancel.isCanceled(date, 3, a.getId())) {
                    if (!activitiesMap.containsKey(a)) {
                        activitiesMap.put(a, 1);
                    } else {
                        activitiesMap.put(a, activitiesMap.get(a) + 1);
                    }
                }
            } else {
                if (i != 2 && (registration.getCodeGarderie() >> 1) % 2 == 1 && !cancel.isCanceled(date, 1, 1)) {
                    garderie1++;
                }
                if (i != 2 && (registration.getCodeGarderie() >> 2) % 2 == 1 && !cancel.isCanceled(date, 1, 2)) {
                    garderie2++;
                }
                if ((i != 2 && (registration.getCodeGarderie() >> 3) % 2 == 1) && !cancel.isCanceled(date, 1, 3)) {
                    garderie3++;
                }
            }
            date = date.plusDays(1);
        }
        date = date.plusDays(2);
    }
%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Facture</title>
        <style>
            table, th, td {
                width: 100%;
                border: 1px solid black;
                border-collapse: collapse;
            }
            th, td {
                padding: 15px;
            }
        </style>
    </head>
    <body>
        <h1>École : Facture des prestations</h1>
        <p>Période : ${period.start} au ${period.end}</p><br />
        <p>Enfant inscrit : ${child.firstname} ${child.lastname}</p>
        <% float total = 0;%>
        <table>
            <tr>
                <th>TAP</th>
                <th>Prix unitaire</th>
                <th>Quantité</th>
                <th>Total</th>
            </tr>
            <tr>
                <td>Cantine</td>
                <td><%= Config.cantinePrice%></td>
                <td><%= cantine%></td>
                <td><%= Config.cantinePrice * cantine%><% total += Config.cantinePrice * cantine;%></td>
            </tr>
            <tr>
                <td>Garderie Matin</td>
                <td><%= Config.garderie0Price%></td>
                <td><%= garderie0%></td>
                <td><%= Config.garderie0Price * garderie0%><% total += Config.garderie0Price * garderie0;%></td>
            </tr>
            <tr>
                <td>Garderie du soir 1</td>
                <td><%= Config.garderie1Price%></td>
                <td><%= garderie1%></td>
                <td><%= Config.garderie1Price * garderie1%><% total += Config.garderie1Price * garderie1;%></td>
            </tr>
            <tr>
                <td>Garderie du soir 2</td>
                <td><%= Config.garderie2Price%></td>
                <td><%= garderie2%></td>
                <td><%= Config.garderie2Price * garderie2%><% total += Config.garderie2Price * garderie2;%></td>
            </tr>
            <tr>
                <td>Garderie du soir 3</td>
                <td><%= Config.garderie3Price%></td>
                <td><%= garderie3%></td>
                <td><%= Config.garderie3Price * garderie3%><% total += Config.garderie3Price * garderie3;%></td>
            </tr>
            <% for (Activity a : activitiesMap.keySet()) { %>
            <tr>
                <td><%= a.getTitle() %></td>
                <td><%= a.getPrice() %></td>
                <td><%= activitiesMap.get(a) %></td>
                <td><%= activitiesMap.get(a) * a.getPrice() %><% total += activitiesMap.get(a) * a.getPrice();%></td>
            </tr>
            <% } %>
            <tr>
                <td>Total</td>
                <td></td>
                <td></td>
                <td><%= total%></td>
            </tr>
        </table>
    </body>
</html>