-- Primero desconectamos todas las conexiones a la base de datos
SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'medigestion'
AND pid <> pg_backend_pid();

-- Luego eliminamos la base de datos si existe
DROP DATABASE IF EXISTS medigestion;

-- Finalmente creamos la base de datos
CREATE DATABASE medigestion
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Colombia.1252'
    LC_CTYPE = 'Spanish_Colombia.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1; 