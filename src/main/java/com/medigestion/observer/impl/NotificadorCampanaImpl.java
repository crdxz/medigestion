package com.medigestion.observer.impl;

import com.medigestion.entity.Campana;
import com.medigestion.observer.NotificadorCampana;
import org.springframework.stereotype.Component;

@Component
public class NotificadorCampanaImpl implements NotificadorCampana {
    
    @Override
    public void notificarCreacion(Campana campana) {
        System.out.println("Notificación: Nueva campaña creada - " + campana.getNombre());
    }
    
    @Override
    public void notificarInicio(Campana campana) {
        System.out.println("Notificación: Campaña iniciada - " + campana.getNombre());
    }
    
    @Override
    public void notificarFinalizacion(Campana campana) {
        System.out.println("Notificación: Campaña finalizada - " + campana.getNombre());
    }
    
    @Override
    public void notificarCancelacion(Campana campana, String motivo) {
        System.out.println("Notificación: Campaña cancelada - " + campana.getNombre() + " - Motivo: " + motivo);
    }
}