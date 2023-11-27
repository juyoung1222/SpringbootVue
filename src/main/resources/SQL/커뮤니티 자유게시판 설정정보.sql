-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema bedu
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bedu
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bedu` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `bedu` ;

-- -----------------------------------------------------
-- Table `bedu`.`lecture`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bedu`.`lecture` (
  `userId` VARCHAR(100) NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `teacher` VARCHAR(100) NOT NULL,
  `duration` INT NOT NULL,
  `thumbnail` VARCHAR(500) NOT NULL,
  `view` INT NULL DEFAULT '0',
  `likeYn` TINYINT NULL,
  `regiDate` DATE NOT NULL,
  `price` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`userId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS t_bedu_comm (
  COMM_NUM INT not null auto_increment,
  TITLE VARCHAR(100) not NULL,
  USER_ID varchar(100) not null,
  CONTENT varchar(100) not NULL,
  COMM_LIKE_YN int NULL,
  COMM_CNT INT NULL,
  COMM_DATE date not null,
  REG_DATE timestamp not null,
  REG_ID varchar(100) not null,
  PRIMARY KEY (COMM_NUM))
ENGINE = InnoDB;						-- 자유 게시판

CREATE TABLE IF NOT EXISTS t_bedu_qna (
  QNA_BD_NUM INT not null auto_increment,
  TITLE VARCHAR(100) not NULL,
  USER_ID varchar(100) not null,
  CONTENT varchar(100) not NULL,
  QNA_LIKE_YN int NULL,
  QNA_CNT INT NULL,
  DATE date not null,
  REG_DATE timestamp not null,
  REG_ID varchar(45) not null,
  PRIMARY KEY (QNA_BD_NUM))
ENGINE = InnoDB;									-- 질문 답변 게시판

select * from t_bedu_comm;

DROP TABLE t_bedu_qna;
-- insert into (COMM_NUM, TITLE, CONTENT, USER_ID, COMM_DATE, REG_DATE);
insert into t_bedu_comm(USER_ID, title, content, COMM_DATE, REG_DATE, REG_ID)
 values('user', '질문제목', '질문내용', '2023-05-11', '2023-05-11', 'hong59');
 insert into t_bedu_comm(USER_ID, title, content, COMM_DATE, REG_DATE, REG_ID)
 values('hong59', '질문제목', '질문내용', '2023-05-12', '2023-05-12', 'hong59');
 
 insert into t_bedu_comm(USER_ID, title, content, COMM_DATE, REG_DATE, REG_ID)
 values('hong59', '질문제목', '질문내용', '2023-05-12', '2023-05-12', 'hong59');
 
 insert into t_bedu_qna(USER_ID, title, content, COMM_DATE, REG_DATE, REG_ID)
 values('hong59', '질문제목', '질문내용', '2023-05-12', '2023-05-12', 'hong59');
-- -----------------------------------------------------
-- Table `bedu`.`review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bedu`.`review` (
  `userId` VARCHAR(45) NOT NULL,
  `videoLInk` VARCHAR(45) NULL DEFAULT NULL,
  `comment` VARCHAR(45) NULL DEFAULT NULL,
  `writer` VARCHAR(45) NULL DEFAULT NULL,
  `num` INT NULL,
  PRIMARY KEY (`userId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bedu`.`lectureScore`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bedu`.`lectureScore` (
  `num` VARCHAR(45) NOT NULL,
  `userId` VARCHAR(45) NULL,
  `lectureId` VARCHAR(45) NULL,
  `score` VARCHAR(45) NULL,
  `num` INT NULL,
  PRIMARY KEY (`num`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bedu`.`lectureDetail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bedu`.`lectureDetail` (
  `userId` VARCHAR(45) NOT NULL,
  `postId` VARCHAR(45) NULL,
  `title` VARCHAR(45) NULL,
  `times` VARCHAR(45) NULL,
  `num` INT NULL,
  PRIMARY KEY (`userId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bedu`.`userInfo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bedu`.`userInfo` (
  `userId` VARCHAR(10) NOT NULL,
  `password` VARCHAR(2000) NULL,
  `name` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `phoneNumber` VARCHAR(45) NULL,
  `addr` VARCHAR(100) NULL,
  PRIMARY KEY (`userId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bedu`.`mypage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bedu`.`mypage` (
  `userId` VARCHAR(10) NOT NULL,
  `password` VARCHAR(45) NULL,
  `addr` VARCHAR(45) NULL,
  `phoneNumber` VARCHAR(45) NULL,
  `likeYn` TINYINT NULL,
  PRIMARY KEY (`userId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bedu`.`community`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bedu`.`community` (
  `userId` VARCHAR(45) NOT NULL,
  `title` VARCHAR(45) NULL,
  `contents` VARCHAR(1000) NULL,
  `writer` VARCHAR(45) NULL,
  `writeDate` TIMESTAMP NULL,
  `views` INT NULL,
  `num` INT NULL,
  PRIMARY KEY (`userId`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;