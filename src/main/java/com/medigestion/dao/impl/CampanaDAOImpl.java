package com.medigestion.dao.impl;

import com.medigestion.dao.CampanaDAO;
import com.medigestion.entity.Campana;
import com.medigestion.entity.EstadoCampana;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CampanaDAOImpl implements CampanaDAO {
    
    private static final Logger logger = LoggerFactory.getLogger(CampanaDAOImpl.class);
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Campana save(Campana campana) {
        if (campana.getId() == null) {
            entityManager.persist(campana);
        } else {
            campana = entityManager.merge(campana);
        }
        return campana;
    }
    
    @Override
    public Campana findById(Long id) {
        return entityManager.find(Campana.class, id);
    }
    
    @Override
    public List<Campana> findAll() {
        TypedQuery<Campana> query = entityManager.createQuery("SELECT c FROM Campana c", Campana.class);
        return query.getResultList();
    }
    
    @Override
    public List<Campana> findByEstado(EstadoCampana estado) {
        TypedQuery<Campana> query = entityManager.createQuery(
            "SELECT c FROM Campana c WHERE c.estado = :estado", Campana.class);
        query.setParameter("estado", estado);
        return query.getResultList();
    }
    
    @Override
    public List<Campana> findByNombre(String nombre) {
        TypedQuery<Campana> query = entityManager.createQuery(
            "SELECT c FROM Campana c WHERE LOWER(c.nombre) LIKE LOWER(:nombre)", Campana.class);
        query.setParameter("nombre", "%" + nombre + "%");
        return query.getResultList();
    }
    
    @Override
    public List<Campana> findByFechaInicioBetween(LocalDate fechaInicio, LocalDate fechaFin) {
        TypedQuery<Campana> query = entityManager.createQuery(
            "SELECT c FROM Campana c WHERE c.fechaInicio BETWEEN :fechaInicio AND :fechaFin", Campana.class);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);
        return query.getResultList();
    }
    
    @Override
    public void delete(Long id) {
        Campana campana = findById(id);
        if (campana != null) {
            entityManager.remove(campana);
        }
    }
}