-- ============================================================
-- Migration for Feature 1: Student Resume Upload
-- ============================================================
--
-- NO SCHEMA CHANGE IS ACTUALLY REQUIRED.
--
-- The `students` table (see placemnt_db.sql) already has:
--     `resume_link` varchar(255) DEFAULT NULL
--
-- This column is exactly what the new feature uses: after a student
-- uploads a PDF, UploadResumeServlet stores the file on disk under
-- WEB-INF/uploads/resumes/resume_<student_id>.pdf and writes ONLY the
-- generated file name (e.g. "resume_7.pdf") into this existing column.
--
-- The statement below is included only as a SAFE, idempotent guard in
-- case you are applying this migration to an older copy of the database
-- that was created before `resume_link` existed. It will not error and
-- will not touch any existing data if the column is already present.
-- No table is dropped, no column is dropped, no existing data is modified.
-- ============================================================

ALTER TABLE `students`
    ADD COLUMN IF NOT EXISTS `resume_link` varchar(255) DEFAULT NULL;
