package ensimag.acvl.controller;

import ensimag.acvl.dao.DAOException;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "Admin", urlPatterns = {"/admin"})
public class Admin extends Controller {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String view = request.getParameter("view");
        try {
            if (view == null) {
                viewMain(request, response);
            } else if (view.equals("main")) {
                viewMain(request, response);
            } else if (view.equals("users")) {
                viewUsers(request, response);
            } else if (view.equals("calendar")) {
                viewCalendar(request, response);
            } else if (view.equals("activities")) {
                viewActivities(request, response);
            } else if (view.equals("settings")) {
                viewSettings(request, response);
            } else {
                request.setAttribute("title", "Parameter Error");
                request.setAttribute("message", "Mauvais paramètre view=" + view);
                System.out.println(request);
                showError(request, response);
            }
        } catch (DAOException e) {
            request.setAttribute("title", "DAO exception");
            request.setAttribute("message", "Quelque chose ne s'est pas bien passé...\n" + e.getMessage());
            showError(request, response, e);
        }
    }

    private void viewMain(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("view", "main");
        request.getRequestDispatcher("/WEB-INF/Admin.jsp").forward(request, response);
    }
    
    private void viewUsers(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("view", "users");
        request.getRequestDispatcher("/WEB-INF/Admin.jsp").forward(request, response);
    }
    
    private void viewCalendar(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("view", "calendar");
        request.getRequestDispatcher("/WEB-INF/Admin.jsp").forward(request, response);
    }
    
    private void viewActivities(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("view", "activities");
        request.getRequestDispatcher("/WEB-INF/Admin.jsp").forward(request, response);
    }
    
    private void viewSettings(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("view", "settings");
        request.getRequestDispatcher("/WEB-INF/Admin.jsp").forward(request, response);
    }

}
