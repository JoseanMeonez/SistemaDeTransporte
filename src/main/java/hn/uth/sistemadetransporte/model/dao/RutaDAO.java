
package hn.uth.sistemadetransporte.model.dao;


import hn.uth.sistemadetransporte.model.entities.Ruta;
import hn.uth.sistemadetransporte.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RutaDAO {

    public List<Ruta> listar() {
        List<Ruta> lista = new ArrayList<>();
        String sql = "SELECT * FROM rutas WHERE estado = 'Activo'";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Ruta r = new Ruta();
                r.setId(rs.getInt("id"));
                r.setNombreRuta(rs.getString("nombre_ruta"));
                r.setOrigen(rs.getString("origen"));
                r.setDestino(rs.getString("destino"));
                r.setDistanciaKm(rs.getDouble("distancia_km"));
                lista.add(r);
            }
        } catch (SQLException e) {
            System.err.println("Error en RutaDAO: " + e.getMessage());
        }
        return lista;
    }

    public void guardar(Ruta r) {
        String sql = "INSERT INTO rutas (nombre_ruta, origen, destino, distancia_km, estado) VALUES (?,?,?,?, 'Activo')";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, r.getNombreRuta());
            ps.setString(2, r.getOrigen());
            ps.setString(3, r.getDestino());
            ps.setDouble(4, r.getDistanciaKm());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("ERROR SQL AL GUARDAR RUTA: " + e.getMessage());
        }
    }

    public void editar(Ruta r) {
        String sql = "UPDATE rutas SET nombre_ruta=?, origen=?, destino=?, distancia_km=? WHERE id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, r.getNombreRuta());
            ps.setString(2, r.getOrigen());
            ps.setString(3, r.getDestino());
            ps.setDouble(4, r.getDistanciaKm());
            ps.setInt(5, r.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("ERROR SQL AL EDITAR RUTA: " + e.getMessage());
        }
    }

    public void inactivar(int id) {
        String sql = "UPDATE rutas SET estado='Inactivo' WHERE id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("ERROR SQL AL INACTIVAR RUTA: " + e.getMessage());
        }
    }
}