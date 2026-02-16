package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;

@WebServlet("/studentJobs")
public class StudentJobsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // 1️⃣ Check student session
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("student") == null) {
            response.sendRedirect("student-login.jsp");
            return;
        }

        try {
            // 2️⃣ Fetch jobs from DAO
            UserDAO dao = new UserDAO();
            List<String[]> jobs = dao.getAllJobs();

            // 3️⃣ Send to JSP
            request.setAttribute("jobs", jobs);

            request.getRequestDispatcher("student-jobs.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Something went wrong while loading jobs.");
        }
    }
}
