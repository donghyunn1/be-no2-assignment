CREATE TABLE author (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE TABLE schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    todo VARCHAR(255) NOT NULL,
    author_id BIGINT NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    CONSTRAINT fk_schedule_author
        FOREIGN KEY (author_id) REFERENCES author(id)
        ON DELETE CASCADE
);