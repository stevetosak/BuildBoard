CREATE OR REPLACE VIEW v_project_thread
AS
SELECT thread.id, content, user_id, title, repo_url
FROM project_thread project
         JOIN thread
              ON project.id = thread.id;
CREATE OR REPLACE VIEW v_topic_thread
AS
SELECT thread.id, content, user_id, title, parent_id
FROM topic_thread topic
         JOIN thread
              ON topic.id = thread.id;
CREATE OR REPLACE VIEW v_moderator
AS
SELECT users.id, username, is_activate, password, description, registered_at, sex
FROM moderator
         JOIN users ON moderator.id = users.id;

create or replace view v_discussion_thread
as
with recursive
    depth_table as
        (select parent_id, id, 0 as depth
         from discussion_thread
         UNION ALL
         select discuss.parent_id, dpth.id, dpth.depth + 1
         from depth_table dpth
                  join discussion_thread discuss
                       on dpth.parent_id = discuss.id),
    tmp as (select id, max(depth) as depth
            from depth_table
            group by id)
select d.id as id, t.user_id as user_id, d.depth as depth, d1.parent_id as parent_id, t.created_at as "created_at"
from tmp d
         join depth_table d1
              on d.id = d1.id and d1.depth = d.depth
         join thread t
              on t.id = d.id;


CREATE OR REPLACE VIEW role_channel_permissions AS
SELECT
    c.id as channel_id,
    c.name,
    pr.id as role_id,
    COALESCE(
                    STRING_AGG(
                    DISTINCT rp.permission_name, ',' ORDER BY rp.permission_name
                              ) FILTER (
                        WHERE
                        (pr.override_type = 'INCLUDE' AND rpo.channel_id IS NOT NULL)
                            OR
                        (pr.override_type = 'EXCLUDE' AND rpo.channel_id IS NULL)
                        ),
                    ''
    ) AS permissions
FROM channel c
         JOIN project_role pr
              ON pr.project_id = c.project_id
         LEFT JOIN role_permissions rp
                   ON rp.role_id = pr.id
                       AND rp.permission_name IN ('READ','WRITE')
         LEFT JOIN role_permissions_overrides rpo
                   ON rpo.role_id = pr.id
                       AND rpo.permission_name = rp.permission_name
                       AND rpo.channel_id = c.id
GROUP BY c.id, c.name,pr.id
