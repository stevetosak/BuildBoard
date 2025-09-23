CREATE OR REPLACE PROCEDURE mark_denied_reports_older_than_1month()
LANGUAGE plpgsql
AS $$
DECLARE
    older_reports RECORD;
    user_id int;
BEGIN
    FOR older_reports IN
        SELECT
            r.id AS submission_id,
            r.thread_id as topic
        FROM report r
                 JOIN submission s ON s.id = r.id
        WHERE s.status = 'PENDING' and now() - s.created_at >= INTERVAL '1 month'
    LOOP
        BEGIN
            UPDATE submission
            SET status = 'DENIED'
            WHERE id = older_reports.submission_id;

            select u.id
            into user_id
            from thread t
            join users u
            on t.user_id = u.id
            where t.id = older_reports.thread_id;


            INSERT INTO feedback(description, submission_type, created_at, submission_id, created_by)
            VALUES (
                       'Stale report. Closing due to inactivity.',
                       'R',
                       now(),
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
user_id int;
BEGIN
    FOR older_reports IN
        SELECT
            pr.id AS submission_id,
            pr.project_id as thread_id
        FROM project_request pr
                 JOIN submission s ON s.id = pr.id
        WHERE s.status = 'PENDING' and now() - s.created_at >= INTERVAL '1 month'
            LOOP
        BEGIN
            UPDATE submission
            SET status = 'DENIED'
            WHERE id = older_reports.submission_id;

            select u.id
            into user_id
            from thread t
                     join users u
                          on t.user_id = u.id
            where older_reports.thread_id = t.id;

            INSERT INTO feedback(description, submission_type, created_at, submission_id, created_by)
            VALUES (
                       'Stale report. Closing due to inactivity.',
                       'P',
                       now(),
                       older_reports.submission_id,
                       user_id
                   );
        END;
    END LOOP;
END;
$$;
