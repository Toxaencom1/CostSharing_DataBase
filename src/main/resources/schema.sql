CREATE TABLE if not exists session
(
    id      INT PRIMARY KEY,
    adminId INT,
    isClosed  BOOLEAN
);

CREATE TABLE if not exists my_user
(
    id        INT PRIMARY KEY,
    firstname VARCHAR(255),
    lastname  VARCHAR(255)
);

CREATE TABLE if not exists pay_fact
(
    session_id INT,
    user_id    INT,
    amount     DOUBLE,
    FOREIGN KEY (session_id) REFERENCES session (id),
    FOREIGN KEY (user_id) REFERENCES my_user (id)
);

CREATE TABLE if not exists my_check
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    session_id INT,
    name       VARCHAR(255),
    FOREIGN KEY (session_id) REFERENCES session (id)
);

CREATE TABLE IF NOT EXISTS product_using (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               check_id INT,
                               product_name VARCHAR(255),
                               user_id INT,
                               FOREIGN KEY (check_id) REFERENCES my_check(id),
                               FOREIGN KEY (user_id) REFERENCES my_user(id)
);
CREATE TABLE IF NOT EXISTS product_using_User (
                                   product_using_id INT NOT NULL,
                                   user_id INT NOT NULL,
                                   PRIMARY KEY(product_using_id, user_id),
                                   FOREIGN KEY (product_using_id) REFERENCES Product_Using(id),
                                   FOREIGN KEY (user_id) REFERENCES my_user(id)
);