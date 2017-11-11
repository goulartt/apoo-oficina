use oficina;
drop table art;
drop table art_image; 

CREATE TABLE IF NOT EXISTS `tag` (
  `id_tag` INT NOT NULL AUTO_INCREMENT,
  `tag` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_tag`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`art`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `art` (
  `id_art` INT NOT NULL AUTO_INCREMENT,
  `s3_key` VARCHAR(200) NOT NULL,
  `description` VARCHAR(65) NULL,
  PRIMARY KEY (`id_art`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`art_has_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `art_has_tag` (
  `art_id_art` INT NOT NULL,
  `tag_id_tag` INT NOT NULL,
  PRIMARY KEY (`art_id_art`, `tag_id_tag`),
  INDEX `fk_art_has_tag_tag1_idx` (`tag_id_tag` ASC),
  INDEX `fk_art_has_tag_art_idx` (`art_id_art` ASC),
  CONSTRAINT `fk_art_has_tag_art`
    FOREIGN KEY (`art_id_art`)
    REFERENCES `art` (`id_art`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_art_has_tag_tag1`
    FOREIGN KEY (`tag_id_tag`)
    REFERENCES `tag` (`id_tag`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;