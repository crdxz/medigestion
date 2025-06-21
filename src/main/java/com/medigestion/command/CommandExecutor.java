package com.medigestion.command;

import com.medigestion.entity.Campana;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;

/**
 * PATRÓN COMANDO: Ejecutor de comandos con auditoría
 * - Funcionalidad: Ejecuta comandos y mantiene historial
 * - Características: Logging, auditoría, manejo de errores
 * - Beneficios: Trazabilidad completa de operaciones
 */
@Slf4j
@Component
public class CommandExecutor {
    
    private final List<CampanaCommand> historialComandos = new ArrayList<>();
    private final List<CampanaCommand> comandosDeshechos = new ArrayList<>();
    
    /**
     * PATRÓN COMANDO: Ejecuta un comando y lo registra en el historial
     * @param comando Comando a ejecutar
     * @return Resultado de la ejecución
     */
    public Campana executeCommand(CampanaCommand comando) {
        try {
            // PATRÓN COMANDO: Validación antes de ejecutar
            if (!comando.canExecute()) {
                throw new IllegalStateException("El comando no puede ser ejecutado: " + comando.getDescription());
            }
            
            // PATRÓN COMANDO: Logging de la operación
            log.info("Ejecutando comando: {}", comando.getDescription());
            
            // PATRÓN COMANDO: Ejecución del comando
            Campana resultado = comando.execute();
            
            // PATRÓN COMANDO: Registro en historial para auditoría
            historialComandos.add(comando);
            comandosDeshechos.clear(); // Limpiar comandos deshechos al ejecutar uno nuevo
            
            log.info("Comando ejecutado exitosamente: {}", comando.getDescription());
            
            return resultado;
            
        } catch (Exception e) {
            log.error("Error ejecutando comando: {}", comando.getDescription(), e);
            throw e;
        }
    }
    
    /**
     * PATRÓN COMANDO: Deshace el último comando ejecutado
     * @return Resultado de deshacer la operación
     */
    public Campana undoLastCommand() {
        if (historialComandos.isEmpty()) {
            throw new IllegalStateException("No hay comandos para deshacer");
        }
        
        CampanaCommand ultimoComando = historialComandos.remove(historialComandos.size() - 1);
        
        try {
            log.info("Deshaciendo comando: {}", ultimoComando.getDescription());
            
            Campana resultado = ultimoComando.undo();
            comandosDeshechos.add(ultimoComando);
            
            log.info("Comando deshecho exitosamente: {}", ultimoComando.getDescription());
            
            return resultado;
            
        } catch (Exception e) {
            log.error("Error deshaciendo comando: {}", ultimoComando.getDescription(), e);
            // Reinsertar el comando en el historial si falla el undo
            historialComandos.add(ultimoComando);
            throw e;
        }
    }
    
    /**
     * PATRÓN COMANDO: Obtiene el historial de comandos para auditoría
     * @return Lista de comandos ejecutados
     */
    public List<CampanaCommand> getHistorialComandos() {
        return new ArrayList<>(historialComandos);
    }
    
    /**
     * PATRÓN COMANDO: Obtiene comandos deshechos
     * @return Lista de comandos deshechos
     */
    public List<CampanaCommand> getComandosDeshechos() {
        return new ArrayList<>(comandosDeshechos);
    }
    
    /**
     * PATRÓN COMANDO: Limpia el historial de comandos
     */
    public void limpiarHistorial() {
        historialComandos.clear();
        comandosDeshechos.clear();
        log.info("Historial de comandos limpiado");
    }
} 