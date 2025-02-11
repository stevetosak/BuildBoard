
INSERT INTO users (username, is_activate, password, description, registered_at, sex,name,email)
VALUES
    ('user1', true, '$2a$12$0f.x7aBM2wFBZBXoLPj0BObVsk.J1kXFYo5nb4niAWkI4hk5tHvDy', 'First user', NOW(), 'M','viki', 'viki@gmail.com'),
    ('user2', true, '$2a$12$VkR0a47LDVM6aUqFcEJGSu9jhZCz.05tCoyiRicFObt4f2x2gijKa', 'Second user', NOW(), 'F','stefan', 'stefan@gmail.com'),
    ('user3', true, '$2a$12$eSLdHHJ1KFgv.dOupmloXeItjrt2o1IB6ER6Nq7WYj9Jfr2bEwK2a', 'Third user', NOW(), 'M','darko', 'darko@gmail.com'),
    ('user4', true, '$2a$12$dF5SXcNhMulgU3Qre3nh1e.aatRiJZsnfoBSqReGnXe9rIbHYVWhe', 'Fourth user', NOW(), 'F','andrej', 'andrej@gmail.com'),
    ('user5', true, '$2a$12$zHrloz8WG2zo5S6MTf1C0ez1raMlmDJdB8OOa2I1S2pVy9oI76YTa', 'Fifth user', NOW(), 'M','ramche', 'ramche@gmail.com');


INSERT INTO thread (content, user_id)
VALUES
    ('Main content for topic thread 1', 1), --1
    ('Main content for topic thread 2', 2), --2
    ('Discussion content for topic 1', 1), --3
    ('Discussion content for topic 2', 2), --4
    ('Project-specific thread content', 3), --5
    ('Reply to topic 1', 4), -- 6
    ('Further discussion on topic 2', 5), --7
    ('Main content for topic thread', 1), --8
    ('Project-specific thread content 2', 5); --9

insert into embdedable_thread(id)
values (1),(2),(3),(4),(6),(7),(8);


INSERT INTO project_thread (id, title, repo_url)
VALUES
    (5, 'Project 1 Thread', 'http://github.com/project1'),
    (9, 'Project 2 Thread', 'http://github.com/project1');

INSERT INTO topic_thread (id, title, parent_id)
VALUES
    (1, 'Topic 1' , 5),
    (2, 'Topic 2', NULL),
    (8, 'Topic 7' , NULL);

insert into topic_guidelines(topic_id,description)
values
    (1,'Follow guidelines'),
    ( 2,'Be respectful');

INSERT INTO discussion_thread (id, parent_id)
VALUES
    (3, 1),
    (4, 2),
    (6, 1),
    (7, 4);


INSERT INTO likes (user_id, thread_id)
VALUES
    (1, 3),
    (2, 4),
    (3, 5),
    (4, 6),
    (5, 7);


INSERT INTO blacklisted_user (topic_id, user_id, moderator_id, start_date, end_date, reason)
VALUES
    (1, 2, 1, NOW(), NOW() + INTERVAL '7 days', 'Spamming'),
    (2, 3, 2, NOW(), NOW() + INTERVAL '3 days', 'Offensive language');

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
    (5, 5, 'Developer');

INSERT INTO project_roles_permissions (permission_name, role_name, project_id)
VALUES
    ('Create Thread', 'Admin', 5),
    ('Delete Thread', 'Admin', 5);

insert into submission(created_by,status,description)
values
    (1,'PENDING','Inappropriate content'),
    (3,'DENIED','Spam content');

INSERT INTO report (id,thread_id, for_user_id)
VALUES
    (1, 2, 1),
    (2, 1, 3);

INSERT INTO channel (name, description, project_id, developer_id)
VALUES
    ('Updates', 'Project updates channel', 5, 3);

INSERT INTO messages (sent_at, content, sent_by, project_id, channel_name)
VALUES
    (NOW(), 'Zdravo. Ova e real-time chat za dopisuvanje', 3, 5, 'General'),
    (NOW(), 'Resen ladno a?', 3, 5, 'Updates');










