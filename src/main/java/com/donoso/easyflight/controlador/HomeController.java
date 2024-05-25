package com.donoso.easyflight.controlador;

import com.donoso.easyflight.http.HttpClient;
import com.donoso.easyflight.pojos.Vuelo;
import com.donoso.easyflight.utils.URLApi;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.math.BigInteger;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HomeController implements Initializable {


    @FXML
    public BarChart dataChart;
    @FXML
    private Label main_TotalFlights;

    @FXML
    private Label main_TotalMonthlyFlights;

    @FXML
    private Label main_AnnualTotalFlights;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            pintarEstadísticas();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void inicializarDataChart(BigInteger totalVuelosMesActual, BigInteger totalVuelosAnioAtual, BigInteger vuelosTotal) {
        dataChart.setTitle("Vuelos Compañía");

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Vuelos Anual");
        series1.getData().add(new XYChart.Data<>("Anual", totalVuelosAnioAtual));

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Vuelos Mensual");
        series2.getData().add(new XYChart.Data<>("Mensual", totalVuelosMesActual));

        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName("Vuelos Total");
        series3.getData().add(new XYChart.Data<>("Total", vuelosTotal));

        dataChart.getData().setAll(series1, series2, series3);

    }

    /**
     *
     * Método que setea los parámetros en sus respectivas label
     * @param totalVuelosMesActual
     * @param totalVuelosAnioAtual
     * @param vuelosTotal
     */
    private void inicializarLabel(BigInteger totalVuelosMesActual, BigInteger totalVuelosAnioAtual, BigInteger vuelosTotal) {
        main_TotalMonthlyFlights.setText(totalVuelosMesActual.toString());
        main_AnnualTotalFlights.setText(totalVuelosAnioAtual.toString());
        main_TotalFlights.setText(vuelosTotal.toString());
    }

    private void pintarEstadísticas() throws Exception {
        BigInteger totalVuelosMesActual = null;
        BigInteger totalVuelosAnioAtual = null;
        BigInteger vuelosTotal = null;

        HttpClient<BigInteger, BigInteger> client = new HttpClient<>(BigInteger.class);
        totalVuelosMesActual = client.execute(URLApi.API_VUELO_CONTADOR.replace("{tipo}", "MONTH"), null, "GET");
        totalVuelosAnioAtual = client.execute(URLApi.API_VUELO_CONTADOR.replace("{tipo}", "YEAR"), null, "GET");
        vuelosTotal = client.execute(URLApi.API_VUELO_CONTADOR.replace("{tipo}", "TOTAL"), null, "GET");
        inicializarLabel(totalVuelosMesActual, totalVuelosAnioAtual, vuelosTotal);
        inicializarDataChart(totalVuelosMesActual, totalVuelosAnioAtual, vuelosTotal);
    }
}
