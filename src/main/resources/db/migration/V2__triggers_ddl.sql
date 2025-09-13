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
    project_resource_id INT;
BEGIN
    select user_id
    into developer_id
    from thread t
    where t.id=NEW.id;
    insert into project_resource default values returning id into project_resource_id;

    insert into channel(name,description,project_id,developer_id,project_resource_id)
    values ('General','General',NEW.id,developer_id,project_resource_id);

    return new;
end;
$$;


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
    delete from embeddable_thread
    where id = OLD.id;
    delete from thread t
    where t.id=OLD.id;
    RETURN OLD;
END;
$$;


create or replace function fn_add_project_resource()
    returns trigger
    language plpgsql
as $$
DECLARE
    project_resource_id INT;
BEGIN
    insert into project_resource default values returning id into project_resource_id;
    new.project_resource_id := project_resource_id;
    return new;
end;
$$;


------------------------------------------------------------

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

create or replace trigger tr_add_project_resource_channel
    before insert
    on channel
    for each row
execute function fn_add_project_resource();

create or replace trigger tr_add_project_resource_project_roles_permissions
    before insert
    on project_roles_permissions
    for each row
execute function fn_add_project_resource();