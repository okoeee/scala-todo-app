CREATE TABLE `user` (
  `id`         BIGINT       unsigned NOT NULL AUTO_INCREMENT,
  `name`       VARCHAR(255) NOT NULL,
  `email`      VARCHAR(255) NOT NULL,
  `password`   VARCHAR(255) NOT NULL,
  `updated_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_unique` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_session` (
  `id`          BIGINT       unsigned NOT NULL AUTO_INCREMENT,
  `user_id`     BIGINT       unsigned NOT NULL,
  `token`       VARCHAR(512) NOT NULL,
  `expiry_date` TIMESTAMP    NOT NULL,
  `updated_at`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `token_unique` (`token`),
  FOREIGN KEY (`user_id`) REFERENCES user(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `category` (
  `id`         BIGINT       unsigned NOT NULL AUTO_INCREMENT,
  `user_id`    BIGINT       unsigned NOT NULL,
  `name`       VARCHAR(255) NOT NULL,
  `color`      TINYINT      unsigned  NOT NULL,
  `updated_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES user(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `todo` (
  `id`          BIGINT       unsigned NOT NULL AUTO_INCREMENT,
  `user_id`     BIGINT       unsigned NOT NULL,
  `category_id` BIGINT       unsigned,
  `title`       VARCHAR(255) NOT NULL,
  `body`        TEXT,
  `state`       TINYINT      unsigned NOT NULL,
  `updated_at`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES user(`id`),
  FOREIGN KEY (`category_id`) REFERENCES category(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;