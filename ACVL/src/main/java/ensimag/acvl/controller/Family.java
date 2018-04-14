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
public class Family extends HttpServlet {

    @Resource(name = "jdbc/database")
    private DataSource ds;

    
    private void invalidParameters(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/FamilyError.jsp").forward(request, response);
    }

    private void databaseError(HttpServletRequest request,
            HttpServletResponse response, DAOException e)
            throws ServletException, IOException {
        e.printStackTrace();
        request.setAttribute("errorMessage", e.getMessage());
        request.getRequestDispatcher("/WEB-INF/DatabaseError.jsp").forward(request, response);
    }

    private void error(HttpServletRequest request,
            HttpServletResponse response, Exception e)
            throws ServletException, IOException {
        if (e != null) {
            e.printStackTrace();
            request.setAttribute("eMessage", e.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/DatabaseError.jsp").forward(request, response);
    }

    
    
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
                invalidParameters(request, response);
            }
        } catch (DAOException e) {
            databaseError(request, response, e);
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
            request.getRequestDispatcher("/WEB-INF/Family.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            invalidParameters(request, response);
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
                invalidParameters(request, response);
                return;
            }

            actionShow(request, response, childDAO);

        } catch (DAOException e) {
            databaseError(request, response, e);
        }
    }
    
    private void actionCreate(HttpServletRequest request,
            HttpServletResponse response,
            ChildDAO childDAO)
            throws IOException, ServletException {
        try {
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            Date birthdate = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("birthdate")).getTime());
            int idChild = childDAO.createChild(firstname, lastname, birthdate);
            childDAO.setParent((String) request.getSession().getAttribute("username"), idChild);
        } catch (ParseException e) {
            error(request, response, e);
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
