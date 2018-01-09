-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema SearchStatDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema SearchStatDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `SearchStatDB` DEFAULT CHARACTER SET utf8 ;
USE `SearchStatDB` ;

-- -----------------------------------------------------
-- Table `SearchStatDB`.`Persons`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SearchStatDB`.`Persons` (
  `ID` INT NOT NULL,
  `Name` VARCHAR(2048) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SearchStatDB`.`Keywords`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SearchStatDB`.`Keywords` (
  `ID` INT NOT NULL,
  `Name` VARCHAR(2048) NULL,
  `PersonID` INT NULL,
  PRIMARY KEY (`ID`),
  INDEX `PersonID_idx` (`PersonID` ASC),
  CONSTRAINT `PersonID`
    FOREIGN KEY (`PersonID`)
    REFERENCES `SearchStatDB`.`Persons` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SearchStatDB`.`Sites`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SearchStatDB`.`Sites` (
  `ID` INT NOT NULL,
  `Name` VARCHAR(2048) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SearchStatDB`.`Pages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SearchStatDB`.`Pages` (
  `ID` INT NOT NULL,
  `Url` VARCHAR(2048) NULL,
  `SiteID` INT NULL,
  `FoundDateTIme` DATETIME NULL,
  `LastScanDate` DATETIME NULL,
  PRIMARY KEY (`ID`),
  INDEX `SiteID_idx` (`SiteID` ASC),
  CONSTRAINT `SiteID`
    FOREIGN KEY (`SiteID`)
    REFERENCES `SearchStatDB`.`Sites` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SearchStatDB`.`PersonPageRank`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SearchStatDB`.`PersonPageRank` (
  `PersonID` INT NOT NULL,
  `PageID` INT NOT NULL,
  `Rank` INT NULL,
  PRIMARY KEY (`PersonID`, `PageID`),
  INDEX `Page_idx` (`PageID` ASC),
  CONSTRAINT `Person`
    FOREIGN KEY (`PersonID`)
    REFERENCES `SearchStatDB`.`Persons` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Page`
    FOREIGN KEY (`PageID`)
    REFERENCES `SearchStatDB`.`Pages` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
