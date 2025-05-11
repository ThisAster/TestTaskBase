-- Сначала создаем таблицу role_type
CREATE TABLE IF NOT EXISTS role_type (
                                         id SERIAL PRIMARY KEY,
                                         name TEXT NOT NULL UNIQUE
);

-- Затем создаем таблицу user_ с ссылкой на role_type
CREATE TABLE IF NOT EXISTS user_ (
                                     id SERIAL PRIMARY KEY,
                                     email TEXT NOT NULL UNIQUE,
                                     username TEXT NOT NULL UNIQUE,
                                     password TEXT NOT NULL,
                                     role_id INT,
                                     FOREIGN KEY (role_id) REFERENCES role_type (id)
);

-- Вставляем роли в таблицу role_type
INSERT INTO role_type (name)
VALUES ('ADMIN'), ('SUPERVISOR'), ('USER')
ON CONFLICT (name) DO NOTHING;

-- Создаем другие таблицы
CREATE TABLE IF NOT EXISTS subscriptions (
                                             id SERIAL PRIMARY KEY,
                                             name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS user_subscription (
                                                 user_id INTEGER NOT NULL REFERENCES user_(id) ON DELETE CASCADE,
                                                 subscription_id INTEGER NOT NULL REFERENCES subscriptions(id) ON DELETE CASCADE,
                                                 PRIMARY KEY (user_id, subscription_id)
);

-- Вставляем пользователя с ролью ADMIN
INSERT INTO user_ (email, username, password, role_id)
VALUES ('admin@test.ru', 'admin', '$2a$12$sEro9rIaSELb2Btm4zflO.SaAB5Q.DcNI.2cXi0PwmNGwktR08sCy', 2);
