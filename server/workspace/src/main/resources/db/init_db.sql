DROP TABLE IF EXISTS users_on_workspaces;
DROP TABLE IF EXISTS workspaces;

CREATE TABLE workspaces
(
    id             VARCHAR(50) PRIMARY KEY,
    workspace_name VARCHAR(50) NOT NULL,
    description    VARCHAR(1000),
    created_on     DATE        NOT NULL
);

CREATE TABLE users_on_workspaces
(

    user_id      VARCHAR(100) NOT NULL,
    workspace_id VARCHAR(50)  NOT NULL,
    user_type    VARCHAR(10)  NOT NULL,
    joined_on    DATE         NOT NULL
);
