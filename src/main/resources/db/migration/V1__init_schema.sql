CREATE TABLE IF NOT EXISTS user_ (
                                     id SERIAL PRIMARY KEY,
                                     email TEXT NOT NULL UNIQUE,
                                     username TEXT NOT NULL UNIQUE,
                                     password TEXT NOT NULL,
                                     roles VARCHAR(255)[]
);



INSERT INTO user_ (email, username, password)
VALUES ('admin@test.ru', 'admin', '$2a$12$sEro9rIaSELb2Btm4zflO.SaAB5Q.DcNI.2cXi0PwmNGwktR08sCy');


CREATE TABLE IF NOT EXISTS subscriptions (
                                             id SERIAL PRIMARY KEY,
                                             name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS user_subscription (
                                                 user_id INTEGER NOT NULL REFERENCES user_(id) ON DELETE CASCADE,
                                                 subscription_id INTEGER NOT NULL REFERENCES subscriptions(id) ON DELETE CASCADE,
                                                 PRIMARY KEY (user_id, subscription_id)
);