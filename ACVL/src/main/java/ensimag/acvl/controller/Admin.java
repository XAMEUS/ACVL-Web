package ensimag.acvl.controller;

import ensimag.acvl.dao.ActivityDAO;
import ensimag.acvl.dao.ChildDAO;
import ensimag.acvl.dao.DAOException;
import ensimag.acvl.dao.PeriodDAO;
import ensimag.acvl.dao.UserDAO;
import ensimag.acvl.models.Period;
import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("message", "Mauvais paramètre: action=" + action);
            showError(request, response);
            return;
        }

        try {
            if (action.equals("period")) {
                PeriodDAO periodDAO = new PeriodDAO(ds);
                periodDAO.createPeriod(
                        new Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("limitDate")).getTime()),
                        new Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("startDate")).getTime()),
                        new Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("endDate")).getTime()));
                viewCalendar(request, response);
                return;
            } else if (action.equals("create-activity")) {
                ActivityDAO activityDAO = new ActivityDAO(ds);
                int codeDays = 0;
                int codeGrades = 0;
                Set<String> parameters = request.getParameterMap().keySet();
                for (String p : parameters) {
                    if (p.equals("Lundi"))
                        codeDays += 1;
                    if (p.equals("Mardi"))
                        codeDays += 2;
                    if (p.equals("Mercredi"))
                        codeDays += 4;
                    if (p.equals("Jeudi"))
                        codeDays += 8;
                    if (p.equals("Vendredi"))
                        codeDays += 16;
                    if (p.equals("PS"))
                        codeGrades += 1;
                    if (p.equals("MS"))
                        codeGrades += 2;
                    if (p.equals("GS"))
                        codeGrades += 4;
                    if (p.equals("CP"))
                        codeGrades += 8;
                    if (p.equals("CE1"))
                        codeGrades += 16;
                    if (p.equals("CE2"))
                        codeGrades += 32;
                    if (p.equals("CM1"))
                        codeGrades += 64;
                    if (p.equals("CM2"))
                        codeGrades += 128;
                }
                List<Integer> periods = new ArrayList<>();
                PeriodDAO periodDAO = new PeriodDAO(ds);
                for (int i = 1; i <= periodDAO.getPeriods().size(); i++) {
                    String period = request.getParameter("period" + i);
                    if (period != null) {
                        periods.add(Integer.valueOf(period));
                    }
                }
                activityDAO.createActivity(Integer.valueOf(request.getParameter("capacity")),
                        periods,
                        codeGrades,
                        codeDays,
                        Integer.valueOf(request.getParameter("strategy")),
                        request.getParameter("title"),
                        request.getParameter("description"),
                        request.getParameter("animators"));
                viewActivities(request, response);
                return;
            } else if (action.equals("diet")) {
                ChildDAO childDAO = new ChildDAO(ds);
                childDAO.addDiet(request.getParameter("diet"));
                viewSettings(request, response);
                return;
            } else {
                request.setAttribute("message", "Mauvais paramètre: action=" + action);
                showError(request, response);
            }
            viewMain(request, response);
        } catch (DAOException e) {
            request.setAttribute("title", "DAO exception");
            request.setAttribute("message", "Quelque chose ne s'est pas bien passé...\n" + e.getMessage());
            showError(request, response, e);
        } catch (ParseException ex) {
            request.setAttribute("title", "DAO exception");
            request.setAttribute("message", "Quelque chose ne s'est pas bien passé au niveau des dates...\n" + ex.getMessage());
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            showError(request, response, ex);
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
        request.setAttribute("users", new UserDAO(ds).getUsersList());
        request.setAttribute("children", new ChildDAO(ds).getChildrenList());
        request.getRequestDispatcher("/WEB-INF/Admin.jsp").forward(request, response);
    }

    private void viewCalendar(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PeriodDAO periodDAO = new PeriodDAO(ds);
        List<Period> periods = periodDAO.getPeriods();
        request.setAttribute("periods", periods);
        request.setAttribute("view", "calendar");
        request.getRequestDispatcher("/WEB-INF/Admin.jsp").forward(request, response);
    }

    private void viewActivities(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PeriodDAO periodDAO = new PeriodDAO(ds);
        ActivityDAO activityDAO = new ActivityDAO(ds);
        List<Period> periods = periodDAO.getPeriods();
        request.setAttribute("periods", periods);
        request.setAttribute("activities", activityDAO.getActivities());
        request.setAttribute("view", "activities");
        request.getRequestDispatcher("/WEB-INF/Admin.jsp").forward(request, response);
    }

    private void viewSettings(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        ChildDAO childDAO = new ChildDAO(ds);
        List<String> diets = childDAO.getDiets();
        request.setAttribute("diets", diets);
        request.setAttribute("view", "settings");
        request.getRequestDispatcher("/WEB-INF/Admin.jsp").forward(request, response);
    }

}
