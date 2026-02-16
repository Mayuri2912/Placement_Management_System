package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.UserDAO;

@WebServlet("/StudentLoginServlet")
public class StudentLoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO dao = new UserDAO();

        if (dao.validateStudent(email, password)) {

            HttpSession session = request.getSession();
            session.setAttribute("student", email);

            // ✅ Redirect to servlet, NOT JSP
            response.sendRedirect(request.getContextPath() + "/studentDashboard");

        } else {

            response.sendRedirect("student-login.jsp?error=1");
        }
    }
}
