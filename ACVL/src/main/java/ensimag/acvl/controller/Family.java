package ensimag.acvl.controller;

import ensimag.acvl.dao.CancelDAO;
import ensimag.acvl.dao.ChildDAO;
import ensimag.acvl.dao.DAOException;
import ensimag.acvl.dao.PeriodDAO;
import ensimag.acvl.dao.RegistrationDAO;
import ensimag.acvl.models.Child;
import ensimag.acvl.models.Period;
import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

@WebServlet(name = "Family", urlPatterns = {"/family"})
public class Family extends Controller {

    @Resource(name = "jdbc/database")
    private DataSource ds;

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");

        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            request.setAttribute("title", "Erreur de connexion");
            request.setAttribute("message", "Veuillez vous connecter afin de pouvoir continuer");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } else {
            ChildDAO childDAO = new ChildDAO(ds);
            String view = request.getParameter("view");
            try {
                if (view == null) {
                    showMain(request, response, childDAO);
                    return;
                }
                switch (view) {
                    case "view":
                        showMain(request, response, childDAO);
                        break;
                    case "editChild":
                        showEditChild(request, response, childDAO);
                        break;
                    case "register":
                        showRegister(request, response);
                        break;
                    case "calendar":
                        showCalendar(request, response);
                        break;
                    case "period":
                        showPeriod(request, response);
                        break;
                    case "facture":
                        showFacture(request, response);
                        break;
                    default:
                        showMain(request, response, childDAO);
                        break;
                }
            } catch (DAOException e) {
                request.setAttribute("title", "DAO exception");
                request.setAttribute("message", "Quelque chose ne s'est pas bien passé...\n" + e.getMessage());
                showError(request, response, e);
            }
        }

    }

    private void showMain(HttpServletRequest request,
            HttpServletResponse response,
            ChildDAO childDAO) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        List<Child> children = childDAO.getChildrenList(username);
        request.setAttribute("children", children);
        List<String> diets = childDAO.getDiets();
        request.setAttribute("diets", diets);
        request.setAttribute("view", "home");
        request.getRequestDispatcher("/WEB-INF/Family.jsp").forward(request, response);
    }

    private void showEditChild(HttpServletRequest request,
            HttpServletResponse response,
            ChildDAO childDAO) throws ServletException, IOException {
        request.setAttribute("child", childDAO.getChild(Integer.valueOf(request.getParameter("child"))));
        List<String> diets = childDAO.getDiets();
        request.setAttribute("diets", diets);
        request.setAttribute("view", "editChild");
        request.getRequestDispatcher("/WEB-INF/Family.jsp").forward(request, response);
    }

    private void showRegister(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        ChildDAO childDAO = new ChildDAO(ds);
        String username = (String) request.getSession().getAttribute("username");
        List<Child> children = childDAO.getChildrenList(username);
        request.setAttribute("children", children);
        request.setAttribute("view", "register");
        request.getRequestDispatcher("/WEB-INF/Family.jsp").forward(request, response);
    }

    private void showCalendar(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        ChildDAO childDAO = new ChildDAO(ds);
        String username = (String) request.getSession().getAttribute("username");
        List<Child> children = childDAO.getChildrenList(username);
        request.setAttribute("children", children);
        request.setAttribute("view", "calendar");
        request.getRequestDispatcher("/WEB-INF/Family.jsp").forward(request, response);
    }

    private void showPeriod(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("view", "period");
        ChildDAO childDAO = new ChildDAO(ds);
        int child = Integer.valueOf(request.getParameter("child"));
        request.setAttribute("child", childDAO.getChild(Integer.valueOf(request.getParameter("child"))));
        int period = Integer.valueOf(request.getParameter("period"));

        CancelDAO cancelDAO = new CancelDAO(ds);
        if (request.getParameterMap().containsKey("cancelDate")) {
            try {
                int codeType = Integer.valueOf(request.getParameter("codeType"));
                int code = Integer.valueOf(request.getParameter("code"));
                Date day = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("cancelDate")).getTime());
                cancelDAO.addCancel(child, period, codeType, code, day);
            } catch (ParseException ex) {
                request.setAttribute("title", "Parse exception");
                request.setAttribute("message", "Quelque chose ne s'est pas bien passé...\n" + ex.getMessage());
                showError(request, response, ex);
            }
        }

        PeriodDAO periodDAO = new PeriodDAO(ds);
        RegistrationDAO registrationDAO = new RegistrationDAO(ds);
        Period p = periodDAO.getPeriod(period);
        p.addActivities(1, registrationDAO.getActivities(child, period, 0));
        p.addActivities(2, registrationDAO.getActivities(child, period, 1));
        p.addActivities(3, registrationDAO.getActivities(child, period, 2));
        p.addActivities(4, registrationDAO.getActivities(child, period, 3));
        p.addActivities(5, registrationDAO.getActivities(child, period, 4));
        request.setAttribute("period", p);
        request.setAttribute("cancel", cancelDAO.getCancels(child, period));
        request.setAttribute("registration", registrationDAO.getRegistration(child, period));
        request.getRequestDispatcher("/WEB-INF/Family.jsp").forward(request, response);
    }
    
    private void showFacture(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("view", "facture");
        ChildDAO childDAO = new ChildDAO(ds);
        int child = Integer.valueOf(request.getParameter("child"));
        request.setAttribute("child", childDAO.getChild(Integer.valueOf(request.getParameter("child"))));
        int period = Integer.valueOf(request.getParameter("period"));

        CancelDAO cancelDAO = new CancelDAO(ds);
        if (request.getParameterMap().containsKey("cancelDate")) {
            try {
                int codeType = Integer.valueOf(request.getParameter("codeType"));
                int code = Integer.valueOf(request.getParameter("code"));
                Date day = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("cancelDate")).getTime());
                cancelDAO.addCancel(child, period, codeType, code, day);
            } catch (ParseException ex) {
                request.setAttribute("title", "Parse exception");
                request.setAttribute("message", "Quelque chose ne s'est pas bien passé...\n" + ex.getMessage());
                showError(request, response, ex);
            }
        }

        PeriodDAO periodDAO = new PeriodDAO(ds);
        RegistrationDAO registrationDAO = new RegistrationDAO(ds);
        Period p = periodDAO.getPeriod(period);
        p.addActivities(1, registrationDAO.getActivities(child, period, 0));
        p.addActivities(2, registrationDAO.getActivities(child, period, 1));
        p.addActivities(3, registrationDAO.getActivities(child, period, 2));
        p.addActivities(4, registrationDAO.getActivities(child, period, 3));
        p.addActivities(5, registrationDAO.getActivities(child, period, 4));
        request.setAttribute("period", p);
        request.setAttribute("cancel", cancelDAO.getCancels(child, period));
        request.setAttribute("registration", registrationDAO.getRegistration(child, period));
        request.getRequestDispatcher("/WEB-INF/family/facture.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("title", "Parameter Error");
            request.setAttribute("message", "Mauvais paramètre action=" + action);
            return;
        }
        ChildDAO childDAO = new ChildDAO(ds);

        try {
            switch (action) {
                case "create":
                    actionCreate(request, response, childDAO);
                    break;
                case "editChild":
                    actionEditChild(request, response, childDAO);
                    break;
                case "register":
                    actionRegister(request, response);
                    break;
                default:
                    request.setAttribute("title", "Parameter Error");
                    request.setAttribute("message", "Mauvais paramètre action=" + action);
                    showError(request, response);
                    return;
            }

            showMain(request, response, childDAO);

        } catch (DAOException e) {
            request.setAttribute("title", "DAO exception");
            request.setAttribute("message", "Quelque chose ne s'est pas bien passé...\n" + e.getMessage());
            showError(request, response, e);
        }
    }

    private void actionCreate(HttpServletRequest request,
            HttpServletResponse response,
            ChildDAO childDAO)
            throws IOException, ServletException {
        try {            
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String gender = request.getParameter("gender");
            String grade = request.getParameter("grade");
            Date birthdate = new Date(new SimpleDateFormat("yyyy-mm-dd").parse(request.getParameter("birthdate")).getTime());
            
            int idChild = childDAO.createChild(firstname, lastname, gender, grade, birthdate);
            childDAO.setParent((String) request.getSession().getAttribute("username"), idChild);
            for (int i = 1; i <= childDAO.getDiets().size(); i++) {
                String diet = request.getParameter("diet" + i);
                if (diet != null) {
                    childDAO.setDiet(idChild, diet);
                }
            }
        } catch (ParseException e) {
            request.setAttribute("title", "Parse exception");
            request.setAttribute("message", "Quelque chose ne s'est pas bien passé...\n" + e.getMessage());
            showError(request, response, e);
        } catch (IllegalArgumentException exc) {
            request.setAttribute("title", "Illegal Argument Exception");
            request.setAttribute("message", "Quelque chose ne s'est pas bien passé au niveau des arguments...\n" + exc.getMessage());
            showError(request, response, exc);
        }
    }

    private void actionRegister(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        int child = Integer.valueOf(request.getParameter("child"));
        int period = Integer.valueOf(request.getParameter("period"));
        int codeCantine = 0;
        int codeGarderie = 0;
        Enumeration<String> params = request.getParameterNames();
        RegistrationDAO registrationDAO = new RegistrationDAO(ds);
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            if (param.startsWith("cantine")) {
                codeCantine += Integer.valueOf(request.getParameter(param));
            } else if (param.startsWith("garderie")) {
                codeGarderie += Integer.valueOf(request.getParameter(param));
            } else if (param.startsWith("activity") && !request.getParameter(param).equals("0")) {
                String[] args = param.split("-");
                int activity = Integer.valueOf(args[1]);
                int day = Integer.valueOf(args[2]);
                registrationDAO.registerWish(child, period, activity, day, Integer.valueOf(request.getParameter(param)));
            }
        }
        registrationDAO.registerChild(child, period, codeCantine, codeGarderie, request.getParameter("infos"));
        ChildDAO childDAO = new ChildDAO(ds);
        showMain(request, response, childDAO);
    }

    private void actionEditChild(HttpServletRequest request,
            HttpServletResponse response,
            ChildDAO childDAO)
            throws IOException, ServletException {
        try {
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String gender = request.getParameter("gender");
            String grade = request.getParameter("grade");
            Date birthdate = new Date(new SimpleDateFormat("yyyy-mm-dd").parse(request.getParameter("birthdate")).getTime());
            int idChild = Integer.valueOf(request.getParameter("child"));
            childDAO.editChild(idChild, firstname, lastname, gender, grade, birthdate);
            childDAO.removeChildDiet(idChild);
            for (int i = 1; i <= childDAO.getDiets().size(); i++) {
                String diet = request.getParameter("diet" + i);
                if (diet != null) {
                    childDAO.setDiet(idChild, diet);
                }
            }
            showMain(request, response, childDAO);
        } catch (ParseException e) {
            System.out.println("ensimag.acvl.controller.Family.actionEditChild()");
            request.setAttribute("title", "Parse exception");
            request.setAttribute("message", "Quelque chose ne s'est pas bien passé...\n" + e.getMessage());
            showError(request, response, e);
        }
    }

}
