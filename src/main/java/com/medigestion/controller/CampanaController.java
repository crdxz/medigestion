package com.medigestion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.medigestion.service.CampanaService;
import com.medigestion.dto.CampanaResponseDTO;
import com.medigestion.dto.CampanaRequestDTO;
import com.medigestion.entity.Campana;
import com.medigestion.entity.EstadoCampana;
import com.medigestion.mapper.CampanaMapper;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/campanas")
@Slf4j
public class CampanaController {
    
    @Autowired
    private CampanaService campanaService;
    
    @Autowired
    private CampanaMapper campanaMapper;
    
    @GetMapping
    public ResponseEntity<List<CampanaResponseDTO>> obtenerCampanas() {
        log.info("Obteniendo todas las campañas");
        List<Campana> campanas = campanaService.buscarCampanas();
        return ResponseEntity.ok(campanaMapper.toDTOList(campanas));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CampanaResponseDTO> obtenerCampana(@PathVariable Long id) {
        log.info("Obteniendo campaña con ID: {}", id);
        Optional<Campana> campana = campanaService.buscarCampanaPorId(id);
        return campana.map(c -> ResponseEntity.ok(campanaMapper.toDTO(c)))
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<CampanaResponseDTO> crearCampana(@RequestBody Campana campana) {
        log.info("Creando nueva campaña: {}", campana.getNombre());
        Campana nuevaCampana = campanaService.crearCampana(campana);
        return ResponseEntity.ok(campanaMapper.toDTO(nuevaCampana));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CampanaResponseDTO> actualizarCampana(@PathVariable Long id, @RequestBody Campana campana) {
        log.info("Actualizando campaña con ID: {}", id);
        Campana campanaActualizada = campanaService.actualizarCampana(id, campana);
        if (campanaActualizada != null) {
            return ResponseEntity.ok(campanaMapper.toDTO(campanaActualizada));
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCampana(@PathVariable Long id) {
        log.info("Eliminando campaña con ID: {}", id);
        campanaService.eliminarCampana(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CampanaResponseDTO>> buscarPorEstado(@PathVariable EstadoCampana estado) {
        log.info("Buscando campañas por estado: {}", estado);
        List<Campana> campanas = campanaService.buscarCampanasPorEstado(estado);
        return ResponseEntity.ok(campanaMapper.toDTOList(campanas));
    }
    
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<CampanaResponseDTO>> buscarPorTipo(@PathVariable String tipo) {
        log.info("Buscando campañas por tipo: {}", tipo);
        List<Campana> campanas = campanaService.buscarCampanasPorTipo(tipo);
        return ResponseEntity.ok(campanaMapper.toDTOList(campanas));
    }
    
    @GetMapping("/fechas")
    public ResponseEntity<List<CampanaResponseDTO>> buscarPorRangoFechas(
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin) {
        log.info("Buscando campañas entre {} y {}", fechaInicio, fechaFin);
        List<Campana> campanas = campanaService.buscarPorRangoFechas(fechaInicio, fechaFin);
        return ResponseEntity.ok(campanaMapper.toDTOList(campanas));
    }
    
    @PutMapping("/{id}/iniciar")
    public ResponseEntity<CampanaResponseDTO> iniciarCampana(@PathVariable Long id) {
        log.info("Iniciando campaña con ID: {}", id);
        Campana campana = campanaService.iniciarCampana(id);
        if (campana != null) {
            return ResponseEntity.ok(campanaMapper.toDTO(campana));
        }
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<CampanaResponseDTO> finalizarCampana(@PathVariable Long id) {
        log.info("Finalizando campaña con ID: {}", id);
        Campana campana = campanaService.finalizarCampana(id);
        if (campana != null) {
            return ResponseEntity.ok(campanaMapper.toDTO(campana));
        }
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<CampanaResponseDTO> cancelarCampana(@PathVariable Long id) {
        log.info("Cancelando campaña con ID: {}", id);
        Campana campana = campanaService.cancelarCampana(id);
        if (campana != null) {
            return ResponseEntity.ok(campanaMapper.toDTO(campana));
        }
        return ResponseEntity.notFound().build();
    }
} 