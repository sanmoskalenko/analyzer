DROP TABLE IF EXISTS url_checks;
DROP TABLE IF EXISTS urls;

CREATE TABLE IF NOT EXISTS urls (
    id bigint generated always as identity primary key,
    name varchar(255) not null,
    created_at timestamp NOT NULL
);

CREATE TABLE IF NOT EXISTS url_checks (
    id bigint GENERATED ALWAYS AS identity PRIMARY KEY,
    status_code integer NOT NULL,
    title varchar NOT NULL,
    h1 varchar,
    description varchar,
    url_id bigint REFERENCES urls(id) NOT NULL,
    created_at timestamp NOT NULL
);
