package com.medigestion.mapper;

import com.medigestion.dto.*;
import com.medigestion.entity.*;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;
import java.util.List;

@Component
public class CampanaMapper {
    
    public CampanaResponseDTO toDTO(Campana campana) {
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
        dto.setTipo(campana.getTipo() != null ? campana.getTipo() : "");
        dto.setTipoPromocion(campana.getTipoPromocion() != null ? campana.getTipoPromocion() : "");
        dto.setGrupoObjetivo(campana.getGrupoObjetivo() != null ? campana.getGrupoObjetivo() : "");
        dto.setTema(campana.getTema() != null ? campana.getTema() : "");
        dto.setTipoExamen(campana.getTipoExamen() != null ? campana.getTipoExamen() : "");
        
        // Mapear relaciones
        if (campana.getDoctor() != null) {
            dto.setDoctor(toDoctorDTO(campana.getDoctor()));
        }
        
        if (campana.getEmpresa() != null) {
            dto.setEmpresa(toEmpresaDTO(campana.getEmpresa()));
        }
        
        if (campana.getCoordinador() != null) {
            dto.setCoordinador(toEmpleadoDTO(campana.getCoordinador()));
        }
        
        // Mapear listas
        if (campana.getExamenes() != null) {
            dto.setExamenes(campana.getExamenes().stream()
                .map(this::toExamenCampanaDTO)
                .collect(Collectors.toList()));
        } else {
            dto.setExamenes(List.of());
        }
        
        if (campana.getResultados() != null) {
            dto.setResultados(campana.getResultados().stream()
                .map(this::toResultadoCampanaDTO)
                .collect(Collectors.toList()));
        } else {
            dto.setResultados(List.of());
        }
        
        if (campana.getAcciones() != null) {
            dto.setAcciones(campana.getAcciones().stream()
                .map(this::toAccionCampanaDTO)
                .collect(Collectors.toList()));
        } else {
            dto.setAcciones(List.of());
        }
        
        return dto;
    }
    
    public List<CampanaResponseDTO> toDTOList(List<Campana> campanas) {
        if (campanas == null) {
            return List.of();
        }
        return campanas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
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
        
        if (resultado.getDoctor() != null) {
            dto.setDoctor(toDoctorDTO(resultado.getDoctor()));
        }
        
        return dto;
    }
    
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
        
        if (accion.getDoctor() != null) {
            dto.setDoctor(toDoctorDTO(accion.getDoctor()));
        }
        
        if (accion.getEmpleado() != null) {
            dto.setEmpleado(toEmpleadoDTO(accion.getEmpleado()));
        }
        
        return dto;
    }
} 