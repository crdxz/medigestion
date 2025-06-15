package com.medigestion.controller;

import com.medigestion.dto.CampanaResponseDTO;
import com.medigestion.entity.Campana;
import com.medigestion.entity.EstadoCampana;
import com.medigestion.service.CampanaService;
import com.medigestion.mapper.CampanaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CampanaControllerTest {

    @Mock
    private CampanaService campanaService;

    @Mock
    private CampanaMapper campanaMapper;

    @InjectMocks
    private CampanaController campanaController;

    private Campana campana;
    private CampanaResponseDTO campanaDTO;
    private List<Campana> campanas;
    private List<CampanaResponseDTO> campanasDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Crear una campaña de prueba
        campana = new Campana();
        campana.setId(1L);
        campana.setNombre("Campaña de Prueba");
        campana.setDescripcion("Descripción de prueba");
        campana.setFechaInicio(LocalDate.now());
        campana.setFechaFin(LocalDate.now().plusDays(30));
        campana.setEstado(EstadoCampana.PENDIENTE);
        campana.setTipo("GENERAL");
        
        campanas = Arrays.asList(campana);

        // Crear DTOs correspondientes
        campanaDTO = new CampanaResponseDTO();
        campanaDTO.setId(1L);
        campanaDTO.setNombre("Campaña de Prueba");
        campanaDTO.setDescripcion("Descripción de prueba");
        campanaDTO.setFechaInicio(LocalDate.now());
        campanaDTO.setFechaFin(LocalDate.now().plusDays(30));
        campanaDTO.setEstado(EstadoCampana.PENDIENTE);
        campanaDTO.setTipo("GENERAL");
        
        campanasDTO = Arrays.asList(campanaDTO);

        // Configurar el comportamiento del mapper
        when(campanaMapper.toDTO(any(Campana.class))).thenReturn(campanaDTO);
        when(campanaMapper.toDTOList(anyList())).thenReturn(campanasDTO);
    }

    @Test
    void obtenerCampanas_DeberiaRetornarListaDeCampanas() {
        when(campanaService.buscarCampanas()).thenReturn(campanas);

        ResponseEntity<List<CampanaResponseDTO>> response = campanaController.obtenerCampanas();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(campanaService, times(1)).buscarCampanas();
    }

    @Test
    void obtenerCampana_DeberiaRetornarCampanaExistente() {
        when(campanaService.buscarCampanaPorId(1L)).thenReturn(Optional.of(campana));

        ResponseEntity<CampanaResponseDTO> response = campanaController.obtenerCampana(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(campanaDTO.getId(), response.getBody().getId());
        verify(campanaService, times(1)).buscarCampanaPorId(1L);
    }

    @Test
    void obtenerCampana_DeberiaRetornarNotFoundParaCampanaInexistente() {
        when(campanaService.buscarCampanaPorId(999L)).thenReturn(Optional.empty());

        ResponseEntity<CampanaResponseDTO> response = campanaController.obtenerCampana(999L);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(campanaService, times(1)).buscarCampanaPorId(999L);
    }

    @Test
    void crearCampana_DeberiaCrearNuevaCampana() {
        when(campanaService.crearCampana(any(Campana.class))).thenReturn(campana);

        ResponseEntity<CampanaResponseDTO> response = campanaController.crearCampana(campana);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(campanaDTO.getId(), response.getBody().getId());
        verify(campanaService, times(1)).crearCampana(any(Campana.class));
    }

    @Test
    void actualizarCampana_DeberiaActualizarCampanaExistente() {
        when(campanaService.actualizarCampana(eq(1L), any(Campana.class))).thenReturn(campana);

        ResponseEntity<CampanaResponseDTO> response = campanaController.actualizarCampana(1L, campana);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(campanaDTO.getId(), response.getBody().getId());
        verify(campanaService, times(1)).actualizarCampana(eq(1L), any(Campana.class));
    }

    @Test
    void actualizarCampana_DeberiaRetornarNotFoundParaCampanaInexistente() {
        when(campanaService.actualizarCampana(eq(999L), any(Campana.class))).thenReturn(null);

        ResponseEntity<CampanaResponseDTO> response = campanaController.actualizarCampana(999L, campana);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(campanaService, times(1)).actualizarCampana(eq(999L), any(Campana.class));
    }

    @Test
    void eliminarCampana_DeberiaEliminarCampanaExistente() {
        doNothing().when(campanaService).eliminarCampana(1L);

        ResponseEntity<Void> response = campanaController.eliminarCampana(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(campanaService, times(1)).eliminarCampana(1L);
    }

    @Test
    void buscarPorEstado_DeberiaRetornarCampanasPorEstado() {
        when(campanaService.buscarCampanasPorEstado(EstadoCampana.PENDIENTE)).thenReturn(campanas);

        ResponseEntity<List<CampanaResponseDTO>> response = campanaController.buscarPorEstado(EstadoCampana.PENDIENTE);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(campanaService, times(1)).buscarCampanasPorEstado(EstadoCampana.PENDIENTE);
    }

    @Test
    void buscarPorTipo_DeberiaRetornarCampanasPorTipo() {
        when(campanaService.buscarCampanasPorTipo("GENERAL")).thenReturn(campanas);

        ResponseEntity<List<CampanaResponseDTO>> response = campanaController.buscarPorTipo("GENERAL");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(campanaService, times(1)).buscarCampanasPorTipo("GENERAL");
    }

    @Test
    void buscarPorRangoFechas_DeberiaRetornarCampanasEnRango() {
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = LocalDate.now().plusDays(30);
        when(campanaService.buscarPorRangoFechas(fechaInicio, fechaFin)).thenReturn(campanas);

        ResponseEntity<List<CampanaResponseDTO>> response = campanaController.buscarPorRangoFechas(fechaInicio, fechaFin);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(campanaService, times(1)).buscarPorRangoFechas(fechaInicio, fechaFin);
    }

    @Test
    void iniciarCampana_DeberiaIniciarCampanaPendiente() {
        when(campanaService.iniciarCampana(1L)).thenReturn(campana);

        ResponseEntity<CampanaResponseDTO> response = campanaController.iniciarCampana(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(campanaDTO.getId(), response.getBody().getId());
        verify(campanaService, times(1)).iniciarCampana(1L);
    }

    @Test
    void finalizarCampana_DeberiaFinalizarCampanaActiva() {
        when(campanaService.finalizarCampana(1L)).thenReturn(campana);

        ResponseEntity<CampanaResponseDTO> response = campanaController.finalizarCampana(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(campanaDTO.getId(), response.getBody().getId());
        verify(campanaService, times(1)).finalizarCampana(1L);
    }

    @Test
    void cancelarCampana_DeberiaCancelarCampana() {
        when(campanaService.cancelarCampana(1L)).thenReturn(campana);

        ResponseEntity<CampanaResponseDTO> response = campanaController.cancelarCampana(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(campanaDTO.getId(), response.getBody().getId());
        verify(campanaService, times(1)).cancelarCampana(1L);
    }
} 