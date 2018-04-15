package ensimag.acvl.controller;

import ensimag.acvl.dao.DAOException;
import java.io.*;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

@WebServlet(name = "Admin", urlPatterns = {"/admin"})
public class Admin extends HttpServlet {

    @Resource(name = "jdbc/database")
    private DataSource ds;

    
    private void invalidParameters(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
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

}
