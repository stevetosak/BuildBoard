--- Trigger before update/insert za check na iminjata topic/discussion -> OK
--- Trigger za ko ke adnit dete na topic thread sho e vo proekt, da go dodajt kako belongs_to vo proektot
--- Trigger za check dali reply na discussion thread pripagjat na ist topic thread kako na toj so mu pret reply
--- IMENUVANJE: triggeri so provervat nesto prefix = check, funkcii za istite prefix = validate
--- Nemame contraint sho velit deka sekoj topic thread trebat da e moderiran
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS moderator CASCADE;
DROP TABLE IF EXISTS developer CASCADE;
DROP TABLE IF EXISTS project_manager CASCADE;
DROP TABLE IF EXISTS thread CASCADE;
DROP TABLE IF EXISTS likes CASCADE;
DROP TABLE IF EXISTS topic_threads_moderators CASCADE;
DROP TABLE IF EXISTS tag CASCADE;
DROP TABLE IF EXISTS tag_threads CASCADE;
DROP TABLE IF EXISTS topic_thread CASCADE;
DROP TABLE IF EXISTS topic_belongs_to_project CASCADE;
DROP TABLE IF EXISTS blacklisted_user CASCADE;
DROP TABLE IF EXISTS project_thread CASCADE;
DROP TABLE IF EXISTS discussion_thread CASCADE;
DROP TABLE IF EXISTS developer_associated_with_project CASCADE;
DROP TABLE IF EXISTS permissions CASCADE;
DROP TABLE IF EXISTS project_roles CASCADE;
DROP TABLE IF EXISTS users_project_roles CASCADE;
DROP TABLE IF EXISTS project_roles_permissions CASCADE;
DROP TABLE IF EXISTS project_request CASCADE;
DROP TABLE IF EXISTS report CASCADE;
DROP TABLE IF EXISTS channel CASCADE;
DROP TABLE IF EXISTS messages CASCADE;
DROP TABLE IF EXISTS threads_moderators CASCADE;
DROP TYPE IF EXISTS status;
DROP VIEW IF EXISTS v_topic_thread CASCADE;
DROP VIEW IF EXISTS v_project_thread CASCADE;
DROP VIEW IF EXISTS v_discussion_thread CASCADE;
DROP VIEW IF EXISTS v_developer CASCADE;
DROP VIEW IF EXISTS v_project_owner CASCADE;
DROP VIEW IF EXISTS v_moderator CASCADE;
drop function if exists fn_insert_project_manager CASCADE;
drop function if exists fn_insert_topics_creator_as_moderator CASCADE;
drop function if exists fn_validate_topic_title CASCADE;
drop function if exists clean_tables CASCADE;
drop function if exists clean_routines CASCADE;
DROP TRIGGER IF EXISTS validate_same_parent ON discussion_thread CASCADE;

---- DDL
CREATE TABLE users
(
    id            SERIAL PRIMARY KEY,
    username      VARCHAR(32) UNIQUE NOT NULL,
    is_activate   bool,
    password      VARCHAR(72),
    description   VARCHAR(200),
    registered_at TIMESTAMP,
    sex           VARCHAR(1)
);
CREATE TABLE moderator
(
    id INT PRIMARY KEY REFERENCES users (id)  on delete cascade
);
CREATE TABLE developer
(
    id INT PRIMARY KEY REFERENCES users (id)  on delete cascade
);
CREATE TABLE project_manager
(
    id INT PRIMARY KEY REFERENCES users (id)  on delete cascade
);
CREATE TABLE thread
(
    id      SERIAL PRIMARY KEY,
    content TEXT,
    user_id INT REFERENCES users (id) NOT NULL
);
CREATE TABLE topic_thread
(
    title           VARCHAR(256) NOT NULL,
    guidelines      jsonb,
    parent_id INT REFERENCES thread (id),
    id              INT PRIMARY KEY REFERENCES thread (id) on delete cascade
);
CREATE TABLE discussion_thread
(
    parent_id           INT REFERENCES thread (id) on delete cascade NOT NULL,
    id                  INT PRIMARY KEY REFERENCES thread (id) on delete cascade
);
CREATE TABLE project_thread
(
    title    VARCHAR(256) NOT NULL,
    repo_url TEXT,
    id       INT PRIMARY KEY REFERENCES thread (id) on delete cascade
);
CREATE TABLE likes
(
    user_id   INT REFERENCES users (id),
    thread_id INT REFERENCES thread (id),
    PRIMARY KEY (user_id, thread_id)
);
CREATE TABLE topic_threads_moderators
(
    thread_id INT REFERENCES thread (id) ON DELETE CASCADE,
    user_id   INT REFERENCES users (id) ON DELETE CASCADE,
    PRIMARY KEY (thread_id, user_id)
);
CREATE TABLE tag
(
    name VARCHAR(64) PRIMARY KEY
);
CREATE TABLE tag_threads
(
    thread_id INT REFERENCES thread (id) ON DELETE CASCADE ,
    tag_name  VARCHAR(64) REFERENCES tag (name) ON DELETE CASCADE,
    PRIMARY KEY (thread_id, tag_name)
);

