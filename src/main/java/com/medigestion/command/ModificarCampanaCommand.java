package com.medigestion.command;

import com.medigestion.entity.Campana;
import com.medigestion.repository.CampanaRepository;
import com.medigestion.state.CampanaStateFactory;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

/**
 * PATRÓN COMANDO: Comando para modificar campañas
 * - Funcionalidad: Encapsula la operación de modificación
 * - Características: Auditoría, validación de estado, rollback
 * - Beneficios: Trazabilidad y reversibilidad de operaciones
 */
@RequiredArgsConstructor
public class ModificarCampanaCommand implements CampanaCommand {
    
    private final CampanaRepository campanaRepository;
    private final Campana campanaOriginal;
    private final Campana campanaModificada;
    private final String usuario;
    private final LocalDateTime timestamp;
    
    // PATRÓN COMANDO: Estado para rollback
    private Campana estadoAnterior;
    
    @Override
    public Campana execute() {
        // PATRÓN COMANDO: Guardar estado anterior para rollback
        this.estadoAnterior = new Campana();
        this.estadoAnterior.setId(campanaOriginal.getId());
        this.estadoAnterior.setNombre(campanaOriginal.getNombre());
        this.estadoAnterior.setDescripcion(campanaOriginal.getDescripcion());
        this.estadoAnterior.setFechaInicio(campanaOriginal.getFechaInicio());
        this.estadoAnterior.setFechaFin(campanaOriginal.getFechaFin());
        this.estadoAnterior.setEstado(campanaOriginal.getEstado());
        this.estadoAnterior.setTipoCampana(campanaOriginal.getTipoCampana());
        
        // PATRÓN ESTADO: Validar si se puede modificar según el estado actual
        var estadoActual = CampanaStateFactory.createState(campanaOriginal.getEstado());
        estadoActual.validarOperacion("modificar", campanaOriginal);
        
        // PATRÓN COMANDO: Ejecutar la modificación
        campanaModificada.setId(campanaOriginal.getId());
        campanaModificada.setFechaModificacion(LocalDateTime.now());
        
        Campana resultado = campanaRepository.save(campanaModificada);
        
        return resultado;
    }
    
    @Override
    public Campana undo() {
        // PATRÓN COMANDO: Restaurar estado anterior
        if (estadoAnterior != null) {
            return campanaRepository.save(estadoAnterior);
        }
        throw new IllegalStateException("No se puede deshacer: estado anterior no disponible");
    }
    
    @Override
    public boolean canExecute() {
        // PATRÓN ESTADO: Verificar si el estado permite modificación
        var estadoActual = CampanaStateFactory.createState(campanaOriginal.getEstado());
        return estadoActual.puedeModificar();
    }
    
    @Override
    public String getDescription() {
        return String.format("Modificar campaña ID: %d por usuario: %s", 
                           campanaOriginal.getId(), usuario);
    }
} 