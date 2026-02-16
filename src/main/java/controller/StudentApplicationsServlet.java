package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.UserDAO;

@WebServlet("/studentApplications")
public class StudentApplicationsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("student") == null) {
            response.sendRedirect("student-login.jsp");
            return;
        }

        String email = (String) session.getAttribute("student");

        UserDAO dao = new UserDAO();
        int studentId = dao.getStudentIdByEmail(email);

        List<String[]> applications =
                dao.getApplicationsByStudent(studentId);

        request.setAttribute("applications", applications);

        request.getRequestDispatcher("student-applications.jsp")
               .forward(request, response);
    }
}
