package ensimag.acvl.controller;

import ensimag.acvl.dao.DAOException;
import java.io.*;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

//@WebServlet(name = "C", urlPatterns = {"/admin"})
public class Controller extends HttpServlet {

    @Resource(name = "jdbc/database")
    protected DataSource ds;

    protected void showError(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/error/Error.jsp").forward(request, response);
    }

    protected void showError(HttpServletRequest request,
            HttpServletResponse response, Exception e)
            throws ServletException, IOException {
        if (e != null) {
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.getCause().printStackTrace(pw);
            request.setAttribute("exception", sw.toString());
        }
        request.getRequestDispatcher("/WEB-INF/error/Error.jsp").forward(request, response);
    }
    

}