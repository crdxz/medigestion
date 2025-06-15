package com.medigestion.factory;

import com.medigestion.entity.Campana;
import com.medigestion.entity.ExamenCampana;
import com.medigestion.entity.EstadoCampana;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CampanaFactory {
    
    private void inicializarCamposOpcionales(Campana campana) {
        campana.setTipoPromocion("NO_APLICA");
        campana.setGrupoObjetivo("GENERAL");
        campana.setTema("SALUD");
        campana.setTipoExamen("NO_APLICA");
    }
    
    public Campana crearCampanaGeneral(String nombre, LocalDate fechaInicio, LocalDate fechaFin, String descripcion) {
        Campana campana = new Campana();
        campana.setNombre(nombre);
        campana.setDescripcion(descripcion);
        campana.setFechaInicio(fechaInicio);
        campana.setFechaFin(fechaFin);
        campana.setEstado(EstadoCampana.PENDIENTE);
        campana.setTipo("GENERAL");
        inicializarCamposOpcionales(campana);
        return campana;
    }
    
    public Campana crearCampanaExamenesObligatorios(String nombre, LocalDate fechaInicio, LocalDate fechaFin, String descripcion) {
        Campana campana = new Campana();
        campana.setNombre(nombre);
        campana.setDescripcion(descripcion);
        campana.setFechaInicio(fechaInicio);
        campana.setFechaFin(fechaFin);
        campana.setEstado(EstadoCampana.PENDIENTE);
        campana.setTipo("EXAMENES_OBLIGATORIOS");
        inicializarCamposOpcionales(campana);
        return campana;
    }
    
    public Campana crearCampanaEspecifica(String nombre, LocalDate fechaInicio, LocalDate fechaFin, String descripcion, String tipoExamen) {
        Campana campana = new Campana();
        campana.setNombre(nombre);
        campana.setDescripcion(descripcion);
        campana.setFechaInicio(fechaInicio);
        campana.setFechaFin(fechaFin);
        campana.setEstado(EstadoCampana.PENDIENTE);
        campana.setTipo("ESPECIFICA");
        campana.setTipoExamen(tipoExamen);
        inicializarCamposOpcionales(campana);
        return campana;
    }
    
    private void agregarExamen(Campana campana, String tipo, String descripcion, Integer cantidad) {
        ExamenCampana examen = new ExamenCampana();
        examen.setTipoExamen(tipo);
        examen.setDescripcion(descripcion);
        examen.setCantidadEstimada(cantidad);
        campana.agregarExamen(examen);
    }

    public Campana crearCampanaPromocional(String nombre, LocalDate fechaInicio, LocalDate fechaFin, String descripcion) {
        Campana campana = new Campana();
        campana.setNombre(nombre);
        campana.setDescripcion(descripcion);
        campana.setFechaInicio(fechaInicio);
        campana.setFechaFin(fechaFin);
        campana.setEstado(EstadoCampana.PENDIENTE);
        campana.setTipo("PROMOCIONAL");
        campana.setTipoPromocion("DESCUENTO");
        inicializarCamposOpcionales(campana);
        return campana;
    }

    public Campana crearCampanaPreventiva(String nombre, LocalDate fechaInicio, LocalDate fechaFin, String descripcion) {
        Campana campana = new Campana();
        campana.setNombre(nombre);
        campana.setDescripcion(descripcion);
        campana.setFechaInicio(fechaInicio);
        campana.setFechaFin(fechaFin);
        campana.setEstado(EstadoCampana.PENDIENTE);
        campana.setTipo("PREVENTIVA");
        inicializarCamposOpcionales(campana);
        return campana;
    }

    public Campana crearCampanaEducativa(String nombre, LocalDate fechaInicio, LocalDate fechaFin, String descripcion) {
        Campana campana = new Campana();
        campana.setNombre(nombre);
        campana.setDescripcion(descripcion);
        campana.setFechaInicio(fechaInicio);
        campana.setFechaFin(fechaFin);
        campana.setEstado(EstadoCampana.PENDIENTE);
        campana.setTipo("EDUCATIVA");
        inicializarCamposOpcionales(campana);
        return campana;
    }

    public static Campana crearCampanaVacunacion(String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin) {
        Campana campana = new Campana();
        campana.setNombre(nombre);
        campana.setDescripcion(descripcion);
        campana.setFechaInicio(fechaInicio);
        campana.setFechaFin(fechaFin);
        campana.setEstado(EstadoCampana.PENDIENTE);
        campana.setTipo("VACUNACION");
        campana.setTipoPromocion("NO_APLICA");
        campana.setGrupoObjetivo("GENERAL");
        campana.setTema("VACUNACION");
        campana.setTipoExamen("NO_APLICA");
        return campana;
    }
    
    public static Campana crearCampanaPrevencion(String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin) {
        Campana campana = new Campana();
        campana.setNombre(nombre);
        campana.setDescripcion(descripcion);
        campana.setFechaInicio(fechaInicio);
        campana.setFechaFin(fechaFin);
        campana.setEstado(EstadoCampana.PENDIENTE);
        campana.setTipo("PREVENCION");
        campana.setTipoPromocion("NO_APLICA");
        campana.setGrupoObjetivo("GENERAL");
        campana.setTema("PREVENCION");
        campana.setTipoExamen("NO_APLICA");
        return campana;
    }
    
    public static Campana crearCampanaDeteccion(String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin) {
        Campana campana = new Campana();
        campana.setNombre(nombre);
        campana.setDescripcion(descripcion);
        campana.setFechaInicio(fechaInicio);
        campana.setFechaFin(fechaFin);
        campana.setEstado(EstadoCampana.PENDIENTE);
        campana.setTipo("DETECCION");
        campana.setTipoPromocion("NO_APLICA");
        campana.setGrupoObjetivo("GENERAL");
        campana.setTema("DETECCION");
        campana.setTipoExamen("NO_APLICA");
        return campana;
    }
    
    public static Campana crearCampanaEducacion(String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin) {
        Campana campana = new Campana();
        campana.setNombre(nombre);
        campana.setDescripcion(descripcion);
        campana.setFechaInicio(fechaInicio);
        campana.setFechaFin(fechaFin);
        campana.setEstado(EstadoCampana.PENDIENTE);
        campana.setTipo("EDUCACION");
        campana.setTipoPromocion("NO_APLICA");
        campana.setGrupoObjetivo("GENERAL");
        campana.setTema("EDUCACION");
        campana.setTipoExamen("NO_APLICA");
        return campana;
    }
    
    public static Campana crearCampanaScreening(String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin) {
        Campana campana = new Campana();
        campana.setNombre(nombre);
        campana.setDescripcion(descripcion);
        campana.setFechaInicio(fechaInicio);
        campana.setFechaFin(fechaFin);
        campana.setEstado(EstadoCampana.PENDIENTE);
        campana.setTipo("SCREENING");
        campana.setTipoPromocion("NO_APLICA");
        campana.setGrupoObjetivo("GENERAL");
        campana.setTema("SCREENING");
        campana.setTipoExamen("NO_APLICA");
        return campana;
    }
    
    public static Campana crearCampanaInvestigacion(String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin) {
        Campana campana = new Campana();
        campana.setNombre(nombre);
        campana.setDescripcion(descripcion);
        campana.setFechaInicio(fechaInicio);
        campana.setFechaFin(fechaFin);
        campana.setEstado(EstadoCampana.PENDIENTE);
        campana.setTipo("INVESTIGACION");
        campana.setTipoPromocion("NO_APLICA");
        campana.setGrupoObjetivo("GENERAL");
        campana.setTema("INVESTIGACION");
        campana.setTipoExamen("NO_APLICA");
        return campana;
    }
}