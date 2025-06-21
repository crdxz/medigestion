package com.medigestion.state;

import com.medigestion.entity.Campana;

/**
 * PATRÓN ESTADO IMPLEMENTADO:
 * - Funcionalidad: Maneja diferentes comportamientos según el estado de la campaña
 * - Características: Cada estado implementa operaciones específicas
 * - Beneficios: Cambio dinámico de comportamiento, fácil extensión de estados
 */
public interface CampanaState {
    
    /**
     * PATRÓN ESTADO: Operación específica del estado
     * Determina si se puede crear la campaña según su estado actual
     */
    boolean puedeCrear();
    
    /**
     * PATRÓN ESTADO: Operación específica del estado
     * Determina si se puede modificar la campaña según su estado actual
     */
    boolean puedeModificar();
    
    /**
     * PATRÓN ESTADO: Operación específica del estado
     * Determina si se puede eliminar la campaña según su estado actual
     */
    boolean puedeEliminar();
    
    /**
     * PATRÓN ESTADO: Operación específica del estado
     * Obtiene el mensaje de estado para mostrar al usuario
     */
    String getMensajeEstado();
    
    /**
     * PATRÓN ESTADO: Operación específica del estado
     * Valida si las operaciones son permitidas en este estado
     */
    void validarOperacion(String operacion, Campana campana);
} 