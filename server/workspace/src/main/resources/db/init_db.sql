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
    id             uuid PRIMARY KEY default gen_random_uuid(),
    workspace_name VARCHAR(50),
    description    VARCHAR(1000),
    owned_by       VARCHAR(100),
    created_on     DATE,
    workspace_type workspace_type NOT NULL
);

CREATE TABLE users_on_workspace
(
    workspace_id uuid,
    user_id      VARCHAR(100),
    joined_on    DATE NOT NULL,
    PRIMARY KEY (workspace_id, user_id),
    FOREIGN KEY (workspace_id) REFERENCES workspaces (id) ON DELETE CASCADE
);

CREATE TABLE groups
(
    group_name   VARCHAR(50),
    workspace_id uuid,
    group_type   group_type NOT NULL,
    created_on   DATE       NOT NULL,
    PRIMARY KEY (group_name, workspace_id),
    FOREIGN KEY (workspace_id) REFERENCES workspaces (id) ON DELETE CASCADE
);