package com.medigestion.state;

import com.medigestion.entity.Campana;

/**
 * PATRÓN ESTADO: Implementación del estado PENDIENTE
 * - Comportamiento: Campaña recién creada, permite modificaciones
 * - Operaciones: Crear (no), Modificar (sí), Eliminar (sí)
 */
public class PendienteState implements CampanaState {
    
    @Override
    public boolean puedeCrear() {
        // PATRÓN ESTADO: Comportamiento específico del estado PENDIENTE
        // Una campaña pendiente ya existe, no se puede crear otra
        return false;
    }
    
    @Override
    public boolean puedeModificar() {
        // PATRÓN ESTADO: Comportamiento específico del estado PENDIENTE
        // Una campaña pendiente puede ser modificada
        return true;
    }
    
    @Override
    public boolean puedeEliminar() {
        // PATRÓN ESTADO: Comportamiento específico del estado PENDIENTE
        // Una campaña pendiente puede ser eliminada
        return true;
    }
    
    @Override
    public String getMensajeEstado() {
        // PATRÓN ESTADO: Mensaje específico del estado PENDIENTE
        return "La campaña está pendiente de activación";
    }
    
    @Override
    public void validarOperacion(String operacion, Campana campana) {
        // PATRÓN ESTADO: Validación específica del estado PENDIENTE
        switch (operacion.toLowerCase()) {
            case "crear":
                if (!puedeCrear()) {
                    throw new IllegalStateException("No se puede crear una campaña que ya existe");
                }
                break;
            case "modificar":
                if (!puedeModificar()) {
                    throw new IllegalStateException("No se puede modificar una campaña en estado PENDIENTE");
                }
                break;
            case "eliminar":
                if (!puedeEliminar()) {
                    throw new IllegalStateException("No se puede eliminar una campaña en estado PENDIENTE");
                }
                break;
            default:
                throw new IllegalArgumentException("Operación no reconocida: " + operacion);
        }
    }
} 