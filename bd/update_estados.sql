-- Actualizar los estados de PLANIFICADA a PENDIENTE
UPDATE campanas 
SET estado = 'PENDIENTE' 
WHERE estado = 'PLANIFICADA'; 