CREATE TABLE topic_belongs_to_project
(
    topic_id   INT REFERENCES thread (id) ON DELETE CASCADE,
    project_id INT REFERENCES thread (id) ON DELETE CASCADE,
    PRIMARY KEY (topic_id, project_id)
);
CREATE TABLE blacklisted_user
(
    topic_id     INT REFERENCES thread (id) ON DELETE CASCADE,
    user_id      INT REFERENCES users (id) ON DELETE CASCADE,
    moderator_id INT REFERENCES users (id) ON DELETE CASCADE,
    start_date   TIMESTAMP,
    end_date     TIMESTAMP,
    reason       TEXT,
    PRIMARY KEY (user_id, moderator_id, topic_id, start_date)
);

CREATE TABLE developer_associated_with_project
(
    project_id   INT REFERENCES thread (id),
    developer_id INT REFERENCES users (id),
    started_at   TIMESTAMP,
    ended_at     TIMESTAMP,
    PRIMARY KEY (project_id, developer_id, started_at)
);
CREATE TABLE permissions
(
    name VARCHAR(32) PRIMARY KEY
);
CREATE TABLE project_roles
(
    name        VARCHAR(32),
    project_id  INT REFERENCES thread (id) ON DELETE CASCADE,
    description TEXT,
    PRIMARY KEY (name, project_id)
);
CREATE TABLE users_project_roles
(
    user_id    INT REFERENCES users (id),
    project_id INT,
    role_name  VARCHAR(32),
    FOREIGN KEY (role_name, project_id)
        REFERENCES project_roles (name, project_id),
    PRIMARY KEY (user_id, project_id, role_name)
);
CREATE TABLE project_roles_permissions
(
    permission_name VARCHAR(32) REFERENCES permissions (name),
    role_name       VARCHAR(32),
    project_id      INT,
    PRIMARY KEY (permission_name, role_name, project_id),
    FOREIGN KEY (role_name, project_id)
        REFERENCES project_roles (name, project_id)
);
CREATE TYPE status AS ENUM ('ACCEPTED', 'DENIED', 'PENDING');
CREATE TABLE project_request
(
    id          SERIAL PRIMARY KEY,
    description VARCHAR(200),
    status      status                     NOT NULL,
    user_id     INT REFERENCES users (id)  NOT NULL,
    project_id  INT REFERENCES thread (id) NOT NULL
);
CREATE TABLE report
(
    id          SERIAL,
    created_at  TIMESTAMP,
    description VARCHAR(200) NOT NULL,
    status      status,
    thread_id   INT REFERENCES thread (id),
    for_user_id INT REFERENCES users (id),
    by_user_id  INT REFERENCES users (id),
    PRIMARY KEY (id, thread_id, for_user_id, by_user_id)
);
CREATE TABLE channel
(
    name         VARCHAR(64),
    description  VARCHAR(200),
    project_id   INT REFERENCES thread (id) ON DELETE CASCADE,
    developer_id INT REFERENCES users (id),
    PRIMARY KEY (name, project_id)
);
CREATE TABLE messages
(
    sent_at      TIMESTAMP,
    content      VARCHAR(200) NOT NULL,
    sent_by      INT REFERENCES users (id),
    project_id   INT,
    channel_name VARCHAR(64),
    FOREIGN KEY (channel_name, project_id)
        REFERENCES channel (name, project_id) ON DELETE CASCADE,
    PRIMARY KEY (channel_name, project_id, sent_at, sent_by)
);
------------------------VIEWS-----------------------------
CREATE OR REPLACE VIEW v_project_thread
AS
SELECT thread.id, content, user_id, title, repo_url
FROM project_thread project
         JOIN thread
              ON project.id = thread.id;
