CREATE TABLE `users`
(
    `id`         BIGINT      NOT NULL,
    `email`      VARCHAR(45) NOT NULL,
    `password`   VARCHAR(45) NOT NULL,
    `first_name` VARCHAR(45) NOT NULL,
    `last_name`  VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `user_roles`
(
    `id`        BIGINT      NOT NULL,
    `user_role` VARCHAR(45) NOT NULL,
    `user_id`   BIGINT      NOT NULL,
    PRIMARY KEY (`id`),

    FOREIGN KEY (`user_id`)
        REFERENCES `users` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

CREATE TABLE `interviews`
(
    `id`             BIGINT   NOT NULL,
    `interviewee_id` BIGINT   NOT NULL,
    `interviewer_id` BIGINT   NOT NULL,
    `start_time`     DATETIME NOT NULL,
    `end_time`       DATETIME NOT NULL,
    `comment`        TEXT     NULL DEFAULT NULL,
    PRIMARY KEY (`id`),

    FOREIGN KEY (`interviewer_id`)
        REFERENCES `users` (`id`),
    FOREIGN KEY (`interviewee_id`)
        REFERENCES `users` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

CREATE TABLE `scheduled_timeslots`
(
    `id`           BIGINT   NOT NULL,
    `start_time`   DATETIME NOT NULL,
    `end_time`     DATETIME NOT NULL,
    `interview_id` BIGINT   NOT NULL,
    `comment`      TEXT     NULL DEFAULT NULL,
    PRIMARY KEY (`id`),

    FOREIGN KEY (`interview_id`)
        REFERENCES `interviews` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

CREATE TABLE `skills`
(
    `id`         BIGINT      NOT NULL,
    `skill_name` VARCHAR(45) NOT NULL,
    `user_id`    BIGINT      NOT NULL,
    PRIMARY KEY (`ID`),

    FOREIGN KEY (`user_id`)
        REFERENCES `users` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);
