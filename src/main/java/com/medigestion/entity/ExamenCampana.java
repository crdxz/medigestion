package com.medigestion.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "examenes_campana")
public class ExamenCampana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "campana_id")
    private Campana campana;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String descripcion;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDate fechaActualizacion;
    
    // PATRÓN OBSERVADOR: Callbacks JPA que observan eventos de persistencia
    // Se ejecutan automáticamente antes de persistir la entidad
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDate.now();
    }
    
    // PATRÓN OBSERVADOR: Callback que observa eventos de actualización
    // Se ejecuta automáticamente antes de actualizar la entidad
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDate.now();
    }
    
    // Constructores
    public ExamenCampana() {}
    
    public ExamenCampana(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Campana getCampana() {
        return campana;
    }
    
    public void setCampana(Campana campana) {
        this.campana = campana;
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
    
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public LocalDate getFechaActualizacion() {
        return fechaActualizacion;
    }
    
    public void setFechaActualizacion(LocalDate fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    
    public void setTipoExamen(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return "ExamenCampana{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               '}';
    }
}