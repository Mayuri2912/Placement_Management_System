package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.UserDAO;

@WebServlet("/adminDashboard")
public class AdminDashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect("admin-login.jsp");
            return;
        }

        UserDAO dao = new UserDAO();

        // ===== BASIC COUNTS =====
        int totalStudents = dao.getTotalStudents();
        int totalCompanies = dao.getTotalCompanies();
        int totalJobs = dao.getTotalJobs();
        int totalApplications = dao.getTotalApplications();

        // ===== STATUS COUNTS =====
        int appliedCount = dao.getApplicationCountByStatus("APPLIED");
        int shortlistedCount = dao.getApplicationCountByStatus("SHORTLISTED");
        int rejectedCount = dao.getApplicationCountByStatus("REJECTED");
        int selectedCount = dao.getApplicationCountByStatus("SELECTED");

        // ===== PLACEMENT PERCENTAGE =====
        double placementPercentage = 0;

        if (totalStudents > 0) {
            placementPercentage =
                    ((double) selectedCount / totalStudents) * 100;
        }

        // ===== RECENT APPLICATIONS =====
        List<String[]> recentApplications = dao.getRecentApplications();

        // ===== SET ATTRIBUTES =====
        request.setAttribute("totalStudents", totalStudents);
        request.setAttribute("totalCompanies", totalCompanies);
        request.setAttribute("totalJobs", totalJobs);
        request.setAttribute("totalApplications", totalApplications);

        request.setAttribute("appliedCount", appliedCount);
        request.setAttribute("shortlistedCount", shortlistedCount);
        request.setAttribute("rejectedCount", rejectedCount);
        request.setAttribute("finalSelectedCount", selectedCount);

        request.setAttribute("selectedCount", selectedCount);
        request.setAttribute("placementPercentage",
                String.format("%.2f", placementPercentage));

        request.setAttribute("recentApplications", recentApplications);

        request.getRequestDispatcher("admin-dashboard.jsp")
               .forward(request, response);
    }
}
