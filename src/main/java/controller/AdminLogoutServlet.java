package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// BUG FIX: this servlet's logic was already correct (session.invalidate() +
// redirect to admin-login.jsp), but it had NO url mapping anywhere - neither
// a @WebServlet annotation nor a web.xml entry - so every "Logout" link in
// admin-dashboard.jsp, manage-companies.jsp, manage-jobs.jsp, and
// manage-applications.jsp that pointed to "AdminLogoutServlet" was a 404.
// Adding the mapping below is the fix; no other logic in this class changed.
@WebServlet("/AdminLogoutServlet")
public class AdminLogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();   // destroy session
        }

        // Redirect safely to login page
        response.sendRedirect(request.getContextPath() + "/admin-login.jsp");
    }
}
