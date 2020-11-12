-- Table: users
CREATE TABLE users (
                       id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

-- Table: roles
CREATE TABLE roles (
                       id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(100) NOT NULL
);

-- Table for mapping user and roles: users_roles
CREATE TABLE users_roles (
                            user_id INT NOT NULL,
                            role_id INT NOT NULL,

                            FOREIGN KEY (user_id) REFERENCES users (id),
                            FOREIGN KEY (role_id) REFERENCES roles (id),

                            UNIQUE (user_id, role_id)
);

-- Insert data

INSERT INTO users VALUES (1, 'ADMIN', '100');
INSERT INTO users VALUES (2, 'USER', '100');

INSERT INTO roles VALUES (1, 'ROLE_USER');
INSERT INTO roles VALUES (2, 'ROLE_ADMIN');

INSERT INTO users_roles VALUES (1, 2);
INSERT INTO users_roles VALUES (1, 1);
INSERT INTO users_roles VALUES (2, 1);
