package hn.uth.sistemadetransporte.controller;

import hn.uth.sistemadetransporte.model.dao.RutaDAO;
import hn.uth.sistemadetransporte.model.entities.Ruta;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class RutaBean implements Serializable {
    private List<Ruta> listaRutas;
    private Ruta ruta = new Ruta();
    private RutaDAO dao = new RutaDAO();

    @PostConstruct
    public void init() {
        listar();
    }

    public void listar() {
        listaRutas = dao.listar();
    }

    public void prepararNuevo() {
        ruta = new Ruta();
    }

    public void guardar() {
        try {
            if (ruta.getId() == 0) {
                dao.guardar(ruta);
            } else {
                dao.editar(ruta);
            }
            listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Exito", "Ruta guardada"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public void eliminar(int id) {
        try {
            dao.inactivar(id);
            listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Eliminado", "Ruta inactivada"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public void registrar() {
        guardar();
        prepararNuevo();
    }

    // Getters y Setters (Importante para que JSF encuentre las propiedades)
    public List<Ruta> getListaRutas() {
        return listaRutas;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }
}