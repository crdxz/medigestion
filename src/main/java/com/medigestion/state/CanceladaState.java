package com.medigestion.state;

import com.medigestion.entity.Campana;

/**
 * PATRÓN ESTADO: Implementación del estado CANCELADA
 * - Comportamiento: Campaña cancelada, solo lectura
 * - Operaciones: Crear (no), Modificar (no), Eliminar (sí)
 */
public class CanceladaState implements CampanaState {
    
    @Override
    public boolean puedeCrear() {
        return false;
    }
    
    @Override
    public boolean puedeModificar() {
        return false;
    }
    
    @Override
    public boolean puedeEliminar() {
        return true;
    }
    
    @Override
    public String getMensajeEstado() {
        return "La campaña ha sido cancelada";
    }
    
    @Override
    public void validarOperacion(String operacion, Campana campana) {
        switch (operacion.toLowerCase()) {
            case "crear":
            case "modificar":
                throw new IllegalStateException("No se pueden realizar operaciones en una campaña CANCELADA");
            case "eliminar":
                if (!puedeEliminar()) {
                    throw new IllegalStateException("No se puede eliminar esta campaña cancelada");
                }
                break;
            default:
                throw new IllegalArgumentException("Operación no reconocida: " + operacion);
        }
    }
} 