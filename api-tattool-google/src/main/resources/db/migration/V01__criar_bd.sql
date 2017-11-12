use oficina;
-- -----------------------------------------------------
-- Table `oficina`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `archived` CHAR(1) NOT NULL,
  `nome` VARCHAR(255) NULL DEFAULT NULL,
  `role` INT(11) NULL DEFAULT NULL,
  `senha` VARCHAR(255) NULL DEFAULT NULL,
  `usuario` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS art_image (
  id INT NOT NULL AUTO_INCREMENT,
  s3_key VARCHAR(200) NOT NULL,
  url VARCHAR(1000) NOT NULL,
  PRIMARY KEY (id));  
  
  CREATE TABLE IF NOT EXISTS art (
   id INT NOT NULL AUTO_INCREMENT,
  description VARCHAR(30) NOT NULL,
  tag VARCHAR(30) NOT NULL,
  art_image_id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_ART_IMAGE_ID
    FOREIGN KEY (art_image_id)
    REFERENCES art_image (id));   

