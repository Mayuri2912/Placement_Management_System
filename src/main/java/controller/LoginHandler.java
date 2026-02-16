package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.UserDAO;

@WebServlet("/LoginHandler")
public class LoginHandler extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO dao = new UserDAO();

        if (dao.validateUser(email, password)) {

            HttpSession session = request.getSession();
            session.setAttribute("admin", email);

            response.sendRedirect(request.getContextPath() + "/adminDashboard");

        } else {
            response.sendRedirect("admin-login.jsp?error=1");
        }
    }
}
