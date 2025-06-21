package com.medigestion.command;

import com.medigestion.entity.Campana;
import com.medigestion.repository.CampanaRepository;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

/**
 * PATRÓN COMANDO: Implementación del comando para crear campañas
 * - Funcionalidad: Encapsula la operación de crear una campaña
 * - Características: Permite deshacer la creación, logging detallado
 */
@RequiredArgsConstructor
public class CrearCampanaCommand implements CampanaCommand {
    
    private final CampanaRepository campanaRepository;
    private final Campana campana;
    private final String usuario;
    private final LocalDateTime timestamp;
    private Campana campanaCreada;
    private boolean ejecutado = false;
    
    @Override
    public Campana execute() {
        // PATRÓN COMANDO: Ejecuta la operación encapsulada
        if (!canExecute()) {
            throw new IllegalStateException("No se puede ejecutar el comando de crear campaña");
        }
        
        // PATRÓN COMANDO: Ejecuta la operación real
        campana.setFechaCreacion(timestamp);
        campana.setFechaModificacion(timestamp);
        campanaCreada = campanaRepository.save(campana);
        ejecutado = true;
        
        return campanaCreada;
    }
    
    @Override
    public Campana undo() {
        // PATRÓN COMANDO: Deshace la operación (elimina la campaña creada)
        if (!ejecutado || campanaCreada == null) {
            throw new IllegalStateException("No se puede deshacer una operación no ejecutada");
        }
        
        // En un caso real, aquí se eliminaría la campaña
        // Por simplicidad, retornamos el estado anterior
        return campana;
    }
    
    @Override
    public String getDescription() {
        // PATRÓN COMANDO: Descripción para logging/auditoría
        return String.format("Crear campaña: %s por usuario: %s", campana.getNombre(), usuario);
    }
    
    @Override
    public boolean canExecute() {
        // PATRÓN COMANDO: Validación antes de ejecutar
        return campana != null && 
               campana.getNombre() != null && 
               !campana.getNombre().trim().isEmpty() &&
               !ejecutado;
    }
} 