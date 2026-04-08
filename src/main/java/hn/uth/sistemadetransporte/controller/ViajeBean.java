package hn.uth.sistemadetransporte.controller;

import hn.uth.sistemadetransporte.model.dao.ConductorDAO;
import hn.uth.sistemadetransporte.model.dao.RutaDAO;
import hn.uth.sistemadetransporte.model.dao.VehiculoDAO;
import hn.uth.sistemadetransporte.model.dao.ViajeDAO;
import hn.uth.sistemadetransporte.model.entities.Conductor;
import hn.uth.sistemadetransporte.model.entities.Ruta;
import hn.uth.sistemadetransporte.model.entities.Vehiculo;
import hn.uth.sistemadetransporte.model.entities.Viaje;
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
public class ViajeBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Viaje viaje = new Viaje();
    private List<Viaje> listaViajes;
    private List<Vehiculo> listaVehiculos;
    private List<Conductor> listaConductores;
    private List<Ruta> listaRutas;

    private final ViajeDAO dao = new ViajeDAO();
    private final VehiculoDAO vehiculoDAO = new VehiculoDAO();
    private final ConductorDAO conductorDAO = new ConductorDAO();
    private final RutaDAO rutaDAO = new RutaDAO();

    @PostConstruct
    public void init() {
        listar();
        cargarCatalogos();
    }

    public void listar() {
        listaViajes = dao.listar();
    }

    public void cargarCatalogos() {
        listaVehiculos = vehiculoDAO.listarParaRelacion();
        listaConductores = conductorDAO.listarParaRelacion();
        listaRutas = rutaDAO.listarParaRelacion();
    }

    public void prepararNuevo() {
        cargarCatalogos();
        viaje = new Viaje();
        viaje.setFechaSalida(new Date());
    }

    public void guardar() {
        if (!validarDatos()) {
            return;
        }

        try {
            if (viaje.getId() == 0) {
                dao.guardar(viaje);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Exito", "Viaje guardado"));
            } else {
                dao.editar(viaje);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Exito", "Viaje actualizado"));
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
            dao.inactivar(id);
            listar();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Eliminado", "Viaje inactivado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    private boolean validarDatos() {
        if (viaje.getIdVehiculo() <= 0) {
            mensajeError("Debe seleccionar un vehiculo");
            return false;
        }

        if (viaje.getIdConductor() <= 0) {
            mensajeError("Debe seleccionar un conductor");
            return false;
        }

        if (viaje.getIdRuta() <= 0) {
            mensajeError("Debe seleccionar una ruta");
            return false;
        }

        if (viaje.getFechaSalida() == null) {
            mensajeError("La fecha de salida es obligatoria");
            return false;
        }

        if (viaje.getFechaLlegada() != null && viaje.getFechaLlegada().before(viaje.getFechaSalida())) {
            mensajeError("La fecha de llegada no puede ser menor que la fecha de salida");
            return false;
        }

        return true;
    }

    private void mensajeError(String detalle) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validacion", detalle));
    }

    public Viaje getViaje() {
        return viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }

    public List<Viaje> getListaViajes() {
        return listaViajes;
    }

    public List<Vehiculo> getListaVehiculos() {
        if (listaVehiculos == null) {
            cargarCatalogos();
        }
        return listaVehiculos;
    }

    public List<Conductor> getListaConductores() {
        if (listaConductores == null) {
            cargarCatalogos();
        }
        return listaConductores;
    }

    public List<Ruta> getListaRutas() {
        if (listaRutas == null) {
            cargarCatalogos();
        }
        return listaRutas;
    }
}
