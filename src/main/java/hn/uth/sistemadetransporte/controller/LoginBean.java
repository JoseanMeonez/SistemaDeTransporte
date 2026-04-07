package hn.uth.sistemadetransporte.controller;

import hn.uth.sistemadetransporte.model.dao.UsuarioDAO;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {
    private String username;
    private String password;
    private UsuarioDAO dao = new UsuarioDAO();

    public String autenticar() {
        if (dao.login(username, password)) {
            // Si es correcto, redirigimos al menú o dashboard
            return "menu?faces-redirect=true";
        } else {
            // Si es incorrecto, enviamos un mensaje de error a PrimeFaces
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario o clave incorrectos"));
            return null;
        }
    }

    public String cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }

    // Getters y Setters para username y password
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
