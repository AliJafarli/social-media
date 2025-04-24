
INSERT INTO users (first_name, last_name, username, password, email, created, updated, registration_status, last_login, deleted) VALUES
                                                                                                              ('super_admin', 'super_admin','super_admin', '$2a$10$ODYQFFe4.QGUTX37aJHfk.KIHUhdES593d1ggMRqZQF0Phk5j8hcO', 'superadmin@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', CURRENT_TIMESTAMP, false),
                                                                                                              ('admin', 'admin', 'admin', '$2a$10$yjT3MUJ.y5vxpiWCNS4/guMcrOMEM.5F0Vo2c9pAghE2lcv6EfrAe', 'admin@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', CURRENT_TIMESTAMP, false),
                                                                                                              ('user', 'user','user', '$2a$10$R08bWtD.BmdmJe.B3/Jwwu39oP.jIpkgaCxc2kKOz/MbrMDJ.IcjC', 'user@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', CURRENT_TIMESTAMP, false);


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
