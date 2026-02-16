package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.UserDAO;

@WebServlet("/ApplyJobServlet")
public class ApplyJobServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("student") == null) {
            response.sendRedirect("student-login.jsp");
            return;
        }

        String email = (String) session.getAttribute("student");

        int jobId = Integer.parseInt(request.getParameter("jobId"));

        UserDAO dao = new UserDAO();

        int studentId = dao.getStudentIdByEmail(email);   // SAFE METHOD

        if (studentId == 0) {
            response.sendRedirect("student-dashboard.jsp");
            return;
        }

        dao.applyForJob(studentId, jobId);

        response.sendRedirect("studentDashboard");
    }
}
