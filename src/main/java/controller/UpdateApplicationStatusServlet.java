package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import dao.UserDAO;

public class UpdateApplicationStatusServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int applicationId = Integer.parseInt(request.getParameter("applicationId"));
        String status = request.getParameter("status");

        UserDAO dao = new UserDAO();
        dao.updateApplicationStatus(applicationId, status);

        response.sendRedirect("manage-applications.jsp");
    }
}
