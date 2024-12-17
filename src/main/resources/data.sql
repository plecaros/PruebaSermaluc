-- Creación de tabla Phone
CREATE TABLE phone (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(15) NOT NULL,
    citycode VARCHAR(5) NOT NULL,
    contrycode VARCHAR(5) NOT NULL
);

-- Creación de tabla User
CREATE TABLE user (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    token VARCHAR(255),
    phones_id BIGINT,
    FOREIGN KEY (phones_id) REFERENCES phone (id)
);
