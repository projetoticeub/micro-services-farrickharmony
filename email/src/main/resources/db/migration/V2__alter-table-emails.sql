ALTER TABLE emails
  ALTER COLUMN text TYPE oid
  USING text::oid;
