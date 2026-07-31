<%--
    LEGACY / UNUSED PAGE - left in place, not deleted, per project rules.

    Nothing in the application links to "logout.jsp". Both the admin side
    (AdminLogoutServlet, now properly mapped) and the student side
    (StudentLogoutServlet, mapped at /studentLogout) have their own
    dedicated, working logout servlets. This generic page also always
    redirects to student-login.jsp regardless of which session type was
    active, which would be the wrong destination for an admin anyway.
    Left untouched and unused.
--%>
<%
    session.invalidate();
    response.sendRedirect("student-login.jsp");
%>
