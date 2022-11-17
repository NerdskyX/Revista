-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`amigo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`amigo` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NULL DEFAULT NULL,
  `nome_responsavel` VARCHAR(45) NULL DEFAULT NULL,
  `telefone` VARCHAR(45) NULL DEFAULT NULL,
  `endereco` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`caixa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`caixa` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `cor` VARCHAR(45) NULL DEFAULT NULL,
  `codigo_etiqueta` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 0
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`tipo_colecao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tipo_colecao` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 0
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`revista`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`revista` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NULL DEFAULT NULL,
  `ano` DATE NULL DEFAULT NULL,
  `caixa_id` INT(11) NOT NULL,
  `tipo_colecao_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_revista_caixa_idx` (`caixa_id` ASC),
  INDEX `fk_revista_tipo_colecao1_idx` (`tipo_colecao_id` ASC),
  CONSTRAINT `fk_revista_caixa`
    FOREIGN KEY (`caixa_id`)
    REFERENCES `mydb`.`caixa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_revista_tipo_colecao1`
    FOREIGN KEY (`tipo_colecao_id`)
    REFERENCES `mydb`.`tipo_colecao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 0
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`emprestimo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`emprestimo` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `data_emprestimo` DATE NULL DEFAULT NULL,
  `data_devolucao` DATE NULL DEFAULT NULL,
  `amigo_id` INT(11) NOT NULL,
  `revista_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_emprestimo_revista1_idx` (`revista_id` ASC),
  INDEX `fk_emprestimo_amigo1_idx` (`amigo_id` ASC),
  CONSTRAINT `fk_emprestimo_amigo1`
    FOREIGN KEY (`amigo_id`)
    REFERENCES `mydb`.`amigo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_emprestimo_revista1`
    FOREIGN KEY (`revista_id`)
    REFERENCES `mydb`.`revista` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
