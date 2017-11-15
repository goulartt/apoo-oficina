-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema oficina
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema oficina
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `oficina` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema oficina
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema oficina
-- -----------------------------------------------------
USE `oficina` ;

-- -----------------------------------------------------
-- Table `oficina`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oficina`.`tag` (
  `id_tag` INT NOT NULL AUTO_INCREMENT,
  `tag` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_tag`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `oficina`.`art`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oficina`.`art` (
  `id_art` INT NOT NULL AUTO_INCREMENT,
  `image` MEDIUMBLOB NOT NULL,
  `description` VARCHAR(65) NULL,
  PRIMARY KEY (`id_art`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `oficina`.`art_has_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oficina`.`art_has_tag` (
  `art_id_art` INT NOT NULL,
  `tag_id_tag` INT NOT NULL,
  PRIMARY KEY (`art_id_art`, `tag_id_tag`),
  INDEX `fk_art_has_tag_tag1_idx` (`tag_id_tag` ASC),
  INDEX `fk_art_has_tag_art_idx` (`art_id_art` ASC),
  CONSTRAINT `fk_art_has_tag_art`
    FOREIGN KEY (`art_id_art`)
    REFERENCES `oficina`.`art` (`id_art`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_art_has_tag_tag1`
    FOREIGN KEY (`tag_id_tag`)
    REFERENCES `oficina`.`tag` (`id_tag`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `oficina` ;

-- -----------------------------------------------------
-- Table `oficina`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oficina`.`address` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(255) NULL DEFAULT NULL,
  `neighborhood` VARCHAR(255) NULL DEFAULT NULL,
  `number` VARCHAR(255) NULL DEFAULT NULL,
  `state` VARCHAR(255) NULL DEFAULT NULL,
  `street` VARCHAR(255) NULL DEFAULT NULL,
  `zip_code` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `oficina`.`contact`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oficina`.`contact` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `phone` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `oficina`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oficina`.`customer` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `birth_date` DATE NULL,
  `cpf` VARCHAR(22) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `removed` INT NULL,
  `contact_id` INT(11) NOT NULL,
  `address_id` INT(11) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_customer_contact1_idx` (`contact_id` ASC),
  INDEX `fk_customer_address1_idx` (`address_id` ASC),
  CONSTRAINT `fk_customer_contact1`
    FOREIGN KEY (`contact_id`)
    REFERENCES `oficina`.`contact` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_customer_address1`
    FOREIGN KEY (`address_id`)
    REFERENCES `oficina`.`address` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `oficina`.`service`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oficina`.`service` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `removed` INT NOT NULL,
  `name_service` VARCHAR(255) NOT NULL,
  `status` INT(11) NULL DEFAULT NULL,
  `art_id_art` INT NOT NULL,
  `customer_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `art_id_art`),
  INDEX `fk_service_art1_idx` (`art_id_art` ASC),
  INDEX `fk_service_customer1_idx` (`customer_id` ASC),
  CONSTRAINT `fk_service_art1`
    FOREIGN KEY (`art_id_art`)
    REFERENCES `oficina`.`art` (`id_art`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_service_customer1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `oficina`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `oficina`.`session`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oficina`.`session` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `date_session` DATETIME NULL DEFAULT NULL,
  `obs` VARCHAR(255) NULL DEFAULT NULL,
  `price` DECIMAL(19,2) NULL DEFAULT NULL,
  `status` INT(11) NULL DEFAULT NULL,
  `service_id` INT(11) NOT NULL,
  `removed` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_session_service1_idx` (`service_id` ASC),
  CONSTRAINT `fk_session_service1`
    FOREIGN KEY (`service_id`)
    REFERENCES `oficina`.`service` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `oficina`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oficina`.`user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `removed` INT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `role` INT(11) NULL DEFAULT NULL,
  `senha` VARCHAR(255) NULL DEFAULT NULL,
  `usuario` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
