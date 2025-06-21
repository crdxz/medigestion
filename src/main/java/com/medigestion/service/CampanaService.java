package com.medigestion.service;

import com.medigestion.cache.CacheSingleton;
import com.medigestion.command.CommandExecutor;
import com.medigestion.command.CrearCampanaCommand;
import com.medigestion.command.ModificarCampanaCommand;
import com.medigestion.dto.CampanaResponseDTO;
import com.medigestion.entity.Campana;
import com.medigestion.entity.EstadoCampana;
import com.medigestion.mapper.CampanaMapper;
import com.medigestion.repository.CampanaRepository;
import com.medigestion.state.CampanaState;
import com.medigestion.state.CampanaStateFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * PATRÓN SERVICIO: Capa de servicio para gestión de campañas
 * - Funcionalidad: Lógica de negocio para campañas
 * - Características: Integración de patrones Estado y Comando
 * - Beneficios: Separación de responsabilidades, reutilización
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CampanaService {

    private final CampanaRepository campanaRepository;
    private final CampanaMapper campanaMapper;
    private final CommandExecutor commandExecutor;
    
    // PATRÓN SINGLETON: Cache para optimización de rendimiento
    private final CacheSingleton cache = CacheSingleton.INSTANCE;

    /**
     * PATRÓN COMANDO + ESTADO: Crear campaña usando comandos y validación de estado
     */
    @Transactional
    public CampanaResponseDTO crearCampana(Campana campana, String usuario) {
        // PATRÓN ESTADO: Validar que se puede crear una nueva campaña
        CampanaState estadoPendiente = CampanaStateFactory.createState(EstadoCampana.PENDIENTE);
        estadoPendiente.validarOperacion("crear", campana);
        
        // PATRÓN COMANDO: Encapsular la operación de creación
        CrearCampanaCommand comando = new CrearCampanaCommand(
            campanaRepository, campana, usuario, LocalDateTime.now()
        );
        
        Campana campanaCreada = commandExecutor.executeCommand(comando);
        
        // PATRÓN CACHE: Invalidar cache después de crear
        cache.remove("campanas");
        
        return campanaMapper.toResponseDTO(campanaCreada);
    }

    /**
     * PATRÓN COMANDO + ESTADO: Crear campaña usando comandos y validación de estado
     */
    @Transactional
    public CampanaResponseDTO crearCampana(Campana campana) {
        return crearCampana(campana, "sistema");
    }

    /**
     * PATRÓN COMANDO + ESTADO: Modificar campaña usando comandos y validación de estado
     */
    @Transactional
    public CampanaResponseDTO modificarCampana(Long id, Campana campanaModificada, String usuario) {
        Campana campanaExistente = campanaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Campaña no encontrada"));
        
        // PATRÓN ESTADO: Validar si se puede modificar según el estado actual
        CampanaState estadoActual = CampanaStateFactory.createState(campanaExistente.getEstado());
        estadoActual.validarOperacion("modificar", campanaExistente);
        
        // PATRÓN COMANDO: Encapsular la operación de modificación
        ModificarCampanaCommand comando = new ModificarCampanaCommand(
            campanaRepository, campanaExistente, campanaModificada, usuario, LocalDateTime.now()
        );
        
        Campana campanaModificadaResult = (Campana) commandExecutor.executeCommand(comando);
        
        // PATRÓN CACHE: Invalidar cache después de modificar
        cache.remove("campanas");
        cache.remove("campana:" + id);
        
        return campanaMapper.toResponseDTO(campanaModificadaResult);
    }

    /**
     * PATRÓN CACHE + REPOSITORY: Obtener todas las campañas con cache
     */
    public List<CampanaResponseDTO> obtenerTodasLasCampanas() {
        // PATRÓN CACHE: Verificar cache antes de consultar BD
        @SuppressWarnings("unchecked")
        List<Campana> campanasCache = (List<Campana>) cache.get("campanas");
        
        if (campanasCache != null) {
            log.info("Retornando campañas desde cache");
            return campanasCache.stream()
                .map(campanaMapper::toResponseDTO)
                .collect(Collectors.toList());
        }
        
        // PATRÓN REPOSITORY: Consultar BD si no está en cache
        List<Campana> campanas = campanaRepository.findAll();
        
        // PATRÓN CACHE: Guardar en cache para futuras consultas
        cache.put("campanas", campanas);
        
        return campanas.stream()
            .map(campanaMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    /**
     * PATRÓN CACHE + REPOSITORY: Obtener campaña por ID con cache
     */
    public Optional<CampanaResponseDTO> obtenerCampanaPorId(Long id) {
        // PATRÓN CACHE: Verificar cache antes de consultar BD
        Campana campanaCache = (Campana) cache.get("campana:" + id);
        
        if (campanaCache != null) {
            log.info("Retornando campaña desde cache para ID: {}", id);
            return Optional.of(campanaMapper.toResponseDTO(campanaCache));
        }
        
        // PATRÓN REPOSITORY: Consultar BD si no está en cache
        Optional<Campana> campana = campanaRepository.findById(id);
        
        // PATRÓN CACHE: Guardar en cache si existe
        campana.ifPresent(c -> cache.put("campana:" + id, c));
        
        return campana.map(campanaMapper::toResponseDTO);
    }

    /**
     * PATRÓN COMANDO: Deshacer última operación
     */
    public Campana deshacerUltimaOperacion() {
        return commandExecutor.undoLastCommand();
    }

    /**
     * PATRÓN ESTADO: Obtener información del estado de una campaña
     */
    public String obtenerInformacionEstado(Long id) {
        Campana campana = campanaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Campaña no encontrada"));
        
        CampanaState estado = CampanaStateFactory.createState(campana.getEstado());
        return estado.getMensajeEstado();
    }

    /**
     * PATRÓN ESTADO: Validar operación según el estado de la campaña
     */
    public boolean validarOperacion(Long id, String operacion) {
        Campana campana = campanaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Campaña no encontrada"));
        
        CampanaState estado = CampanaStateFactory.createState(campana.getEstado());
        
        switch (operacion.toLowerCase()) {
            case "crear":
                return estado.puedeCrear();
            case "modificar":
                return estado.puedeModificar();
            case "eliminar":
                return estado.puedeEliminar();
            default:
                return false;
        }
    }
}
