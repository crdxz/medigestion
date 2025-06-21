package com.medigestion.state;

import com.medigestion.entity.Campana;

/**
 * PATRÓN ESTADO: Implementación del estado FINALIZADA
 * - Comportamiento: Campaña completada, solo lectura
 * - Operaciones: Crear (no), Modificar (no), Eliminar (no)
 */
public class FinalizadaState implements CampanaState {
    
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
        return false;
    }
    
    @Override
    public String getMensajeEstado() {
        return "La campaña ha finalizado y no puede ser modificada";
    }
    
    @Override
    public void validarOperacion(String operacion, Campana campana) {
        throw new IllegalStateException("No se pueden realizar operaciones en una campaña FINALIZADA");
    }
} 