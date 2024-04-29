-- group
INSERT INTO `group` (id, name)
VALUES (1, 'テストグループ');

-- user
INSERT INTO `user` (id, name, email, password) VALUES
(1, 'taro', 'taro@gmail.com', '$2a$10$thTkcDVr1hXw8ryvEZqP4eAt5SdxxYL9ERCea0jztQSV5G707/FRW'),
(2, 'hanako', 'hanako@gmail.com', '$2a$10$thTkcDVr1hXw8ryvEZqP4eAt5SdxxYL9ERCea0jztQSV5G707/FRW');

-- group_membership
INSERT INTO `group_membership` (group_id, user_id)
VALUES (1, 1);

-- category
INSERT INTO `category` (id, group_id, name, color) VALUES
(1, 1, 'フロントエンド', 1),
(2, 1, 'バックエンド', 2);

-- todo
INSERT INTO `todo` (id, group_id, category_id, title, body, state) VALUES
(1, 1, 1, 'UI調節', 'ヘッダーの位置調節', 1),
(2, 1, 2, 'API', 'Todoを返すAPIを作成', 2);
