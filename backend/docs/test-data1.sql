-- Insert more threads for new topics
INSERT INTO thread (content, user_id)
VALUES
    ('Main content for topic thread 3', 1),
    ('Main content for topic thread 4', 2),
    ('Discussion content for topic 3', 1),
    ('Discussion content for topic 4', 2),
    ('Project-specific thread 2', 3),
    ('Reply to topic 3', 4),
    ('Further discussion on topic 4', 5);

-- Insert more topics
INSERT INTO topic_thread (id, title, parent_id)
VALUES
    (3, 'Topic 3' , NULL),
    (4, 'Topic 4', NULL);

-- Insert guidelines for new topics
INSERT INTO topic_guidelines(topic_id, description)
VALUES
    (3, 'Share your thoughts respectfully'),
    (4, 'Avoid trolling');

-- Insert discussion threads for new topics
INSERT INTO discussion_thread (id, parent_id)
VALUES
    (5, 3),
    (6, 4),
    (8, 5),
    (9, 6);

-- Insert project-specific thread for a new project
INSERT INTO project_thread (id, title, repo_url)
VALUES
    (6, 'Project 2 Thread', 'http://github.com/project2');

-- Insert likes for new threads
INSERT INTO likes (user_id, thread_id)
VALUES
    (1, 5),
    (2, 6),
    (3, 7),
    (4, 8),
    (5, 9);

-- Associate new topics with the project
INSERT INTO topic_belongs_to_project (topic_id, project_id)
VALUES
    (3, 6),
    (4, 6);

-- Insert more blacklisted users
INSERT INTO blacklisted_user (topic_id, user_id, moderator_id, start_date, end_date, reason)
VALUES
    (3, 2, 1, NOW(), NOW() + INTERVAL '5 days', 'Disrespectful behavior'),
    (4, 3, 4, NOW(), NOW() + INTERVAL '2 days', 'Trolling');

-- Add developers to the new project
INSERT INTO developer_associated_with_project (project_id, developer_id, started_at)
VALUES
    (6, 2, NOW()),
    (6, 4, NOW());

-- Insert new permissions for project 2
INSERT INTO permissions (name)
VALUES
    ('View Thread'),
    ('Comment on Thread');

-- Insert new roles for project 2
INSERT INTO project_roles (name, project_id, description)
VALUES
    ('Admin', 6, 'Admin role for Project 2'),
    ('Developer', 6, 'Developer role for Project 2');

-- Assign users to project 2 roles
INSERT INTO users_project_roles (user_id, project_id, role_name)
VALUES
    (2, 6, 'Admin'),
    (3, 6, 'Developer'),
    (4, 6, 'Developer');

-- Insert new role permissions for Project 2
INSERT INTO project_roles_permissions (permission_name, role_name, project_id)
VALUES
    ('View Thread', 'Admin', 6),
    ('Comment on Thread', 'Developer', 6);

-- Insert more project requests
INSERT INTO project_request (description, status, user_id, project_id)
VALUES
    ('Request to join Project 2', 'PENDING', 3, 6),
    ('Request to join Project 2', 'ACCEPTED', 4, 6);

-- Insert more reports
INSERT INTO report (created_at, description, status, thread_id, for_user_id, by_user_id)
VALUES
    (NOW(), 'Inappropriate content on topic 3', 'PENDING', 5, 2, 1),
    (NOW(), 'Trolling in topic 4', 'DENIED', 6, 4, 3);

-- Insert more channels for the new project
INSERT INTO channel (name, description, project_id, developer_id)
VALUES
    ('General Discussions', 'General discussions for Project 2', 6, 2),
    ('Project Updates', 'Project updates for Project 2', 6, 3);

-- Insert more messages in new channels
INSERT INTO messages (sent_at, content, sent_by, project_id, channel_name)
VALUES
    (NOW(), 'Let\'s work together to complete this task!', 2, 6, 'General Discussions'),
    (NOW(), 'We need to update the task list.', 3, 6, 'Project Updates');
