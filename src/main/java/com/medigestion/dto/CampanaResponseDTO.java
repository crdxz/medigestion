package com.medigestion.dto;

import com.medigestion.entity.EstadoCampana;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;

public class CampanaResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;
    
    private EstadoCampana estado;
    private String tipo;
    private String tipoPromocion;
    private String grupoObjetivo;
    private String tema;
    private String tipoExamen;
    
    // Información básica de las relaciones
    private DoctorDTO doctor;
    private EmpresaDTO empresa;
    private EmpleadoDTO coordinador;
    private List<ExamenCampanaDTO> examenes;
    private List<ResultadoCampanaDTO> resultados;
    private List<AccionCampanaDTO> acciones;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public EstadoCampana getEstado() {
        return estado;
    }

    public void setEstado(EstadoCampana estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipoPromocion() {
        return tipoPromocion;
    }

    public void setTipoPromocion(String tipoPromocion) {
        this.tipoPromocion = tipoPromocion;
    }

    public String getGrupoObjetivo() {
        return grupoObjetivo;
    }

    public void setGrupoObjetivo(String grupoObjetivo) {
        this.grupoObjetivo = grupoObjetivo;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getTipoExamen() {
        return tipoExamen;
    }

    public void setTipoExamen(String tipoExamen) {
        this.tipoExamen = tipoExamen;
    }

    public DoctorDTO getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorDTO doctor) {
        this.doctor = doctor;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }

    public EmpleadoDTO getCoordinador() {
        return coordinador;
    }

    public void setCoordinador(EmpleadoDTO coordinador) {
        this.coordinador = coordinador;
    }

    public List<ExamenCampanaDTO> getExamenes() {
        return examenes;
    }

    public void setExamenes(List<ExamenCampanaDTO> examenes) {
        this.examenes = examenes;
    }

    public List<ResultadoCampanaDTO> getResultados() {
        return resultados;
    }

    public void setResultados(List<ResultadoCampanaDTO> resultados) {
        this.resultados = resultados;
    }

    public List<AccionCampanaDTO> getAcciones() {
        return acciones;
    }

    public void setAcciones(List<AccionCampanaDTO> acciones) {
        this.acciones = acciones;
    }
} 