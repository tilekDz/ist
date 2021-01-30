CREATE TABLE role (
  id INT(11) auto_INCREMENT PRIMARY KEY,
  role VARCHAR(255)
);

CREATE TABLE user (
  id BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
  user_first_name VARCHAR(255),
  user_active BIT(1),
  is_admin BIT(1),
  user_email VARCHAR(255),
  user_password VARCHAR(255),
  created_date DATETIME,
  user_last_name VARCHAR(255)
);

CREATE TABLE user_role (
  user_id BIGINT(20),
  role_id INT(11)
);


insert into role (id, role) VALUES (1,'ADMIN');

insert into user (user_first_name, user_active, user_email,
                          user_password, created_date, user_last_name, is_admin)
VALUES ('Admin', TRUE , 'admin', '$2y$12$ZhmTiVUzxsaf1HLeAo3j2uKWuz/rpKLoRMxuo5nka..job6MhbYt.', current_date, 'Adminov', TRUE);

INSERT INTO user_role (user_id, role_id) VALUES (1,1);