--- Trigger before update/insert za check na iminjata topic/discussion -> OK
--- Trigger za ko ke adnit dete na topic thread sho e vo proekt, da go dodajt kako belongs_to vo proektot
--- Trigger za check dali reply na discussion thread pripagjat na ist topic thread kako na toj so mu pret reply
--- IMENUVANJE: triggeri so provervat nesto prefix = check, funkcii za istite prefix = validate
--- Nemame contraint sho velit deka sekoj topic thread trebat da e moderiran

CREATE OR REPLACE PROCEDURE clean_tables(schema_name text)
LANGUAGE plpgsql
AS $$
    DECLARE
        row RECORD;
    BEGIN
        FOR row in SELECT t.table_name FROM information_schema.tables t WHERE t.table_schema=schema_name
        LOOP
            EXECUTE 'DROP TABLE IF EXISTS ' || quote_ident(row.table_name) || ' CASCADE';
        END LOOP;
    END;
$$;

CREATE OR REPLACE PROCEDURE clean_routines(schema_name text)
    LANGUAGE plpgsql
AS
$$
DECLARE
    row RECORD;
BEGIN
    FOR row IN SELECT *
               FROM information_schema.routines
               WHERE specific_schema = schema_name
                 AND routine_type <> 'PROCEDURE'
    LOOP
        EXECUTE 'DROP FUNCTION IF EXISTS ' || quote_ident(row.routine_name) || ' CASCADE';
    END LOOP;
END;
$$;

---- DDL
CREATE TABLE users
(
    id SERIAL PRIMARY KEY,
    username VARCHAR(32) UNIQUE NOT NULL,
    is_activate bool,
    password VARCHAR(72),
    description VARCHAR(200),
    registered_at TIMESTAMP,
    sex VARCHAR(1)
);
CREATE TABLE moderator
(
    id INT PRIMARY KEY REFERENCES users(id)
);
CREATE TABLE developer
(
    id INT PRIMARY KEY REFERENCES users(id)
); 
CREATE TABLE project_manager
(
    id INT PRIMARY KEY REFERENCES users(id)
);
CREATE TABLE thread
(
    id SERIAL PRIMARY KEY,
    content TEXT,
    user_id INT REFERENCES users (id) NOT NULL
);
CREATE TABLE topic_thread
(
    title VARCHAR(32) NOT NULL,
    guidelines jsonb,
    parent_topic_id INT REFERENCES thread (id),
    id INT PRIMARY KEY REFERENCES thread(id)
);
CREATE TABLE discussion_thread
(
    reply_discussion_id INT REFERENCES thread (id),
    topic_id INT REFERENCES thread (id) NOT NULL,
    id INT PRIMARY KEY REFERENCES thread(id)
);
CREATE TABLE project_thread
(
    title VARCHAR(32) NOT NULL,
    repo_url TEXT,
    id INT PRIMARY KEY REFERENCES thread(id)
);
CREATE TABLE likes
(
    user_id INT REFERENCES users(id),
    thread_id INT REFERENCES thread(id),
    PRIMARY KEY (user_id, thread_id)
);
CREATE TABLE topic_threads_moderators
(
    thread_id INT REFERENCES thread (id) ON DELETE CASCADE,
    user_id INT REFERENCES users (id) ON DELETE CASCADE,
    PRIMARY KEY (thread_id, user_id)
);
CREATE TABLE tag
(
    name VARCHAR(64) PRIMARY KEY
);
CREATE TABLE tag_threads
(
    thread_id INT REFERENCES thread (id),
    tag_name VARCHAR(64) REFERENCES tag (name),
    PRIMARY KEY (thread_id, tag_name)
);

CREATE TABLE topic_belongs_to_project
(
    topic_id INT REFERENCES thread (id) ON DELETE CASCADE,
    project_id INT REFERENCES thread (id) ON DELETE CASCADE,
    PRIMARY KEY (topic_id, project_id)
);
CREATE TABLE blacklisted_user
(
    topic_id INT REFERENCES thread (id) ON DELETE CASCADE,
    user_id INT REFERENCES users (id) ON DELETE CASCADE,
    moderator_id INT REFERENCES users (id) ON DELETE CASCADE,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    reason TEXT,
    PRIMARY KEY (user_id, moderator_id, topic_id, start_date)
);

