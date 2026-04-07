package hn.uth.sistemadetransporte.controller;

import hn.uth.sistemadetransporte.model.dao.ConductorDAO;
import hn.uth.sistemadetransporte.model.entities.Conductor;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ConductorBean implements Serializable {
    private List<Conductor> listaConductores;
    private Conductor conductor = new Conductor();
    private ConductorDAO dao = new ConductorDAO();

    @PostConstruct
    public void init() {
        listar();
    }

    public void listar() {
        listaConductores = dao.listar();
    }

    public void prepararNuevo() {
        this.conductor = new Conductor();
    }

    public void guardar() {
        try {
            if (conductor.getId() == 0) dao.guardar(conductor);
            else dao.editar(conductor);
            listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Éxito", "Conductor guardado correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public void eliminar(int id) {
        try {
            dao.inactivar(id);
            listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Eliminado", "Registro inactivado"));
        } catch (Exception e) {
        }
    }

    // Getters y Setters
    public List<Conductor> getListaConductores() {
        return listaConductores;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }
}