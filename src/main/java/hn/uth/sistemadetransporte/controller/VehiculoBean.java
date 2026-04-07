package hn.uth.sistemadetransporte.controller;

import hn.uth.sistemadetransporte.model.dao.VehiculoDAO;
import hn.uth.sistemadetransporte.model.entities.Vehiculo;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class VehiculoBean implements Serializable {
    private List<Vehiculo> listaVehiculos;
    private Vehiculo vehiculo = new Vehiculo();
    private VehiculoDAO dao = new VehiculoDAO();

    @PostConstruct
    public void init() {
        listar();
    }

    public void listar() {
        listaVehiculos = dao.listar();
    }

    public void prepararNuevo() {
        this.vehiculo = new Vehiculo();
    }

    public void guardar() {
        try {
            if (vehiculo.getId() == 0) dao.guardar(vehiculo);
            else dao.editar(vehiculo);

            listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Éxito", "Vehículo guardado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public void eliminar(int id) {
        try {
            dao.inactivar(id);
            listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Eliminado", "Vehículo inactivado"));
        } catch (Exception e) { /* Manejar error */ }
    }

    // Getters y Setters
    public List<Vehiculo> getListaVehiculos() {
        return listaVehiculos;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
}