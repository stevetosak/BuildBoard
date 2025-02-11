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
DROP VIEW IF EXISTS v_project_owner CASCADE;
DROP VIEW IF EXISTS v_moderator CASCADE;
drop function if exists fn_insert_project_manager CASCADE;
drop function if exists fn_insert_topics_creator_as_moderator CASCADE;
drop function if exists fn_validate_topic_title CASCADE;
drop function if exists clean_tables CASCADE;
drop function if exists clean_routines CASCADE;
DROP TRIGGER IF EXISTS validate_same_parent ON discussion_thread CASCADE;
drop table if exists topic_guidelines cascade;
drop table if exists submission cascade;
drop table if exists feedback;


---- DDL
CREATE TABLE users
(
    id            SERIAL PRIMARY KEY,
    username      VARCHAR(32) UNIQUE NOT NULL,
    email         varchar(60)        not null,
    name          varchar(32)        not null,
    is_activate   bool DEFAULT true,
    password      VARCHAR(72),
    description   VARCHAR(200),
    registered_at TIMESTAMP DEFAULT NOW(),
    sex           VARCHAR(1)
);
CREATE TABLE moderator
(
    id INT PRIMARY KEY REFERENCES users (id) on delete cascade
);
CREATE TABLE developer
(
    id INT PRIMARY KEY REFERENCES users (id) on delete cascade
);
CREATE TABLE project_manager
(
    id INT PRIMARY KEY REFERENCES users (id) on delete cascade
);
CREATE TABLE thread
(
    id      SERIAL PRIMARY KEY,
    content TEXT,
    created_at timestamp DEFAULT NOW(),
    user_id INT REFERENCES users (id) NOT NULL --IS_CREATED_BY TOTAL
);
CREATE TABLE project_thread
(
    title    VARCHAR(256) UNIQUE NOT NULL,
    repo_url TEXT,
    id       INT PRIMARY KEY REFERENCES thread (id) on delete cascade --INHERITANCE
);
create table embdedable_thread(
   id int primary key references thread(id) on delete cascade
);

CREATE TABLE topic_thread
(
    title     VARCHAR(256) NOT NULL,
    id        INT PRIMARY KEY REFERENCES thread(id) on delete cascade, --INHERITANCE
    parent_id int REFERENCES project_thread(id) on delete CASCADE  --PARENT
);
create table topic_guidelines
(
    id          serial,
    topic_id    int references topic_thread(id) on delete cascade,
    description text,
    PRIMARY KEY (id, topic_id)
);
CREATE TABLE discussion_thread
(
    id  INT PRIMARY KEY REFERENCES  thread(id) on delete cascade, --INHERITANCE,
    parent_id int REFERENCES embdedable_thread(id)  NOT NULL --on delete CASCADE ne tuku preku trigger PARENT TOTAL BIGINT
);

CREATE TABLE likes
(
    user_id   INT REFERENCES users (id) on delete cascade ,
    thread_id INT REFERENCES thread (id) on delete cascade,
    PRIMARY KEY (user_id, thread_id)
);
CREATE TABLE topic_threads_moderators
(
    thread_id INT REFERENCES topic_thread(id) ON DELETE CASCADE,
    user_id   INT REFERENCES moderator(id) ON DELETE CASCADE,
    started_at TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (thread_id, user_id)
);
CREATE TABLE tag
(
    name VARCHAR(64) PRIMARY KEY,
    creator_id int REFERENCES moderator(id)  on delete CASCADE not null
);
CREATE TABLE tag_threads
(
    thread_id INT REFERENCES thread (id) ON DELETE CASCADE,
    tag_name  VARCHAR(64) REFERENCES tag (name) ON DELETE CASCADE,
    PRIMARY KEY (thread_id, tag_name)
);

CREATE TABLE blacklisted_user
(
    topic_id     INT REFERENCES topic_thread(id) ON DELETE CASCADE, --BLACLISTED_FROM
    user_id      INT REFERENCES users(id) ON DELETE CASCADE, --REFERS_TO
    moderator_id INT REFERENCES moderator(id) ON DELETE CASCADE, --BLACKLISTED_BY
    start_date   TIMESTAMP,
    end_date     TIMESTAMP,
    reason       TEXT,
    PRIMARY KEY (user_id, moderator_id, topic_id, start_date)
);

