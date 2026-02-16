package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.UserDAO;

@WebServlet("/studentDashboard")
public class StudentDashboardServlet extends HttpServlet {

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

        int totalApplications = dao.getStudentTotalApplications(studentId);
        int shortlistedCount = dao.getStudentShortlistedCount(studentId);
        int profileViews = dao.getStudentProfileViews(studentId);

        List<String[]> recommendedJobs = dao.getRecommendedJobs(studentId);

        request.setAttribute("totalApplications", totalApplications);
        request.setAttribute("shortlistedCount", shortlistedCount);
        request.setAttribute("profileViews", profileViews);
        request.setAttribute("recommendedJobs", recommendedJobs);

        request.getRequestDispatcher("student-dashboard.jsp")
               .forward(request, response);
    }
}
