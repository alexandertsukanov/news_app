-- Create the articles table
CREATE TABLE IF NOT EXISTS article
(
    id                BIGINT       NOT NULL AUTO_INCREMENT,
    header            VARCHAR(255) NOT NULL,
    short_description VARCHAR(500) NOT NULL,
    text              TEXT         NOT NULL,
    publish_date      DATE         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS author
(
    id                BIGINT       NOT NULL AUTO_INCREMENT,
    name            VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

-- Create the article_author table
CREATE TABLE IF NOT EXISTS article_author
(
    article_id BIGINT NOT NULL,
    author_id  BIGINT NOT NULL,
    PRIMARY KEY (article_id, author_id),
    FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (author_id) REFERENCES author (id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Create the keyword table
CREATE TABLE IF NOT EXISTS keyword
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

-- Create the article_keyword table
CREATE TABLE IF NOT EXISTS article_keyword
(
    article_id BIGINT NOT NULL,
    keyword_id BIGINT NOT NULL,
    PRIMARY KEY (article_id, keyword_id),
    FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (keyword_id) REFERENCES keyword (id) ON DELETE CASCADE ON UPDATE CASCADE
);


