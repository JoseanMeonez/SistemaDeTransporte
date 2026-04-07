
package hn.uth.sistemadetransporte.controller;

import hn.uth.sistemadetransporte.model.dao.MantenimientoDAO;
import hn.uth.sistemadetransporte.model.dao.VehiculoDAO;
import hn.uth.sistemadetransporte.model.entities.Mantenimiento;
import hn.uth.sistemadetransporte.model.entities.Vehiculo;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
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
        listaMantenimientos = dao.listar();
        listaVehiculos = vDao.listar(); // Traemos los vehículos existentes
    }

    public void registrar() {
        dao.guardar(mantenimiento);
        mantenimiento = new Mantenimiento();
        listaMantenimientos = dao.listar();
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
