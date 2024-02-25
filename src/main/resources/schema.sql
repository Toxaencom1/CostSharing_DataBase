CREATE TABLE if not exists session
(
    id      BIGINT PRIMARY KEY,
    adminId BIGINT,
    isClosed  BOOLEAN
);

CREATE TABLE if not exists my_user
(
    id        BIGINT PRIMARY KEY,
    firstname VARCHAR(255),
    lastname  VARCHAR(255)
);

CREATE TABLE if not exists pay_fact
(
    session_id BIGINT,
    user_id    BIGINT,
    user_data  VARCHAR(255),
    amount     DOUBLE,
    FOREIGN KEY (session_id) REFERENCES session (id),
    FOREIGN KEY (user_id) REFERENCES my_user (id)
);

CREATE TABLE if not exists my_check
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT,
    name       VARCHAR(255),
    FOREIGN KEY (session_id) REFERENCES session (id)
);

CREATE TABLE IF NOT EXISTS product_using (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               check_id BIGINT,
                               product_name VARCHAR(255),
                               user_id BIGINT,
                               FOREIGN KEY (check_id) REFERENCES my_check(id),
                               FOREIGN KEY (user_id) REFERENCES my_user(id)
);
CREATE TABLE IF NOT EXISTS product_using_User (
                                   product_using_id BIGINT NOT NULL,
                                   user_id BIGINT NOT NULL,
                                   PRIMARY KEY(product_using_id, user_id),
                                   FOREIGN KEY (product_using_id) REFERENCES Product_Using(id),
                                   FOREIGN KEY (user_id) REFERENCES my_user(id)
);