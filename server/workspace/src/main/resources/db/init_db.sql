DROP TABLE IF EXISTS users_on_workspace;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS workspaces;
DROP TYPE IF EXISTS workspace_type;
DROP TYPE IF EXISTS group_type;

CREATE TYPE workspace_type AS ENUM (
    'IT',
    'ELECTRONICS',
    'MEDICAL',
    'IMPORTS_EXPORTS'
    );
CREATE TYPE group_type AS ENUM (
    'MANAGEMENT',
    'SERVICE',
    'MAINTENANCE',
    'DEVELOPMENT'
    );


CREATE TABLE workspaces
(
    owned_by       VARCHAR(100) NOT NULL ,
    workspace_name VARCHAR(50) NOT NULL ,
    description    VARCHAR(1000),
    created_on     DATE,
    workspace_type workspace_type NOT NULL,
    PRIMARY KEY (owned_by,workspace_name)
);

CREATE TABLE users_on_workspace
(

    user_id       VARCHAR(100) NOT NULL ,
    workspace_name VARCHAR(50) NOT NULL ,
    owned_by      VARCHAR(100) NOT NULL ,
    joined_on    DATE NOT NULL,
    PRIMARY KEY (workspace_name, user_id, owned_by)
);

CREATE TABLE groups
(
    group_name   VARCHAR(50) NOT NULL ,
    workspace_name VARCHAR(50) NOT NULL ,
    owned_by VARCHAR(50) NOT NULL ,
    group_type   group_type NOT NULL,
    created_on   DATE       NOT NULL,
    PRIMARY KEY (group_name, workspace_name,owned_by)
);