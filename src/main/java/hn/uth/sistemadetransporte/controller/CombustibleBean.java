
package hn.uth.sistemadetransporte.controller;

import hn.uth.sistemadetransporte.model.dao.CombustibleDAO;
import hn.uth.sistemadetransporte.model.dao.VehiculoDAO;
import hn.uth.sistemadetransporte.model.entities.*;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
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
        listaCombustible = dao.listar();
        listaVehiculos = vDao.listar();
    }

    public void calcularTotal() {
        combustible.setTotalPagado(combustible.getGalones() * combustible.getPrecioGalon());
    }

    public void registrar() {
        dao.guardar(combustible);
        combustible = new Combustible();
        listaCombustible = dao.listar();
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
