CREATE OR REPLACE FUNCTION fn_validate_topic_title()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF new.title IN
       (SELECT title
        FROM topic_thread AS t
        WHERE t.referenced_by = new.referenced_by
           OR (t.referenced_by IS NULL AND new.referenced_by IS NULL)
       )
    THEN
        RAISE EXCEPTION 'There already exists a topic with title % in parent topic with id %', new.title, new.referenced_by;
    END IF;
    RETURN new;
END;
$$;

CREATE OR REPLACE FUNCTION check_if_user_exists_in(table_name text, field_name text, field_value text) RETURNS boolean
    LANGUAGE plpgsql
AS
$$
DECLARE
    result BOOL;
BEGIN
    EXECUTE format('SELECT EXISTS (SELECT 1 FROM %I WHERE %I = %L)', table_name, field_name, field_value)
        INTO result;
    RETURN result;
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
    SELECT v_topic_thread.is_created_by
    INTO v_user_id
    FROM v_topic_thread
    WHERE v_topic_thread.id = NEW.id;

    IF NOT check_if_user_exists_in('moderator', 'id', v_user_id::text) THEN
        INSERT INTO moderator VALUES (v_user_id);
    END IF;

    INSERT INTO topic_thread_is_moderated_by_moderator(thread_id, user_id) VALUES (NEW.id, v_user_id);
    RETURN NEW;
END;
$$;

CREATE OR REPLACE FUNCTION fn_insert_project_manager()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
DECLARE
    usrId INT;
    new_project_id INT;
BEGIN
    SELECT is_created_by, id
    INTO usrId, new_project_id
    FROM v_project_thread p
    WHERE NEW.id = p.id;

    IF NOT EXISTS(
        SELECT 1
        FROM developer_associated_with_project dawp
        WHERE dawp.in_project = new_project_id
          AND dawp.about_dev = usrId
    ) THEN
        INSERT INTO developer_associated_with_project(in_project, about_dev, started_at)
        VALUES (new_project_id, usrId, NOW());
    END IF;

    IF NOT check_if_user_exists_in('project_manager', 'id', usrId::text) THEN
        INSERT INTO project_manager VALUES (usrId);
    END IF;

    RETURN NEW;
END;
$$;

CREATE OR REPLACE FUNCTION fn_remove_unused_tags()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF NOT check_if_user_exists_in('tag_assigned_to_thread', 'tag_name', OLD.tag_name) THEN
        DELETE FROM tag WHERE name = OLD.tag_name;
    END IF;
    RETURN OLD;
END;
$$;

CREATE OR REPLACE FUNCTION fn_add_dev_if_not_exist()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF NOT check_if_user_exists_in('developer', 'id', NEW.about_dev::text) THEN
        INSERT INTO developer VALUES (NEW.about_dev);
    END IF;
    RETURN NEW;
END;
$$;

CREATE OR REPLACE FUNCTION fn_insert_general_for_project()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
DECLARE
    developer_id INT;
BEGIN
    SELECT is_created_by
    INTO developer_id
    FROM thread t
    WHERE t.id = NEW.id;

    INSERT INTO channel(name, description, project_has, constructed_by)
    VALUES ('General', 'General', NEW.id, developer_id);

    RETURN NEW;
END;
$$;

CREATE OR REPLACE FUNCTION fn_remove_orphan_moderator()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM topic_thread_is_moderated_by_moderator t
        WHERE t.user_id = OLD.user_id
    ) THEN
        DELETE FROM moderator WHERE id = OLD.user_id;
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM topic_thread_is_moderated_by_moderator t
        WHERE t.thread_id = OLD.thread_id
    ) THEN
        DELETE FROM discussion_thread WHERE contained_in = OLD.thread_id;
        DELETE FROM topic_thread WHERE id = OLD.thread_id;
    END IF;

    RETURN OLD;
END;
$$;

CREATE OR REPLACE FUNCTION fn_aa_rm_orphan_dics()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    DELETE FROM discussion_thread dt
    WHERE dt.contained_in = OLD.id;

    DELETE FROM embeddable_thread
    WHERE id = OLD.id;

    DELETE FROM thread t
    WHERE t.id = OLD.id;

    RETURN OLD;
END;
$$;

CREATE OR REPLACE FUNCTION fn_change_status_on_pending_reports()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    RAISE NOTICE 'user_id: %, topic_id: %', NEW.refers_to, NEW.blacklisted_from;

    UPDATE submission
    SET status = 'ACCEPTED'
    WHERE id IN (
        SELECT id
        FROM report r
        WHERE r.about = NEW.refers_to
          AND r.for_misconduct_in = NEW.blacklisted_from
    );

    RETURN NEW;
END;
$$;

CREATE OR REPLACE FUNCTION fn_add_blacklisted_user()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM blacklisted_user
        WHERE blacklisted_from = NEW.blacklisted_from
          AND refers_to = NEW.refers_to
          AND end_date IS NULL
    ) THEN
        RETURN NEW;
    END IF;
    RETURN NULL;
END;
$$;

CREATE OR REPLACE FUNCTION fn_delete_dangling_tags()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM tag_assigned_to_thread
        WHERE tag_name = OLD.tag_name
        GROUP BY tag_name
    ) THEN
        DELETE FROM tag WHERE name = OLD.tag_name;
    END IF;
    RETURN OLD;
END;
$$;

------------------------------------------------------------

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

CREATE OR REPLACE TRIGGER tr_remove_orphan_moderator
    AFTER DELETE
    ON topic_thread_is_moderated_by_moderator
    FOR EACH ROW
EXECUTE FUNCTION fn_remove_orphan_moderator();

CREATE OR REPLACE TRIGGER tr_a_insert_project_manager
    AFTER INSERT
    ON project_thread
    FOR EACH ROW
EXECUTE FUNCTION fn_insert_project_manager();

CREATE OR REPLACE TRIGGER tr_add_dev_if_not_exist
    BEFORE INSERT
    ON developer_associated_with_project
    FOR EACH ROW
EXECUTE FUNCTION fn_add_dev_if_not_exist();

CREATE OR REPLACE TRIGGER tr_insert_general_for_project
    AFTER INSERT
    ON project_thread
    FOR EACH ROW
EXECUTE FUNCTION fn_insert_general_for_project();

CREATE OR REPLACE TRIGGER tr_rm_orphan_disc
    AFTER DELETE
    ON discussion_thread
    FOR EACH ROW
EXECUTE FUNCTION fn_aa_rm_orphan_dics();

CREATE OR REPLACE TRIGGER tr_add_blacklisted_user
    BEFORE INSERT
    ON blacklisted_user
    FOR EACH ROW
EXECUTE FUNCTION fn_add_blacklisted_user();

CREATE OR REPLACE TRIGGER tr_change_status_on_pending_reports
    AFTER INSERT
    ON blacklisted_user
    FOR EACH ROW
EXECUTE FUNCTION fn_change_status_on_pending_reports();

CREATE OR REPLACE TRIGGER tr_delete_dangling_tags
    AFTER DELETE
    ON tag_assigned_to_thread
    FOR EACH ROW
EXECUTE FUNCTION fn_delete_dangling_tags();