
package hn.uth.sistemadetransporte.model.entities;

import java.io.Serializable;
import java.util.Date;

public class Mantenimiento implements Serializable {
    private int id;
    private int idVehiculo;
    private Date fechaMantenimiento;
    private String descripcion;
    private double costo;
    private String taller;
    private String placaVehiculo; // Campo auxiliar para la tabla

    public Mantenimiento() {

    }

    public int getId() {
        return id;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public Date getFechaMantenimiento() {
        return fechaMantenimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getCosto() {
        return costo;
    }

    public String getTaller() {
        return taller;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public void setFechaMantenimiento(Date fechaMantenimiento) {
        this.fechaMantenimiento = fechaMantenimiento;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public void setTaller(String taller) {
        this.taller = taller;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }


}
