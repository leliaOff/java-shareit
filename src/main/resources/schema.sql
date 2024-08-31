DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS items CASCADE;
DROP TABLE IF EXISTS bookings CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id bigserial not null constraint users_pk primary key,
    email varchar not null,
    name varchar not null,
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS items
(
    id bigserial not null constraint items_pk primary key,
    owner_id bigint not null constraint items_users_id_fk references users,
    name varchar not null,
    description text,
    available boolean default false
);

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