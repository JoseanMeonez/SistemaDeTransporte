
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
        String sql = "INSERT INTO rutas (nombre_ruta, origen, destino, distancia_km) VALUES (?,?,?,?)";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            System.out.println("Intentando guardar ruta: " + r.getNombreRuta()); // TESTIGO 1

            ps.setString(1, r.getNombreRuta());
            ps.setString(2, r.getOrigen());
            ps.setString(3, r.getDestino());
            ps.setDouble(4, r.getDistanciaKm());

            int filas = ps.executeUpdate();
            System.out.println("Filas insertadas: " + filas); // TESTIGO 2

        } catch (SQLException e) {
            System.err.println("ERROR SQL AL GUARDAR: " + e.getMessage());
            e.printStackTrace(); // Esto te dirá exactamente qué columna falla
        }
    }
}