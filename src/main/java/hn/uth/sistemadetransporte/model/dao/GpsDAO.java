package hn.uth.sistemadetransporte.model.dao;

import hn.uth.sistemadetransporte.model.entities.Gps;
import hn.uth.sistemadetransporte.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class GpsDAO {

    public List<Gps> listar() {
        List<Gps> lista = new ArrayList<>();
        String sql = "SELECT g.*, v.placa FROM seguimiento_gps g " +
                "INNER JOIN vehiculos v ON g.id_vehiculo = v.id " +
                "ORDER BY g.ultima_actualizacion DESC";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Gps gps = new Gps();
                gps.setId(rs.getInt("id"));
                gps.setIdVehiculo(rs.getInt("id_vehiculo"));
                gps.setUltimaActualizacion(rs.getTimestamp("ultima_actualizacion"));
                gps.setLatitud(rs.getDouble("latitud"));
                gps.setLongitud(rs.getDouble("longitud"));
                gps.setPlacaVehiculo(rs.getString("placa"));
                lista.add(gps);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar registros GPS: " + e.getMessage());
        }

        return lista;
    }

    public void guardar(Gps gps) throws SQLException {
        String sql = "INSERT INTO seguimiento_gps (id_vehiculo, latitud, longitud, ultima_actualizacion) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, gps.getIdVehiculo());
            ps.setDouble(2, gps.getLatitud());
            ps.setDouble(3, gps.getLongitud());
            ps.setTimestamp(4, new Timestamp(gps.getUltimaActualizacion().getTime()));
            ps.executeUpdate();
        }
    }

    public void editar(Gps gps) throws SQLException {
        String sql = "UPDATE seguimiento_gps SET id_vehiculo=?, latitud=?, longitud=?, ultima_actualizacion=? WHERE id=?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, gps.getIdVehiculo());
            ps.setDouble(2, gps.getLatitud());
            ps.setDouble(3, gps.getLongitud());
            ps.setTimestamp(4, new Timestamp(gps.getUltimaActualizacion().getTime()));
            ps.setInt(5, gps.getId());
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM seguimiento_gps WHERE id=?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
