
package hn.uth.sistemadetransporte.model.entities;


import java.io.Serializable;
import java.util.Date;

public class Combustible implements Serializable {
    private int id;
    private int idVehiculo;
    private Date fecha;
    private String tipoCombustible;
    private double galones;
    private double precioGalon;
    private double totalPagado;
    private int kilometrajeActual;
    private String placaVehiculo; // Auxiliar para mostrar en la tabla

    // Constructor vacío
    public Combustible() {
    }

    public int getId() {
        return id;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public double getGalones() {
        return galones;
    }

    public double getPrecioGalon() {
        return precioGalon;
    }

    public double getTotalPagado() {
        return totalPagado;
    }

    public int getKilometrajeActual() {
        return kilometrajeActual;
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

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public void setGalones(double galones) {
        this.galones = galones;
    }

    public void setPrecioGalon(double precioGalon) {
        this.precioGalon = precioGalon;
    }

    public void setTotalPagado(double totalPagado) {
        this.totalPagado = totalPagado;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }


}
