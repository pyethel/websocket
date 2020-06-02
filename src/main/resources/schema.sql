
DROP TABLE IF EXISTS "FRIENDSHIP";
CREATE TABLE "FRIENDSHIP"(
  FRIENDSHIP_ID INTEGER AUTO_INCREMENT,
  FRIEND_ID INTEGER NOT NULL,
  USER_ID INTEGER NOT NULL,
  PRIMARY KEY (FRIENDSHIP_ID)
);

DROP TABLE IF EXISTS "USER";
CREATE TABLE "USER"(
  USER_ID INTEGER AUTO_INCREMENT,
  USER_NAME varchar(50) NOT NULL,
  PASSWORD varchar(50) NOT NULL,
  IS_ONLINE INTEGER  DEFAULT 0,
  PICTURE varchar(50) DEFAULT 'images/user/1.jpg',
  PRIMARY KEY (USER_ID)
);



DROP TABLE IF EXISTS "GROUP_REL";
CREATE TABLE "GROUP_REL"(
    GROUP_REL_ID INTEGER AUTO_INCREMENT,
    GROUP_ID INTEGER NOT NULL,
    GROUP_MEMBER_ID INTEGER NOT NULL,
    PRIMARY KEY (GROUP_REL_ID)
);


DROP TABLE IF EXISTS "GROUPS";
CREATE TABLE "GROUPS"(
                       GROUP_ID INTEGER AUTO_INCREMENT,
                       GROUP_NAME varchar(50) NOT NULL,
                       GROUP_PIC varchar(50) DEFAULT 'images/group/3.jpg',
                       GROUP_OWNER_ID INTEGER NOT NULL,
                       PRIMARY KEY (GROUP_ID)
);

DROP TABLE IF EXISTS "MESSAGE";
CREATE TABLE "MESSAGE"(
                         MESSAGE_ID INTEGER AUTO_INCREMENT,
                         SENDER_ID INTEGER NOT NULL,
                         SENDER_NAME varchar(255) NOT NULL,
                         SENDER_PIC varchar(255) NOT NULL,
                         RECEIVER_ID INTEGER NOT NULL,
                         SEND_TIME DATETIME NOT NULL,
                         MSG varchar(255) NOT NULL,
                         TYPE INTEGER NOT NULL,
                         PRIMARY KEY (MESSAGE_ID)
);