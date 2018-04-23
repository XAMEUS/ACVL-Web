<%@page import="ensimag.acvl.time.Time"%>
<%@page import="ensimag.acvl.config.Config"%>
<%@ page import="java.util.*" %>
<%
    if(Config.debug) {
%>
Debug Tools:
    <table class="table">
    <tr><td>
        <a href="#header" data-toggle="collapse">Header</a>
        <div id="header" class="collapse">
        <table class="table">
        <% Enumeration e = request.getHeaderNames();
             while (e.hasMoreElements()) {
                 String name = (String) e.nextElement();
                 String value = request.getHeader(name);%>
            <tr><td><%= name%></td><td><%= value%></td></tr>
        <%
            }
        %>
        </table>
        </div>
    </td></tr>
    <tr><td>
        <a href="#attributes" data-toggle="collapse">Attributes</a>
        <div id="attributes" class="collapse">
        <table class="table">
        <% e = request.getAttributeNames();
             while (e.hasMoreElements()) {
                 String name = e.nextElement().toString();
         String value = request.getAttribute(name).toString();%>
            <tr><td><%= name%></td><td><%= value%></td></tr>
        <%
            }
        %>
        </table>
        </div>
    </td></tr>
    <tr><td>
        <strong>Parameters</strong>
        ${param}
    </td></tr>
    <tr><td>
        <strong>Session</strong>
        ${sessionScope}
    </td></tr>
    <tr><td>
        <strong>Cookies</strong>
        ${cookie}
    </td></tr>
    <tr><td>
        <strong>Current System Date</strong>
        <%
            out.print(Time.getDate());
        %>
        <a href="<%= request.getContextPath()%>/time"><br><i class="fas fa-cog"></i> configure</a>
    </td></tr>
</table>

<hr />
<%
    }
%>