CREATE TABLE IF NOT EXISTS rsvps (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    attending BOOLEAN NOT NULL,
    guests INTEGER NOT NULL,
    song_request TEXT,
    message TEXT
);

CREATE TABLE IF NOT EXISTS guestbook_entries (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    relationship VARCHAR(255),
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS gallery_images (
    id SERIAL PRIMARY KEY,
    url TEXT NOT NULL,
    caption TEXT,
    uploaded_by VARCHAR(255) NOT NULL,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Ensure schema is correct for existing tables
ALTER TABLE rsvps ADD COLUMN IF NOT EXISTS song_request TEXT;
ALTER TABLE rsvps ADD COLUMN IF NOT EXISTS message TEXT;
ALTER TABLE rsvps DROP COLUMN IF EXISTS diet_restrictions;
ALTER TABLE guestbook_entries ADD COLUMN IF NOT EXISTS relationship VARCHAR(255);
