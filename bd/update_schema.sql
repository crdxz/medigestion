-- Crear tabla de doctores
CREATE TABLE IF NOT EXISTS doctores (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    licencia VARCHAR(50) NOT NULL UNIQUE,
    especialidad VARCHAR(100) NOT NULL
);

-- Crear tabla de empresas
CREATE TABLE IF NOT EXISTS empresas (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    nit VARCHAR(20) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- Crear tabla de empleados
CREATE TABLE IF NOT EXISTS empleados (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    cargo VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    empresa_id INTEGER NOT NULL REFERENCES empresas(id)
);

-- Actualizar tabla de campañas
ALTER TABLE campanas
ADD COLUMN doctor_id INTEGER REFERENCES doctores(id),
ADD COLUMN empresa_id INTEGER REFERENCES empresas(id),
ADD COLUMN coordinador_id INTEGER REFERENCES empleados(id);

-- Crear tabla de resultados de campaña
CREATE TABLE IF NOT EXISTS resultados_campana (
    id SERIAL PRIMARY KEY,
    campana_id INTEGER NOT NULL REFERENCES campanas(id),
    descripcion TEXT NOT NULL,
    fecha_registro TIMESTAMP NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    observaciones TEXT,
    doctor_id INTEGER NOT NULL REFERENCES doctores(id)
);

-- Crear tabla de acciones de campaña
CREATE TABLE IF NOT EXISTS acciones_campana (
    id SERIAL PRIMARY KEY,
    campana_id INTEGER NOT NULL REFERENCES campanas(id),
    descripcion TEXT NOT NULL,
    fecha_accion TIMESTAMP NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    observaciones TEXT,
    doctor_id INTEGER NOT NULL REFERENCES doctores(id),
    empleado_id INTEGER NOT NULL REFERENCES empleados(id)
);

-- Crear índices para mejorar el rendimiento
CREATE INDEX idx_campanas_doctor ON campanas(doctor_id);
CREATE INDEX idx_campanas_empresa ON campanas(empresa_id);
CREATE INDEX idx_campanas_coordinador ON campanas(coordinador_id);
CREATE INDEX idx_resultados_campana ON resultados_campana(campana_id);
CREATE INDEX idx_acciones_campana ON acciones_campana(campana_id);
CREATE INDEX idx_empleados_empresa ON empleados(empresa_id); 