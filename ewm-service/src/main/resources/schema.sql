CREATE TABLE IF NOT EXISTS categories (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  name VARCHAR(255) NOT NULL,
  CONSTRAINT pk_category PRIMARY KEY (id),
  CONSTRAINT UQ_CATEGORY_NAME UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS users (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(512) NOT NULL,
  CONSTRAINT pk_user PRIMARY KEY (id),
  CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS locations (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  lat FLOAT NOT NULL,
  lon FLOAT NOT NULL,
  CONSTRAINT pk_location PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS events (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  annotation VARCHAR(2000) NOT NULL,
  category_id BIGINT NOT NULL,
  created_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  description VARCHAR(7000) NOT NULL,
  event_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  initiator_id BIGINT NOT NULL,
  location_id BIGINT NOT NULL,
  paid BOOLEAN NOT NULL,
  participant_limit BIGINT NOT NULL,
  published_on TIMESTAMP WITHOUT TIME ZONE,
  request_moderation BOOLEAN NOT NULL,
  state VARCHAR(40),
  title VARCHAR(120),
  CONSTRAINT pk_event PRIMARY KEY (id),
  CONSTRAINT FK_EVENT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id),
  CONSTRAINT FK_EVENT_ON_INITIATOR FOREIGN KEY (initiator_id) REFERENCES users (id),
  CONSTRAINT FK_EVENT_ON_LOCATION FOREIGN KEY (location_id) REFERENCES locations (id),
  CONSTRAINT UQ_EVENT_TITLE UNIQUE (title)
);

CREATE TABLE IF NOT EXISTS participation_requests (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  event_id BIGINT NOT NULL,
  requester_id BIGINT NOT NULL,
  status VARCHAR(40),
  CONSTRAINT pk_participation_request PRIMARY KEY (id),
  CONSTRAINT FK_PARTICIPATION_REQUEST_ON_EVENT FOREIGN KEY (event_id) REFERENCES events (id),
  CONSTRAINT FK_PARTICIPATION_REQUEST_ON_REQUESTER FOREIGN KEY (requester_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS compilations (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  pinned BOOLEAN NOT NULL,
  title VARCHAR(500) NOT NULL,
  CONSTRAINT pk_compilation PRIMARY KEY (id),
  CONSTRAINT UQ_COMPILATION_TITLE UNIQUE (title)
);

CREATE TABLE IF NOT EXISTS compilations_events (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  compilation_id BIGINT NOT NULL,
  event_id BIGINT NOT NULL,
  CONSTRAINT pk_compilation_event PRIMARY KEY (id),
  CONSTRAINT FK_COMPILATION_EVENT_ON_COMPILATION FOREIGN KEY (compilation_id) REFERENCES compilations ON DELETE CASCADE,
  CONSTRAINT FK_COMPILATION_EVENT_ON_EVENT FOREIGN KEY (event_id) REFERENCES events
);
