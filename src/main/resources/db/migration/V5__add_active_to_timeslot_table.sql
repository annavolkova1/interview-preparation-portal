ALTER TABLE `users` CHANGE COLUMN `password` `password` VARCHAR(64);

UPDATE `skills` SET `skill_name` = 'JAVA' WHERE `id` = '1';
UPDATE `skills` SET `skill_name` = 'JAVASCRIPT' WHERE `id` = '2';

ALTER TABLE `scheduled_timeslots`
ADD `active` BOOLEAN;


