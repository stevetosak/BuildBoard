INSERT INTO users (username, is_activate, password, description, registered_at, sex)
VALUES
    ('user1', true, '$2a$12$0f.x7aBM2wFBZBXoLPj0BObVsk.J1kXFYo5nb4niAWkI4hk5tHvDy', 'First user', NOW(), 'M'),
    ('user2', true, '$2a$12$VkR0a47LDVM6aUqFcEJGSu9jhZCz.05tCoyiRicFObt4f2x2gijKa', 'Second user', NOW(), 'F'),
    ('user3', true, '$2a$12$eSLdHHJ1KFgv.dOupmloXeItjrt2o1IB6ER6Nq7WYj9Jfr2bEwK2a', 'Third user', NOW(), 'M'),
    ('user4', true, '$2a$12$dF5SXcNhMulgU3Qre3nh1e.aatRiJZsnfoBSqReGnXe9rIbHYVWhe', 'Fourth user', NOW(), 'F'),
    ('user5', true, '$2a$12$zHrloz8WG2zo5S6MTf1C0ez1raMlmDJdB8OOa2I1S2pVy9oI76YTa', 'Fifth user', NOW(), 'M');

INSERT INTO thread (content, user_id)
VALUES
    ('Main content for topic thread 1', 1),
    ('Main content for topic thread 2', 2),
    ('Discussion content for topic 1', 1),
    ('Discussion content for topic 2', 2),
    ('Project-specific thread content', 3),
    ('Reply to topic 1', 4),
    ('Further discussion on topic 2', 5);

INSERT INTO topic_thread (id, title, guidelines, parent_id)
VALUES
    (1, 'Topic 1', '{"rule1": "Follow guidelines"}', NULL),
    (2, 'Topic 2', '{"rule2": "Be respectful"}', NULL);

INSERT INTO discussion_thread (id, parent_id)
VALUES
    (3, 1),
    (4, 2),
    (6, 3),
    (7, 4);

INSERT INTO project_thread (id, title, repo_url)
VALUES
    (5, 'Project 1 Thread', 'http://github.com/project1');

INSERT INTO likes (user_id, thread_id)
VALUES
    (1, 3),
    (2, 4),
    (3, 5),
    (4, 6),
    (5, 7);
INSERT INTO topic_belongs_to_project (topic_id, project_id)
VALUES
    (1, 5),
    (2, 5);

INSERT INTO blacklisted_user (topic_id, user_id, moderator_id, start_date, end_date, reason)
VALUES
    (1, 2, 1, NOW(), NOW() + INTERVAL '7 days', 'Spamming'),
    (2, 3, 4, NOW(), NOW() + INTERVAL '3 days', 'Offensive language');

INSERT INTO developer_associated_with_project (project_id, developer_id, started_at)
VALUES
    (5, 2, NOW()),
    (5, 3, NOW());

INSERT INTO permissions (name)
VALUES
    ('Create Thread'),
    ('Delete Thread');

INSERT INTO project_roles (name, project_id, description)
VALUES
    ('Admin', 5, 'Admin role for the project'),
    ('Developer', 5, 'Developer role for the project');

INSERT INTO users_project_roles (user_id, project_id, role_name)
VALUES
    (3, 5, 'Admin'),
    (2, 5, 'Developer'),
    (4, 5, 'Developer');

INSERT INTO project_roles_permissions (permission_name, role_name, project_id)
VALUES
    ('Create Thread', 'Admin', 5),
    ('Delete Thread', 'Admin', 5);

INSERT INTO project_request (description, status, user_id, project_id)
VALUES
    ('Request to join Project 1', 'PENDING', 2, 5),
    ('Request to join Project 1', 'ACCEPTED', 4, 5);

INSERT INTO report (created_at, description, status, thread_id, for_user_id, by_user_id)
VALUES
    (NOW(), 'Inappropriate content', 'PENDING', 3, 2, 1),
    (NOW(), 'Spam content', 'DENIED', 6, 4, 3);

INSERT INTO channel (name, description, project_id, developer_id)
VALUES
    ('General', 'General discussion channel', 5, 2),
    ('Updates', 'Project updates channel', 5, 3);

INSERT INTO messages (sent_at, content, sent_by, project_id, channel_name)
VALUES
    (NOW(), 'Hello, team!', 2, 5, 'General'),
    (NOW(), 'We need to push the deadline.', 3, 5, 'Updates');
   
