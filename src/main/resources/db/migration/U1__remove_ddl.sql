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
