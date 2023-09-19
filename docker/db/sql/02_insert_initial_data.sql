INSERT INTO user(id, name, email, password) VALUES (1, 'taro', 'taro@taro.com', 'aaaa');

INSERT INTO category(user_id, name, color) VALUES (1, 'フロントエンド', 1);
INSERT INTO category(user_id, name, color) VALUES (1, 'バックエンド', 2);

INSERT INTO todo(user_id, category_id, title, body, state) VALUES (1, 1, 'UI調節', 'ヘッダーの位置調節', 1);
INSERT INTO todo(user_id, category_id, title, body, state) VALUES (1, 2, 'API', 'Todoを返すAPIを作成', 2);
