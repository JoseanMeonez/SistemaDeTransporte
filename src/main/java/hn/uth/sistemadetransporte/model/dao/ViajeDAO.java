package hn.uth.sistemadetransporte.model.dao;

import hn.uth.sistemadetransporte.model.entities.Viaje;
import hn.uth.sistemadetransporte.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ViajeDAO {

    public List<Viaje> listar() {
        List<Viaje> lista = new ArrayList<>();
        String sql = "SELECT vi.*, v.placa, c.nombre AS nombre_conductor, r.nombre_ruta " +
                "FROM viajes vi " +
                "INNER JOIN vehiculos v ON vi.id_vehiculo = v.id " +
                "INNER JOIN conductores c ON vi.id_conductor = c.id " +
                "INNER JOIN rutas r ON vi.id_ruta = r.id " +
                "WHERE vi.esta_activo = TRUE " +
                "ORDER BY vi.fecha_salida DESC";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Viaje viaje = new Viaje();
                viaje.setId(rs.getInt("id"));
                viaje.setIdVehiculo(rs.getInt("id_vehiculo"));
                viaje.setIdConductor(rs.getInt("id_conductor"));
                viaje.setIdRuta(rs.getInt("id_ruta"));
                viaje.setFechaSalida(rs.getTimestamp("fecha_salida"));
                viaje.setFechaLlegada(rs.getTimestamp("fecha_llegada"));
                viaje.setPlacaVehiculo(rs.getString("placa"));
                viaje.setNombreConductor(rs.getString("nombre_conductor"));
                viaje.setNombreRuta(rs.getString("nombre_ruta"));
                lista.add(viaje);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar viajes: " + e.getMessage());
        }

        return lista;
    }

    public void guardar(Viaje viaje) throws SQLException {
        String sql = "INSERT INTO viajes (id_vehiculo, id_conductor, id_ruta, fecha_salida, fecha_llegada, esta_activo) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, viaje.getIdVehiculo());
            ps.setInt(2, viaje.getIdConductor());
            ps.setInt(3, viaje.getIdRuta());
            ps.setTimestamp(4, new Timestamp(viaje.getFechaSalida().getTime()));
            if (viaje.getFechaLlegada() != null) {
                ps.setTimestamp(5, new Timestamp(viaje.getFechaLlegada().getTime()));
            } else {
                ps.setTimestamp(5, null);
            }
            ps.setBoolean(6, true);
            ps.executeUpdate();
        }
    }

    public void editar(Viaje viaje) throws SQLException {
        String sql = "UPDATE viajes SET id_vehiculo=?, id_conductor=?, id_ruta=?, fecha_salida=?, fecha_llegada=? WHERE id=?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, viaje.getIdVehiculo());
            ps.setInt(2, viaje.getIdConductor());
            ps.setInt(3, viaje.getIdRuta());
            ps.setTimestamp(4, new Timestamp(viaje.getFechaSalida().getTime()));
            if (viaje.getFechaLlegada() != null) {
                ps.setTimestamp(5, new Timestamp(viaje.getFechaLlegada().getTime()));
            } else {
                ps.setTimestamp(5, null);
            }
            ps.setInt(6, viaje.getId());
            ps.executeUpdate();
        }
    }

    public void inactivar(int id) throws SQLException {
        String sql = "UPDATE viajes SET esta_activo=? WHERE id=?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setBoolean(1, false);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }
}
