package com.medigestion.state;

import com.medigestion.entity.Campana;

/**
 * PATRÓN ESTADO: Implementación del estado ACTIVA
 * - Comportamiento: Campaña en ejecución, operaciones limitadas
 * - Operaciones: Crear (no), Modificar (limitado), Eliminar (no)
 */
public class ActivaState implements CampanaState {
    
    @Override
    public boolean puedeCrear() {
        // PATRÓN ESTADO: Comportamiento específico del estado ACTIVA
        // Una campaña activa ya existe, no se puede crear otra
        return false;
    }
    
    @Override
    public boolean puedeModificar() {
        // PATRÓN ESTADO: Comportamiento específico del estado ACTIVA
        // Una campaña activa puede ser modificada con restricciones
        return true;
    }
    
    @Override
    public boolean puedeEliminar() {
        // PATRÓN ESTADO: Comportamiento específico del estado ACTIVA
        // Una campaña activa NO puede ser eliminada
        return false;
    }
    
    @Override
    public String getMensajeEstado() {
        // PATRÓN ESTADO: Mensaje específico del estado ACTIVA
        return "La campaña está activa y en ejecución";
    }
    
    @Override
    public void validarOperacion(String operacion, Campana campana) {
        // PATRÓN ESTADO: Validación específica del estado ACTIVA
        switch (operacion.toLowerCase()) {
            case "crear":
                if (!puedeCrear()) {
                    throw new IllegalStateException("No se puede crear una campaña que ya está activa");
                }
                break;
            case "modificar":
                if (!puedeModificar()) {
                    throw new IllegalStateException("No se puede modificar una campaña en estado ACTIVA");
                }
                // Validaciones adicionales para campañas activas
                if (campana.getFechaFin().isBefore(java.time.LocalDate.now())) {
                    throw new IllegalStateException("No se puede modificar una campaña que ya expiró");
                }
                break;
            case "eliminar":
                if (!puedeEliminar()) {
                    throw new IllegalStateException("No se puede eliminar una campaña en estado ACTIVA");
                }
                break;
            default:
                throw new IllegalArgumentException("Operación no reconocida: " + operacion);
        }
    }
} 