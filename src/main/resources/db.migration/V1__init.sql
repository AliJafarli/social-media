-- CREATE TABLE users (
--                        id BIGSERIAL PRIMARY KEY,
--                        username VARCHAR(30) NOT NULL UNIQUE,
--                        password VARCHAR(80) NOT NULL,
--                        email VARCHAR(50) UNIQUE,
--                        created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--                        updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--                        registration_status VARCHAR(30) NOT NULL,
--                        last_login TIMESTAMP,
--                        deleted BOOLEAN NOT NULL DEFAULT false
-- );
--
-- CREATE TABLE posts (
--                        id BIGSERIAL PRIMARY KEY,
--                        user_id INTEGER NOT NULL ,
--                        content TEXT NOT NULL,
--                        created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--                        deleted BOOLEAN NOT NULL DEFAULT false,
--                        likes INTEGER NOT NULL  DEFAULT 0,
--                        created_by VARCHAR(50),
--                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
-- );
--
-- CREATE TABLE roles (
--                        id SERIAL PRIMARY KEY,
--                        name VARCHAR(50) NOT NULL,
--                        user_system_role VARCHAR(64) NOT NULL,
--                        active BOOLEAN NOT NULL DEFAULT true,
--                        created_by VARCHAR(50) NOT NULL
-- );
--
-- CREATE TABLE users_roles (
--                              user_id BIGINT NOT NULL,
--                              role_id INT NOT NULL,
--                              PRIMARY KEY (user_id, role_id),
--                              FOREIGN KEY (user_id) REFERENCES users (id),
--                              FOREIGN KEY (role_id) REFERENCES roles (id)
-- );
--
-- CREATE TABLE refresh_token (
--                                id SERIAL PRIMARY KEY,
--                                token VARCHAR(128) NOT NULL,
--                                created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--                                user_id BIGINT NOT NULL,
--                                CONSTRAINT FK_refresh_tokens_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
--                                CONSTRAINT refresh_token_UNIQUE UNIQUE (user_id, id)
-- );
--
INSERT INTO users (first_name, last_name, username, password, email, created, updated, registration_status, last_login, deleted) VALUES
                                                                                                              ('super_admin', 'super_admin','super_admin', '$2a$10$ODYQFFe4.QGUTX37aJHfk.KIHUhdES593d1ggMRqZQF0Phk5j8hcO', 'superadmin@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', CURRENT_TIMESTAMP, false),
                                                                                                              ('admin', 'admin', 'admin', '$2a$10$yjT3MUJ.y5vxpiWCNS4/guMcrOMEM.5F0Vo2c9pAghE2lcv6EfrAe', 'admin@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', CURRENT_TIMESTAMP, false),
                                                                                                              ('user', 'user','user', '$2a$10$R08bWtD.BmdmJe.B3/Jwwu39oP.jIpkgaCxc2kKOz/MbrMDJ.IcjC', 'user@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', CURRENT_TIMESTAMP, false);

-- INSERT INTO posts (user_id, content, created, deleted, likes) VALUES
--                                                                                   (1,  'This is content of the first post', CURRENT_TIMESTAMP,  false, 6),
--                                                                                   (2,  'This is content of the second post', CURRENT_TIMESTAMP,  false, 3);
--
INSERT INTO roles (name, user_system_role, created_by) VALUES
                                                           ('SUPER_ADMIN', 'SUPER_ADMIN', 'SUPER_ADMIN'),
                                                           ('ADMIN', 'ADMIN', 'SUPER_ADMIN'),
                                                           ('USER', 'USER', 'SUPER_ADMIN');
--
INSERT INTO users_roles (user_id, role_id) VALUES
                                               (1, 1),
                                               (2, 2),
                                               (3, 3);
--
