create table users(
	username varchar(32) primary key, 
	is_activate bool, 
	password varchar(72), 
	description varchar(200), 
	registered_at timestamp,
	sex varchar(1)
); 

create table moderator() inherits (users);
create table developer() inherits (users); 
create table project_manager() inherits (users); 

create table thread (
	id serial primary key,
	description text,
	logo_url text,
	title varchar(32),
	username varchar(32) references users(username)
);

create table likes(
	username varchar(32) references users(username), 
	thread_id int references thread(id),
	primary key(username, thread_id)
); 

create table threads_moderators(
	thread_id int references thread(id) on delete cascade,
	username varchar(32) references users(username) on delete cascade,
	primary key(thread_id, username)
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
	guidelines jsonb,
	next_discussion_id int
) inherits (thread);

create table topic_belongs_to_project(
	topic_id int references thread(id) on delete cascade,
	project_id int references thread(id) on delete cascade,
	primary key(topic_id,project_id)
);

create table topic_blacklist(
	username varchar(32) references users(username) on delete cascade,
	project_id varchar(32) references users(username) on delete cascade,
	primary key(username,project_id)
);

create table project_thread (
	repo_url text
) inherits (thread);

create table discussion_thread(
	text text,
	id int,
	topic_id int not null references thread(id),
	created_by_user varchar(32) not null references users(username),
	reply_discussion int,
	reply_topic_id int,
	primary key(topic_id, id),
	check ((reply_discussion is null) and (topic_id = reply_topic_id))
);

create table developer_associated_with_project(
	project_id int references thread(id),
	developer varchar(32) references users(username),
	started_at timestamp,
	ended_at timestamp,
	primary key(project_id, developer, started_at)
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
	username varchar(32) references users(username),
	project_id int, 
	role_name varchar(32),
	FOREIGN KEY (role_name, project_id)
    REFERENCES project_roles(name, project_id),
    primary key(username, project_id, role_name)
);

create table project_roles_permissions(
	permission_name varchar(32) references permissions(name),
	role_name varchar(32),
	project_id int,
	primary key(permission_name, role_name, project_id),
	FOREIGN KEY (role_name, project_id)
    REFERENCES project_roles(name, project_id)
);

CREATE TYPE status AS ENUM ('ACCEPTED', 'DENIED', 'PENDING');

create table project_request(
	id serial primary key,
	reason varchar (200),
	status status,
	submited_by_user varchar(32) references users(username) not null,
	for_project int references thread(id) not null	
); 

create table report(
	id serial primary key,
	created_at timestamp,
	description varchar(200),
	status status,
	thread_id int references thread(id) not null,
	for_user varchar(32) references users(username) not null,
	by_user varchar(32) references users(username) not null
);

create table channel (
	name varchar (64),
	description varchar(200),
	logo_url text,
	project_id int references thread(id) on delete cascade,
	developer varchar(32) references users(username),
	primary key(name, project_id)
);

create table messages (
	sent_at timestamp,
	content varchar(200),
	sent_by varchar(32) references users(username) not null,
	project_id int,
	channel_name varchar(64),
	FOREIGN KEY (channel_name, project_id)
    REFERENCES channel(name, project_id) on delete cascade,
    primary key(channel_name, project_id, sent_at, sent_by)
);
