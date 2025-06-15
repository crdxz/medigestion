package com.medigestion.dao;

import com.medigestion.entity.Campana;
import com.medigestion.entity.EstadoCampana;
import java.time.LocalDate;
import java.util.List;

public interface CampanaDAO {
    Campana save(Campana campana);
    Campana findById(Long id);
    List<Campana> findAll();
    List<Campana> findByEstado(EstadoCampana estado);
    List<Campana> findByNombre(String nombre);
    List<Campana> findByFechaInicioBetween(LocalDate fechaInicio, LocalDate fechaFin);
    void delete(Long id);
}