INSERT INTO users (username, is_activate, password, description, registered_at, sex, name, email)
VALUES
    ('user1', true, '$2a$12$0f.x7aBM2wFBZBXoLPj0BObVsk.J1kXFYo5nb4niAWkI4hk5tHvDy', 'First user', NOW(), 'M','viki', 'viki@gmail.com'),
    ('user2', true, '$2a$12$VkR0a47LDVM6aUqFcEJGSu9jhZCz.05tCoyiRicFObt4f2x2gijKa', 'Second user', NOW(), 'F','stefan', 'stefan@gmail.com'),
    ('user3', true, '$2a$12$eSLdHHJ1KFgv.dOupmloXeItjrt2o1IB6ER6Nq7WYj9Jfr2bEwK2a', 'Third user', NOW(), 'M','darko', 'darko@gmail.com'),
    ('user4', true, '$2a$12$dF5SXcNhMulgU3Qre3nh1e.aatRiJZsnfoBSqReGnXe9rIbHYVWhe', 'Fourth user', NOW(), 'F','andrej', 'andrej@gmail.com'),
    ('user5', true, '$2a$12$zHrloz8WG2zo5S6MTf1C0ez1raMlmDJdB8OOa2I1S2pVy9oI76YTa', 'Fifth user', NOW(), 'M','ramche', 'ramche@gmail.com');


INSERT INTO thread (content, is_created_by)
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

INSERT INTO topic_thread (id, title, referenced_by)
VALUES
    (1, 'Topic 1' , 5),
    (2, 'Topic 2', NULL),
    (8, 'Topic 7' , NULL);

insert into topic_guidelines(topic_id,description)
values
    (1,'Follow guidelines'),
    (2,'Be respectful');

INSERT INTO discussion_thread (id, contained_in)
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

-- blacklisted_user matches DDL, so skipped for now

INSERT INTO permissions (name)
VALUES
    ('READ'),
    ('WRITE'),
    ('CREATE'),
    ('DELETE');

INSERT INTO project_role (name, valid_in, override_type)
VALUES
    ('Admin', 5,'EXCLUDE'),
    ('Developer', 5,'INCLUDE');


INSERT INTO project_role_is_assigned_to_developer (user_id, role_id)
VALUES
    (3, 1),
    (5, 2);

INSERT INTO role_permissions (for_permission, for_role)
VALUES
    ('READ', 1),
    ('WRITE', 1),
    ('CREATE', 1),
    ('DELETE', 1);

insert into submission(submitted_by, status, description)
values
    (1,'PENDING','Inappropriate content'),
    (3,'DENIED','Spam content');

INSERT INTO report (id, for_misconduct_in, about)
VALUES
    (1, 2, 1),
    (2, 1, 3);


INSERT INTO channel (name, description, project_has, constructed_by)
VALUES
    ('Updates', 'Project updates channel', 5, 3);


---------------- NOV TEST DATA

-- Add new users (already correct)
INSERT INTO users (username, is_activate, password, description, registered_at, sex, name, email)
VALUES
    ('user6', true, '$2a$12$jB9g/.KP95fsYYOTy0pwZ.kFrwA/G2cMvPvFLzGtCk8jJ2qO3O.3u', 'Sixth user', NOW(), 'M', 'marko', 'marko@gmail.com'),
    ('user7', true, '$2a$12$KRxRufuMscrlQOLKGw4fBehNLWaP7Zu.M964G2JedKVM4o4wTiJaG', 'Seventh user', NOW(), 'F', 'jana', 'jana@gmail.com'),
    ('user8', true, '$2a$12$SCqlK.Rl72tFT0kIUNP6KuSy6BYzfdb9sKJPSWbIK8/uk7y8U7hgS', 'Eighth user', NOW(), 'M', 'nikola', 'nikola@gmail.com'),
    ('user9', true, '$2a$12$LpDTYNb/i0cohkmszkx93ef9rkgFTNFQz/KqHEYIAE9MPOmlyXJ9m', 'Ninth user', NOW(), 'F', 'elena', 'elena@gmail.com'),
    ('user10', true, '$2a$12$p/kZdDKCUCmXjWTsknss/.UaD4a8vxrTcfvc6mdkpHRRPqRZLLtr6', 'Tenth user', NOW(), 'M', 'petar', 'petar@gmail.com');

-- Add new developers (matches DDL)
INSERT INTO developer (id)
VALUES (6),(7),(8),(9),(10);

-- Add new threads
INSERT INTO thread (content, is_created_by)
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

-- Add embeddable_thread entries
INSERT INTO embeddable_thread (id)
VALUES (10),(11),(12),(13),(15),(16),(17);

-- New project_threads (matches DDL)
INSERT INTO project_thread (id, title, repo_url)
VALUES
    (14, 'Project 3 Thread', 'http://github.com/project3'),
    (18, 'Project 4 Thread', 'http://github.com/project4');

-- New topic_threads
INSERT INTO topic_thread (id, title, referenced_by)
VALUES
    (10, 'Topic 3', 14),
    (11, 'Topic 4', NULL),
    (17, 'Topic 8', NULL);

-- Guidelines
INSERT INTO topic_guidelines (topic_id, description)
VALUES
    (10, 'Stay on topic'),
    (11, 'No personal attacks');

-- New discussion_threads
INSERT INTO discussion_thread (id, contained_in)
VALUES
    (12, 10),
    (13, 11),
    (15, 10),
    (16, 13);

-- Likes (matches DDL)
INSERT INTO likes (user_id, thread_id)
VALUES
    (6, 12),
    (7, 13),
    (8, 14),
    (9, 15),
    (10, 16);

-- blacklisted_user (already matches DDL)

-- New submissions
INSERT INTO submission (submitted_by, status, description)
VALUES
    (6, 'PENDING', 'Request for new feature'),
    (7, 'ACCEPTED', 'Bug report');

-- Reports
INSERT INTO report (id, for_misconduct_in, about)
VALUES
    (3, 10, 7),
    (4, 11, 8);

-- Associate developers with projects
INSERT INTO developer_associated_with_project (in_project, about_dev, started_at)
VALUES
    (14, 6, NOW()),
    (14, 7, NOW()),
    (18, 8, NOW()),
    (18, 9, NOW());

-- Channels
INSERT INTO channel (name, description, project_has, constructed_by)
VALUES
    ('General2', 'General discussion', 14, 6),
    ('Bugs', 'Bug reports and fixes', 14, 7),
    ('General2', 'General discussion', 18, 8),
    ('Ideas', 'Feature ideas', 18, 9);