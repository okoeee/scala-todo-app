-- group
INSERT INTO `group` (id, name)
VALUES (1, 'テストグループ');

-- group_membership
INSERT INTO `group_membership` (group_id, user_id)
VALUES (1, 1);

-- user
INSERT INTO `user` (id, name, email, password)
VALUES (1, 'taro', 'taro@taro.com', '$2a$10$thTkcDVr1hXw8ryvEZqP4eAt5SdxxYL9ERCea0jztQSV5G707/FRW');

-- category
INSERT INTO `category` (group_id, name, color)
VALUES (1, 'フロントエンド', 1);
INSERT INTO `category` (group_id, name, color)
VALUES (1, 'バックエンド', 2);

-- todo
INSERT INTO `todo` (user_id, category_id, title, body, state)
VALUES (1, 1, 'UI調節', 'ヘッダーの位置調節', 1);
INSERT INTO `todo` (user_id, category_id, title, body, state)
VALUES (1, 2, 'API', 'Todoを返すAPIを作成', 2);
