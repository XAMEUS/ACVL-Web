package ensimag.acvl.controller;

import ensimag.acvl.dao.DAOException;
import ensimag.acvl.dao.UserDAO;
import ensimag.acvl.models.User;
import java.io.*;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "Account", urlPatterns = {"/account"})
public class Account extends Controller {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        UserDAO userDAO = new UserDAO(ds);
        System.out.println(action);
        try {
            if (action == null) {
                actionShow(request, response, userDAO);
            } else if (action.equals("register")) {
                actionRegister(request, response, userDAO);
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

    private void actionRegister(HttpServletRequest request,
            HttpServletResponse response,
            UserDAO userDAO) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
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
            request.setAttribute("message", "Mauvais paramètre: action=" + action);
            showError(request, response);
            return;
        }
        UserDAO userDAO = new UserDAO(ds);

        try {
            if (action.equals("create")) {
                actionCreate(request, response, userDAO);
                return;
            } else if (action.equals("signin")) {
                actionSignIn(request, response, userDAO);
                return;
            } else {
                request.setAttribute("message", "Mauvais paramètre: action=" + action);
                showError(request, response);
            }
            actionShow(request, response, userDAO);
        } catch (DAOException e) {
            if (action.equals("signin")) {
                request.setAttribute("title", "Echec de la connexion");
                request.setAttribute("message", "Utilisateur ou mot de passe incorrect");
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            } else if (action.equals("create")) {
                request.setAttribute("title", "Echec de la création de compte");
                request.setAttribute("message", "Utilisateur ou mot de passe incorrect");
                request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
            } else {
                request.setAttribute("title", "DAO exception");
                request.setAttribute("message", "Quelque chose ne s'est pas bien passé...\n" + e.getMessage());
                showError(request, response, e);
            }
        }
    }

    private void actionCreate(HttpServletRequest request,
            HttpServletResponse response,
            UserDAO userDAO)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        if (username.equals("") || password.equals("") || address.equals("")) {
            throw new DAOException("Empty parameters");
        } else {
            userDAO.createUser(username, password, address);
            session.setAttribute("username", username);
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
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
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } else {
            throw new DAOException("Invalid parameters");
        }
    }

}
