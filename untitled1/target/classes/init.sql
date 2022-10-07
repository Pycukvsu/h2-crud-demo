CREATE SCHEMA IF NOT EXISTS test;
USE test;

CREATE TABLE players (
    playerId INT,
    nickname VARCHAR(50)
);

CREATE TABLE progress(
    id INT,
    playerId INT,
    resourceId INT,
    score INT,
    maxScore INT
);

CREATE TABLE currencies(
    id INT,
    playerId INT,
    resourceId INT,
    name VARCHAR(50),
    count INT
);

CREATE TABLE items(
    id INT,
    playerId INT,
    resourceId INT,
    count INT,
    level INT
);