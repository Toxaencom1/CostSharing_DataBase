CREATE TABLE IF NOT EXISTS my_session
(
    id        BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    admin_Id  BIGINT,
    is_closed BOOLEAN
);

CREATE TABLE IF NOT EXISTS my_user
(
    id        BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    firstname VARCHAR(255),
    lastname  VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS pay_fact
(
    id         BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    session_id BIGINT,
    user_id    BIGINT,
    user_data  VARCHAR(255),
    amount     DOUBLE PRECISION,
    CONSTRAINT fk_session FOREIGN KEY (session_id) REFERENCES my_session (id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES my_user (id)
);

CREATE TABLE IF NOT EXISTS my_check
(
    id         BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    session_id BIGINT,
    name       VARCHAR(255),
    CONSTRAINT fk_session_check FOREIGN KEY (session_id) REFERENCES my_session (id)
);

CREATE TABLE IF NOT EXISTS product_using
(
    id           BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    check_id     BIGINT,
    product_name VARCHAR(255),
    CONSTRAINT fk_check FOREIGN KEY (check_id) REFERENCES my_check (id)
);

CREATE TABLE IF NOT EXISTS product_using_user
(
    product_using_id BIGINT NOT NULL,
    user_id          BIGINT NOT NULL,
    PRIMARY KEY (product_using_id, user_id),
    CONSTRAINT fk_product_using FOREIGN KEY (product_using_id) REFERENCES product_using (id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES my_user (id)
);