CREATE OR REPLACE VIEW v_discussion_thread
AS
SELECT thread.id, content, user_id,parent_id
FROM discussion_thread discussion
         JOIN thread
              ON discussion.id = thread.id;
CREATE OR REPLACE VIEW v_topic_thread
AS
SELECT thread.id, content, user_id, title, guidelines, parent_id
FROM topic_thread topic
         JOIN thread
              ON topic.id = thread.id;
CREATE OR REPLACE VIEW v_moderator
AS
SELECT users.id, username, is_activate, password, description, registered_at, sex
FROM moderator
         JOIN users ON moderator.id = users.id;

CREATE OR REPLACE VIEW v_developer
AS
SELECT users.id, username, is_activate, password, description, registered_at, sex
FROM developer
         JOIN users ON developer.id = users.id;
CREATE OR REPLACE VIEW v_project_owner
AS
SELECT users.id, username, is_activate, password, description, registered_at, sex
FROM project_manager
         JOIN users ON project_manager.id = users.id;
CREATE OR REPLACE VIEW v_moderator
AS
SELECT users.id, username, is_activate, password, description, registered_at, sex
FROM moderator
         JOIN users ON moderator.id = users.id;

CREATE OR REPLACE VIEW v_developer
AS
SELECT users.id, username, is_activate, password, description, registered_at, sex
FROM developer
         JOIN users ON developer.id = users.id;
CREATE OR REPLACE VIEW v_project_owner
AS
SELECT users.id, username, is_activate, password, description, registered_at, sex
FROM project_manager
         JOIN users ON project_manager.id = users.id;
-------------------------- FUNCTIONS ----------------------
CREATE OR REPLACE FUNCTION fn_validate_topic_title()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF new.title IN
       (SELECT title
        FROM topic_thread
                 AS t
        WHERE t.parent_id = new.parent_id OR (t.parent_id IS NULL AND new.parent_id IS NULL))
    THEN
        RAISE EXCEPTION 'There already exists a topic with title % in parent topic with id %',new.title,new.parent_id;
END IF;
RETURN new;
END;
$$;
CREATE OR REPLACE FUNCTION fn_insert_topics_creator_as_moderator()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
DECLARE
v_user_id INT;
BEGIN
SELECT v_topic_thread.user_id
INTO v_user_id
FROM v_topic_thread
WHERE v_topic_thread.id = new.id;
INSERT INTO topic_threads_moderators(thread_id, user_id) VALUES (new.id, v_user_id);
RETURN NEW;
END;
$$;

CREATE OR REPLACE FUNCTION fn_insert_project_manager()
RETURNS TRIGGER
LANGUAGE plpgsql
AS
    $$
    DECLARE usrId INT;
BEGIN
SELECT user_id INTO usrId FROM v_project_thread p WHERE NEW.id = p.id;
INSERT INTO developer VALUES (usrId);
INSERT INTO project_manager VALUES (usrId);
RETURN NEW;
END;
    $$;
-------------------------- TRIGGERS ----------------------
CREATE OR REPLACE TRIGGER tr_check_topic_name
    BEFORE INSERT OR UPDATE
                                ON topic_thread
                                FOR EACH ROW
                                EXECUTE FUNCTION fn_validate_topic_title();
CREATE OR REPLACE TRIGGER tr_insert_topics_creator_as_moderator
    AFTER INSERT
    ON topic_thread
    FOR EACH ROW
EXECUTE FUNCTION fn_insert_topics_creator_as_moderator();
CREATE OR REPLACE TRIGGER tr_insert_project_manager
    AFTER INSERT
    ON project_thread
    FOR EACH ROW
    EXECUTE FUNCTION fn_insert_project_manager();
