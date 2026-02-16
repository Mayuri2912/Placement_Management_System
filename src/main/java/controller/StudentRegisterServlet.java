package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.UserDAO;

@WebServlet("/StudentRegisterServlet")
public class StudentRegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("full_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String dob = request.getParameter("dob");
        String course = request.getParameter("course");
        String cgpaStr = request.getParameter("cgpa");
        String skills = request.getParameter("skills");

        double cgpa = 0.0;
        if (cgpaStr != null && !cgpaStr.isEmpty()) {
            cgpa = Double.parseDouble(cgpaStr);
        }

        UserDAO dao = new UserDAO();

        boolean status = dao.registerStudent(
                fullName, email, password,
                phone, gender, dob,
                course, cgpa, skills
        );

        if (status) {
            response.sendRedirect("student-login.jsp?success=1");
        } else {
            response.sendRedirect("student-register.jsp?error=1");
        }
    }
}
