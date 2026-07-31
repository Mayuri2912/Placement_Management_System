package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;

/**
 * Streams a student's resume PDF.
 *
 * Access rules:
 *  - A logged-in admin can view/download ANY student's resume (used from manage-students.jsp).
 *  - A logged-in student can only view/download THEIR OWN resume (used from student-profile.jsp).
 *  - Anyone else gets a 403.
 *
 * Files live under WEB-INF/uploads/resumes/, which Tomcat never serves directly,
 * so this servlet is the only path to a resume file.
 */
@WebServlet("/downloadResume")
public class DownloadResumeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        boolean isAdmin = session != null && session.getAttribute("admin") != null;
        boolean isStudent = session != null && session.getAttribute("student") != null;

        if (!isAdmin && !isStudent) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Please log in to view this resume.");
            return;
        }

        int requestedStudentId;

        try {
            requestedStudentId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid student id.");
            return;
        }

        UserDAO dao = new UserDAO();

        if (isStudent && !isAdmin) {
            String email = (String) session.getAttribute("student");
            int ownStudentId = dao.getStudentIdByEmail(email);

            if (ownStudentId != requestedStudentId) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "You can only view your own resume.");
                return;
            }
        }

        String storedFileName = dao.getResumeLinkByStudentId(requestedStudentId);

        if (storedFileName == null || storedFileName.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "No resume uploaded for this student.");
            return;
        }

        String uploadDirPath = getServletContext().getRealPath("/WEB-INF/uploads/resumes");
        File file = new File(uploadDirPath, storedFileName);

        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resume file is missing on the server.");
            return;
        }

        response.setContentType("application/pdf");
        response.setContentLengthLong(file.length());
        response.setHeader("Content-Disposition", "inline; filename=\"" + storedFileName + "\"");

        try (InputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

        } catch (IOException e) {
            e.printStackTrace();
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Could not read resume file.");
            }
        }
    }
}
