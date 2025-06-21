package com.medigestion.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Singleton para manejar cache de datos en memoria
 * 
 * PATRÓN SINGLETON IMPLEMENTADO:
 * - Tipo: Enum Pattern (Singleton con Enum)
 * - Ventajas: Thread-safe por defecto, serialización automática, 
 *   protección contra reflexión y múltiples instancias
 * - Uso: Cache centralizado para toda la aplicación
 * 
 * CARACTERÍSTICAS DEL PATRÓN:
 * - INSTANCE: Instancia única garantizada por el enum
 * - Constructor privado implícito en enum
 * - Thread-safe sin sincronización adicional
 * - Inicialización eager (se crea al cargar la clase)
 */
public enum CacheSingleton {
    
    // PATRÓN SINGLETON: Instancia única garantizada por el enum
    INSTANCE;
    
    // Cache principal usando ConcurrentHashMap para thread-safety
    private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();
    
    // Configuración del cache
    private static final int DEFAULT_TTL_MINUTES = 30;
    private static final int MAX_CACHE_SIZE = 1000;
    
    /**
     * Clase interna para representar una entrada del cache
     * Encapsula los datos y la lógica de expiración
     */
    private static class CacheEntry {
        private final Object data;
        private final LocalDateTime expirationTime;
        
        public CacheEntry(Object data, int ttlMinutes) {
            this.data = data;
            this.expirationTime = LocalDateTime.now().plusMinutes(ttlMinutes);
        }
        
        public Object getData() {
            return data;
        }
        
        public boolean isExpired() {
            return LocalDateTime.now().isAfter(expirationTime);
        }
        
        public long getTimeToExpire() {
            return ChronoUnit.MINUTES.between(LocalDateTime.now(), expirationTime);
        }
    }
    
    /**
     * Almacena un valor en el cache
     * PATRÓN SINGLETON: Acceso a la instancia única
     */
    public void put(String key, Object value) {
        put(key, value, DEFAULT_TTL_MINUTES);
    }
    
    /**
     * Almacena un valor en el cache con TTL personalizado
     * PATRÓN SINGLETON: Método de la instancia única
     */
    public void put(String key, Object value, int ttlMinutes) {
        // Limpiar cache si está lleno
        if (cache.size() >= MAX_CACHE_SIZE) {
            cleanExpiredEntries();
            if (cache.size() >= MAX_CACHE_SIZE) {
                // Si aún está lleno, eliminar la entrada más antigua
                removeOldestEntry();
            }
        }
        
        cache.put(key, new CacheEntry(value, ttlMinutes));
    }
    
    /**
     * Obtiene un valor del cache
     * PATRÓN SINGLETON: Acceso centralizado a los datos cacheados
     */
    public Object get(String key) {
        CacheEntry entry = cache.get(key);
        
        if (entry == null) {
            return null;
        }
        
        if (entry.isExpired()) {
            cache.remove(key);
            return null;
        }
        
        return entry.getData();
    }
    
    /**
     * Verifica si una clave existe en el cache
     * PATRÓN SINGLETON: Verificación centralizada
     */
    public boolean containsKey(String key) {
        CacheEntry entry = cache.get(key);
        if (entry == null || entry.isExpired()) {
            if (entry != null) {
                cache.remove(key);
            }
            return false;
        }
        return true;
    }
    
    /**
     * Elimina una entrada del cache
     * PATRÓN SINGLETON: Operación centralizada
     */
    public void remove(String key) {
        cache.remove(key);
    }
    
    /**
     * Limpia todas las entradas expiradas
     * PATRÓN SINGLETON: Mantenimiento centralizado del cache
     */
    public void cleanExpiredEntries() {
        cache.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }
    
    /**
     * Limpia todo el cache
     * PATRÓN SINGLETON: Control centralizado del estado
     */
    public void clear() {
        cache.clear();
    }
    
    /**
     * Obtiene el tamaño del cache
     * PATRÓN SINGLETON: Información centralizada
     */
    public int size() {
        cleanExpiredEntries();
        return cache.size();
    }
    
    /**
     * Obtiene estadísticas del cache
     * PATRÓN SINGLETON: Métricas centralizadas
     */
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("size", size());
        stats.put("maxSize", MAX_CACHE_SIZE);
        stats.put("defaultTTL", DEFAULT_TTL_MINUTES);
        
        return stats;
    }
    
    /**
     * Elimina la entrada más antigua del cache
     * PATRÓN SINGLETON: Lógica de limpieza centralizada
     */
    private void removeOldestEntry() {
        String oldestKey = null;
        LocalDateTime oldestTime = LocalDateTime.now();
        
        for (Map.Entry<String, CacheEntry> entry : cache.entrySet()) {
            if (entry.getValue().expirationTime.isBefore(oldestTime)) {
                oldestTime = entry.getValue().expirationTime;
                oldestKey = entry.getKey();
            }
        }
        
        if (oldestKey != null) {
            cache.remove(oldestKey);
        }
    }
    
    /**
     * Métodos específicos para campañas
     * PATRÓN SINGLETON: Funcionalidad específica del dominio
     */
    public void cacheCampana(Long id, Object campana) {
        put("campana:" + id, campana, 60); // Cache por 1 hora
    }
    
    public Object getCampana(Long id) {
        return get("campana:" + id);
    }
    
    public void cacheListaCampanas(String key, Object campanas) {
        put("lista:" + key, campanas, 30); // Cache por 30 minutos
    }
    
    public Object getListaCampanas(String key) {
        return get("lista:" + key);
    }
} 