package com.medigestion.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "doctores")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String apellido;
    
    @Column(nullable = false, unique = true)
    private String licencia;
    
    @Column(nullable = false)
    private String especialidad;
    
    @OneToMany(mappedBy = "doctor")
    private List<Campana> campanas = new ArrayList<>();
    
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
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getLicencia() {
        return licencia;
    }
    
    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }
    
    public String getEspecialidad() {
        return especialidad;
    }
    
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
    public List<Campana> getCampanas() {
        return campanas;
    }
    
    public void setCampanas(List<Campana> campanas) {
        this.campanas = campanas;
    }
} 