DROP SCHEMA IF EXISTS `task_management_system`;

CREATE SCHEMA `task_management_system`;

USE `task_management_system`;

SET FOREIGN_KEY_CHECKS = 0;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `firstname` char(20),
  `lastname` char(20),
  `username` varchar(20),
  `password` char(70) NOT NULL,
  `enabled` tinyint NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Table structure for table `tasks`
--

DROP TABLE IF EXISTS `tasks`;

CREATE TABLE `tasks` (
	`task_id` int NOT NULL AUTO_INCREMENT,
    `description` varchar(200),
    `name` varchar(20),
    `status` char(20),
    `points` int,
    `owner` int,
    
	PRIMARY KEY (`task_id`),
	CONSTRAINT FOREIGN KEY (`owner`) REFERENCES `users` (`user_id`)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `pulses`
--

DROP TABLE IF EXISTS `pulses`;

CREATE TABLE `pulses` (
	`pulse_id` int NOT NULL AUTO_INCREMENT,
    `comment` varchar(100),
    `task_id` int,
    
	PRIMARY KEY (`pulse_id`),
	CONSTRAINT FOREIGN KEY (`task_id`) REFERENCES `tasks` (`task_id`)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for join table `users_roles`
--

DROP TABLE IF EXISTS `course_student`;

CREATE TABLE `user_role` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  
  PRIMARY KEY (`user_id`,`role_id`),
    
  CONSTRAINT  FOREIGN KEY (`user_id`) 
  REFERENCES `users` (`user_id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT FOREIGN KEY (`role_id`) 
  REFERENCES `roles` (`role_id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;