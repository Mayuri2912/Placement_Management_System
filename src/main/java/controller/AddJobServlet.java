package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import dao.UserDAO;

public class AddJobServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int companyId = Integer.parseInt(request.getParameter("companyId"));
        String title = request.getParameter("title");
        String salary = request.getParameter("salary");
        String lastDate = request.getParameter("lastDate");

        UserDAO dao = new UserDAO();
        dao.addJob(companyId, title, salary, lastDate);

        response.sendRedirect("manage-jobs.jsp");
    }
}
