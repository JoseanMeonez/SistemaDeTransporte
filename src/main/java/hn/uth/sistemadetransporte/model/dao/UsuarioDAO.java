package hn.uth.sistemadetransporte.model.dao;

import hn.uth.sistemadetransporte.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public boolean login(String user, String pass) {
        String sql = "SELECT 1 FROM usuarios WHERE username = ? AND password = ?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.err.println("Error en login DAO: " + e.getMessage());
        }
        return false;
    }
}
