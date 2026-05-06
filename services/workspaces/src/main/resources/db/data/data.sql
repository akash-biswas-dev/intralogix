INSERT INTO workspaces (id, workspace_name, description, created_on)
VALUES ('w1', 'Workspace 1', 'Desc 1', CURRENT_DATE),
       ('w2', 'Workspace 2', 'Desc 2', CURRENT_DATE),
       ('w3', 'Workspace 3', 'Desc 3', CURRENT_DATE),
       ('w4', 'Workspace 4', 'Desc 4', CURRENT_DATE),
       ('w5', 'Workspace 5', 'Desc 5', CURRENT_DATE),
       ('w6', 'Workspace 6', 'Desc 6', CURRENT_DATE);

INSERT INTO groups (id, group_name, description, created_on)
VALUES ('g1', 'Group 1', 'Group Desc 1', CURRENT_DATE),
       ('g2', 'Group 2', 'Group Desc 2', CURRENT_DATE),
       ('g3', 'Group 3', 'Group Desc 3', CURRENT_DATE),
       ('g4', 'Group 4', 'Group Desc 4', CURRENT_DATE),
       ('g5', 'Group 5', 'Group Desc 5', CURRENT_DATE),
       ('g6', 'Group 6', 'Group Desc 6', CURRENT_DATE);


INSERT INTO users_on_workspaces
(user_id, workspace_id, user_type, joined_on, last_active)
VALUES
-- w1
('u1', 'w1', 'OWNER', CURRENT_DATE, NOW()),
('u2', 'w1', 'MEMBER', CURRENT_DATE, NOW()),
('u3', 'w1', 'MEMBER', CURRENT_DATE, NOW()),

-- w2
('u2', 'w2', 'OWNER', CURRENT_DATE, NOW()),
('u4', 'w2', 'MEMBER', CURRENT_DATE, NOW()),

-- w3
('u1', 'w3', 'OWNER', CURRENT_DATE, NOW()),
('u5', 'w3', 'MEMBER', CURRENT_DATE, NOW()),

-- w4
('u4', 'w4', 'OWNER', CURRENT_DATE, NOW()),
('u1', 'w4', 'MEMBER', CURRENT_DATE, NOW()),

-- w5
('u5', 'w5', 'OWNER', CURRENT_DATE, NOW()),
('u6', 'w5', 'MEMBER', CURRENT_DATE, NOW()),

-- w6
('u6', 'w6', 'OWNER', CURRENT_DATE, NOW());



INSERT INTO groups_on_workspaces
(group_id, workspace_id, created_at)
VALUES
    ('g1', 'w1', CURRENT_DATE),
    ('g2', 'w1', CURRENT_DATE),
    ('g3', 'w2', CURRENT_DATE),
    ('g4', 'w3', CURRENT_DATE),
    ('g5', 'w3', CURRENT_DATE),
    ('g6', 'w3', CURRENT_DATE),
    ('g1', 'w4', CURRENT_DATE),
    ('g2', 'w5', CURRENT_DATE),
    ('g3', 'w6', CURRENT_DATE),
    ('g4', 'w6', CURRENT_DATE);


INSERT INTO users_on_groups
(group_id, user_id, joined_on, left_at)
VALUES
    ('g1', 'u1', CURRENT_DATE, NULL),
    ('g1', 'u2', CURRENT_DATE, NULL),
    ('g2', 'u3', CURRENT_DATE, NULL),
    ('g2', 'u4', CURRENT_DATE, NULL),
    ('g3', 'u5', CURRENT_DATE, NULL),
    ('g3', 'u6', CURRENT_DATE, NULL),
    ('g4', 'u1', CURRENT_DATE, NULL),
    ('g5', 'u2', CURRENT_DATE, NULL),
    ('g6', 'u3', CURRENT_DATE, NULL),
    ('g6', 'u4', CURRENT_DATE, NULL);