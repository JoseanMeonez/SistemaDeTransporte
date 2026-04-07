package hn.uth.sistemadetransporte.model.dao;

import hn.uth.sistemadetransporte.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ReporteDAO {

    public Map<String, Integer> obtenerConteoPorMarca() {
        Map<String, Integer> datos = new HashMap<>();
        String sql = "SELECT marca, COUNT(*) as total FROM vehiculos WHERE estado = 'Activo' GROUP BY marca";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                datos.put(rs.getString("marca"), rs.getInt("total"));
            }
        } catch (SQLException e) {
            System.err.println("Error en reporte: " + e.getMessage());
        }
        return datos;
    }
}
