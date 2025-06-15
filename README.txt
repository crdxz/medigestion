Configuracion Maven 
    mvn clean install 

Correr el programa 
    mvn spring-boot:run

Comprobrar la instalacion de Maven  
    mvn -version 

Ejecutar el programa si compilar Maven 
    java -jar target/medigestion-0.0.1-SNAPSHOT.jar


EndPoints: 
POST /api/campanas/examenes-obligatorios - Crea una campaña de exámenes obligatorios
POST /api/campanas/especifica - Crea una campaña específica para un tipo de examen
POST /api/campanas/promocional - Crea una campaña promocional
POST /api/campanas/preventiva - Crea una campaña preventiva
POST /api/campanas/educativa - Crea una campaña educativa


  npm start
    Starts the development server.

  npm run build
    Bundles the app into static files for production.

  npm test
    Starts the test runner.

  npm run eject
    Removes this tool and copies build dependencies, configuration files
    and scripts into the app directory. If you do this, you can’t go back!