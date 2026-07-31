package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.UserDAO;

/**
 * Handles resume upload / replace for a logged-in student.
 *
 * Files are stored under WEB-INF/uploads/resumes/ rather than a public
 * webapp folder. Tomcat never serves anything under WEB-INF directly to
 * a browser, so the only way to retrieve a resume is through
 * DownloadResumeServlet, which enforces the session/ownership check.
 * Storing resumes in a publicly reachable folder (e.g. webapp/uploads)
 * would let anyone download any student's resume just by guessing the
 * file name/URL.
 *
 * Only the file name (e.g. "resume_7.pdf") is saved in the students.resume_link
 * column, not an absolute path, so the data stays portable across machines/deployments.
 */
@WebServlet("/uploadResume")
@MultipartConfig(
        maxFileSize = 5 * 1024 * 1024,        // 5 MB hard limit at the container level
        maxRequestSize = 5 * 1024 * 1024 + 20480
)
public class UploadResumeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("student") == null) {
            response.sendRedirect("student-login.jsp");
            return;
        }

        String email = (String) session.getAttribute("student");

        UserDAO dao = new UserDAO();
        int studentId = dao.getStudentIdByEmail(email);

        if (studentId == 0) {
            response.sendRedirect("student-login.jsp");
            return;
        }

        Part filePart;

        try {
            filePart = request.getPart("resumeFile");
        } catch (IllegalStateException e) {
            // Thrown by the container when the upload exceeds maxFileSize/maxRequestSize
            e.printStackTrace();
            response.sendRedirect("studentProfile?resumeMsg=error&reason=size");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("studentProfile?resumeMsg=error&reason=generic");
            return;
        }

        if (filePart == null || filePart.getSize() == 0) {
            response.sendRedirect("studentProfile?resumeMsg=error&reason=empty");
            return;
        }

        if (filePart.getSize() > MAX_FILE_SIZE) {
            response.sendRedirect("studentProfile?resumeMsg=error&reason=size");
            return;
        }

        String submittedFileName = getSubmittedFileName(filePart);
        String contentType = filePart.getContentType();

        boolean validExtension = submittedFileName != null
                && submittedFileName.toLowerCase().endsWith(".pdf");
        boolean validContentType = contentType != null
                && contentType.toLowerCase().contains("application/pdf");

        if (!validExtension || !validContentType) {
            response.sendRedirect("studentProfile?resumeMsg=error&reason=type");
            return;
        }

        try {
            String uploadDirPath = getServletContext().getRealPath("/WEB-INF/uploads/resumes");
            File uploadDir = new File(uploadDirPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Deterministic name per student -> re-uploading naturally replaces the old file.
            String storedFileName = "resume_" + studentId + ".pdf";
            File targetFile = new File(uploadDir, storedFileName);

            try (InputStream in = filePart.getInputStream();
                 FileOutputStream out = new FileOutputStream(targetFile)) {
                Files.copy(in, targetFile.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }

            boolean updated = dao.updateResumeLink(studentId, storedFileName);

            if (updated) {
                response.sendRedirect("studentProfile?resumeMsg=success");
            } else {
                response.sendRedirect("studentProfile?resumeMsg=error&reason=db");
            }

        } catch (IOException e) {
            e.printStackTrace();
            response.sendRedirect("studentProfile?resumeMsg=error&reason=io");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("studentProfile?resumeMsg=error&reason=generic");
        }
    }

    /**
     * Part#getSubmittedFileName() is Servlet 3.1+; extracted defensively
     * from the Content-Disposition header for maximum container compatibility.
     */
    private String getSubmittedFileName(Part part) {
        String header = part.getHeader("content-disposition");

        if (header == null) {
            return null;
        }

        for (String token : header.split(";")) {
            token = token.trim();
            if (token.startsWith("filename")) {
                String fileName = token.substring(token.indexOf('=') + 1).trim();
                fileName = fileName.replace("\"", "");
                // Guard against a full path being sent by some browsers
                int lastSlash = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
                if (lastSlash >= 0) {
                    fileName = fileName.substring(lastSlash + 1);
                }
                return fileName;
            }
        }

        return null;
    }
}
