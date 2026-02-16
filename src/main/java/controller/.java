package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.UserDAO;

@WebServlet("/studentJobs")
public class StudentJobsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("student") == null) {
            response.sendRedirect("student-login.jsp");
            return;
        }

        UserDAO dao = new UserDAO();
        List<String[]> jobs = dao.getAllJobs();

        request.setAttribute("jobs", jobs);

        request.getRequestDispatcher("student-jobs.jsp")
               .forward(request, response);
    }
}
