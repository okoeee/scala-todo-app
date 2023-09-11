INSERT INTO category(name, color) VALUES ('フロントエンド', 1);
INSERT INTO category(name, color) VALUES ('バックエンド', 2);

INSERT INTO todo(category_id, title, body, state) VALUES (1, 'UI調節', 'ヘッダーの位置調節', 1);
INSERT INTO todo(category_id, title, body, state) VALUES (2, 'API', 'Todoを返すAPIを作成', 2);
