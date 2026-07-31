package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import dao.UserDAO;

public class UpdateApplicationStatusServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // BUG FIX: this admin-only action had no session check at all -
        // anyone could change any application's status by POSTing here
        // directly, logged in or not.
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect("admin-login.jsp");
            return;
        }

        int applicationId = Integer.parseInt(request.getParameter("applicationId"));
        String status = request.getParameter("status");

        UserDAO dao = new UserDAO();
        dao.updateApplicationStatus(applicationId, status);

        response.sendRedirect("manage-applications.jsp");
    }
}
