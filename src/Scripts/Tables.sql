CREATE TABLE `leagues` (
    `id` VARCHAR(36) NOT NULL,
    `name` VARCHAR(32) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uq_league_name` (`name`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

CREATE TABLE `match_report` (
    `id` VARCHAR(255) NOT NULL,
    `name` VARCHAR(255) DEFAULT NULL,
    `total_draws` INT NOT NULL,
    `total_losses` INT NOT NULL,
    `total_wins` INT NOT NULL,
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

CREATE TABLE `matches` (
    `id` VARCHAR(36) NOT NULL,
    `first_team_id` VARCHAR(36) NOT NULL,
    `second_team_id` VARCHAR(36) NOT NULL,
    `first_team_score` INT NOT NULL DEFAULT '0',
    `second_team_score` INT NOT NULL DEFAULT '0',
    `date` DATE NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_matches_first_team_id` (`first_team_id`),
    KEY `fk_matches_second_team_id` (`second_team_id`),
    CONSTRAINT `fk_matches_first_team_id` FOREIGN KEY (`first_team_id`)
        REFERENCES `teams` (`id`),
    CONSTRAINT `fk_matches_second_team_id` FOREIGN KEY (`second_team_id`)
        REFERENCES `teams` (`id`),
    CONSTRAINT `chk_matches_teams` CHECK ((`first_team_id` <> `second_team_id`))
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

CREATE TABLE `players` (
    `id` VARCHAR(36) NOT NULL,
    `first_name` VARCHAR(32) NOT NULL,
    `last_name` VARCHAR(32) NOT NULL,
    `date_of_Birth` DATE NOT NULL,
    `team_id` VARCHAR(36) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_players_teams` (`team_id`),
    CONSTRAINT `fk_players_teams` FOREIGN KEY (`team_id`)
        REFERENCES `teams` (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

CREATE TABLE `roles` (
    `name` VARCHAR(20) NOT NULL,
    `description` VARCHAR(50) DEFAULT NULL,
    PRIMARY KEY (`name`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

CREATE TABLE `teams` (
    `id` VARCHAR(36) NOT NULL,
    `name` VARCHAR(32) NOT NULL,
    `league_id` VARCHAR(36) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uq_teams_name` (`name`),
    KEY `fk_teams_leagues` (`league_id`),
    CONSTRAINT `fk_teams_leagues` FOREIGN KEY (`league_id`)
        REFERENCES `leagues` (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

CREATE TABLE `users` (
    `username` VARCHAR(255) NOT NULL,
    `first_name` VARCHAR(20) DEFAULT NULL,
    `last_name` VARCHAR(20) DEFAULT NULL,
    `password` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`username`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

CREATE TABLE `users_roles` (
    `user_id` VARCHAR(255) NOT NULL,
    `role_id` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`user_id` , `role_id`),
    KEY `fk_role_id` (`role_id`),
    CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`)
        REFERENCES `users` (`username`),
    CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`)
        REFERENCES `roles` (`name`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;
