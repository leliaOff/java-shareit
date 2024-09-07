DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS items CASCADE;
DROP TABLE IF EXISTS bookings CASCADE;
DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS requests CASCADE;
DROP TABLE IF EXISTS request_items CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id bigserial not null constraint users_pk primary key,
    email varchar not null,
    name varchar not null,
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);
create index users_email_index on users (email);

CREATE TABLE IF NOT EXISTS items
(
    id bigserial not null constraint items_pk primary key,
    owner_id bigint not null constraint items_users_id_fk references users,
    name varchar not null,
    description text,
    available boolean default false
);
create index items_owner_id_index on items (owner_id);
create index items_name_index on items (name);
create index items_available_index on items (available);

CREATE TABLE IF NOT EXISTS bookings
(
    id bigserial not null constraint bookings_pk primary key,
    item_id bigint not null constraint bookings_items_id_fk references items,
    user_id bigint not null constraint bookings_users_id_fk references users,
    date_start timestamp not null,
    date_end timestamp not null,
    status varchar default 'WAITING',
    review text
);
create index bookings_item_id_index on bookings (item_id);
create index bookings_user_id_index on bookings (user_id);

CREATE TABLE IF NOT EXISTS comments
(
    id bigserial not null constraint comments_pk primary key,
    item_id bigint not null constraint comments_items_id_fk references items,
    user_id bigint not null constraint comments_users_id_fk references users,
    text text not null,
    created timestamp not null
);
create index comments_item_id_index on comments (item_id);

CREATE TABLE IF NOT EXISTS requests
(
    id bigserial not null constraint requests_pk primary key,
    user_id bigint not null constraint requests_users_id_fk references users,
    description text,
    created timestamp not null
);
create index requests_user_id_index on requests (user_id);
create index requests_created_index on requests (created);

CREATE TABLE IF NOT EXISTS request_items
(
    id bigserial not null constraint request_items_pk primary key,
    request_id bigint not null constraint request_items_requests_id_fk references requests,
    item_id bigint not null constraint request_items_items_id_fk references items,
    created timestamp not null
);
create index request_items_request_id_index on request_items (request_id);
create index request_items_item_id_index on request_items (item_id);