CREATE TABLE time_entries (
  id         BIGSERIAL,
  project_id BIGINT,
  user_id    BIGINT,
  date       DATE,
  hours      INT,

  PRIMARY KEY (id)
)
