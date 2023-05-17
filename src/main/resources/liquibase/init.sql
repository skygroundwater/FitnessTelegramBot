--l liquibase formatted sql

-- changeset oleg:1
create table athlete
(
    athlete_name  varchar not null,
    registered_at timestamp,
    is_active     boolean,
    athlete_id    bigint  not null
        constraint athlete_chat_id_pk
            primary key
);

create table faq
(
    answer   varchar not null,
    question varchar not null
        constraint question_key
            primary key
);

create table notification
(
    description        varchar   not null,
    time_to_do         timestamp not null,
    id                 bigserial
        constraint notification_pk
            primary key,
    athlete_athlete_id bigint
        constraint athlete_athlete_id
            references athlete
);