CREATE TABLE developer_associated_with_project
(
    project_id   INT REFERENCES project_thread(id) on delete cascade,
    developer_id INT REFERENCES developer(id) on delete cascade,
    started_at   TIMESTAMP DEFAULT NOW(),
    ended_at     TIMESTAMP,
    PRIMARY KEY (project_id, developer_id, started_at)
);
---DO TUKA
CREATE TABLE permissions
(
    name VARCHAR(32) PRIMARY KEY
);
CREATE TABLE project_roles
(
    name        VARCHAR(32),
    project_id  INT REFERENCES project_thread(id) ON DELETE CASCADE NOT NULL, --VALID_IN
    description TEXT,
    PRIMARY KEY (name, project_id)
);
CREATE TABLE users_project_roles
(
    user_id    INT REFERENCES developer(id) on delete cascade,
    project_id INT,
    role_name  VARCHAR(32),
    FOREIGN KEY (role_name, project_id)
        REFERENCES project_roles (name, project_id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, project_id, role_name)
);
CREATE TABLE project_roles_permissions
(
    permission_name VARCHAR(32) REFERENCES permissions (name),
    role_name       VARCHAR(32),
    project_id      INT,
    FOREIGN KEY (role_name, project_id)
        REFERENCES project_roles (name, project_id) ON DELETE CASCADE,
    PRIMARY KEY (permission_name, role_name, project_id)
);

create table submission(
    id serial primary key,
    created_at  TIMESTAMP default now() not null,
    description VARCHAR(200) NOT NULL,
    status      varchar(32) default 'PENDING' CHECK(status IN ( 'ACCEPTED', 'DENIED', 'PENDING')),
    created_by int REFERENCES users(id) not null
);

CREATE TABLE project_request
(
    id          int PRIMARY KEY REFERENCES submission(id),
    project_id  INT REFERENCES thread (id) ON DELETE CASCADE NOT NULL --RECIEVES
);

create table feedback (
    description TEXT,
    submission_type varchar(1) CHECK(submission_type IN ('P','R')),
    created_at timestamp default now() not null,
    created_by int references users(id) NOT NULL, --WRITTEN_BY
    submission_id int PRIMARY KEY references submission(id) on delete cascade
);

CREATE TABLE report
(
    id          int PRIMARY KEY REFERENCES submission(id),
    thread_id   INT REFERENCES topic_thread(id) on delete cascade not null, --FOR_MISCONDUCT
    for_user_id INT REFERENCES users (id) on delete cascade not null  --ABOUT
);
CREATE TABLE channel
(
    name         VARCHAR(64),
    description  VARCHAR(200),
    project_id   INT REFERENCES project_thread(id) ON DELETE CASCADE NOT NULL, --HAS
    developer_id INT REFERENCES developer(id) NOT NULL, --CONSTRUCTS
    PRIMARY KEY (name, project_id)
);
CREATE TABLE messages
(
    sent_at      TIMESTAMP,
    content      VARCHAR(200) NOT NULL,
    sent_by      INT REFERENCES developer(id),
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
        WHERE t.parent_id = new.parent_id
           OR (t.parent_id IS NULL AND new.parent_id IS NULL)
        )
    THEN
        RAISE EXCEPTION 'There already exists a topic with title % in parent topic with id %',new.title,new.parent_id;
    END IF;
    RETURN new;
END;
$$;
create or replace function check_if_user_exists_in(table_name text, field_name text, field_value text) returns boolean
    language plpgsql
as
$$
DECLARE
    result BOOL;
BEGIN
    EXECUTE format('SELECT EXISTS (SELECT 1 FROM %I WHERE %I = %L)', table_name, field_name, field_value)
        INTO result;
    RETURN result;
END
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
    IF not check_if_user_exists_in('moderator', 'id', v_user_id::text) THEN
        INSERT INTO moderator values (v_user_id);
    END IF;
    INSERT INTO topic_threads_moderators(thread_id, user_id) VALUES (new.id, v_user_id);
    RETURN NEW;
END
$$;

CREATE OR REPLACE FUNCTION fn_insert_project_manager()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
DECLARE
    usrId      INT;
    new_project_id INT;
BEGIN
    SELECT user_id, id
    into usrId,new_project_id
    FROM v_project_thread p
    WHERE NEW.id = p.id;
    IF not EXISTS(
      select 1
      from developer_associated_with_project dawp
      where dawp.project_id=new_project_id and dawp.developer_id=usrId
    ) THEN
        INSERT INTO developer_associated_with_project(project_id, developer_id, started_at)
            values (new_project_id, usrId, NOW());
    end if;
    IF not check_if_user_exists_in('project_manager', 'id', usrId::text) THEN
        INSERT INTO project_manager VALUES (usrId);
    end if;
    RETURN NEW;
END
$$;
create or replace function fn_remove_unused_tags()
    returns trigger
    language plpgsql
as
$$
BEGIN
    IF not check_if_user_exists_in('tag_threads', 'tag_name', old.tag_name)
    THEN
        delete from tag t where t.name = old.tag_name;
    end if;
    return old;