CREATE TABLE developer_associated_with_project
(
    project_id INT REFERENCES thread (id),
    developer_id INT REFERENCES users (id),
    started_at TIMESTAMP,
    ended_at TIMESTAMP,
    PRIMARY KEY (project_id, developer_id, started_at)
);
CREATE TABLE permissions
(
    name VARCHAR(32) PRIMARY KEY
);
CREATE TABLE project_roles
(
    name VARCHAR(32),
    project_id INT REFERENCES thread (id) ON DELETE CASCADE,
    description TEXT,
    PRIMARY KEY (name, project_id)
);
CREATE TABLE users_project_roles
(
    user_id INT REFERENCES users (id),
    project_id INT,
    role_name VARCHAR(32),
    FOREIGN KEY (role_name, project_id)
        REFERENCES project_roles (name, project_id),
    PRIMARY KEY (user_id, project_id, role_name)
);
CREATE TABLE project_roles_permissions
(
    permission_name VARCHAR(32) REFERENCES permissions (name),
    role_name VARCHAR(32),
    project_id INT,
    PRIMARY KEY (permission_name, role_name, project_id),
    FOREIGN KEY (role_name, project_id)
        REFERENCES project_roles (name, project_id)
);
CREATE TYPE status AS ENUM ('ACCEPTED', 'DENIED', 'PENDING');
CREATE TABLE project_request
(
    id SERIAL PRIMARY KEY,
    description VARCHAR(200) ,
    status status NOT NULL,
    user_id INT REFERENCES users (id) NOT NULL,
    project_id INT REFERENCES thread (id) NOT NULL
);
CREATE TABLE report
(
    id SERIAL,
    created_at TIMESTAMP,
    description VARCHAR(200) NOT NULL,
    status status,
    thread_id INT REFERENCES thread (id),
    for_user_id INT REFERENCES users (id),
    by_user_id INT REFERENCES users (id),
    PRIMARY KEY (id, thread_id, for_user_id, by_user_id)
);
CREATE TABLE channel
(
    name VARCHAR(64),
    description VARCHAR(200),
    project_id INT REFERENCES thread (id) ON DELETE CASCADE,
    developer_id INT REFERENCES users (id),
    PRIMARY KEY (name, project_id)
);
CREATE TABLE messages
(
    sent_at TIMESTAMP,
    content VARCHAR(200) NOT NULL,
    sent_by INT REFERENCES users (id),
    project_id INT,
    channel_name VARCHAR(64),
    FOREIGN KEY (channel_name, project_id)
        REFERENCES channel (name, project_id) ON DELETE CASCADE,
    PRIMARY KEY (channel_name, project_id, sent_at, sent_by)
);
------------------------VIEWS-----------------------------
CREATE OR REPLACE VIEW v_project_thread
AS
    SELECT thread.id,content,user_id,title,repo_url
     FROM project_thread project
    JOIN thread
    ON project.id = thread.id;
CREATE OR REPLACE VIEW v_discussion_thread
AS
    SELECT thread.id,content,user_id,reply_discussion_id,topic_id
     FROM discussion_thread discussion
    JOIN thread
    ON discussion.id = thread.id;
CREATE OR REPLACE VIEW v_topic_thread
AS
    SELECT thread.id,content,user_id,title,guidelines,parent_topic_id
    FROM topic_thread topic
    JOIN thread
    ON topic.id = thread.id;
-------------------------- FUNCTIONS ----------------------
CREATE FUNCTION fn_validate_topic_title()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF new.title IN
       (SELECT title
        FROM topic_thread
                 AS t
        WHERE t.parent_topic_id = new.parent_topic_id)
    THEN
        RAISE EXCEPTION 'There already exists a topic with title % in parent topic with id %',new.title,new.parent_topic_id;
    END IF;
    RETURN new;
END;
$$;
CREATE FUNCTION fn_project_insert_child_topic()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
DECLARE
    parent_project_id INT;
BEGIN
    IF new.parent_topic_id IS NOT NULL
    THEN
        SELECT project_id INTO parent_project_id FROM topic_belongs_to_project t WHERE new.parent_topic_id = t.topic_id;
        IF parent_project_id IS NOT NULL THEN
            INSERT INTO topic_belongs_to_project VALUES (new.id,parent_project_id);
        END IF;
    END IF;
    RETURN new;
END;
$$;
CREATE FUNCTION fn_validate_same_parent()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF NOT EXISTS (SELECT 1
                   FROM discussion_thread
                            AS dt
                   WHERE new.reply_discussion_id = dt.id
                     AND dt.topic_id = new.topic_id) THEN
        RAISE EXCEPTION 'Can not reply to a discussion that is not in the same topic';
    END IF;
    RETURN new;
END;
$$;

CREATE FUNCTION fn_insert_topics_creator_as_moderator()
RETURNS TRIGGER
LANGUAGE plpgsql
as $$
    BEGIN
        INSERT INTO topic_threads_moderators(thread_id, user_id) VALUES (old.id, old.user_id);
    END;
$$;


-------------------------- TRIGGERS ----------------------

CREATE OR REPLACE TRIGGER TR_check_topic_name
    BEFORE INSERT OR UPDATE
    ON topic_thread
    FOR EACH ROW
EXECUTE FUNCTION fn_Validate_topic_title();

CREATE OR REPLACE TRIGGER TR_project_insert_child_topic
    AFTER INSERT
    ON topic_thread
    FOR EACH ROW
EXECUTE FUNCTION fn_Project_insert_child_topic();

CREATE OR REPLACE TRIGGER TR_check_same_parent
    BEFORE INSERT
    ON discussion_thread
    FOR EACH ROW
EXECUTE FUNCTION fn_Validate_same_parent();

CREATE TRIGGER tr_insert_topics_creator_as_moderator
    AFTER INSERT ON topic_thread
    FOR EACH ROW
    EXECUTE FUNCTION fn_insert_topics_creator_as_moderator();
