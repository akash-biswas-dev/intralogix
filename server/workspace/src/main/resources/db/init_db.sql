DROP TABLE IF EXISTS users_on_workspace;
DROP TABLE IF EXISTS workspaces;

CREATE TABLE workspaces
(
    id             VARCHAR(50) PRIMARY KEY,
    workspace_name VARCHAR(50) NOT NULL,
    description    VARCHAR(1000),
    created_on     DATE        NOT NULL,
    owned_by       VARCHAR(50) NOT NULL,
    UNIQUE (workspace_name, owned_by)
);

CREATE TABLE users_on_workspace
(

    user_id        VARCHAR(100) NOT NULL,
    workspace_name VARCHAR(50)  NOT NULL,
    owned_by       VARCHAR(100) NOT NULL,
    joined_on      DATE         NOT NULL,
    PRIMARY KEY (workspace_name, user_id, owned_by)
);
