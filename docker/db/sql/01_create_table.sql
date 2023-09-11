CREATE TABLE `category` (
  `id`         BIGINT       unsigned NOT NULL AUTO_INCREMENT,
  `name`       VARCHAR(255) NOT NULL,
  `color`      TINYINT      unsigned  NOT NULL,
  `updated_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `todo` (
  `id`          BIGINT       unsigned NOT NULL AUTO_INCREMENT,
  `category_id` BIGINT       unsigned,
  `title`       VARCHAR(255) NOT NULL,
  `body`        TEXT,
  `state`       TINYINT      unsigned NOT NULL,
  `updated_at`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`category_id`) REFERENCES category(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
