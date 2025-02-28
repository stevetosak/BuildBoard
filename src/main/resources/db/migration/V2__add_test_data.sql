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

insert into embeddable_thread(id)
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

---------------- NOV TEST DATA

-- Add new users
INSERT INTO users (username, is_activate, password, description, registered_at, sex, name, email)
VALUES
    -- Password: user6pass
    ('user6', true, '$2a$12$jB9g/.KP95fsYYOTy0pwZ.kFrwA/G2cMvPvFLzGtCk8jJ2qO3O.3u', 'Sixth user', NOW(), 'M', 'marko', 'marko@gmail.com'),
    -- Password: user7pass
    ('user7', true, '$2a$12$KRxRufuMscrlQOLKGw4fBehNLWaP7Zu.M964G2JedKVM4o4wTiJaG', 'Seventh user', NOW(), 'F', 'jana', 'jana@gmail.com'),
    -- Password: user8pass
    ('user8', true, '$2a$12$SCqlK.Rl72tFT0kIUNP6KuSy6BYzfdb9sKJPSWbIK8/uk7y8U7hgS', 'Eighth user', NOW(), 'M', 'nikola', 'nikola@gmail.com'),
    -- Password: user9pass
    ('user9', true, '$2a$12$LpDTYNb/i0cohkmszkx93ef9rkgFTNFQz/KqHEYIAE9MPOmlyXJ9m', 'Ninth user', NOW(), 'F', 'elena', 'elena@gmail.com'),
    -- Password: user10pass
    ('user10', true, '$2a$12$p/kZdDKCUCmXjWTsknss/.UaD4a8vxrTcfvc6mdkpHRRPqRZLLtr6', 'Tenth user', NOW(), 'M', 'petar', 'petar@gmail.com');

-- Add new developers
INSERT INTO developer (id)
VALUES
    (6), -- user6
    (7), -- user7
    (8), -- user8
    (9), -- user9
    (10); -- user10

-- Add new threads
INSERT INTO thread (content, user_id)
VALUES
    ('Main content for topic thread 3', 6), --10
    ('Main content for topic thread 4', 7), --11
    ('Discussion content for topic 3', 6), --12
    ('Discussion content for topic 4', 7), --13
    ('Project-specific thread content 3', 8), --14
    ('Reply to topic 3', 9), --15
    ('Further discussion on topic 4', 10), --16
    ('Main content for topic thread 5', 6), --17
    ('Project-specific thread content 4', 10); --18

-- Add embeddable_thread entries for topic and discussion threads
INSERT INTO embeddable_thread (id)
VALUES
    (10), (11), (12), (13), (15), (16), (17);

-- Add new project_threads
INSERT INTO project_thread (id, title, repo_url)
VALUES
    (14, 'Project 3 Thread', 'http://github.com/project3'),
    (18, 'Project 4 Thread', 'http://github.com/project4');

-- Add new topic_threads
INSERT INTO topic_thread (id, title, parent_id)
VALUES
    (10, 'Topic 3', 14),
    (11, 'Topic 4', NULL),
    (17, 'Topic 8', NULL);

-- Add new topic_guidelines
INSERT INTO topic_guidelines (topic_id, description)
VALUES
    (10, 'Stay on topic'),
    (11, 'No personal attacks');

-- Add new discussion_threads
INSERT INTO discussion_thread (id, parent_id)
VALUES
    (12, 10),
    (13, 11),
    (15, 10),
    (16, 13);

-- Add new likes
INSERT INTO likes (user_id, thread_id)
VALUES
    (6, 12),
    (7, 13),
    (8, 14),
    (9, 15),
    (10, 16);

-- Add new blacklisted_user entries
INSERT INTO blacklisted_user (topic_id, user_id, moderator_id, start_date, end_date, reason)
VALUES
    (10, 7, 6, NOW(), NOW() + INTERVAL '5 days', 'Repeated off-topic posts'),
    (11, 8, 7, NOW(), NOW() + INTERVAL '10 days', 'Harassment');

-- Add new submissions
INSERT INTO submission (created_by, status, description)
VALUES
    (6, 'PENDING', 'Request for new feature'),
    (7, 'ACCEPTED', 'Bug report');

-- Add new reports
INSERT INTO report (id, thread_id, for_user_id)
VALUES
    (3, 10, 7),
    (4, 11, 8);

-- Associate developers with projects
INSERT INTO developer_associated_with_project (project_id, developer_id, started_at)
VALUES
    (14, 6, NOW()), -- user6 associated with Project 3
    (14, 7, NOW()), -- user7 associated with Project 3
    (18, 8, NOW()), -- user8 associated with Project 4
    (18, 9, NOW()); -- user9 associated with Project 4

-- Add new channels
INSERT INTO channel (name, description, project_id, developer_id)
VALUES
    ('General2', 'General discussion', 14, 6), -- Created by user6 for Project 3
    ('Bugs', 'Bug reports and fixes', 14, 7), -- Created by user7 for Project 3
    ('General2', 'General discussion', 18, 8), -- Created by user8 for Project 4
    ('Ideas', 'Feature ideas', 18, 9); -- Created by user9 for Project 4

-- Add new messages (only users associated with the project can send messages)
INSERT INTO messages (sent_at, content, sent_by, project_id, channel_name)
VALUES
    (NOW(), 'Welcome to Project 3!', 6, 14, 'General'), -- Sent by user6 in Project 3
    (NOW(), 'Found a bug in the login module.', 7, 14, 'Bugs'), -- Sent by user7 in Project 3
    (NOW(), 'Let’s discuss new features.', 8, 18, 'General'), -- Sent by user8 in Project 4
    (NOW(), 'I have an idea for a new feature.', 9, 18, 'Ideas'); -- Sent by user9 in Project 4








