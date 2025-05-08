CREATE TABLE IF NOT EXISTS role_type (
     id SERIAL PRIMARY KEY,
     name TEXT NOT NULL UNIQUE
);

INSERT INTO role_type(name)
VALUES ('ADMIN'), ('USER')
    ON CONFLICT (name) DO NOTHING;


CREATE TABLE IF NOT EXISTS users (
     id SERIAL PRIMARY KEY,
     username TEXT NOT NULL UNIQUE,
     password TEXT NOT NULL,
     email TEXT NOT NULL UNIQUE,
     role_id INTEGER REFERENCES role_type(id)
);


CREATE TABLE IF NOT EXISTS subscriptions (
     id SERIAL PRIMARY KEY,
     name TEXT NOT NULL
);


CREATE TABLE IF NOT EXISTS user_subscription (
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    subscription_id INTEGER NOT NULL REFERENCES subscriptions(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, subscription_id)
);

INSERT INTO users (username, password, email, role_id)
VALUES ('admin', '$2a$12$sEro9rIaSELb2Btm4zflO.SaAB5Q.DcNI.2cXi0PwmNGwktR08sCy', 'admin@test.ru', 1);