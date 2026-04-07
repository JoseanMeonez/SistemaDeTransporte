package hn.uth.sistemadetransporte.model.dao;

import hn.uth.sistemadetransporte.model.entities.Conductor;
import hn.uth.sistemadetransporte.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConductorDAO {

    public List<Conductor> listar() {
        List<Conductor> lista = new ArrayList<>();
        String sql = "SELECT * FROM conductores WHERE esta_activo = TRUE";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Conductor c = new Conductor();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setDni(rs.getString("dni"));
                c.setLicencia(rs.getString("licencia"));
                c.setTelefono(rs.getString("telefono"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error Listar Conductores: " + e.getMessage());
        }
        return lista;
    }

    public void guardar(Conductor c) throws SQLException {
        String sql = "INSERT INTO conductores (nombre, dni, licencia, telefono, esta_activo) VALUES (?,?,?,?,?)";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDni());
            ps.setString(3, c.getLicencia());
            ps.setString(4, c.getTelefono());
            ps.setBoolean(5, true);
            ps.executeUpdate();
        }
    }

    public void editar(Conductor c) throws SQLException {
        String sql = "UPDATE conductores SET nombre=?, dni=?, licencia=?, telefono=? WHERE id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDni());
            ps.setString(3, c.getLicencia());
            ps.setString(4, c.getTelefono());
            ps.setInt(5, c.getId());
            ps.executeUpdate();
        }
    }

    public void inactivar(int id) throws SQLException {
        String sql = "UPDATE conductores SET esta_activo=? WHERE id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setBoolean(1, false);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }
}
