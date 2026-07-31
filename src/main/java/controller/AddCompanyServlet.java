package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import dao.UserDAO;

public class AddCompanyServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // BUG FIX: this admin-only action had no session check at all -
        // anyone could add a company by POSTing here directly, logged in or not.
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect("admin-login.jsp");
            return;
        }

        String name = request.getParameter("name");
        String location = request.getParameter("location");
        String email = request.getParameter("email");

        UserDAO dao = new UserDAO();
        dao.addCompany(name, location, email);

        response.sendRedirect("manage-companies.jsp");
    }
}
