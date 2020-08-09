--DROP TABLE IF EXISTS log;

CREATE TABLE IF NOT EXISTS log (
    id serial primary key,
    fk_task bigint not null references task (id),
    log_date date not null,
    used_hour smallint not null,
    progress smallint not null,
    memo text
);

INSERT INTO log
    (fk_task, log_date, used_hour, progress)
SELECT
    ( SELECT min(id) FROM task ), '2020-08-09', 1, 30
WHERE NOT EXISTS
    ( SELECT id FROM log );