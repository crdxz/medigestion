package com.medigestion.repository;

import com.medigestion.entity.Campana;
import com.medigestion.entity.EstadoCampana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CampanaRepository extends JpaRepository<Campana, Long> {
    List<Campana> findByEstado(EstadoCampana estado);
    List<Campana> findByTipo(String tipo);
    List<Campana> findByFechaInicio(LocalDate fechaInicio);
    List<Campana> findByFechaFin(LocalDate fechaFin);
} 