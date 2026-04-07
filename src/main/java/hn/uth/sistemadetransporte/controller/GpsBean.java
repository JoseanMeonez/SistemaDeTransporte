package hn.uth.sistemadetransporte.controller;

import hn.uth.sistemadetransporte.model.dao.GpsDAO;
import hn.uth.sistemadetransporte.model.dao.VehiculoDAO;
import hn.uth.sistemadetransporte.model.entities.Gps;
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
public class GpsBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Gps> listaGps;
    private List<Vehiculo> listaVehiculos;
    private Gps gps = new Gps();

    private final GpsDAO dao = new GpsDAO();
    private final VehiculoDAO vehiculoDAO = new VehiculoDAO();

    @PostConstruct
    public void init() {
        listar();
        cargarVehiculos();
    }

    public void listar() {
        listaGps = dao.listar();
    }

    public void cargarVehiculos() {
        listaVehiculos = vehiculoDAO.listar();
    }

    public void prepararNuevo() {
        gps = new Gps();
        gps.setUltimaActualizacion(new Date());
    }

    public void guardar() {
        if (!validarDatos()) {
            return;
        }

        try {
            if (gps.getId() == 0) {
                dao.guardar(gps);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Exito", "Registro GPS creado"));
            } else {
                dao.editar(gps);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Exito", "Registro GPS actualizado"));
            }

            listar();
            prepararNuevo();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public void eliminar(int id) {
        try {
            dao.eliminar(id);
            listar();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Eliminado", "Registro GPS eliminado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    private boolean validarDatos() {
        if (gps.getIdVehiculo() <= 0) {
            mensajeError("Debe seleccionar un vehiculo");
            return false;
        }

        if (gps.getUltimaActualizacion() == null) {
            mensajeError("La ultima actualizacion es obligatoria");
            return false;
        }

        if (gps.getLatitud() < -90 || gps.getLatitud() > 90) {
            mensajeError("La latitud debe estar entre -90 y 90");
            return false;
        }

        if (gps.getLongitud() < -180 || gps.getLongitud() > 180) {
            mensajeError("La longitud debe estar entre -180 y 180");
            return false;
        }

        return true;
    }

    private void mensajeError(String detalle) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validacion", detalle));
    }

    public List<Gps> getListaGps() {
        return listaGps;
    }

    public List<Vehiculo> getListaVehiculos() {
        return listaVehiculos;
    }

    public Gps getGps() {
        return gps;
    }

    public void setGps(Gps gps) {
        this.gps = gps;
    }
}
