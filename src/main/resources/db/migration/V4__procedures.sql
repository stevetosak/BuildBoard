CREATE OR REPLACE PROCEDURE mark_denied_reports_older_than_1month()
    LANGUAGE plpgsql
AS $$
DECLARE
    older_reports RECORD;
    user_id INT;
BEGIN
    FOR older_reports IN
        SELECT
            r.id AS submission_id,
            r.for_misconduct_in AS topic
        FROM report r
                 JOIN submission s ON s.id = r.id
        WHERE s.status = 'PENDING' AND now() - s.created_at >= INTERVAL '1 month'
        LOOP
            BEGIN
                UPDATE submission
                SET status = 'DENIED'
                WHERE id = older_reports.submission_id;

                SELECT t.is_created_by
                INTO user_id
                FROM thread t
                WHERE t.id = older_reports.topic;

                INSERT INTO feedback(description, submission_type, created_at, submitted_for, written_by)
                VALUES (
                           'Stale report. Closing due to inactivity.',
                           'R',
                           NOW(),
                           older_reports.submission_id,
                           user_id
                       );
            END;
        END LOOP;
END;
$$;

CREATE OR REPLACE PROCEDURE mark_denied_pr_requests_older_than_1month()
    LANGUAGE plpgsql
AS $$
DECLARE
    older_reports RECORD;
    user_id INT;
BEGIN
    FOR older_reports IN
        SELECT
            pr.id AS submission_id,
            pr.project_receives AS project_id
        FROM project_request pr
                 JOIN submission s ON s.id = pr.id
        WHERE s.status = 'PENDING' AND now() - s.created_at >= INTERVAL '1 month'
        LOOP
            BEGIN
                UPDATE submission
                SET status = 'DENIED'
                WHERE id = older_reports.submission_id;

                SELECT t.is_created_by
                INTO user_id
                FROM thread t
                WHERE t.id = older_reports.project_id;

                INSERT INTO feedback(description, submission_type, created_at, submitted_for, written_by)
                VALUES (
                           'Stale report. Closing due to inactivity.',
                           'P',
                           NOW(),
                           older_reports.submission_id,
                           user_id
                       );
            END;
        END LOOP;
END;
$$;