-- Fix DOCUMENTS table auto increment issue

-- Check current table structure
DESCRIBE DOCUMENTS;

-- Show table creation statement to see current AUTO_INCREMENT setting
SHOW CREATE TABLE DOCUMENTS;

-- If AUTO_INCREMENT is not set, add it:
-- ALTER TABLE DOCUMENTS MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT;

-- If you need to reset AUTO_INCREMENT value:
-- ALTER TABLE DOCUMENTS AUTO_INCREMENT = 1;

-- Verify the fix
-- INSERT INTO DOCUMENTS (file_name, file_type, file_size, status, storage_path, uploaded_at, owner_id) 
-- VALUES ('test.pdf', 'PDF', 1024, 'uploaded', '/tmp/test.pdf', NOW(), 1);

-- Check if the insert worked and ID was auto-generated
-- SELECT * FROM DOCUMENTS WHERE file_name = 'test.pdf';
