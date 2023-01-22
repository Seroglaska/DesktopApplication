package sample.controllers.admincontrollers;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import sample.Main;


public class PieChartController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PieChart pieChart;

    ObservableList<PieChart.Data> list = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        try {
            Main.getCoos().writeObject("PROFIT_ALL_MONTHS");
            Map<String, Integer> map = (Map) Main.getCois().readObject();
            System.out.println(map);
            if (map.isEmpty()){
                System.out.println("idiot");
            }
            else{
                double sum = 0;
                for(Map.Entry<String, Integer> mapEntry : map.entrySet()){
                    sum += mapEntry.getValue();
                }
                double value = 0;
                for(Map.Entry<String, Integer> mapEntry : map.entrySet()){
                    value = (mapEntry.getValue() / sum) * 100;
                    list.add(new PieChart.Data(mapEntry.getKey(), value));
                }
            }
            pieChart.setData(list);
            pieChart.setTitle("Статистика дохода по месяцам");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}