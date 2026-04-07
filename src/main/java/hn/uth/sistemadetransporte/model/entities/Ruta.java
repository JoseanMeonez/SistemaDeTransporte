
package hn.uth.sistemadetransporte.model.entities;


import java.io.Serializable;

public class Ruta implements Serializable {
    private int id;
    private String nombreRuta;
    private String origen;
    private String destino;
    private double distanciaKm;

    // Constructores, Getters y Setters (Generarlos en NetBeans con Alt+Insert)
    public Ruta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }
}
