DROP TABLE `skills`;

CREATE TABLE `skills`
(
    `id`         BIGINT      NOT NULL,
    `skill_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO skills
VALUES (1, 'Java'),
       (2, 'JavaScript');

CREATE TABLE `user_skills`
(
    `id`       BIGINT NOT NULL,
    `skill_id` BIGINT NOT NULL,
    `user_id`  BIGINT NOT NULL,
    PRIMARY KEY (`id`),

    FOREIGN KEY (`user_id`)
        REFERENCES `users` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,

    FOREIGN KEY (`skill_id`)
        REFERENCES `skills` (`id`)
        ON DELETE CASCADE
);


ALTER TABLE `scheduled_timeslots`
    ADD `skill_id` BIGINT NOT NULL,
    ADD FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`) ON
DELETE
NO ACTION ON UPDATE NO ACTION;


ALTER TABLE `interviews`
    ADD `skill_id` BIGINT NOT NULL,
    ADD FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`) ON
DELETE
NO ACTION ON UPDATE NO ACTION;
