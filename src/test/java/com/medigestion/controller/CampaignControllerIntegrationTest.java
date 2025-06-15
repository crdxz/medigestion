package com.medigestion.controller;

import com.medigestion.dto.CampanaResponseDTO;
import com.medigestion.entity.Campana;
import com.medigestion.entity.EstadoCampana;
import com.medigestion.entity.Empresa;
import com.medigestion.entity.Doctor;
import com.medigestion.entity.Empleado;
import com.medigestion.repository.CampanaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import com.medigestion.dto.CampanaRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CampanaControllerIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger(CampanaControllerIntegrationTest.class);
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CampanaRepository campanaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @PersistenceContext
    private EntityManager entityManager;

    private Campana campana;
    private Empresa empresa;
    private Doctor doctor;
    private Empleado coordinador;

    @BeforeEach
    void setUp() {
        campanaRepository.deleteAll();
        
        // Crear y persistir empresa
        empresa = new Empresa();
        empresa.setNombre("Empresa Test");
        empresa.setNit("123456789-0");
        empresa.setDireccion("Calle Falsa 123");
        empresa.setTelefono("1234567");
        empresa.setEmail("empresa@test.com");
        entityManager.persist(empresa);
        // Crear y persistir doctor
        doctor = new Doctor();
        doctor.setNombre("Juan");
        doctor.setApellido("Pérez");
        doctor.setLicencia("MED123");
        doctor.setEspecialidad("General");
        entityManager.persist(doctor);
        // Crear y persistir coordinador
        coordinador = new Empleado();
        coordinador.setNombre("Ana");
        coordinador.setApellido("Martínez");
        coordinador.setCargo("Coordinadora");
        coordinador.setEmail("ana@test.com");
        coordinador.setTelefono("3001234567");
        coordinador.setEmpresa(empresa);
        entityManager.persist(coordinador);
        // Crear campaña y asignar relaciones obligatorias
        campana = new Campana();
        campana.setNombre("Campaña de Integración");
        campana.setDescripcion("Descripción de prueba de integración");
        campana.setFechaInicio(LocalDate.now());
        campana.setFechaFin(LocalDate.now().plusDays(30));
        campana.setEstado(EstadoCampana.PENDIENTE);
        campana.setTipo("GENERAL");
        campana.setEmpresa(empresa);
        campana.setDoctor(doctor);
        campana.setCoordinador(coordinador);
        campana = campanaRepository.save(campana);
    }

    @Test
    void obtenerCampanas_DeberiaRetornarListaDeCampanas() throws Exception {
        mockMvc.perform(get("/api/campanas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value(campana.getNombre()))
                .andExpect(jsonPath("$[0].descripcion").value(campana.getDescripcion()));
    }

    @Test
    void obtenerCampana_DeberiaRetornarCampanaExistente() throws Exception {
        mockMvc.perform(get("/api/campanas/{id}", campana.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(campana.getNombre()))
                .andExpect(jsonPath("$.descripcion").value(campana.getDescripcion()));
    }

    @Test
    void obtenerCampana_DeberiaRetornarNotFoundParaCampanaInexistente() throws Exception {
        mockMvc.perform(get("/api/campanas/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crearCampana_DeberiaCrearNuevaCampana() throws Exception {
        Campana nuevaCampana = new Campana();
        nuevaCampana.setNombre("Nueva Campaña");
        nuevaCampana.setDescripcion("Nueva descripción");
        nuevaCampana.setFechaInicio(LocalDate.now());
        nuevaCampana.setFechaFin(LocalDate.now().plusDays(30));
        nuevaCampana.setEstado(EstadoCampana.PENDIENTE);
        nuevaCampana.setTipo("GENERAL");
        nuevaCampana.setEmpresa(empresa);
        nuevaCampana.setDoctor(doctor);
        nuevaCampana.setCoordinador(coordinador);

        mockMvc.perform(post("/api/campanas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevaCampana)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(nuevaCampana.getNombre()))
                .andExpect(jsonPath("$.descripcion").value(nuevaCampana.getDescripcion()));
    }

    @Test
    void actualizarCampana_DeberiaActualizarCampanaExistente() throws Exception {
        campana.setNombre("Campaña Actualizada");
        campana.setDescripcion("Descripción actualizada");

        mockMvc.perform(put("/api/campanas/{id}", campana.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(campana)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Campaña Actualizada"))
                .andExpect(jsonPath("$.descripcion").value("Descripción actualizada"));
    }

    @Test
    void actualizarCampana_DeberiaRetornarNotFoundParaCampanaInexistente() throws Exception {
        mockMvc.perform(put("/api/campanas/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(campana)))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarCampana_DeberiaEliminarCampanaExistente() throws Exception {
        mockMvc.perform(delete("/api/campanas/{id}", campana.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/campanas/{id}", campana.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void buscarPorEstado_DeberiaRetornarCampanasPorEstado() throws Exception {
        mockMvc.perform(get("/api/campanas/estado/{estado}", EstadoCampana.PENDIENTE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value(EstadoCampana.PENDIENTE.toString()));
    }

    @Test
    void buscarPorTipo_DeberiaRetornarCampanasPorTipo() throws Exception {
        mockMvc.perform(get("/api/campanas/tipo/{tipo}", "GENERAL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipo").value("GENERAL"));
    }

    @Test
    void buscarPorRangoFechas_DeberiaRetornarCampanasEnRango() throws Exception {
        LocalDate fechaInicio = LocalDate.now().minusDays(1);
        LocalDate fechaFin = LocalDate.now().plusDays(31);

        mockMvc.perform(get("/api/campanas/fechas")
                .param("fechaInicio", fechaInicio.toString())
                .param("fechaFin", fechaFin.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value(campana.getNombre()));
    }

    @Test
    void iniciarCampana_DeberiaIniciarCampanaPendiente() throws Exception {
        mockMvc.perform(post("/api/campanas/{id}/iniciar", campana.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value(EstadoCampana.ACTIVA.toString()));
    }

    @Test
    void finalizarCampana_DeberiaFinalizarCampanaActiva() throws Exception {
        // Primero iniciamos la campaña
        campana.setEstado(EstadoCampana.ACTIVA);
        campanaRepository.save(campana);

        mockMvc.perform(post("/api/campanas/{id}/finalizar", campana.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value(EstadoCampana.FINALIZADA.toString()));
    }

    @Test
    void cancelarCampana_DeberiaCancelarCampana() throws Exception {
        mockMvc.perform(post("/api/campanas/{id}/cancelar", campana.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value(EstadoCampana.CANCELADA.toString()));
    }
} 