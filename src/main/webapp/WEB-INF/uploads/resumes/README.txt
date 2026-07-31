This folder stores uploaded student resumes (PDF).

Files are named resume_<student_id>.pdf and are managed entirely by
UploadResumeServlet and DownloadResumeServlet. Do not edit this folder
by hand.

It lives under WEB-INF on purpose: Tomcat (and any Servlet-spec compliant
container) never serves files under WEB-INF directly over HTTP, so this
folder is only reachable through DownloadResumeServlet, which checks that
the requester is either an admin or the resume's owning student.
