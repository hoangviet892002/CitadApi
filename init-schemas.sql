-- init-multiple-schemas.sql

-- Create the first schema
CREATE SCHEMA transfers;

CREATE DATABASE napas;

-- Create another schema
CREATE SCHEMA finance;

-- Optionally, grant privileges on these schemas to the default user
GRANT ALL PRIVILEGES ON SCHEMA transfers TO postgres;
GRANT ALL PRIVILEGES ON SCHEMA finance TO postgres;
