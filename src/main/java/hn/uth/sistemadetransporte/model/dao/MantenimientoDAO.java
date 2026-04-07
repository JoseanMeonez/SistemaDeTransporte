/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hn.uth.sistemadetransporte.model.dao;

import hn.uth.sistemadetransporte.model.entities.Mantenimiento;
import hn.uth.sistemadetransporte.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MantenimientoDAO {

    public List<Mantenimiento> listar() {
        List<Mantenimiento> lista = new ArrayList<>();
        String sql = "SELECT m.*, v.placa FROM mantenimiento m " +
                "INNER JOIN vehiculos v ON m.id_vehiculo = v.id";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Mantenimiento m = new Mantenimiento();
                m.setId(rs.getInt("id"));
                m.setFechaMantenimiento(rs.getDate("fecha_mantenimiento"));
                m.setCosto(rs.getDouble("costo"));
                m.setTaller(rs.getString("taller"));
                m.setPlacaVehiculo(rs.getString("placa"));
                lista.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


    public void guardar(Mantenimiento m) {
        String sql = "INSERT INTO mantenimiento (id_vehiculo, fecha_mantenimiento, descripcion, costo, taller) VALUES (?,?,?,?,?)";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, m.getIdVehiculo());

            // Conversión de fecha de Java a SQL
            if (m.getFechaMantenimiento() != null) {
                ps.setDate(2, new Date(m.getFechaMantenimiento().getTime()));
            } else {
                ps.setDate(2, new Date(System.currentTimeMillis())); // Fecha actual si viene nula
            }

            ps.setString(3, m.getDescripcion());
            ps.setDouble(4, m.getCosto());
            ps.setString(5, m.getTaller());

            int resultado = ps.executeUpdate();
            if (resultado > 0) {
                System.out.println("Mantenimiento registrado con éxito para el vehículo ID: " + m.getIdVehiculo());
            }

        } catch (SQLException e) {
            System.err.println("Error al insertar mantenimiento: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
