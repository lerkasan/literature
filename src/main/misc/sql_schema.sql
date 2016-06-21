
CREATE TABLE user_group (
                id TINYINT AUTO_INCREMENT NOT NULL,
                name VARCHAR(255) NOT NULL,
                PRIMARY KEY (id)
);


CREATE TABLE language (
                id SMALLINT AUTO_INCREMENT NOT NULL,
                name VARCHAR(255) NOT NULL,
                PRIMARY KEY (id)
);


CREATE UNIQUE INDEX language_idx
 ON language
 ( name );

CREATE TABLE country (
                id SMALLINT AUTO_INCREMENT NOT NULL,
                name VARCHAR(255) NOT NULL,
                PRIMARY KEY (id)
);


CREATE UNIQUE INDEX country_idx
 ON country
 ( name );

CREATE TABLE subject (
                id INT AUTO_INCREMENT NOT NULL,
                name VARCHAR(255) NOT NULL,
                PRIMARY KEY (id)
);


CREATE UNIQUE INDEX subject_idx
 ON subject
 ( name );

CREATE TABLE user (
                id INT AUTO_INCREMENT NOT NULL,
                username VARCHAR(255) NOT NULL,
                password VARCHAR(255) NOT NULL,
                givenName VARCHAR(255) NOT NULL,
                familyName VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL,
                birthday DATE NOT NULL,
                registrationDate DATE NOT NULL,
                countryId SMALLINT,
                userGroupId TINYINT NOT NULL,
                PRIMARY KEY (id)
);


CREATE UNIQUE INDEX user_idx
 ON user
 ( username );

CREATE UNIQUE INDEX user_idx1
 ON user
 ( email );

CREATE TABLE resource (
                id INT AUTO_INCREMENT NOT NULL,
                addedBy INT,
                name VARCHAR(255) NOT NULL,
                url VARCHAR(255) NOT NULL,
                domain VARCHAR(255) NOT NULL,
                parameterFormat VARCHAR(255),
                responseFormat VARCHAR(25) NOT NULL,
                apiKey VARCHAR(255),
                searchEngineKey VARCHAR(255),
                PRIMARY KEY (id)
);


CREATE TABLE item_to_read (
                id INT AUTO_INCREMENT NOT NULL,
                title VARCHAR(1024) NOT NULL,
                subjectId INT,
                contents LONGTEXT,
                itemType VARCHAR(255),
                dtype VARCHAR(50),
                text LONGTEXT,
                accessType VARCHAR(50),
                languageId SMALLINT,
                url VARCHAR(512) NOT NULL,
                linkToFile VARCHAR(512),
                publishDate DATE,
                keywords VARCHAR(1024),
                visible BOOLEAN DEFAULT true NOT NULL,
                addedBy INT,
                addedAt DATETIME NOT NULL,
                timesViewed INT,
                timesDownloaded INT,
                resourceId INT,
                PRIMARY KEY (id)
);


CREATE TABLE literature (
                id INT AUTO_INCREMENT NOT NULL,
                year SMALLINT NOT NULL,
                publishing VARCHAR(255),
                volume SMALLINT,
                pages SMALLINT,
                isbn VARCHAR(15),
                issn VARCHAR(255),
                doi VARCHAR(255),
                issueOrEditionNumber VARCHAR(255),
                PRIMARY KEY (id)
);


CREATE TABLE category (
                id INT AUTO_INCREMENT NOT NULL,
                name VARCHAR(255) NOT NULL,
                userId INT,
                PRIMARY KEY (id)
);


CREATE UNIQUE INDEX category_idx
 ON category
 ( name );

CREATE TABLE user_categories (
                itemToReadId INT NOT NULL,
                categoryId INT NOT NULL,
                PRIMARY KEY (itemToReadId, categoryId)
);


CREATE TABLE comment (
                id INT AUTO_INCREMENT NOT NULL,
                userId INT NOT NULL,
                itemToReadId INT NOT NULL,
                text LONGBLOB NOT NULL,
                timespamp DATETIME NOT NULL,
                PRIMARY KEY (id)
);


CREATE TABLE author (
                id INT AUTO_INCREMENT NOT NULL,
                givenName VARCHAR(255) NOT NULL,
                familyName VARCHAR(255) NOT NULL,
                PRIMARY KEY (id)
);


CREATE TABLE item_authors (
                itemToReadId INT NOT NULL,
                authorId INT NOT NULL,
                PRIMARY KEY (itemToReadId, authorId)
);


ALTER TABLE user ADD CONSTRAINT user_group_user_fk
FOREIGN KEY (userGroupId)
REFERENCES user_group (id)
ON DELETE RESTRICT
ON UPDATE NO ACTION;

ALTER TABLE item_to_read ADD CONSTRAINT language_reading_item_fk
FOREIGN KEY (languageId)
REFERENCES language (id)
ON DELETE SET NULL
ON UPDATE NO ACTION;

ALTER TABLE user ADD CONSTRAINT country_user_fk
FOREIGN KEY (countryId)
REFERENCES country (id)
ON DELETE SET NULL
ON UPDATE NO ACTION;

ALTER TABLE item_to_read ADD CONSTRAINT subject_reading_item_fk
FOREIGN KEY (subjectId)
REFERENCES subject (id)
ON DELETE SET NULL
ON UPDATE NO ACTION;

ALTER TABLE comment ADD CONSTRAINT user_comment_fk
FOREIGN KEY (userId)
REFERENCES user (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE category ADD CONSTRAINT user_user_defined_category_fk
FOREIGN KEY (userId)
REFERENCES user (id)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE item_to_read ADD CONSTRAINT user_item_to_read_fk
FOREIGN KEY (addedBy)
REFERENCES user (id)
ON DELETE SET NULL
ON UPDATE NO ACTION;

ALTER TABLE resource ADD CONSTRAINT user_resource_fk
FOREIGN KEY (addedBy)
REFERENCES user (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE item_to_read ADD CONSTRAINT resource_itemtoread_fk
FOREIGN KEY (resourceId)
REFERENCES resource (id)
ON DELETE SET NULL
ON UPDATE NO ACTION;

ALTER TABLE item_authors ADD CONSTRAINT reading_item_authors_fk
FOREIGN KEY (itemToReadId)
REFERENCES item_to_read (id)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE literature ADD CONSTRAINT reading_item_literature_fk
FOREIGN KEY (id)
REFERENCES item_to_read (id)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE comment ADD CONSTRAINT reading_item_comment_fk
FOREIGN KEY (itemToReadId)
REFERENCES item_to_read (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE user_categories ADD CONSTRAINT reading_item_categories_fk
FOREIGN KEY (itemToReadId)
REFERENCES item_to_read (id)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE user_categories ADD CONSTRAINT category_categories_fk
FOREIGN KEY (categoryId)
REFERENCES category (id)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE item_authors ADD CONSTRAINT author_authors_fk
FOREIGN KEY (authorId)
REFERENCES author (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;
