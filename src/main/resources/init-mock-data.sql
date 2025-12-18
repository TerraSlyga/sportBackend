-- Mock data initialization script for PostgreSQL
-- Insert event_state values
INSERT INTO event_state (event_state) VALUES ('UPCOMING') ON CONFLICT DO NOTHING;
INSERT INTO event_state (event_state) VALUES ('ACTIVE') ON CONFLICT DO NOTHING;
INSERT INTO event_state (event_state) VALUES ('COMPLETED') ON CONFLICT DO NOTHING;
INSERT INTO event_state (event_state) VALUES ('CANCELLED') ON CONFLICT DO NOTHING;

-- Insert event_type values
INSERT INTO event_type (name, description) VALUES ('CS:GO', 'Counter-Strike: Global Offensive Tournament') ON CONFLICT DO NOTHING;
INSERT INTO event_type (name, description) VALUES ('Valorant', 'Valorant Competitive Tournament') ON CONFLICT DO NOTHING;
INSERT INTO event_type (name, description) VALUES ('Rocket League', 'Rocket League Esports Tournament') ON CONFLICT DO NOTHING;
INSERT INTO event_type (name, description) VALUES ('Dota 2', 'Dota 2 International Tournament') ON CONFLICT DO NOTHING;
INSERT INTO event_type (name, description) VALUES ('League of Legends', 'League of Legends Worlds Tournament') ON CONFLICT DO NOTHING;
INSERT INTO event_type (name, description) VALUES ('Overwatch 2', 'Overwatch 2 Esports Tournament') ON CONFLICT DO NOTHING;

-- Insert mock users (Organizers)
INSERT INTO "user" (username, email, password_hash, nickname) 
VALUES ('admin_org1', 'org1@example.com', '$2a$10$YourHashedPasswordHere1', 'AdminOrg1') 
ON CONFLICT (username) DO NOTHING;

INSERT INTO "user" (username, email, password_hash, nickname) 
VALUES ('admin_org2', 'org2@example.com', '$2a$10$YourHashedPasswordHere2', 'AdminOrg2') 
ON CONFLICT (username) DO NOTHING;

INSERT INTO "user" (username, email, password_hash, nickname) 
VALUES ('admin_org3', 'org3@example.com', '$2a$10$YourHashedPasswordHere3', 'AdminOrg3') 
ON CONFLICT (username) DO NOTHING;

-- Insert mock events (Recommended)
INSERT INTO event (name, description, organizerid, event_stateid, event_typeid) 
VALUES (
  'Weekly CS:GO Cup',
  'Weekly competitive CS:GO tournament for all skill levels',
  (SELECT userid FROM "user" WHERE username = 'admin_org1'),
  (SELECT event_stateid FROM event_state WHERE event_state = 'UPCOMING'),
  (SELECT event_typeid FROM event_type WHERE name = 'CS:GO')
);

INSERT INTO event (name, description, organizerid, event_stateid, event_typeid) 
VALUES (
  'Valorant League',
  'Professional Valorant league with prize pool',
  (SELECT userid FROM "user" WHERE username = 'admin_org2'),
  (SELECT event_stateid FROM event_state WHERE event_state = 'ACTIVE'),
  (SELECT event_typeid FROM event_type WHERE name = 'Valorant')
);

INSERT INTO event (name, description, organizerid, event_stateid, event_typeid) 
VALUES (
  'Rocket League Open',
  'Open tournament for Rocket League players worldwide',
  (SELECT userid FROM "user" WHERE username = 'admin_org3'),
  (SELECT event_stateid FROM event_state WHERE event_state = 'UPCOMING'),
  (SELECT event_typeid FROM event_type WHERE name = 'Rocket League')
);

INSERT INTO event (name, description, organizerid, event_stateid, event_typeid) 
VALUES (
  'Valorant Champions',
  'Elite Valorant championship tournament',
  (SELECT userid FROM "user" WHERE username = 'admin_org1'),
  (SELECT event_stateid FROM event_state WHERE event_state = 'UPCOMING'),
  (SELECT event_typeid FROM event_type WHERE name = 'Valorant')
);

INSERT INTO event (name, description, organizerid, event_stateid, event_typeid) 
VALUES (
  'Dota 2 Championship',
  'International Dota 2 championship with teams worldwide',
  (SELECT userid FROM "user" WHERE username = 'admin_org2'),
  (SELECT event_stateid FROM event_state WHERE event_state = 'UPCOMING'),
  (SELECT event_typeid FROM event_type WHERE name = 'Dota 2')
);

-- Insert mock events (Not Recommended)
INSERT INTO event (name, description, organizerid, event_stateid, event_typeid) 
VALUES (
  'Counter-Strike 2 Open',
  'CS2 regional tournament for Europe',
  (SELECT userid FROM "user" WHERE username = 'admin_org3'),
  (SELECT event_stateid FROM event_state WHERE event_state = 'UPCOMING'),
  (SELECT event_typeid FROM event_type WHERE name = 'CS:GO')
);

INSERT INTO event (name, description, organizerid, event_stateid, event_typeid) 
VALUES (
  'League of Legends Cup',
  'LoL tournament for emerging talent',
  (SELECT userid FROM "user" WHERE username = 'admin_org1'),
  (SELECT event_stateid FROM event_state WHERE event_state = 'UPCOMING'),
  (SELECT event_typeid FROM event_type WHERE name = 'League of Legends')
);

INSERT INTO event (name, description, organizerid, event_stateid, event_typeid) 
VALUES (
  'Overwatch 2 Series',
  'OW2 competitive series for teams',
  (SELECT userid FROM "user" WHERE username = 'admin_org2'),
  (SELECT event_stateid FROM event_state WHERE event_state = 'ACTIVE'),
  (SELECT event_typeid FROM event_type WHERE name = 'Overwatch 2')
);

-- Optional: Add admin user with ADMIN role (if role_to_user table is needed)
-- INSERT INTO "user" (username, email, password_hash, nickname) 
-- VALUES ('admin', 'admin@example.com', '$2a$10$YourHashedPasswordHere', 'Administrator') 
-- ON CONFLICT (username) DO NOTHING;
