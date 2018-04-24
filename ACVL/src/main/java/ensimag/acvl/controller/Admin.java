package ensimag.acvl.controller;

import ensimag.acvl.dao.ActivityDAO;
import ensimag.acvl.dao.CancelDAO;
import ensimag.acvl.dao.ChildDAO;
import ensimag.acvl.dao.DAOException;
import ensimag.acvl.dao.PeriodDAO;
import ensimag.acvl.dao.RegistrationDAO;
import ensimag.acvl.dao.UserDAO;
import ensimag.acvl.models.Activity;
import ensimag.acvl.models.Child;
import ensimag.acvl.models.Period;
import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "Admin", urlPatterns = {"/admin"})
public class Admin extends Controller {

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session.getAttribute("username") != null
                && session.getAttribute("username").equals("admin");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            request.getRequestDispatcher("/home.jsp").forward(request, response);
            return;
        }
        String view = request.getParameter("view");
        try {
            if (view == null || view.equals("main")) {
                viewMain(request, response);
            } else if (view.equals("debugOn") || view.equals("debugOff")) {
                debugSwitcher(request, response);
            } else if (view.equals("autoMoulinetteOn") || view.equals("autoMoulinetteOff")) {
                moulinetteSwitcher(request, response);
            } else if (view.equals("users")) {
                viewUsers(request, response);
            } else if (view.equals("calendar")) {
                viewCalendar(request, response);
            } else if (view.equals("activities")) {
                viewActivities(request, response);
            } else if (view.equals("activity")) {
                viewActivity(request, response);
            } else if (view.equals("child")) {
                viewChild(request, response);
            } else if (view.equals("user")) {
                viewUser(request, response);
            } else if (view.equals("cancel")) {
                viewCancel(request, response);
            } else if (view.equals("settings")) {
                if ("removeDiet".equals(request.getParameter("action"))) {
                    ChildDAO childDAO = new ChildDAO(ds);
                    childDAO.removeDiet(request.getParameter("diet"));
                }
                viewSettings(request, response);
                return;
            } else if (view.equals("debug")) {
                viewDebug(request, response);
            } else if (view.equals("moulinette")) {
                RegistrationDAO registrationDAO = new RegistrationDAO(ds);
                request.setAttribute("map", registrationDAO.moulinette());
                request.getRequestDispatcher("/WEB-INF/admin/moulinette.jsp").forward(request, response);
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
        if (!isAdmin(request)) {
            request.getRequestDispatcher("/home.jsp").forward(request, response);
            return;
        }

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
                Date start = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("startDate")).getTime());
                Date end = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("endDate")).getTime());
                Date limit = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("limitDate")).getTime());
                if (end.before(start) || limit.after(start)) {
                    request.setAttribute("message", "La date de limite d'inscription doit être avant la date de début et la date de fin doit être après la date de début");
                    showError(request, response);
                    return;
                }
                periodDAO.createPeriod(
                        limit,
                        start,
                        end);
                viewCalendar(request, response);
                return;
            } else if (action.equals("create-activity")) {
                ActivityDAO activityDAO = new ActivityDAO(ds);
                int codeDays = 0;
                int codeGrades = 0;
                Set<String> parameters = request.getParameterMap().keySet();
                for (String p : parameters) {
                    if (p.equals("Lundi")) {
                        codeDays += 1;
                    }
                    if (p.equals("Mardi")) {
                        codeDays += 2;
                    }
                    if (p.equals("Mercredi")) {
                        codeDays += 4;
                    }
                    if (p.equals("Jeudi")) {
                        codeDays += 8;
                    }
                    if (p.equals("Vendredi")) {
                        codeDays += 16;
                    }
                    if (p.equals("PS")) {
                        codeGrades += 1;
                    }
                    if (p.equals("MS")) {
                        codeGrades += 2;
                    }
                    if (p.equals("GS")) {
                        codeGrades += 4;
                    }
                    if (p.equals("CP")) {
                        codeGrades += 8;
                    }
                    if (p.equals("CE1")) {
                        codeGrades += 16;
                    }
                    if (p.equals("CE2")) {
                        codeGrades += 32;
                    }
                    if (p.equals("CM1")) {
                        codeGrades += 64;
                    }
                    if (p.equals("CM2")) {
                        codeGrades += 128;
                    }
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
                        Float.valueOf(request.getParameter("price")),
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
        } catch (IllegalArgumentException exc) {
            request.setAttribute("title", "Illegal Argument Exception");
            request.setAttribute("message", "Quelque chose ne s'est pas bien passé au niveau des arguments...\n" + exc.getMessage());
            showError(request, response, exc);
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

    private void viewCancel(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("view", "cancel");
        request.setAttribute("cancels", new CancelDAO(ds).getAllCancels());
        request.getRequestDispatcher("/WEB-INF/Admin.jsp").forward(request, response);
    }

    private void viewCalendar(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PeriodDAO periodDAO = new PeriodDAO(ds);
        ActivityDAO activityDAO = new ActivityDAO(ds);
        List<Period> periods = periodDAO.getPeriods();
        request.setAttribute("periods", periods);
        request.setAttribute("view", "calendar");
        String p = request.getParameter("period");
        if (p != null) {
            RegistrationDAO registrationDAO = new RegistrationDAO(ds);
            Period period = periodDAO.getPeriod(Integer.valueOf(p));
            List<ensimag.acvl.models.Registration> registrations = registrationDAO.getRegistrations(period.getId());
            List<Child> inscrits = new ArrayList<>();
            request.setAttribute("stats", registrationDAO.recap(registrations, inscrits));
            request.setAttribute("inscrits", inscrits);
            request.setAttribute("period", period);
            request.setAttribute("children", registrationDAO.getRegistredChilrend(period.getId()));
            request.setAttribute("activities", activityDAO.getActivitiesByPeriod(period.getId()));
        }
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

    private void viewChild(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        ChildDAO childDAO = new ChildDAO(ds);
        request.setAttribute("view", "child");
        int child = Integer.valueOf(request.getParameter("child"));
        request.setAttribute("child", childDAO.getChild(child));
        request.setAttribute("parent", childDAO.getParent(child));
        request.getRequestDispatcher("/WEB-INF/Admin.jsp").forward(request, response);
    }

    private void viewUser(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        ChildDAO childDAO = new ChildDAO(ds);
        UserDAO userDAO = new UserDAO(ds);
        request.setAttribute("view", "user");
        String username = request.getParameter("user");
        request.setAttribute("user", userDAO.getUser(username));
        request.setAttribute("children", childDAO.getChildrenList(username));
        request.getRequestDispatcher("/WEB-INF/Admin.jsp").forward(request, response);
    }

    private void viewActivity(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PeriodDAO periodDAO = new PeriodDAO(ds);
        RegistrationDAO registrationDAO = new RegistrationDAO(ds);
        ActivityDAO activityDAO = new ActivityDAO(ds);
        int idPeriod = Integer.valueOf(request.getParameter("period"));
        int idActivity = Integer.valueOf(request.getParameter("activity"));
        Activity activity = activityDAO.getActivity(idActivity);
        request.setAttribute("period", periodDAO.getPeriod(idPeriod));
        request.setAttribute("activity", activity);
        request.setAttribute("subscribers", registrationDAO.getSubscribers(idPeriod, idActivity));
        request.setAttribute("view", "activity");
        request.getRequestDispatcher("/WEB-INF/Admin.jsp").forward(request, response);
    }

    private void viewSettings(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        ChildDAO childDAO = new ChildDAO(ds);
        List<String> diets = childDAO.getDiets();
        PeriodDAO periodDAO = new PeriodDAO(ds);
        List<Period> periods = periodDAO.getPeriods();
        HashMap<Period, List<Child>> children = new HashMap<>();
        RegistrationDAO registrationDAO = new RegistrationDAO(ds);
        for (Period p : periods) {
            children.put(p, registrationDAO.getRegistredChilrend(p.getId()));
        }
        HashMap<Period, HashMap<String, Integer>> stats = new HashMap<>();
        for (Period p : periods) {
            stats.put(p, registrationDAO.recapDiets(children.get(p)));
        }
        request.setAttribute("periods", periods);
        request.setAttribute("children", children);
        request.setAttribute("diets", diets);
        request.setAttribute("stats", stats);
        request.setAttribute("view", "settings");
        request.getRequestDispatcher("/WEB-INF/Admin.jsp").forward(request, response);
    }

    private void viewDebug(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("view", "debug");
        request.getRequestDispatcher("/WEB-INF/Admin.jsp").forward(request, response);
    }

    private void debugSwitcher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ensimag.acvl.config.Config.debug = request.getParameter("view").equals("debugOn");
        viewDebug(request, response);
    }

    private void moulinetteSwitcher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ensimag.acvl.config.Config.autoMoulinette = request.getParameter("view").equals("autoMoulinetteOn");
        viewDebug(request, response);
    }

}
