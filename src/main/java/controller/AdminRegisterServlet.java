package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.UserDAO;

@WebServlet("/AdminRegister")
public class AdminRegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO dao = new UserDAO();

        if (dao.registerAdmin(name, email, password)) {

            response.sendRedirect("admin-login.jsp?registered=1");

        } else {
            response.sendRedirect("admin-registration.jsp?error=1");
        }
    }
}
