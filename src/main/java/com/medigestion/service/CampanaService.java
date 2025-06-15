package com.medigestion.service;

import com.medigestion.entity.Campana;
import com.medigestion.entity.EstadoCampana;
import com.medigestion.exception.CampanaException;
import com.medigestion.dao.CampanaDAO;
import com.medigestion.observer.NotificadorCampana;
import com.medigestion.factory.CampanaFactory;
import com.medigestion.repository.CampanaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class CampanaService {
    
    private final CampanaDAO campanaDAO;
    private final NotificadorCampana notificador;
    private final CampanaFactory campanaFactory;
    private final CampanaRepository campanaRepository;
    
    @Autowired
    public CampanaService(CampanaDAO campanaDAO, NotificadorCampana notificador, CampanaFactory campanaFactory, CampanaRepository campanaRepository) {
        this.campanaDAO = campanaDAO;
        this.notificador = notificador;
        this.campanaFactory = campanaFactory;
        this.campanaRepository = campanaRepository;
    }
    
    public Campana crearCampanaGeneral(String nombre, LocalDate fechaInicio, LocalDate fechaFin, String descripcion) {
        Campana campana = campanaFactory.crearCampanaGeneral(nombre, fechaInicio, fechaFin, descripcion);
        return campanaDAO.save(campana);
    }
    
    public Campana crearCampanaExamenesObligatorios(String nombre, LocalDate fechaInicio, LocalDate fechaFin, String descripcion) {
        Campana campana = campanaFactory.crearCampanaExamenesObligatorios(nombre, fechaInicio, fechaFin, descripcion);
        return campanaDAO.save(campana);
    }
    
    public Campana crearCampanaEspecifica(String nombre, LocalDate fechaInicio, LocalDate fechaFin, String descripcion, String tipoExamen) {
        Campana campana = campanaFactory.crearCampanaEspecifica(nombre, fechaInicio, fechaFin, descripcion, tipoExamen);
        return campanaDAO.save(campana);
    }
    
    public Campana crearCampanaPromocional(String nombre, LocalDate fechaInicio, LocalDate fechaFin, String descripcion) {
        Campana campana = campanaFactory.crearCampanaPromocional(nombre, fechaInicio, fechaFin, descripcion);
        return campanaDAO.save(campana);
    }
    
    public Campana crearCampanaPreventiva(String nombre, LocalDate fechaInicio, LocalDate fechaFin, String descripcion) {
        Campana campana = campanaFactory.crearCampanaPreventiva(nombre, fechaInicio, fechaFin, descripcion);
        return campanaDAO.save(campana);
    }
    
    public Campana crearCampanaEducativa(String nombre, LocalDate fechaInicio, LocalDate fechaFin, String descripcion) {
        Campana campana = campanaFactory.crearCampanaEducativa(nombre, fechaInicio, fechaFin, descripcion);
        return campanaDAO.save(campana);
    }
    
    public Campana buscarPorId(Long id) {
        return campanaDAO.findById(id);
    }
    
    public List<Campana> listarTodas() {
        return campanaDAO.findAll();
    }
    
    public List<Campana> buscarPorEstado(EstadoCampana estado) {
        return campanaDAO.findByEstado(estado);
    }
    
    public List<Campana> buscarPorNombre(String nombre) {
        return campanaDAO.findByNombre(nombre);
    }
    
    public List<Campana> buscarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return campanaDAO.findByFechaInicioBetween(fechaInicio, fechaFin);
    }
    
    public Campana iniciarCampana(Long id) {
        Campana campana = buscarPorId(id);
        if (campana != null && campana.getEstado() == EstadoCampana.PENDIENTE) {
            campana.setEstado(EstadoCampana.ACTIVA);
            return campanaDAO.save(campana);
        }
        return null;
    }
    
    public Campana finalizarCampana(Long id) {
        Campana campana = buscarPorId(id);
        if (campana != null && campana.getEstado() == EstadoCampana.ACTIVA) {
            campana.setEstado(EstadoCampana.FINALIZADA);
            return campanaDAO.save(campana);
        }
        return null;
    }
    
    public Campana cancelarCampana(Long id) {
        Campana campana = buscarPorId(id);
        if (campana != null && (campana.getEstado() == EstadoCampana.PENDIENTE || campana.getEstado() == EstadoCampana.ACTIVA)) {
            campana.setEstado(EstadoCampana.CANCELADA);
            return campanaDAO.save(campana);
        }
        return null;
    }
    
    public void eliminarCampana(Long id) {
        log.info("Eliminando campaña con ID: {}", id);
        campanaRepository.deleteById(id);
    }
    
    public Campana crearCampana(Campana campana) {
        log.info("Creando nueva campaña: {}", campana.getNombre());
        return campanaRepository.save(campana);
    }
    
    public List<Campana> buscarCampanas() {
        log.info("Buscando todas las campañas");
        return campanaRepository.findAll();
    }
    
    public Optional<Campana> buscarCampanaPorId(Long id) {
        log.info("Buscando campaña por ID: {}", id);
        return campanaRepository.findById(id);
    }
    
    public List<Campana> buscarCampanasPorEstado(EstadoCampana estado) {
        log.info("Buscando campañas por estado: {}", estado);
        return campanaRepository.findByEstado(estado);
    }
    
    public List<Campana> buscarCampanasPorTipo(String tipo) {
        log.info("Buscando campañas por tipo: {}", tipo);
        return campanaRepository.findByTipo(tipo);
    }
    
    public List<Campana> buscarCampanasPorFechaInicio(LocalDate fechaInicio) {
        log.info("Buscando campañas por fecha de inicio: {}", fechaInicio);
        return campanaRepository.findByFechaInicio(fechaInicio);
    }
    
    public List<Campana> buscarCampanasPorFechaFin(LocalDate fechaFin) {
        log.info("Buscando campañas por fecha de fin: {}", fechaFin);
        return campanaRepository.findByFechaFin(fechaFin);
    }
    
    public Campana actualizarCampana(Long id, Campana campanaActualizada) {
        log.info("Actualizando campaña con ID: {}", id);
        Optional<Campana> campanaExistente = campanaRepository.findById(id);
        if (campanaExistente.isPresent()) {
            Campana campana = campanaExistente.get();
            campana.setNombre(campanaActualizada.getNombre());
            campana.setDescripcion(campanaActualizada.getDescripcion());
            campana.setFechaInicio(campanaActualizada.getFechaInicio());
            campana.setFechaFin(campanaActualizada.getFechaFin());
            campana.setEstado(campanaActualizada.getEstado());
            campana.setTipo(campanaActualizada.getTipo());
            return campanaRepository.save(campana);
        }
        return null;
    }
}
