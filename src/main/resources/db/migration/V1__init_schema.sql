CREATE TABLE IF NOT EXISTS role_type (
                                         id SERIAL PRIMARY KEY,
                                         name TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS user_ (
                                     id SERIAL PRIMARY KEY,
                                     email TEXT NOT NULL UNIQUE,
                                     username TEXT NOT NULL UNIQUE,
                                     password TEXT NOT NULL,
                                     role_id INT,
                                     FOREIGN KEY (role_id) REFERENCES role_type (id)
);

CREATE TABLE IF NOT EXISTS subscriptions (
                                             id SERIAL PRIMARY KEY,
                                             name TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS user_subscription (
                                                 user_id INTEGER NOT NULL REFERENCES user_(id) ON DELETE CASCADE,
                                                 subscription_id INTEGER NOT NULL REFERENCES subscriptions(id) ON DELETE CASCADE,
                                                 PRIMARY KEY (user_id, subscription_id)
);
