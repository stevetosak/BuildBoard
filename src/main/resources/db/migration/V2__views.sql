CREATE OR REPLACE VIEW v_project_thread
AS
SELECT thread.id, content, is_created_by, title, repo_url
FROM project_thread project
         JOIN thread
              ON project.id = thread.id;
CREATE OR REPLACE VIEW v_topic_thread
AS
SELECT thread.id, content, is_created_by, title, topic.referenced_by
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
        (select contained_in, id, 0 as depth
         from discussion_thread
         UNION ALL
         select discuss.contained_in, dpth.id, dpth.depth + 1
         from depth_table dpth
                  join discussion_thread discuss
                       on dpth.contained_in = discuss.id),
    tmp as (select id, max(depth) as depth
            from depth_table
            group by id)
select d.id as id, t.is_created_by as user_id, d.depth as depth, d1.contained_in as parent_id, t.created_at as "created_at"
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
                    DISTINCT rp.for_permission, ',' ORDER BY rp.for_permission
                              ) FILTER (
                        WHERE
                        (pr.override_type = 'INCLUDE' AND rpo.for_resource IS NOT NULL)
                            OR
                        (pr.override_type = 'EXCLUDE' AND rpo.for_resource IS NULL)
                        ),
                    ''
    ) AS permissions
FROM channel c
         JOIN project_role pr
              ON pr.valid_in = c.project_has
         LEFT JOIN role_permissions rp
                   ON rp.for_role = pr.id
                       AND rp.for_permission IN ('READ','WRITE')
         LEFT JOIN role_permissions_overrides rpo
                   ON rpo.for_role_permission_role_id = pr.id
                       AND rpo.for_role_permission_permission_name = rp.for_permission
                       AND rpo.for_resource = c.id
GROUP BY c.id, c.name,pr.id
