-- Limpiar tablas existentes (en orden inverso a las dependencias)
TRUNCATE TABLE acciones_campana CASCADE;
TRUNCATE TABLE resultados_campana CASCADE;
TRUNCATE TABLE examenes_campana CASCADE;
TRUNCATE TABLE campanas CASCADE;
TRUNCATE TABLE empleados CASCADE;
TRUNCATE TABLE doctores CASCADE;
TRUNCATE TABLE empresas CASCADE;

-- Insertar empresas de ejemplo
INSERT INTO empresas (id, nombre, nit, direccion, telefono, email) VALUES
(1, 'Clínica San José', '900123456-7', 'Calle 123 #45-67', '601-1234567', 'contacto@clinicasanjose.com'),
(2, 'Hospital Central', '900765432-1', 'Avenida Principal #89-01', '601-7654321', 'info@hospitalcentral.com');

-- Insertar doctores de ejemplo
INSERT INTO doctores (id, nombre, apellido, licencia, especialidad) VALUES
(1, 'Juan', 'Pérez', 'MED123', 'Medicina General'),
(2, 'María', 'González', 'MED456', 'Pediatría'),
(3, 'Carlos', 'Rodríguez', 'MED789', 'Medicina Interna');

-- Insertar empleados de ejemplo
INSERT INTO empleados (id, nombre, apellido, cargo, email, telefono, empresa_id) VALUES
(1, 'Ana', 'Martínez', 'Coordinadora de Salud', 'ana.martinez@clinicasanjose.com', '300-1112233', 1),
(2, 'Pedro', 'Sánchez', 'Gerente de Operaciones', 'pedro.sanchez@hospitalcentral.com', '300-4445566', 2);

-- Insertar campañas de ejemplo
INSERT INTO campanas (id, nombre, descripcion, fecha_inicio, fecha_fin, estado, tipo_campana, doctor_id, empresa_id, coordinador_id) VALUES
(1, 'Campaña de Vacunación COVID-19', 'Campaña de vacunación contra COVID-19 para empleados', CURRENT_DATE, CURRENT_DATE + INTERVAL '30 days', 'PENDIENTE', 'VACUNACION', 1, 1, 1),
(2, 'Campaña de Detección Temprana', 'Campaña de exámenes preventivos', CURRENT_DATE, CURRENT_DATE + INTERVAL '60 days', 'PENDIENTE', 'DETECCION', 2, 2, 2);

-- Insertar resultados de ejemplo
INSERT INTO resultados_campana (campana_id, descripcion, fecha_registro, tipo, observaciones, doctor_id) VALUES
(1, 'Resultado de prueba COVID-19', CURRENT_TIMESTAMP, 'NEGATIVO', 'Paciente asintomático', 1),
(2, 'Resultado de examen de sangre', CURRENT_TIMESTAMP, 'POSITIVO', 'Se requiere seguimiento', 2);

-- Insertar acciones de ejemplo
INSERT INTO acciones_campana (campana_id, descripcion, fecha_accion, tipo, observaciones, doctor_id, empleado_id) VALUES
(1, 'Aplicación de vacuna COVID-19', CURRENT_TIMESTAMP, 'VACUNACION', 'Primera dosis aplicada', 1, 1),
(2, 'Toma de muestra de sangre', CURRENT_TIMESTAMP, 'EXAMEN', 'Muestra enviada al laboratorio', 2, 2); 