package com.medigestion;

import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnectionTest {
    
    @Test
    public void testConnection() {
        String url = "jdbc:postgresql://localhost:5432/medigestion";
        String user = "postgres";
        String password = "admin";
        
        try {
            System.out.println("Intentando conectar a la base de datos...");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("¡Conexión exitosa!");
            conn.close();
        } catch (Exception e) {
            System.err.println("Error al conectar a la base de datos:");
            e.printStackTrace();
        }
    }
} 