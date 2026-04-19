-- init.sql

-- 1. Create databases
CREATE DATABASE workspaces_db;
CREATE DATABASE access_manager_db;

-- 2. Create user
CREATE USER dev WITH PASSWORD 'password';

-- 3. Grant database-level privileges
GRANT ALL PRIVILEGES ON DATABASE workspaces_db TO dev;
GRANT ALL PRIVILEGES ON DATABASE access_manager_db TO dev;