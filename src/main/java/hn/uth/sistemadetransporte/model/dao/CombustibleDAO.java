
package hn.uth.sistemadetransporte.model.dao;

import hn.uth.sistemadetransporte.model.entities.Combustible;
import hn.uth.sistemadetransporte.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CombustibleDAO {

    public List<Combustible> listar() {
        List<Combustible> lista = new ArrayList<>();
        String sql = "SELECT c.*, v.placa FROM combustible c " +
                "INNER JOIN vehiculos v ON c.id_vehiculo = v.id ORDER BY c.fecha DESC";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Combustible c = new Combustible();
                c.setId(rs.getInt("id"));
                c.setFecha(rs.getDate("fecha"));
                c.setTipoCombustible(rs.getString("tipo_combustible"));
                c.setGalones(rs.getDouble("galones"));
                c.setTotalPagado(rs.getDouble("total_pagado"));
                c.setPlacaVehiculo(rs.getString("placa"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void guardar(Combustible c) {
        String sql = "INSERT INTO combustible (id_vehiculo, fecha, tipo_combustible, galones, precio_galon, total_pagado, kilometraje_actual) VALUES (?,?,?,?,?,?,?)";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, c.getIdVehiculo());
            ps.setDate(2, new Date(c.getFecha().getTime()));
            ps.setString(3, c.getTipoCombustible());
            ps.setDouble(4, c.getGalones());
            ps.setDouble(5, c.getPrecioGalon());
            ps.setDouble(6, c.getTotalPagado());
            ps.setInt(7, c.getKilometrajeActual());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
