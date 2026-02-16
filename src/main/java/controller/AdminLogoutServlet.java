package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

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
