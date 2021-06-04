ALTER TABLE `scheduled_timeslots`
DROP CONSTRAINT scheduled_timeslots_ibfk_1,
DROP COLUMN interview_id,
ADD `interviewer_id` BIGINT NOT NULL;
