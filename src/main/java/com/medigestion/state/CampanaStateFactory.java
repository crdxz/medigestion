package com.medigestion.state;

import com.medigestion.entity.EstadoCampana;

/**
 * PATRÓN FÁBRICA: Fábrica para crear estados de campaña
 * - Funcionalidad: Crea el estado apropiado según el enum EstadoCampana
 * - Características: Centraliza la creación de estados
 * - Beneficios: Encapsulación de la lógica de creación
 */
public class CampanaStateFactory {
    
    /**
     * PATRÓN FÁBRICA: Crea el estado apropiado según el enum
     * @param estado Enum del estado de la campaña
     * @return Implementación del estado correspondiente
     */
    public static CampanaState createState(EstadoCampana estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser null");
        }
        
        switch (estado) {
            case PENDIENTE:
                return new PendienteState();
            case ACTIVA:
                return new ActivaState();
            case FINALIZADA:
                return new FinalizadaState();
            case CANCELADA:
                return new CanceladaState();
            default:
                throw new IllegalArgumentException("Estado no soportado: " + estado);
        }
    }
} 