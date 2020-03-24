SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;


CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL,
  `content` longtext DEFAULT NULL,
  `creation_time` datetime DEFAULT NULL,
  `author_id` bigint(20) DEFAULT NULL,
  `post_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `post` (
  `id` bigint(20) NOT NULL,
  `content` longtext DEFAULT NULL,
  `creation_time` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `author_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `role` (`id`, `role`) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

CREATE TABLE `school_class` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `activation_key` varchar(255) DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `bio` longtext DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `school_class_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


ALTER TABLE `comment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKh1gtv412u19wcbx22177xbkjp` (`author_id`),
  ADD KEY `FKs1slvnkuemjsq2kj4h3vhx7i1` (`post_id`);

ALTER TABLE `post`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK12njtf8e0jmyb45lqfpt6ad89` (`author_id`);

ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `school_class`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  ADD KEY `FKknws0u0j3ahtypieb9qqqg3b1` (`school_class_id`);

ALTER TABLE `user_roles`
  ADD KEY `FKj9553ass9uctjrmh0gkqsmv0d` (`roles_id`),
  ADD KEY `FK55itppkw3i07do3h7qoclqd4k` (`user_id`);


ALTER TABLE `comment`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `post`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `role`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

ALTER TABLE `school_class`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;


ALTER TABLE `comment`
  ADD CONSTRAINT `FKh1gtv412u19wcbx22177xbkjp` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKs1slvnkuemjsq2kj4h3vhx7i1` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`);

ALTER TABLE `post`
  ADD CONSTRAINT `FK12njtf8e0jmyb45lqfpt6ad89` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`);

ALTER TABLE `user`
  ADD CONSTRAINT `FKknws0u0j3ahtypieb9qqqg3b1` FOREIGN KEY (`school_class_id`) REFERENCES `school_class` (`id`);

ALTER TABLE `user_roles`
  ADD CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKj9553ass9uctjrmh0gkqsmv0d` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
