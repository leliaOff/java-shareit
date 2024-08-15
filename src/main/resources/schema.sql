DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS items CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id bigint auto_increment,
    email varchar NOT NULL,
    name varchar NOT NULL,
    CONSTRAINT users_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS items
(
    id bigint auto_increment,
    owner_id bigint NOT NULL,
    name varchar NOT NULL,
    description text,
    available boolean default(false),
    CONSTRAINT items_pk PRIMARY KEY (id),
    CONSTRAINT items_users_id_fk FOREIGN KEY (owner_id) REFERENCES users (id)
);