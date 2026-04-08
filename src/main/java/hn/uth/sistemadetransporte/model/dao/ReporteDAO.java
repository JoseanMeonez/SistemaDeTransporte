package hn.uth.sistemadetransporte.model.dao;

import hn.uth.sistemadetransporte.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReporteDAO {

    public Map<String, Integer> obtenerConteoPorMarca() {
        Map<String, Integer> datos = new HashMap<>();
        String sql = "SELECT marca, COUNT(*) as total FROM vehiculos WHERE esta_activo = TRUE GROUP BY marca";

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

    public Map<String, Double> obtenerCombustibleCargadoPorVehiculo() {
        Map<String, Double> datos = new LinkedHashMap<>();
        String sql = "SELECT v.placa, SUM(c.galones) as total_galones FROM combustible c " +
                "INNER JOIN vehiculos v ON c.id_vehiculo = v.id " +
                "WHERE v.esta_activo = TRUE GROUP BY v.placa ORDER BY SUM(c.galones) DESC";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                datos.put(rs.getString("placa"), rs.getDouble("total_galones"));
            }
        } catch (SQLException e) {
            System.err.println("Error en reporte de combustible: " + e.getMessage());
        }
        return datos;
    }
}
