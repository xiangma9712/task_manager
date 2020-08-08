DROP TABLE IF EXISTS log;

CREATE TABLE log (
    id serial primary key,
    task_id bigint not null references task (id),
    log_date date not null,
    used_hour smallint not null,
    progress smallint not null,
    memo text
);