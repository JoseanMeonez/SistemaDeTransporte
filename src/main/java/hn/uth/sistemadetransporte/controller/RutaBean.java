package hn.uth.sistemadetransporte.controller;

import hn.uth.sistemadetransporte.model.dao.RutaDAO;
import hn.uth.sistemadetransporte.model.entities.Ruta;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class RutaBean implements Serializable {
    private List<Ruta> listaRutas;
    private Ruta ruta = new Ruta();
    private RutaDAO dao = new RutaDAO();

    @PostConstruct
    public void init() {
        listar();
    }

    public void listar() {
        listaRutas = dao.listar();
    }

    public void registrar() {
        dao.guardar(ruta);
        ruta = new Ruta(); // Limpiar formulario
        listar(); // Refrescar tabla
    }

    // Getters y Setters (Importante para que JSF encuentre las propiedades)
    public List<Ruta> getListaRutas() {
        return listaRutas;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }
}