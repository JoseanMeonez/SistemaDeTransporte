
package hn.uth.sistemadetransporte.controller;

import hn.uth.sistemadetransporte.model.dao.CombustibleDAO;
import hn.uth.sistemadetransporte.model.dao.VehiculoDAO;
import hn.uth.sistemadetransporte.model.entities.*;
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
public class CombustibleBean implements Serializable {
    private Combustible combustible = new Combustible();
    private List<Combustible> listaCombustible;
    private List<Vehiculo> listaVehiculos;

    private CombustibleDAO dao = new CombustibleDAO();
    private VehiculoDAO vDao = new VehiculoDAO();

    @PostConstruct
    public void init() {
        listar();
        cargarVehiculos();
    }

    public void listar() {
        listaCombustible = dao.listar();
    }

    public void cargarVehiculos() {
        listaVehiculos = vDao.listar();
    }

    public void calcularTotal() {
        combustible.setTotalPagado(combustible.getGalones() * combustible.getPrecioGalon());
    }

    public void prepararNuevo() {
        combustible = new Combustible();
        combustible.setFecha(new Date());
    }

    public void guardar() {
        if (combustible.getFecha() == null) {
            combustible.setFecha(new Date());
        }
        calcularTotal();

        try {
            if (combustible.getId() == 0) {
                dao.guardar(combustible);
            } else {
                dao.editar(combustible);
            }
            listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Exito", "Registro de combustible guardado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public void eliminar(int id) {
        try {
            dao.eliminar(id);
            listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Eliminado", "Registro de combustible eliminado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public void registrar() {
        guardar();
        prepararNuevo();
    }

    public Combustible getCombustible() {
        return combustible;
    }

    public List<Combustible> getListaCombustible() {
        return listaCombustible;
    }

    public List<Vehiculo> getListaVehiculos() {
        return listaVehiculos;
    }

    public void setCombustible(Combustible combustible) {
        this.combustible = combustible;
    }
}
