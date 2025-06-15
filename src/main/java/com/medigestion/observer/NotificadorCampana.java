package com.medigestion.observer;

import com.medigestion.entity.Campana;

public interface NotificadorCampana {
    void notificarCreacion(Campana campana);
    void notificarInicio(Campana campana);
    void notificarFinalizacion(Campana campana);
    void notificarCancelacion(Campana campana, String motivo);
}