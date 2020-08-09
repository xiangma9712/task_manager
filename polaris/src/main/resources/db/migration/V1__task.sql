DROP TABLE IF EXISTS task;

CREATE TABLE task (
    id serial primary key,
    title varchar(60) not null,
    task_type varchar(15) not null,
    description text not null,
    deadline date,
    assignee_id bigint not null,
    doing boolean not null,
    done boolean not null
);

INSERT INTO task
    (title, task_type, description, deadline, assignee_id, doing, done)
    VALUES ('initial task', 'TODO', 'this is sample task', '2020-12-10', 1, false, false);
