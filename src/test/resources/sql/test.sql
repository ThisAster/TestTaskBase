INSERT INTO role_type (name)
VALUES ('ADMIN'), ('SUPERVISOR'), ('USER')
    ON CONFLICT (name) DO NOTHING;

INSERT INTO user_ (email, username, password, role_id)
SELECT 'admin@test.ru', 'admin', '$2a$12$SLqbSCYLtKctGX4ovLuzNeGTo6zV4g0oq1YTX.IoPfHe5UDdM1KDC', rt.id
FROM role_type rt
WHERE rt.name = 'SUPERVISOR'
    ON CONFLICT (email) DO NOTHING;

INSERT INTO user_ (email, username, password, role_id)
SELECT 'user@test.ru', 'user', '$2a$12$SLqbSCYLtKctGX4ovLuzNeGTo6zV4g0oq1YTX.IoPfHe5UDdM1KDC', rt.id
FROM role_type rt
WHERE rt.name = 'SUPERVISOR'
ON CONFLICT (email) DO NOTHING;

INSERT INTO user_ (email, username, password, role_id)
SELECT 'maksim@test.ru', 'maskim', '$2a$12$SLqbSCYLtKctGX4ovLuzNeGTo6zV4g0oq1YTX.IoPfHe5UDdM1KDC', rt.id
FROM role_type rt
WHERE rt.name = 'USER'
ON CONFLICT (email) DO NOTHING;

INSERT INTO subscriptions (name) VALUES
                                         ('Premium Plan'),
                                         ('Newsletter'),
                                         ('Basic Access');

INSERT INTO user_subscription (subscription_id, user_id) VALUES (1, 3);
INSERT INTO user_subscription (subscription_id, user_id) VALUES (2, 3);
INSERT INTO user_subscription (subscription_id, user_id) VALUES (3, 3);
