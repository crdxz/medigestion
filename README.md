# Módulo de Gestión de Campañas de Salud

Este módulo forma parte del sistema MediGestion IPS y se encarga de la gestión de campañas de salud ocupacional.

## Requisitos

- Java 11+
- PostgreSQL 12+
- Maven 3.6+

## Configuración

1. Crear una base de datos PostgreSQL llamada `medigestion`
2. Configurar las credenciales en `src/main/resources/application.properties`
3. Ejecutar la aplicación con `mvn spring-boot:run`

## Endpoints API

- `GET /api/campanas` - Listar todas las campañas
- `POST /api/campanas` - Crear nueva campaña
- `PUT /api/campanas/{id}/iniciar` - Iniciar campaña
- `PUT /api/campanas/{id}/finalizar` - Finalizar campaña
- `GET /api/campanas/estado/{estado}` - Buscar por estado

faltan endpoints 

## Estructura del Proyecto

El proyecto sigue una arquitectura en capas:
- **Entity**: Modelos de dominio
- **DAO**: Acceso a datos
- **Service**: Lógica de negocio
- **Controller**: Endpoints REST
- **Observer**: Patrón observer para notificaciones
- **Factory**: Patrón factory para creación de objetos

## Patrones de Diseño Implementados

Singleton - CacheSingleton.java (ya tenía comentarios)
Constructor - Campana.java - Constructores para crear instancias
Adaptador - CampanaMapper.java - Convierte entidades a DTOs
Decorador - Anotaciones Jackson en entidades y DTOs
Proxy - CampanaService.java - Cache transparente
Observador - ExamenCampana.java - Callbacks JPA
Estrategia - CampanaService.java - Diferentes estrategias de cache
Método Plantilla - CampanaMapper.java - Algoritmo de mapeo
Patrón Estado - Estados de campana
Patron Comando - Operaciones auditadas 


## Configuracion 

- Configuracion Maven 
    mvn clean install 

- Correr el programa 
    mvn spring-boot:run

- Comprobrar la instalacion de Maven  
    mvn -version 

- Ejecutar el programa si compilar Maven 
    java -jar target/medigestion-0.0.1-SNAPSHOT.jar

- npm start
    Starts the development server.

- npm run build
    Bundles the app into static files for production.

- npm test
    Starts the test runner.

- npm run eject
    Removes this tool and copies build dependencies, configuration files
    and scripts into the app directory. If you do this, you can’t go back!
