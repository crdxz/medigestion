package com.medigestion.controller;

import com.medigestion.dto.CampanaResponseDTO;
import com.medigestion.entity.Campana;
import com.medigestion.service.CampanaService;
import com.medigestion.mapper.CampanaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * PATRÓN CONTROLADOR: Controlador REST para gestión de campañas
 * - Funcionalidad: Endpoints para operaciones CRUD de campañas
 * - Características: Integración con patrones Estado y Comando
 * - Beneficios: Separación de responsabilidades, API RESTful
 */
@Slf4j
@RestController
@RequestMapping("/api/campanas")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"}, allowCredentials = "false")
public class CampanaController {

    private final CampanaService campanaService;
    private final CampanaMapper campanaMapper;

    /**
     * PATRÓN COMANDO + ESTADO: Crear campaña con validación de estado
     */
    @PostMapping
    public ResponseEntity<CampanaResponseDTO> crearCampana(
            @RequestBody Campana campana,
            @RequestParam(defaultValue = "sistema") String usuario) {
        
        log.info("Solicitud para crear campaña: {}", campana.getNombre());
        
        try {
            CampanaResponseDTO campanaCreada = campanaService.crearCampana(campana, usuario);
            return ResponseEntity.ok(campanaCreada);
        } catch (Exception e) {
            log.error("Error al crear campaña: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PATRÓN COMANDO + ESTADO: Modificar campaña con validación de estado
     */
    @PutMapping("/{id}")
    public ResponseEntity<CampanaResponseDTO> modificarCampana(
            @PathVariable Long id,
            @RequestBody Campana campanaModificada,
            @RequestParam(defaultValue = "sistema") String usuario) {
        
        log.info("Solicitud para modificar campaña ID: {}", id);
        
        try {
            CampanaResponseDTO campanaModificadaResult = campanaService.modificarCampana(id, campanaModificada, usuario);
            return ResponseEntity.ok(campanaModificadaResult);
        } catch (Exception e) {
            log.error("Error al modificar campaña: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PATRÓN CACHE + REPOSITORY: Obtener todas las campañas
     */
    @GetMapping
    public ResponseEntity<List<CampanaResponseDTO>> obtenerTodasLasCampanas() {
        log.info("Solicitud para obtener todas las campañas");
        
        try {
            List<CampanaResponseDTO> campanas = campanaService.obtenerTodasLasCampanas();
            return ResponseEntity.ok(campanas);
        } catch (Exception e) {
            log.error("Error al obtener campañas: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * PATRÓN CACHE + REPOSITORY: Obtener campaña por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CampanaResponseDTO> obtenerCampanaPorId(@PathVariable Long id) {
        log.info("Solicitud para obtener campaña ID: {}", id);
        
        try {
            Optional<CampanaResponseDTO> campana = campanaService.obtenerCampanaPorId(id);
            return campana.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Error al obtener campaña: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * PATRÓN COMANDO: Deshacer última operación
     */
    @PostMapping("/undo")
    public ResponseEntity<CampanaResponseDTO> deshacerUltimaOperacion() {
        log.info("Solicitud para deshacer última operación");
        
        try {
            Campana campana = campanaService.deshacerUltimaOperacion();
            return ResponseEntity.ok(campanaMapper.toResponseDTO(campana));
        } catch (Exception e) {
            log.error("Error al deshacer operación: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PATRÓN ESTADO: Obtener información del estado de una campaña
     */
    @GetMapping("/{id}/estado")
    public ResponseEntity<String> obtenerInformacionEstado(@PathVariable Long id) {
        log.info("Solicitud para obtener información de estado de campaña ID: {}", id);
        
        try {
            String informacionEstado = campanaService.obtenerInformacionEstado(id);
            return ResponseEntity.ok(informacionEstado);
        } catch (Exception e) {
            log.error("Error al obtener información de estado: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * PATRÓN ESTADO: Validar si se puede realizar una operación según el estado
     */
    @GetMapping("/{id}/validar/{operacion}")
    public ResponseEntity<Boolean> validarOperacion(
            @PathVariable Long id,
            @PathVariable String operacion) {
        
        log.info("Solicitud para validar operación '{}' en campaña ID: {}", operacion, id);
        
        try {
            boolean puedeRealizar = campanaService.validarOperacion(id, operacion);
            return ResponseEntity.ok(puedeRealizar);
        } catch (Exception e) {
            log.error("Error al validar operación: {}", e.getMessage());
            return ResponseEntity.badRequest().body(false);
        }
    }

    /**
     * Endpoint de prueba para verificar que el backend esté funcionando
     */
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        log.info("Endpoint de prueba llamado");
        return ResponseEntity.ok("Backend funcionando correctamente");
    }
} 