# CHANGELOG - Bug Fix Phase (Phase 0)

This phase fixed bugs only. No new features were added, no entities/tables
were changed, no JSP/servlet/class/resource was deleted or renamed, and no
existing working functionality was touched beyond what's listed below.

## Files Modified

| File | Change |
|---|---|
| `src/main/java/controller/AdminLogoutServlet.java` | Added `@WebServlet("/AdminLogoutServlet")`. The class's logic (`session.invalidate()` + redirect) was already correct - it simply had no URL mapping anywhere, so it was unreachable. |
| `src/main/java/controller/DeleteJobServlet.java` | Removed the duplicate `@WebServlet("/deleteJob")` annotation (this class was mapped twice - once here, once in `web.xml` at `/DeleteJobServlet`, which is the one `manage-jobs.jsp` actually uses). Also added the missing admin session check. |
| `src/main/java/controller/AddCompanyServlet.java` | Added missing admin session check. |
| `src/main/java/controller/AddJobServlet.java` | Added missing admin session check. |
| `src/main/java/controller/UpdateApplicationStatusServlet.java` | Added missing admin session check. |
| `src/main/webapp/WEB-INF/web.xml` | Added a `<session-config>` block (15-minute timeout). Nothing else in the file changed. |
| `src/main/webapp/admin-dashboard.jsp` | Fixed two broken sidebar links: "Logout" pointed to nonexistent `admin-logout.jsp` (now points to `AdminLogoutServlet`); "Applications" pointed to nonexistent `view-applications.jsp` (now points to `manage-applications.jsp`, the real file). |
| `src/main/webapp/manage-students.jsp` | Fixed the identical two broken links found in this file (same "Logout" and "Applications" issues as above). |
| `src/main/webapp/applications.jsp` | No functional change - added a comment block marking it LEGACY/unreferenced, per your instruction, since it is unreachable from any link in the app. |
| `src/main/webapp/admin-login-process.jsp` | No functional change - added a comment block marking it LEGACY/unreferenced (superseded by `LoginHandler.java`, which is what `admin-login.jsp`'s form actually posts to). |
| `src/main/webapp/logout.jsp` | No functional change - added a comment block marking it LEGACY/unreferenced. |

## Files Created

| File | Purpose |
|---|---|
| `src/main/java/filter/NoCacheFilter.java` | New package, new class. A `@WebFilter("/*")` that sets `Cache-Control: no-cache, no-store, must-revalidate`, `Pragma: no-cache`, and `Expires: 0` on every response. This is the fix for the browser Back-button bug (see below) - it does not replace or touch any existing session-check logic anywhere. |

## Bugs Fixed

1. **Admin logout was completely broken.** Every admin-facing page's logout link pointed to a URL that didn't exist:
   - `admin-dashboard.jsp` and `manage-students.jsp` linked to `admin-logout.jsp` (file never existed).
   - `manage-applications.jsp`, `manage-companies.jsp`, `manage-jobs.jsp` linked to `AdminLogoutServlet` (class existed with correct logic, but had zero URL mapping).
   Fix: mapped the existing `AdminLogoutServlet` and corrected the two JSPs pointing at the wrong filename. All five admin pages now log out correctly.

2. **Two additional broken navigation links**, found during final verification, in the same two JSPs: the "Applications" sidebar link pointed to `view-applications.jsp`, which never existed. Fixed to point to `manage-applications.jsp`, the real file already used everywhere else.

3. **Browser Back button could show a protected page after logout.** Root cause was not the session-invalidation logic itself (every servlet/JSP already checked the session correctly) - it was that the app never sent `Cache-Control` headers, so a browser could repaint a page from its own back/forward cache without ever contacting the server again. Fixed with the new `NoCacheFilter`, applied globally.

4. **No session timeout was configured.** Added an explicit 15-minute `<session-timeout>`.

5. **Four admin-only servlets had no session/authorization check at all** (`AddCompanyServlet`, `AddJobServlet`, `DeleteJobServlet`, `UpdateApplicationStatusServlet`) - anyone could call them directly, without ever logging in, to add companies/jobs, delete jobs, or change application statuses. Each now checks for an active admin session before doing anything, matching the pattern already used correctly by every other protected servlet in the app.

6. **Duplicate servlet mapping removed** without breaking the working URL: `DeleteJobServlet` was reachable at both `/deleteJob` (unused by anything) and `/DeleteJobServlet` (what `manage-jobs.jsp` actually links to). Removed the unused one.

## Features Added

None. This phase was bugs only, as scoped.

## Database Changes

None. No entity, table, or column was touched. `migration.sql` is unchanged from what you provided - not regenerated, since nothing in this phase required a schema change.

## Testing Performed

**Compilation (real, not simulated):** Installed the actual Apache Tomcat 9 `servlet-api`/`jsp-api`/`el-api`/`annotations-api` JARs (matching this project's declared target runtime) plus the Eclipse Compiler for Java (ECJ), and:
- Compiled all 22 Java source files (every servlet, the DAO, both models, and the new filter) - **zero errors**.
- Ran Apache Tomcat's actual Jasper JSP compiler against every JSP in `src/main/webapp` - **zero errors** translating all 17 JSPs.
- Compiled Jasper's generated servlet Java source for all 17 JSPs against the real Tomcat runtime classes - **zero errors**.

**Manual trace-through (every link, form, redirect, and forward in the app):**
- Extracted every `href=`/`action=` in every JSP and cross-checked each against the complete, real set of servlet mappings (both `@WebServlet` annotations and `web.xml` entries). Confirmed zero broken links remain in any reachable page (the one exception - inside the already-orphaned `applications.jsp` - is unreachable from any navigation path).
- Traced Admin Login → Dashboard → Manage Students/Companies/Jobs/Applications → Logout, confirming every link, form action, and redirect target resolves correctly and every admin action now checks the session.
- Traced Student Login → Dashboard → Jobs → Apply → Applications → Profile → Resume Upload/Download → Logout, confirming the same.
- Re-verified Resume Upload (`student-profile.jsp` → `uploadResume` → `UploadResumeServlet` → `UserDAO.updateResumeLink`) and Resume Download (`student-profile.jsp` / `manage-students.jsp` → `downloadResume` → `DownloadResumeServlet`, with its existing student-can-only-see-own-resume / admin-can-see-any-resume check) end-to-end - both were already correctly implemented and remain untouched and working.

**One honest limitation:** this sandbox has no installable standalone Tomcat server package (only the library JARs) and no MySQL server, so I could not boot the actual webapp and click through it in a live browser against a real database. The verification above (real compiler, real JSP compiler, exhaustive static link/redirect tracing) is strong, but it is not a substitute for you doing a final manual click-through after deploying - particularly Session Timeout (which requires waiting out a real 15-minute clock) and the Back-button test (which requires an actual browser).
