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
drop table if exists embeddable_thread;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


---- DDL
CREATE TABLE users
(
    id            SERIAL PRIMARY KEY,
    username      VARCHAR(32) UNIQUE      NOT NULL,
    email         varchar(60)             not null,
    name          varchar(32)             not null,
    is_activate   bool      DEFAULT true,
    password      VARCHAR(72) NOT NULL,
    description   VARCHAR(200),
    registered_at TIMESTAMP DEFAULT NOW() NOT NULL,
    sex           VARCHAR(1) NOT NULL
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
    id         SERIAL PRIMARY KEY,
    content    TEXT,
    created_at timestamp DEFAULT NOW()   NOT NULL,
    user_id    INT REFERENCES users (id) NOT NULL --IS_CREATED_BY TOTAL
);
CREATE TABLE project_thread
(
    title    VARCHAR(256) UNIQUE NOT NULL,
    repo_url TEXT,
    id       INT PRIMARY KEY REFERENCES thread (id) on delete cascade --INHERITANCE
);
create table embeddable_thread
(
    id int primary key references thread (id) on delete cascade
);

CREATE TABLE topic_thread
(
    title     VARCHAR(256) NOT NULL,
    id        INT PRIMARY KEY REFERENCES embeddable_thread (id) on delete cascade, --INHERITANCE
    parent_id int REFERENCES project_thread (id) on delete CASCADE                 --PARENT
);
create table topic_guidelines
(
    id          serial,
    topic_id    int references topic_thread (id) on delete cascade,
    description text,
    PRIMARY KEY (id, topic_id)
);
CREATE TABLE discussion_thread
(
    id        INT PRIMARY KEY REFERENCES embeddable_thread (id) on delete cascade, --INHERITANCE,
    parent_id int REFERENCES embeddable_thread (id) NOT NULL                       --on delete CASCADE ne tuku preku trigger PARENT TOTAL BIGINT
);

CREATE TABLE likes
(
    user_id   INT REFERENCES users (id) on delete cascade,
    thread_id INT REFERENCES thread (id) on delete cascade,
    PRIMARY KEY (user_id, thread_id)
);
CREATE TABLE topic_threads_moderators
(
    thread_id  INT REFERENCES topic_thread (id) ON DELETE CASCADE NOT NULL,
    user_id    INT REFERENCES moderator (id) ON DELETE CASCADE NOT NULL,
    started_at TIMESTAMP DEFAULT NOW() NOT NULL,
    PRIMARY KEY (thread_id, user_id)
);
CREATE TABLE tag
(
    name       VARCHAR(64) PRIMARY KEY,
    creator_id int REFERENCES users (id) on delete CASCADE not null
);
CREATE TABLE tag_threads
(
    thread_id INT REFERENCES thread (id) ON DELETE CASCADE,
    tag_name  VARCHAR(64) REFERENCES tag (name) ON DELETE CASCADE,
    PRIMARY KEY (thread_id, tag_name)
);

CREATE TABLE blacklisted_user
(
    topic_id     INT REFERENCES topic_thread (id) ON DELETE CASCADE NOT NULL, --BLACLISTED_FROM
    user_id      INT REFERENCES users (id) ON DELETE CASCADE NOT NULL,        --REFERS_TO
    moderator_id INT REFERENCES moderator (id) ON DELETE CASCADE NOT NULL,    --BLACKLISTED_BY
    start_date   TIMESTAMP NOT NULL,
    end_date     TIMESTAMP,
    reason       TEXT,
    PRIMARY KEY (user_id, moderator_id, topic_id, start_date)
);
CREATE TABLE developer_associated_with_project
(
    project_id   INT REFERENCES project_thread (id) on delete cascade,
    developer_id INT REFERENCES developer (id) on delete cascade,
    started_at   TIMESTAMP DEFAULT NOW() NOT NULL,
    ended_at     TIMESTAMP,
    PRIMARY KEY (project_id, developer_id, started_at)
);
CREATE TABLE channel
(
    id uuid primary key default uuid_generate_v4(),
    name                VARCHAR(64) NOT NULL,
    description         VARCHAR(200),
    project_id          INT REFERENCES project_thread (id) ON DELETE CASCADE NOT NULL, --HAS
    developer_id        INT REFERENCES developer (id)                        NOT NULL, --CONSTRUCTS
    UNIQUE (name,project_id)
);
CREATE TABLE permissions
(
    name VARCHAR(32) PRIMARY KEY
);


create table project_role
(
    id serial PRIMARY KEY ,
    name       varchar(32) NOT NULL,
    project_id int references project_thread (id) ON DELETE CASCADE NOT NULL,
    override_type varchar(20) check ( override_type in ('INCLUDE','EXCLUDE')) NOT NULL DEFAULT 'EXCLUDE'
);

CREATE TABLE role_permissions
(
    permission_name VARCHAR(32) NOT NULl,
    role_id INT REFERENCES project_role(id) ON DELETE CASCADE NOT NULL,
    FOREIGN KEY (permission_name) REFERENCES permissions(name),
    PRIMARY KEY (permission_name, role_id)
);

CREATE TABLE role_permissions_overrides
(
    channel_id uuid references channel(id) on delete cascade ,
    permission_name VARCHAR(32) NOT NULL,
    role_id INT REFERENCES project_role(id) ON DELETE CASCADE NOT NULL,
    FOREIGN KEY (role_id,permission_name) REFERENCES role_permissions (role_id,permission_name) ON DELETE CASCADE,
    PRIMARY KEY (role_id,permission_name,channel_id)
);

CREATE TABLE users_project_roles
(
    user_id    INT REFERENCES developer (id) on delete cascade NOT NULL,
    role_id    INT REFERENCES project_role(id) on delete cascade NOT NULL,
    PRIMARY KEY (user_id, role_id)
);


create table submission
(
    id          serial primary key,
    created_at  TIMESTAMP   default now() NOT NULL                                                    ,
    description VARCHAR(200) NOT NULL,
    status      varchar(32) default 'PENDING' CHECK (status IN ('ACCEPTED', 'DENIED', 'PENDING')) NOT NULL,
    created_by  int REFERENCES users (id) NOT NULL
);

CREATE TABLE project_request
(
    id         int PRIMARY KEY REFERENCES submission (id),
    project_id INT REFERENCES thread (id) ON DELETE CASCADE NOT NULL --RECIEVES
);

create table feedback
(
    description     TEXT,
    submission_type varchar(1) CHECK (submission_type IN ('P', 'R')) NOT NULL,
    created_at      timestamp default now()   not null,
    created_by      int references users (id) NOT NULL, --WRITTEN_BY
    submission_id   int PRIMARY KEY references submission (id) on delete cascade
);

CREATE TABLE report
(
    id          int PRIMARY KEY REFERENCES submission (id),
    thread_id   INT REFERENCES topic_thread (id) on delete cascade NOT NULL, --FOR_MISCONDUCT
    for_user_id INT REFERENCES users (id) on delete cascade        NOT NULL  --ABOUT
);

CREATE TABLE messages
(
    sent_at      TIMESTAMP NOT NULL,
    content      VARCHAR(200) NOT NULL,
    sent_by      INT REFERENCES developer (id) NOT NULL,
    project_id   INT NOT NULL,
    channel_name VARCHAR(64) NOT NULL,
    FOREIGN KEY (channel_name, project_id)
        REFERENCES channel (name, project_id) ON DELETE CASCADE,
    PRIMARY KEY (channel_name, project_id, sent_at, sent_by)
);