end;
$$;
create or replace function fn_add_dev_if_not_exist()
returns trigger
language plpgsql
as $$
    BEGIN
        IF NOT check_if_user_exists_in('developer','id',new.developer_id::text) THEN
            INSERT INTO developer values (NEW.developer_id);
        end if;
        RETURN new;
    end;
    $$;

create or replace function fn_insert_general_for_project()
    returns trigger
    language plpgsql
as $$
DECLARE
    developer_id INT;
BEGIN
    select user_id
    into developer_id
    from thread t
    where t.id=NEW.id;

    insert into channel(name,description,project_id,developer_id)
    values ('General','General',NEW.id,developer_id);

    return new;
end;
$$;

CREATE OR REPLACE FUNCTION fn_defined_by_total_custom_roles()
 RETURNS trigger
 LANGUAGE plpgsql
AS $$
BEGIN
IF not exists(
    select 1
    from project_roles_permissions p
    where p.role_name=OLD.role_name and p.project_id = OLD.project_id
) THEN
    DELETE FROM project_roles
    where name=OLD.role_name and project_id=OLD.project_id;
END IF;
RETURN OLD;
END;
$$
;

CREATE OR REPLACE FUNCTION fn_remove_orphan_moderator()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
BEGIN
 IF not exists (
    select 1
    from topic_threads_moderators t
    where t.user_id = OLD.user_id
 )
THEN
    DELETE FROM moderator where id=OLD.user_id;
 END IF;
 IF not exists ( 
    select 1 
    from topic_threads_moderators t 
    where t.thread_id = OLD.thread_id 
 ) 
THEN
    delete from discussion_thread where parent_id=OLD.thread_id;
    DELETE FROM topic_thread where id = OLD.thread_id;
-- 	delete from thread where id =  OLD.thread_id;
 END IF; 
 RETURN OLD;
END;
$function$
;
create or replace function fn_aa_rm_orphan_dics()
    returns trigger
    language plpgsql
as
$$
BEGIN
--     RAISE NOTICE '%',OLD.id;

    delete from discussion_thread dt
    where dt.parent_id=OLD.id;
    delete from embdedable_thread
    where id = OLD.id;
    delete from thread t
    where t.id=OLD.id;
    RETURN OLD;
END;
$$;



-- create or replace function fn_remove_thread()
-- returns trigger
-- language plpgsql
-- as
-- $$
-- BEGIN
--     delete from thread where id = OLD.id;
--     return OLD;
-- END;
-- $$;


-------------------------- TRIGGERS ----------------------

CREATE OR REPLACE TRIGGER tr_check_topic_name --RADI
    BEFORE INSERT OR UPDATE
    ON topic_thread
    FOR EACH ROW
EXECUTE FUNCTION fn_validate_topic_title();
CREATE OR REPLACE TRIGGER tr_insert_topics_creator_as_moderator --RADI
    AFTER INSERT
    ON topic_thread
    FOR EACH ROW
EXECUTE FUNCTION fn_insert_topics_creator_as_moderator();
----
create or replace TRIGGER tr_defined_by_total_custom_roles
after delete
on project_roles_permissions
for each row
execute function fn_defined_by_total_custom_roles();

CREATE OR REPLACE TRIGGER tr_remove_orphan_moderator --RADI
AFTER DELETE
ON topic_threads_moderators
FOR EACH ROW
EXECUTE FUNCTION fn_remove_orphan_moderator();


CREATE OR REPLACE TRIGGER tr_a_insert_project_manager --RADI
    AFTER INSERT
    ON project_thread
    FOR EACH ROW
EXECUTE FUNCTION fn_insert_project_manager();

create or replace trigger tr_remove_unused_tags --RADI
    after delete
    on tag_threads
    for each row
execute function fn_remove_unused_tags();

create or replace trigger tr_add_dev_if_not_exist --RADI
before insert on developer_associated_with_project
    for each row
    execute function fn_add_dev_if_not_exist();

create or replace trigger tr_insert_general_for_project --RADI
    after insert on project_thread
    for each row
execute function fn_insert_general_for_project();

create or replace trigger tr_rm_orphan_disc
    after delete
    on discussion_thread
    for each row
execute function fn_aa_rm_orphan_dics();

create or replace trigger tr_rm_orphan_disc
    after delete
    on topic_thread
    for each row
execute function fn_aa_rm_orphan_dics();

-- create or replace trigger tr_remove_thread_from_project
-- after delete
-- on project_thread
-- for each row
-- execute function fn_remove_thread();
--
-- create or replace trigger tr_remove_thread_from_topic
-- after delete
-- on topic_thread
-- for each row
-- execute function fn_remove_thread();
--
-- create or replace trigger tr_remove_thread_from_discussion
-- after delete
-- on discussion_thread
-- for each row
-- execute function fn_remove_thread();