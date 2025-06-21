package com.medigestion.command;

import com.medigestion.entity.Campana;

/**
 * PATRÓN COMANDO IMPLEMENTADO:
 * - Funcionalidad: Encapsula operaciones sobre campañas como objetos
 * - Características: Permite deshacer operaciones, logging, validación
 * - Beneficios: Desacoplamiento, operaciones reversibles, auditoría
 */
public interface CampanaCommand {
    
    /**
     * PATRÓN COMANDO: Ejecuta la operación encapsulada
     * @return Resultado de la operación
     */
    Campana execute();
    
    /**
     * PATRÓN COMANDO: Deshace la operación ejecutada
     * @return Estado anterior de la campaña
     */
    Campana undo();
    
    /**
     * PATRÓN COMANDO: Obtiene descripción de la operación
     * @return Descripción para logging/auditoría
     */
    String getDescription();
    
    /**
     * PATRÓN COMANDO: Valida si la operación puede ejecutarse
     * @return true si es válida, false en caso contrario
     */
    boolean canExecute();
} 