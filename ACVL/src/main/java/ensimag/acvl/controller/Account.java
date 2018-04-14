package ensimag.acvl.controller;

import ensimag.acvl.dao.DAOException;
import ensimag.acvl.dao.UserDAO;
import ensimag.acvl.models.User;
import java.io.*;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

@WebServlet(name = "Account", urlPatterns = {"/account"})
public class Account extends HttpServlet {

    @Resource(name = "jdbc/database")
    private DataSource ds;

    
    private void invalidParameters(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/AccountError.jsp").forward(request, response);
    }

    private void erreurBD(HttpServletRequest request,
            HttpServletResponse response, DAOException e)
            throws ServletException, IOException {
        e.printStackTrace();
        request.setAttribute("errorMessage", e.getMessage());
        request.getRequestDispatcher("/WEB-INF/DatabaseError.jsp").forward(request, response);
    }
    
    
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        UserDAO userDAO = new UserDAO(ds);

        try {
            if (action == null) {
                actionShow(request, response, userDAO);
            } else {
                invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }

    private void actionShow(HttpServletRequest request,
            HttpServletResponse response,
            UserDAO userDAO) throws ServletException, IOException {
        List<User> users = userDAO.getUsersList();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/UserList.jsp").forward(request, response);
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
        UserDAO userDAO = new UserDAO(ds);

        try {
            if (action.equals("create")) {
                actionCreate(request, response, userDAO);
            } else if (action.equals("remove")) {
                actionRemove(request, response, userDAO);
            } else if (action.equals("edit")) {
                actionEdit(request, response, userDAO);
            } else if (action.equals("signin")) {
                actionSignIn(request, response, userDAO);
                return;
            } else {
                invalidParameters(request, response);
                return;
            }

            actionShow(request, response, userDAO);

        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }
    
    private void actionCreate(HttpServletRequest request,
            HttpServletResponse response,
            UserDAO userDAO)
            throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        userDAO.createUser(username, password);
    }

    private void actionSignIn(HttpServletRequest request,
            HttpServletResponse response,
            UserDAO userDAO)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (userDAO.signAs(username, password)) {
            session.setAttribute("username", username);
        }
        request.setAttribute("username", username);
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    private void actionRemove(HttpServletRequest request,
            HttpServletResponse response,
            UserDAO userDAO)
            throws IOException, ServletException {
        // TODO
        System.err.println("TODO");
    }

    private void actionEdit(HttpServletRequest request,
            HttpServletResponse response,
            UserDAO userDAO)
            throws IOException, ServletException {
        // TODO
        System.err.println("TODO");
    }

}
