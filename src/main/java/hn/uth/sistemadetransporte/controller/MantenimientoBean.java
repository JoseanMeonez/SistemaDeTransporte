
package hn.uth.sistemadetransporte.controller;

import hn.uth.sistemadetransporte.model.dao.MantenimientoDAO;
import hn.uth.sistemadetransporte.model.dao.VehiculoDAO;
import hn.uth.sistemadetransporte.model.entities.Mantenimiento;
import hn.uth.sistemadetransporte.model.entities.Vehiculo;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
public class MantenimientoBean implements Serializable {
    private Mantenimiento mantenimiento = new Mantenimiento();
    private List<Mantenimiento> listaMantenimientos;
    private List<Vehiculo> listaVehiculos; // Para el combo box

    private MantenimientoDAO dao = new MantenimientoDAO();
    private VehiculoDAO vDao = new VehiculoDAO();

    @PostConstruct
    public void init() {
        listar();
        cargarVehiculos();
    }

    public void listar() {
        listaMantenimientos = dao.listar();
    }

    public void cargarVehiculos() {
        listaVehiculos = vDao.listar();
    }

    public void prepararNuevo() {
        mantenimiento = new Mantenimiento();
        mantenimiento.setFechaMantenimiento(new Date());
    }

    public void guardar() {
        if (mantenimiento.getFechaMantenimiento() == null) {
            mantenimiento.setFechaMantenimiento(new Date());
        }

        try {
            if (mantenimiento.getId() == 0) {
                dao.guardar(mantenimiento);
            } else {
                dao.editar(mantenimiento);
            }
            listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Exito", "Mantenimiento guardado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public void eliminar(int id) {
        try {
            dao.eliminar(id);
            listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Eliminado", "Mantenimiento eliminado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public void registrar() {
        guardar();
        prepararNuevo();
    }
    // Getters y Setters...

    public Mantenimiento getMantenimiento() {
        return mantenimiento;
    }

    public List<Mantenimiento> getListaMantenimientos() {
        return listaMantenimientos;
    }

    public List<Vehiculo> getListaVehiculos() {
        return listaVehiculos;
    }

    public void setMantenimiento(Mantenimiento mantenimiento) {
        this.mantenimiento = mantenimiento;
    }
}
