package hn.uth.sistemadetransporte.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // RUTA DEL ARCHIVO .ACCDB
    private static final String ARCHIVO_ACCESS = "C:/Users/andym/Documents/GitHub/SistemaDeTransporte/sistema_transporte.accdb";
    
    // URL DE CONEXIÓN PARA UCANACCESS
    private static final String URL = "jdbc:ucanaccess://" + ARCHIVO_ACCESS;
    
    // DRIVER DE UCANACCESS
    private static final String DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";

    public static Connection conectar() {
        Connection conexion = null;
        try {
            Class.forName(DRIVER);
            
            conexion = DriverManager.getConnection(URL);
            
            System.out.println("Conexión establecida con Microsoft Access: " + ARCHIVO_ACCESS);
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el Driver de UCanAccess: " + e.getMessage());
            System.err.println("Asegúrate de agregar los JARs de UCanAccess a las librerías del proyecto.");
        } catch (SQLException e) {
            System.err.println("Error de SQL al conectar a Access: " + e.getMessage());
            System.err.println("Verifica que la ruta del archivo sea correcta y que el archivo NO esté abierto.");
        }
        return conexion;
    }
}
