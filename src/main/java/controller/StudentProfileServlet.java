package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.UserDAO;
import model.Student;

@WebServlet("/studentProfile")
public class StudentProfileServlet extends HttpServlet {

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
        Student student = dao.getStudentByEmail(email);

        request.setAttribute("studentData", student);

        request.getRequestDispatcher("student-profile.jsp")
               .forward(request, response);
    }
}
