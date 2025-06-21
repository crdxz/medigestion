package com.medigestion.mapper;

import com.medigestion.dto.*;
import com.medigestion.entity.*;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;
import java.util.List;

/**
 * Mapper para convertir entre entidades y DTOs
 * 
 * PATRÓN ADAPTADOR IMPLEMENTADO:
 * - Funcionalidad: Adapta entidades JPA a DTOs para la API
 * - Características: Convierte entre diferentes representaciones de datos
 * - Beneficios: Separación entre capa de datos y presentación
 * 
 * PATRÓN MÉTODO PLANTILLA IMPLEMENTADO:
 * - Funcionalidad: Define algoritmo de mapeo con pasos específicos
 * - Características: toDTO() define la plantilla, métodos privados implementan detalles
 * - Beneficios: Reutilización de lógica de mapeo, fácil extensión
 */
@Component
public class CampanaMapper {
    
    /**
     * PATRÓN ADAPTER: Convierte entidad Campana a DTO de respuesta
     * PATRÓN TEMPLATE METHOD: Estructura de mapeo definida
     */
    public CampanaResponseDTO toResponseDTO(Campana campana) {
        if (campana == null) {
            return null;
        }
        
        CampanaResponseDTO dto = new CampanaResponseDTO();
        dto.setId(campana.getId());
        dto.setNombre(campana.getNombre());
        dto.setDescripcion(campana.getDescripcion());
        dto.setFechaInicio(campana.getFechaInicio());
        dto.setFechaFin(campana.getFechaFin());
        dto.setEstado(campana.getEstado());
        dto.setTipoCampana(campana.getTipoCampana());
        dto.setTema(campana.getTema());
        dto.setTipoExamen(campana.getTipoExamen());
        dto.setFechaCreacion(campana.getFechaCreacion());
        dto.setFechaModificacion(campana.getFechaModificacion());
        
        return dto;
    }
    
    /**
     * PATRÓN ADAPTADOR: Convierte lista de entidades a lista de DTOs
     */
    public List<CampanaResponseDTO> toDTOList(List<Campana> campanas) {
        if (campanas == null) {
            return List.of();
        }
        return campanas.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * PATRÓN MÉTODO PLANTILLA: Implementación específica para Doctor
     * PATRÓN ADAPTADOR: Adapta entidad Doctor a DoctorDTO
     */
    private DoctorDTO toDoctorDTO(Doctor doctor) {
        if (doctor == null) {
            return null;
        }
        
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setNombre(doctor.getNombre() != null ? doctor.getNombre() : "");
        dto.setApellido(doctor.getApellido() != null ? doctor.getApellido() : "");
        dto.setEspecialidad(doctor.getEspecialidad() != null ? doctor.getEspecialidad() : "");
        dto.setLicencia(doctor.getLicencia() != null ? doctor.getLicencia() : "");
        return dto;
    }
    
    /**
     * PATRÓN MÉTODO PLANTILLA: Implementación específica para Empresa
     * PATRÓN ADAPTADOR: Adapta entidad Empresa a EmpresaDTO
     */
    private EmpresaDTO toEmpresaDTO(Empresa empresa) {
        if (empresa == null) {
            return null;
        }
        
        EmpresaDTO dto = new EmpresaDTO();
        dto.setId(empresa.getId());
        dto.setNombre(empresa.getNombre() != null ? empresa.getNombre() : "");
        dto.setNit(empresa.getNit() != null ? empresa.getNit() : "");
        dto.setEmail(empresa.getEmail() != null ? empresa.getEmail() : "");
        return dto;
    }
    
    /**
     * PATRÓN MÉTODO PLANTILLA: Implementación específica para Empleado
     * PATRÓN ADAPTADOR: Adapta entidad Empleado a EmpleadoDTO
     */
    private EmpleadoDTO toEmpleadoDTO(Empleado empleado) {
        if (empleado == null) {
            return null;
        }
        
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId(empleado.getId());
        dto.setNombre(empleado.getNombre() != null ? empleado.getNombre() : "");
        dto.setApellido(empleado.getApellido() != null ? empleado.getApellido() : "");
        dto.setCargo(empleado.getCargo() != null ? empleado.getCargo() : "");
        dto.setEmail(empleado.getEmail() != null ? empleado.getEmail() : "");
        return dto;
    }
    
    /**
     * PATRÓN MÉTODO PLANTILLA: Implementación específica para ExamenCampana
     * PATRÓN ADAPTADOR: Adapta entidad ExamenCampana a ExamenCampanaDTO
     */
    private ExamenCampanaDTO toExamenCampanaDTO(ExamenCampana examen) {
        if (examen == null) {
            return null;
        }
        
        ExamenCampanaDTO dto = new ExamenCampanaDTO();
        dto.setId(examen.getId());
        dto.setNombre(examen.getNombre() != null ? examen.getNombre() : "");
        dto.setDescripcion(examen.getDescripcion() != null ? examen.getDescripcion() : "");
        dto.setFechaCreacion(examen.getFechaCreacion());
        dto.setFechaActualizacion(examen.getFechaActualizacion());
        return dto;
    }
    
    /**
     * PATRÓN MÉTODO PLANTILLA: Implementación específica para ResultadoCampana
     * PATRÓN ADAPTADOR: Adapta entidad ResultadoCampana a ResultadoCampanaDTO
     */
    private ResultadoCampanaDTO toResultadoCampanaDTO(ResultadoCampana resultado) {
        if (resultado == null) {
            return null;
        }
        
        ResultadoCampanaDTO dto = new ResultadoCampanaDTO();
        dto.setId(resultado.getId());
        dto.setDescripcion(resultado.getDescripcion() != null ? resultado.getDescripcion() : "");
        dto.setFechaRegistro(resultado.getFechaRegistro());
        dto.setTipo(resultado.getTipo() != null ? resultado.getTipo() : "");
        dto.setObservaciones(resultado.getObservaciones() != null ? resultado.getObservaciones() : "");
        
        // PATRÓN ADAPTADOR: Adapta relación anidada
        if (resultado.getDoctor() != null) {
            dto.setDoctor(toDoctorDTO(resultado.getDoctor()));
        }
        
        return dto;
    }
    
    /**
     * PATRÓN MÉTODO PLANTILLA: Implementación específica para AccionCampana
     * PATRÓN ADAPTADOR: Adapta entidad AccionCampana a AccionCampanaDTO
     */
    private AccionCampanaDTO toAccionCampanaDTO(AccionCampana accion) {
        if (accion == null) {
            return null;
        }
        
        AccionCampanaDTO dto = new AccionCampanaDTO();
        dto.setId(accion.getId());
        dto.setDescripcion(accion.getDescripcion() != null ? accion.getDescripcion() : "");
        dto.setFechaAccion(accion.getFechaAccion());
        dto.setTipo(accion.getTipo() != null ? accion.getTipo() : "");
        dto.setObservaciones(accion.getObservaciones() != null ? accion.getObservaciones() : "");
        
        // PATRÓN ADAPTADOR: Adapta relaciones anidadas
        if (accion.getDoctor() != null) {
            dto.setDoctor(toDoctorDTO(accion.getDoctor()));
        }
        
        if (accion.getEmpleado() != null) {
            dto.setEmpleado(toEmpleadoDTO(accion.getEmpleado()));
        }
        
        return dto;
    }
} 