package hn.uth.sistemadetransporte.controller;

import hn.uth.sistemadetransporte.model.dao.ReporteDAO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class ChartBean implements Serializable {

    private DonutChartModel donutModel; // Gráfica de Marcas
    private ReporteDAO dao = new ReporteDAO();

    @PostConstruct
    public void init() {
        crearDonutModel();
    }

    private void crearDonutModel() {
        donutModel = new DonutChartModel();
        ChartData data = new ChartData();
        DonutChartDataSet dataSet = new DonutChartDataSet();

        Map<String, Integer> reporte = dao.obtenerConteoPorMarca();
        List<Number> valores = new ArrayList<>(reporte.values());
        dataSet.setData(valores);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(255, 205, 86)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        data.setLabels(new ArrayList<>(reporte.keySet()));
        donutModel.setData(data);
    }

    // --- GETTER ---
    public DonutChartModel getDonutModel() {
        return donutModel;
    }
}
