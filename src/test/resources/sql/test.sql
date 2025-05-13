INSERT INTO role_type (name)
VALUES ('ADMIN'), ('SUPERVISOR'), ('USER')
    ON CONFLICT (name) DO NOTHING;

INSERT INTO user_ (email, username, password, role_id)
SELECT 'admin@test.ru', 'admin', '$2a$12$SLqbSCYLtKctGX4ovLuzNeGTo6zV4g0oq1YTX.IoPfHe5UDdM1KDC', rt.id
FROM role_type rt
WHERE rt.name = 'ADMIN'
    ON CONFLICT DO NOTHING;