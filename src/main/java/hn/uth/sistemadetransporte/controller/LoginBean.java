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
    private String usuarioAutenticado;
    private UsuarioDAO dao = new UsuarioDAO();

    public String autenticar() {
        if (dao.login(username, password)) {
            usuarioAutenticado = username;
            password = null;
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                    .put("usuarioAutenticado", usuarioAutenticado);
            return "menu?faces-redirect=true";
        } else {
            usuarioAutenticado = null;
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                    .remove("usuarioAutenticado");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario o clave incorrectos"));
            return null;
        }
    }

    public String cerrarSesion() {
        usuarioAutenticado = null;
        username = null;
        password = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }

    public String redirigirSiAutenticado() {
        if (isAutenticado()) {
            return "menu?faces-redirect=true";
        }
        return null;
    }

    public boolean isAutenticado() {
        if (usuarioAutenticado != null && !usuarioAutenticado.isBlank()) {
            return true;
        }

        FacesContext context = FacesContext.getCurrentInstance();
        if (context == null) {
            return false;
        }

        Object usuarioSesion = context.getExternalContext().getSessionMap().get("usuarioAutenticado");
        if (usuarioSesion instanceof String) {
            String usuario = (String) usuarioSesion;
            if (!usuario.isBlank()) {
                usuarioAutenticado = usuario;
                return true;
            }
        }

        return false;
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

    public String getUsuarioAutenticado() {
        return usuarioAutenticado;
    }
}
