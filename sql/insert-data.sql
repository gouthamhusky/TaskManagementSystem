USE `task_management_system`;

-- passwords: password1, password2, password3, password4, password5

INSERT INTO users (user_id, firstname, lastname, username, password, enabled)
VALUES 
    (1, 'John', 'Doe', 'jdoe', '{bcrypt}$2a$10$zm.jz7/8bFnmQqXyU6K0U.AN25/HBzr/d.MrTExT12.DQV7T8foum', 1),
    (2, 'Jane', 'Doe', 'jane.doe', '{bcrypt}$2a$10$7pDrztsb1ld.oLU8dJLF2.3q1iK6Uvd7F8DzKQG3qZBvJlBhW8d92', 1),
    (3, 'Alice', 'Smith', 'asmith', '{bcrypt}$2a$10$HXF5lbbI.hzBlnFtYpMzZOog1tBnukRW9N44tZ/qEaBxNn2sW8Dw6', 1),
    (4, 'Bob', 'Johnson', 'bjohnson', '{bcrypt}$2a$10$7Q.kd4A4V7uvq8D1irUMZe/7JuyP.G2dKj8nN6cegoZB.87NLrc/i', 1),
    (5, 'Ella', 'Lee', 'elee', '{bcrypt}$2a$10$2QsDjKwge81kczBysVOzEuYgUv7VW.dK8Uq7Iu4l6Gv7VrlJ2QIFC', 1);


-- inserting two roles: USER and ADMIN
INSERT INTO roles (role_id, role_name)
VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');

-- associate users and role
INSERT INTO user_role (user_id, role_id)
VALUES (1, 1), (2, 1), (3, 1), (4, 1), (4, 2), (5, 2);