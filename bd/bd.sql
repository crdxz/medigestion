-- Crear la tabla de campañas
CREATE TABLE campanas (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(500),
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE,
    estado VARCHAR(50) NOT NULL,
    tipo_campana VARCHAR(50) NOT NULL,
    tipo VARCHAR(100),
    tipo_promocion VARCHAR(100),
    grupo_objetivo VARCHAR(100),
    tema VARCHAR(100)
);

-- Crear la tabla de exámenes de campaña
CREATE TABLE examenes_campana (
    id BIGSERIAL PRIMARY KEY,
    campana_id BIGINT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    fecha_creacion DATE NOT NULL,
    fecha_actualizacion DATE,
    FOREIGN KEY (campana_id) REFERENCES campanas(id) ON DELETE CASCADE
);

-- Crear índices para mejorar el rendimiento
CREATE INDEX idx_campanas_estado ON campanas(estado);
CREATE INDEX idx_campanas_fecha_inicio ON campanas(fecha_inicio);
CREATE INDEX idx_examenes_campana_campana_id ON examenes_campana(campana_id);

-- Insertar algunos datos de ejemplo
INSERT INTO campanas (nombre, descripcion, fecha_inicio, fecha_fin, estado, tipo_campana, tipo, tipo_promocion, grupo_objetivo, tema)
VALUES 
('Campaña de Vacunación COVID-19', 'Campaña masiva de vacunación contra COVID-19', '2024-03-01', '2024-03-31', 'PENDIENTE', 'VACUNACION', 'SALUD PUBLICA', 'PREVENTIVA', 'ADULTOS', 'VACUNACION'),
('Campaña de Detección Temprana', 'Detección temprana de enfermedades crónicas', '2024-04-01', '2024-04-30', 'PENDIENTE', 'DETECCION', 'SALUD PREVENTIVA', 'DIAGNOSTICO', 'ADULTOS MAYORES', 'PREVENCION');

-- Insertar algunos exámenes de ejemplo
INSERT INTO examenes_campana (campana_id, nombre, descripcion, fecha_creacion)
VALUES 
(1, 'Test COVID-19', 'Prueba rápida de COVID-19', CURRENT_DATE),
(1, 'Vacuna COVID-19', 'Aplicación de vacuna COVID-19', CURRENT_DATE),
(2, 'Glucosa en Sangre', 'Medición de niveles de glucosa', CURRENT_DATE),
(2, 'Presión Arterial', 'Medición de presión arterial', CURRENT_DATE);