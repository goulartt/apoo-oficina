delete from art_has_tag;
delete from art;
delete from tag;

ALTER TABLE art
DROP COLUMN s3_key;

ALTER TABLE art
ADD image  MEDIUMBLOB NOT NULL;