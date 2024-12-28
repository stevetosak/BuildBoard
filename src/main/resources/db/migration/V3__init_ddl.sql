--- Trigger before update/insert za check na iminjata topic/discussion -> OK
--- Trigger za ko ke adnit dete na topic thread sho e vo proekt, da go dodajt kako belongs_to vo proektot
--- Trigger za check dali reply na discussion thread pripagjat na ist topic thread kako na toj so mu pret reply
--- IMENUVANJE: triggeri so provervat nesto prefix = check, funkcii za istite prefix = validate
--- Nemame contraint sho velit deka sekoj topic thread trebat da e moderiran 

---- DROP TABLES
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
DROP TABLE IF EXISTS topic_blacklist CASCADE;
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
drop table if exists blacklisted_user CASCADE; 
drop type if exists status;
drop function if exists add_child_topic;
drop function if exists validate_same_parent;
drop function if exists validate_topic_title;
---- DDL
create table users(
                      id serial PRIMARY key,
                      username varchar(32) UNIQUE NOT NULL,
                      is_activate bool not null,
                      password varchar(72),
                      description varchar(200),
                      registered_at timestamp not null, 
                      sex varchar(1)
);
create table moderator() inherits (users);
create table developer() inherits (users);
create table project_manager() inherits (users);
create table thread (
                        id serial primary key,
                        content text not null,
                        user_id int references users(id) not null 
);
create table likes(
                      user_id int references users(id),
                      thread_id int references thread(id),
                      primary key(user_id, thread_id)
);
create table topic_threads_moderators(
                                         thread_id int references thread(id) on delete cascade,
                                         user_id int references users(id) on delete cascade,
                                         primary key(thread_id, user_id)
);
create table tag(
                    name varchar(64) primary key
);
create table tag_threads(
                            thread_id int references thread(id),
                            tag_name varchar(64) references tag(name),
                            primary key(thread_id, tag_name)
);
create table topic_thread (
                              title varchar(32) not null,
                              guidelines jsonb not null,
                              next_discussion_id int,
                              parent_topic_id int REFERENCES thread(id)
) inherits (thread);
create table topic_belongs_to_project(
                                         topic_id int references thread(id) on delete cascade,
                                         project_id int references thread(id) on delete cascade,
                                         primary key(topic_id,project_id)
);
create table blacklisted_user(
                                topic_id int REFERENCES thread(id) ON DELETE CASCADE,
                                user_id int references users(id) on delete cascade,
                                moderator_id int references users(id) on delete cascade,
                                start_date timestamp,
                                end_date timestamp,
                                primary key(user_id,moderator_id,topic_id,start_date)
);
create table project_thread (
                                title varchar(32) not null,
                                repo_url text
) inherits (thread);
create table discussion_thread(
                                  user_id int references users(id),
                                  reply_discussion_id int REFERENCES thread(id),
                                  topic_id int REFERENCES thread(id) not null 
) inherits(thread);
create table developer_associated_with_project(
                                                  project_id int references thread(id),
                                                  developer_id int references users(id),
                                                  started_at timestamp,
                                                  ended_at timestamp,
                                                  primary key(project_id, developer_id, started_at)
);
create table permissions(
                            name varchar(32) primary key
);
create table project_roles(
                              name varchar(32),
                              project_id int references thread(id) on delete cascade,
                              description text not null,
                              primary key(name, project_id)
);
create table users_project_roles(
                                    user_id int references users(id),
                                    project_id int ,
                                    role_name varchar(32) ,
                                    FOREIGN KEY (role_name, project_id)
                                        REFERENCES project_roles(name, project_id),
                                    primary key(user_id, project_id, role_name)
);
create table project_roles_permissions(
                                          permission_name varchar(32) references permissions(name),
                                          role_name varchar(32) ,
                                          project_id int,
                                          primary key(permission_name, role_name, project_id),
                                          FOREIGN KEY (role_name, project_id)
                                              REFERENCES project_roles(name, project_id) 
);
CREATE TYPE status AS ENUM ('ACCEPTED', 'DENIED', 'PENDING');
create table project_request(
                                id serial primary key,
                                description varchar (200) not null,
                                status status not null,
                                user_id int references users(id) not null,
                                project_id int references thread(id) not null
);
create table report(
                       id serial, 
                       created_at timestamp not null,
                       description varchar(200) not null,
                       status status not null,
                       thread_id int references thread(id),
                       for_user_id int references users(id),
                       by_user_id int references users(id), 
                       primary key(id,thread_id,for_user_id,by_user_id)
);
create table channel (
                         name varchar (64),
                         description varchar(200),
                         project_id int references thread(id) on delete cascade,
                         developer_id int references users(id),
                         primary key(name, project_id)
);
create table messages (
                          sent_at timestamp,
                          content varchar(200) not null,
                          sent_by int references users(id),
                          project_id int,
                          channel_name varchar(64),
                          FOREIGN KEY (channel_name, project_id)
                              REFERENCES channel(name, project_id) on delete cascade,
                          primary key(channel_name, project_id, sent_at, sent_by)
);
-------------------------- FUNCTIONS ----------------------
CREATE FUNCTION validate_topic_title()
    RETURNS trigger
    LANGUAGE plpgsql
as $$
BEGIN
		IF NEW.title IN
			(
			SELECT title
			FROM topic_thread
			AS t
			WHERE t.parent_topic_id = NEW.parent_topic_id)
			THEN RAISE EXCEPTION 'There already exists a topic with title % in parent topic with id %',NEW.title,NEW.parent_topic_id;
END IF;
RETURN NEW;
END;
$$;
CREATE FUNCTION add_child_topic()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS $$
DECLARE
project_id INT;
BEGIN
SELECT t.project_id
INTO project_id
FROM topic_belongs_to_project AS t WHERE NEW.id = t.topic_id;
INSERT INTO topic_belongs_to_project VALUES (NEW.id,project_id);
END;
$$;
CREATE FUNCTION validate_same_parent()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS $$
BEGIN
		IF NOT EXISTS (
			SELECT 1
			FROM discussion_thread
			AS dt
			WHERE NEW.reply_discussion_id = dt.id AND dt.topic_id = NEW.topic_id
		) THEN
		RAISE EXCEPTION 'Can not reply to a discussion that is not in the same topic';
END IF;
RETURN NEW;
END;
$$;

create function validate_topics_moderation()
returns trigger 
LANGUAGE plpgsql 
as $$
BEGIN
    
END; 
$$;  
-------------------------- TRIGGERS ----------------------

CREATE or replace TRIGGER check_topic_name
	BEFORE INSERT OR UPDATE ON topic_thread
                                FOR EACH ROW
                                EXECUTE FUNCTION validate_topic_title();

CREATE OR REPLACE TRIGGER project_insert_child_topic
	AFTER INSERT ON topic_thread
	FOR EACH ROW
	EXECUTE FUNCTION add_child_topic();

CREATE OR REPLACE TRIGGER check_same_parent
	BEFORE INSERT ON discussion_thread
	FOR EACH ROW
	EXECUTE FUNCTION validate_same_parent();

create or replace trigger check_topics_moderation
    before insert on topic_thread
    for each row 
    EXECUTE function validate_topics_moderation