package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;

// BUG FIX: this servlet was previously mapped TWICE - once here via
// @WebServlet("/deleteJob") and once in web.xml via /DeleteJobServlet.
// manage-jobs.jsp only ever links to "DeleteJobServlet" (the web.xml
// mapping), so the "/deleteJob" annotation mapping was unused dead
// mapping. Removed here; the web.xml mapping (unchanged) remains the
// single, working URL for this servlet - no existing link was broken.
public class DeleteJobServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // BUG FIX: this admin-only action had no session check at all -
        // anyone could delete any job by hitting this URL directly,
        // logged in or not.
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect("admin-login.jsp");
            return;
        }

        int jobId = Integer.parseInt(request.getParameter("id"));

        UserDAO dao = new UserDAO();
        dao.deleteJob(jobId);

        response.sendRedirect("manage-jobs.jsp");
    }
}
