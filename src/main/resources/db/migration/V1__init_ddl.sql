DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS moderator CASCADE;
DROP TABLE IF EXISTS developer CASCADE;
DROP TABLE IF EXISTS project_manager CASCADE;
DROP TABLE IF EXISTS thread CASCADE;
DROP TABLE IF EXISTS likes CASCADE;
DROP TABLE IF EXISTS topic_thread_is_moderated_by_moderator CASCADE;
DROP TABLE IF EXISTS tag CASCADE;
DROP TABLE IF EXISTS tag_assigned_to_thread CASCADE;
DROP TABLE IF EXISTS topic_thread CASCADE;
DROP TABLE IF EXISTS topic_belongs_to_project CASCADE;
DROP TABLE IF EXISTS blacklisted_user CASCADE;
DROP TABLE IF EXISTS project_thread CASCADE;
DROP TABLE IF EXISTS discussion_thread CASCADE;
DROP TABLE IF EXISTS developer_associated_with_project CASCADE;
DROP TABLE IF EXISTS permissions CASCADE;
DROP TABLE IF EXISTS project_role CASCADE;
DROP TABLE IF EXISTS project_role_is_assigned_to_developer CASCADE;
DROP TABLE IF EXISTS role_permissions CASCADE;
DROP TABLE IF EXISTS role_permissions_overrides CASCADE;
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
    is_created_by    INT REFERENCES users (id) NOT NULL --IS_CREATED_BY TOTAL
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
    referenced_by int REFERENCES project_thread (id) on delete CASCADE,                 --PARENT
    UNIQUE (referenced_by, title)
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
    contained_in int REFERENCES embeddable_thread (id) NOT NULL                       --on delete CASCADE ne tuku preku trigger PARENT TOTAL BIGINT
);

CREATE TABLE likes
(
    user_id   INT REFERENCES users (id) on delete cascade,
    thread_id INT REFERENCES thread (id) on delete cascade,
    PRIMARY KEY (user_id, thread_id)
);
CREATE TABLE topic_thread_is_moderated_by_moderator
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
CREATE TABLE tag_assigned_to_thread
(
    thread_id INT REFERENCES thread (id) ON DELETE CASCADE,
    tag_name  VARCHAR(64) REFERENCES tag (name) ON DELETE CASCADE,
    PRIMARY KEY (thread_id, tag_name)
);

CREATE TABLE blacklisted_user
(
    id serial primary key,
    blacklisted_from     INT REFERENCES topic_thread (id) ON DELETE CASCADE,
    refers_to      INT REFERENCES users (id) ON DELETE CASCADE,
    blacklisted_by INT REFERENCES moderator (id) ON DELETE CASCADE,
    start_date   TIMESTAMP,
    end_date     TIMESTAMP,
    reason       TEXT,
    UNIQUE (refers_to, blacklisted_by, blacklisted_from, start_date)
);
CREATE TABLE developer_associated_with_project
(
    in_project   INT REFERENCES project_thread (id) on delete cascade,
    about_dev INT REFERENCES developer (id) on delete cascade,
    started_at   TIMESTAMP DEFAULT NOW() NOT NULL,
    ended_at     TIMESTAMP,
    PRIMARY KEY (in_project, about_dev, started_at)
);
CREATE TABLE channel
(
    id uuid primary key default uuid_generate_v4(),
    name                VARCHAR(64) NOT NULL,
    description         VARCHAR(200),
    project_has          INT REFERENCES project_thread (id) ON DELETE CASCADE NOT NULL, --HAS
    constructed_by        INT REFERENCES developer (id)                        NOT NULL, --CONSTRUCTS
    UNIQUE (name,project_has)
);
CREATE TABLE permissions
(
    name VARCHAR(32) PRIMARY KEY
);


create table project_role
(
    id serial PRIMARY KEY ,
    name       varchar(32) NOT NULL,
    valid_in int references project_thread (id) ON DELETE CASCADE NOT NULL,
    override_type varchar(20) check ( override_type in ('INCLUDE','EXCLUDE')) NOT NULL DEFAULT 'EXCLUDE'
);

CREATE TABLE role_permissions
(
    for_permission VARCHAR(32) NOT NULl,
    for_role INT REFERENCES project_role(id) ON DELETE CASCADE NOT NULL,
    FOREIGN KEY (for_permission) REFERENCES permissions(name),
    PRIMARY KEY (for_permission, for_role)
);

CREATE TABLE role_permissions_overrides
(
    for_resource uuid references channel(id) on delete cascade ,
    for_role_permission_permission_name VARCHAR(32) NOT NULL,
    for_role_permission_role_id INT REFERENCES project_role(id) ON DELETE CASCADE NOT NULL,
    FOREIGN KEY (for_role_permission_permission_name, for_role_permission_role_id) REFERENCES role_permissions(for_permission, for_role) ON DELETE CASCADE,
    PRIMARY KEY (for_role_permission_role_id,for_role_permission_permission_name,for_resource)
);

CREATE TABLE project_role_is_assigned_to_developer
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
    submitted_by  int REFERENCES users (id) NOT NULL
);

CREATE TABLE project_request
(
    id         int PRIMARY KEY REFERENCES submission (id),
    project_receives INT REFERENCES project_thread (id) ON DELETE CASCADE NOT NULL --RECIEVES
);

create table feedback
(
    description     TEXT,
    submission_type varchar(1) CHECK (submission_type IN ('P', 'R')) NOT NULL,
    created_at      timestamp default now()   not null,
    written_by      int references users (id) NOT NULL, --WRITTEN_BY
    submitted_for   int PRIMARY KEY references submission (id) on delete cascade
);

CREATE TABLE report
(
    id          int PRIMARY KEY REFERENCES submission (id),
    for_misconduct_in   INT REFERENCES topic_thread (id) on delete cascade NOT NULL, --FOR_MISCONDUCT
    about INT REFERENCES users (id) on delete cascade        NOT NULL  --ABOUT
);

CREATE TABLE messages
(
    sent_at      TIMESTAMP NOT NULL,
    content      VARCHAR(200) NOT NULL,
    sent_by      INT REFERENCES developer (id) on delete no action NOT NULL,
    sent_in   uuid references channel(id) ON DELETE CASCADE NOT NULL,
    PRIMARY KEY (sent_in,sent_by,sent_at)
);
