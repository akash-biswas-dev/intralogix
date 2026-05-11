-- Create users
CREATE USER dev WITH PASSWORD 'devpassword';
CREATE USER test WITH PASSWORD 'testpassword';

-- Create databases
CREATE DATABASE dev_db OWNER dev;
CREATE DATABASE test_db OWNER test;

-- Optional: grant privileges
GRANT ALL PRIVILEGES ON DATABASE dev_db TO dev;
GRANT ALL PRIVILEGES ON DATABASE test_db TO test;
