package com.medigestion.entity;

import lombok.Data;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "campanas")
public class Campana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String descripcion;
    
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;
    
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCampana estado;
    
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;
    
    @ManyToOne
    @JoinColumn(name = "coordinador_id", nullable = false)
    private Empleado coordinador;
    
    @OneToMany(mappedBy = "campana", cascade = CascadeType.ALL)
    private List<ExamenCampana> examenes = new ArrayList<>();
    
    @OneToMany(mappedBy = "campana", cascade = CascadeType.ALL)
    private List<ResultadoCampana> resultados = new ArrayList<>();
    
    @OneToMany(mappedBy = "campana", cascade = CascadeType.ALL)
    private List<AccionCampana> acciones = new ArrayList<>();
    
    private String tipo;
    private String tipoPromocion;
    private String grupoObjetivo;
    private String tema;
    private String tipoExamen;
    
    // Constructores
    public Campana() {}
    
    public Campana(String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
    
    // Getters y Setters
    @JsonProperty("id")
    public Long getId() {
        return id;
    }
    
    @JsonProperty("nombre")
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @JsonProperty("descripcion")
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    @JsonProperty("fechaInicio")
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    @JsonProperty("fechaFin")
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    @JsonProperty("estado")
    public EstadoCampana getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoCampana estado) {
        this.estado = estado;
    }
    
    @JsonProperty("doctor")
    public Doctor getDoctor() {
        return doctor;
    }
    
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    
    @JsonProperty("empresa")
    public Empresa getEmpresa() {
        return empresa;
    }
    
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    @JsonProperty("coordinador")
    public Empleado getCoordinador() {
        return coordinador;
    }
    
    public void setCoordinador(Empleado coordinador) {
        this.coordinador = coordinador;
    }
    
    @JsonProperty("examenes")
    public List<ExamenCampana> getExamenes() {
        return examenes;
    }
    
    public void setExamenes(List<ExamenCampana> examenes) {
        this.examenes = examenes;
    }
    
    @JsonProperty("resultados")
    public List<ResultadoCampana> getResultados() {
        return resultados;
    }
    
    public void setResultados(List<ResultadoCampana> resultados) {
        this.resultados = resultados;
    }
    
    @JsonProperty("acciones")
    public List<AccionCampana> getAcciones() {
        return acciones;
    }
    
    public void setAcciones(List<AccionCampana> acciones) {
        this.acciones = acciones;
    }
    
    @JsonProperty("tipo")
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    @JsonProperty("tipoPromocion")
    public String getTipoPromocion() {
        return tipoPromocion;
    }
    
    public void setTipoPromocion(String tipoPromocion) {
        this.tipoPromocion = tipoPromocion;
    }
    
    @JsonProperty("grupoObjetivo")
    public String getGrupoObjetivo() {
        return grupoObjetivo;
    }
    
    public void setGrupoObjetivo(String grupoObjetivo) {
        this.grupoObjetivo = grupoObjetivo;
    }
    
    @JsonProperty("tema")
    public String getTema() {
        return tema;
    }
    
    public void setTema(String tema) {
        this.tema = tema;
    }
    
    @JsonProperty("tipoExamen")
    public String getTipoExamen() {
        return tipoExamen;
    }
    
    public void setTipoExamen(String tipoExamen) {
        this.tipoExamen = tipoExamen;
    }
    
    // Métodos de ayuda
    public void agregarExamen(ExamenCampana examen) {
        examenes.add(examen);
        examen.setCampana(this);
    }
    
    public void removerExamen(ExamenCampana examen) {
        examenes.remove(examen);
        examen.setCampana(null);
    }
    
    @Override
    public String toString() {
        return "Campana{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", estado=" + estado +
               '}';
    }
}