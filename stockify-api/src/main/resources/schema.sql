-- Crear tabla SUBSCRIPTION_PLANS
-- DROP TABLE IF EXISTS SUBSCRIPTION_PLANS;
CREATE TABLE SUBSCRIPTION_PLANS (
ID INT NOT NULL AUTO_INCREMENT,
NAME VARCHAR(255) NOT NULL,
PRICE DOUBLE NOT NULL,
PRIMARY KEY (ID)
);

-- Crear tabla USERS
-- DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS (
ID INT NOT NULL AUTO_INCREMENT,
NAME VARCHAR(255) NOT NULL,
EMAIL VARCHAR(255) NOT NULL,
PHONE VARCHAR(255) NOT NULL,
PASSWORD VARCHAR(255) NOT NULL,
SUBSCRIPTION_PLAN INT NOT NULL,
PRIMARY KEY (ID),
CONSTRAINT UNIQUE_EMAIL UNIQUE (EMAIL),
FOREIGN KEY (SUBSCRIPTION_PLAN) REFERENCES SUBSCRIPTION_PLANS(ID)
);

-- Crear tabla SIGNED_MESSAGES
-- DROP TABLE IF EXISTS SIGNED_MESSAGES;
CREATE TABLE SIGNED_MESSAGES (
ID INT NOT NULL AUTO_INCREMENT,
USER_ID INT NOT NULL,
MESSAGE VARCHAR(255) NOT NULL,
TIMESTAMP TIMESTAMP NOT NULL,
CONVERSATION_ID INT NOT NULL,
PRIMARY KEY (ID),
FOREIGN KEY (USER_ID) REFERENCES USERS(ID)
);

-- Crear tabla UNSIGNED_MESSAGES
-- DROP TABLE IF EXISTS UNSIGNED_MESSAGES;
CREATE TABLE UNSIGNED_MESSAGES (
ID INT NOT NULL AUTO_INCREMENT,
NAME VARCHAR(255) NOT NULL,
EMAIL VARCHAR(255) NOT NULL,
MESSAGE VARCHAR(255) NOT NULL,
TIMESTAMP TIMESTAMP NOT NULL,
PRIMARY KEY (ID)
);


-- Crear tabla PORTFOLIO_MOVEMENTS
-- DROP TABLE IF EXISTS PORTFOLIO_MOVEMENTS;
CREATE TABLE PORTFOLIO_MOVEMENTS (
ID INT NOT NULL AUTO_INCREMENT,
USER_ID INT NOT NULL,
TICKER VARCHAR(255) NOT NULL,
QUANTITY INT NOT NULL,
PRICE DOUBLE NOT NULL,
DATE DATE NOT NULL,
PRIMARY KEY (ID),
FOREIGN KEY (USER_ID) REFERENCES USERS(ID)
);


-- Create view
CREATE VIEW SOCIAL AS
SELECT 
    U.NAME AS USER_NAME,
    U.EMAIL AS USER_EMAIL,
    SP.NAME AS SUBSCRIPTION_PLAN,
    COUNT(PM.USER_ID) AS PORTFOLIO_MOVEMENTS
FROM 
    USERS U
INNER JOIN 
    SUBSCRIPTION_PLANS SP ON U.SUBSCRIPTION_PLAN = SP.ID
LEFT JOIN 
    PORTFOLIO_MOVEMENTS PM ON U.ID = PM.USER_ID
GROUP BY 
    U.NAME,
    U.EMAIL,
    SP.NAME;
