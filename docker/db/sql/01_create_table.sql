
USE `todo`;

CREATE TABLE `group` (
  `id`         BIGINT       UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`       VARCHAR(255) NOT NULL,
  `updated_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user` (
  `id`         BIGINT       UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`       VARCHAR(255) NOT NULL,
  `email`      VARCHAR(255) NOT NULL,
  `password`   VARCHAR(255) NOT NULL,
  `updated_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_unique` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `group_membership` (
  `group_id`   BIGINT       UNSIGNED,
  `user_id`    BIGINT       UNSIGNED,
  `updated_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`group_id`)  REFERENCES `group`(`id`) ON DELETE SET NULL,
  FOREIGN KEY (`user_id`)   REFERENCES `user`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_session` (
  `id`          BIGINT       UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id`     BIGINT       UNSIGNED NOT NULL,
  `token`       VARCHAR(512) NOT NULL,
  `expiry_date` TIMESTAMP    NOT NULL,
  `updated_at`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `token_unique` (`token`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `category` (
  `id`              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `group_id`        BIGINT UNSIGNED,
  `created_user_id` BIGINT UNSIGNED,
  `name`            VARCHAR(255) NOT NULL,
  `color`           TINYINT UNSIGNED  NOT NULL,
  `updated_at`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`group_id`)  REFERENCES `group`(`id`) ON DELETE SET NULL,
  FOREIGN KEY (`created_user_id`)   REFERENCES `user`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `todo` (
  `id`                      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `group_id`                BIGINT UNSIGNED,
  `created_user_id`         BIGINT UNSIGNED,
  `category_id`             BIGINT UNSIGNED,
  `title`                   VARCHAR(255) NOT NULL,
  `body`                    TEXT,
  `state`                   TINYINT UNSIGNED NOT NULL,
  `updated_at`              TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at`              TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`group_id`)  REFERENCES `group`(`id`) ON DELETE SET NULL,
  FOREIGN KEY (`created_user_id`)   REFERENCES `user`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;