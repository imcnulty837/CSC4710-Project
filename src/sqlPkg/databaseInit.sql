create database if not exists testdb;
use testdb;

drop view if exists coolImages, recentImages, viralImages, topUsers, popularUsers, topTags, positiveUsers, poorImages, inactiveUsers;
drop trigger if exists five_posts_a_day;
drop trigger if exists three_likes_a_day;
drop table if exists ImageTag;
drop table if exists likes;
drop table if exists follows;
drop table if exists Comments;
drop table if exists Image;
drop table if exists tags;
drop table if exists User;

CREATE TABLE if not exists User(
    email VARCHAR(32) NOT NULL,
    firstName VARCHAR(20) NOT NULL,
    lastName VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    birthday DATE NOT NULL,
    gender VARCHAR(10) NOT NULL,
    PRIMARY KEY (email)
);

CREATE TABLE if not exists Image(
	imageId INTEGER NOT NULL auto_increment,
	ts TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	email Varchar(32),
    url VARCHAR(255),
    description VARCHAR(200),
    foreign key (email) references user(email) on delete cascade,
    PRIMARY KEY (imageId)
);

create table if not exists tags(
	tagId integer NOT NULL auto_increment,
    tag varchar(16) unique,
    Primary key (tagId),
    constraint check_lowercase check (lower(tag) = tag)
);

create table if not exists follows(
	followerEmail VARCHAR(32) not null,
    followeeEmail VARCHAR(32) not null,
    FOREIGN KEY (followerEmail) References User(email) on delete cascade,
    FOREIGN KEY (followeeEmail) References User(email) on delete cascade,
    Primary key (followerEmail, followeeEmail),
    constraint self_follow check (FollowerEmail <> FolloweeEmail)
);

Create Table if not exists Comments(
	imageId Integer,
    email VARCHAR(32),
    comment varchar(200),
    FOREIGN KEY (email) REFERENCES User(email) on delete cascade,
    FOREIGN KEY (imageId) References image(imageId) on delete cascade,
    Primary key(email,imageId)
);

create table if not exists ImageTag(
	imageId integer,
    tagId integer,
	FOREIGN KEY (imageId) REFERENCES Image(imageId) on delete cascade,
    FOREIGN KEY (tagId) REFERENCES tags(tagId) on delete cascade,
    primary key(imageId, tagId)
);

create table if not exists likes(
	email  varchar(32),
    imageId integer,
    likeSwitch boolean,
    Foreign key (email) references user(email) on delete cascade,
    Foreign key (imageID) references image(imageid) on delete cascade,
    primary key (email, imageId)
);