package ensimag.acvl.controller;

import ensimag.acvl.dao.ChildDAO;
import ensimag.acvl.dao.DAOException;
import ensimag.acvl.models.Child;
import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        String action = request.getParameter("action");
        ChildDAO childDAO = new ChildDAO(ds);

        try {
            if (action == null) {
                actionShow(request, response, childDAO);
            } else {
                request.setAttribute("title", "Parameter Error");
                request.setAttribute("message", "Mauvais paramètre action=" + action);
                showError(request, response);
            }
        } catch (DAOException e) {
            request.setAttribute("title", "DAO exception");
            request.setAttribute("message", "Quelque chose ne s'est pas bien passé...\n" + e.getMessage());
            showError(request, response, e);
        }
    }

    private void actionShow(HttpServletRequest request,
            HttpServletResponse response,
            ChildDAO childDAO) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            request.setAttribute("message", "Veuillez vous connecter avant de pouvoir continuer");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
        else {
            List<Child> children = childDAO.getChildrenList(username);
            request.setAttribute("children", children);
            List<String> diets = childDAO.getDiets();
            request.setAttribute("diets", diets);
            request.getRequestDispatcher("/WEB-INF/Family.jsp").forward(request, response);
        }
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
            if (action.equals("create")) {
                actionCreate(request, response, childDAO);
            } else if (action.equals("remove")) {
                actionRemove(request, response, childDAO);
            } else if (action.equals("edit")) {
                actionEdit(request, response, childDAO);
            } else {
                request.setAttribute("title", "Parameter Error");
                request.setAttribute("message", "Mauvais paramètre action=" + action);
                return;
            }

            actionShow(request, response, childDAO);

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
            Date birthdate = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("birthdate")).getTime());
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
        }
    }

    private void actionRemove(HttpServletRequest request,
            HttpServletResponse response,
            ChildDAO childDAO)
            throws IOException, ServletException {
        // TODO
        System.err.println("TODO");
    }

    private void actionEdit(HttpServletRequest request,
            HttpServletResponse response,
            ChildDAO childDAO)
            throws IOException, ServletException {
        // TODO
        System.err.println("TODO");
    }

}
