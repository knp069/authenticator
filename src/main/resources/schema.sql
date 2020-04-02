create or drop table users (
    id integer NOT NULL AUTO_INCREMENT,
    created_by varchar(40) NOT NULL,
    created_at timestamp NOT NULL,
    updated_by varchar(40) NOT NULL,
    updated_at timestamp NOT NULL,
    phone varchar(40) NOT NULL,
    password varchar(200) NOT NULL,
    role varchar(10) NOT NULL,
    first_name varchar(40) NOT NULL,
    last_name varchar(40) NOT NULL,
    primary key (id)
);

create or drop table user_tokens (
    id integer NOT NULL AUTO_INCREMENT,
    created_by varchar(40) NOT NULL,
    created_at timestamp NOT NULL,
    updated_by varchar(40) NOT NULL,
    updated_at timestamp NOT NULL,
    user_id integer NOT NULL,
    token varchar NOT NULL UNIQUE,
    status varchar NOT NULL,
    primary key (id)
    foreign key (user_id) references user(id)
);

create or drop table user_details (
    id integer NOT NULL AUTO_INCREMENT,
    created_by varchar(40) NOT NULL,
    created_at timestamp NOT NULL,
    updated_by varchar(40) NOT NULL,
    updated_at timestamp NOT NULL,
    user_id integer NOT NULL UNIQUE,
    primary_address_id integer NULLABLE,
    secondary_address_id integer NULLABLE,
    community varchar(40) NOT NULL,
    primary key (id)
    foreign key (user_id) references user(id)
    foreign key (primary_address_id) references addresses(id)
    foreign key (secondary_address_id) references addresses(id)
);

create or drop table addresses (
    id integer NOT NULL AUTO_INCREMENT,
    created_by varchar(40) NOT NULL,
    created_at timestamp NOT NULL,
    updated_by varchar(40) NOT NULL,
    updated_at timestamp NOT NULL,
    latitude double NULLABLE ,
    longitude double NULLABLE ,
    address varchar NULLABLE,
    city varchar(40) NULLABLE,
    locality varchar(40) NULLABLE,
    state varchar(40) NULLABLE,
    primary key (id)
);