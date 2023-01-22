package sample.controllers.admincontrollers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Calendar;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Main;
import sample.bean.Client;
import sample.bean.Order;

public class InformationAdminController {

    @FXML
    private ComboBox<String> chosenPeriod;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Order> OrderTable;

    @FXML
    private Button backButton;

    @FXML
    private Label sum;

    @FXML
    private Button task;

    @FXML
    private TableColumn<Order, String> toFinalPrice;

    @FXML
    private TableColumn<Order, String> toFullName;

    @FXML
    private TableColumn<Order, String> toName;

    @FXML
    private TableColumn<Order, String> toPrice;

    @FXML
    private TableColumn<Order, String> toQuantity;

    @FXML
    private Button buttonStatistic;

    @FXML
    void clickButtonStatistic(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/files/PieChartInformation.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    @FXML
    void clickButtonChosenPeriod(ActionEvent event) {
    }

    @FXML
    void clickBackButton(ActionEvent event) {
        backButton.getScene().getWindow().hide();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/files/AdminMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void doTask(ActionEvent event) {
        try {
            Main.getCoos().writeObject("DO_TASK");
            String choice = chosenPeriod.getSelectionModel().getSelectedItem();
            int number = 0;
            switch (choice){
                case "За всё время":{
                    Main.getCoos().writeObject("ALL_PERIOD");
                    number = (Integer) Main.getCois().readObject();

                    System.out.println(new Date(System.currentTimeMillis()).getMonth());
                }
                break;
                case "За этот год":{
                    Main.getCoos().writeObject("YEAR_PERIOD");
                    number = (Integer) Main.getCois().readObject();
                }
                break;
                case "За этот месяц":{
                    Main.getCoos().writeObject("MONTH_PERIOD");
                    number = (Integer) Main.getCois().readObject();
                }
                break;
            }
            sum.setText(String.valueOf(number));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    ObservableList<String> list = FXCollections.observableArrayList("За всё время", "За этот год", "За этот месяц");

    @FXML
    void initialize() {
        chosenPeriod.setItems(list);
        chosenPeriod.setValue("За всё время");
        Map<Integer, Map<String, String>> orderMap = null;
        try {
            Main.getCoos().writeObject("FIND_ORDERS");
            orderMap = (Map) Main.getCois().readObject();
            System.out.println(orderMap);
            if (orderMap.isEmpty()){
                System.out.println("idiot");
            }
            else{
                toName.setCellValueFactory(new PropertyValueFactory<>("name"));
                toFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
                toQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                toPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
                toFinalPrice.setCellValueFactory(new PropertyValueFactory<>("finalPrice"));
                for(Map.Entry<Integer, Map<String, String>> mapEntry : orderMap.entrySet()){
                    OrderTable.getItems().add((new Order(mapEntry.getKey(),
                            mapEntry.getValue().get("fullName"),
                            mapEntry.getValue().get("name"),
                            mapEntry.getValue().get("sell_price"),
                            mapEntry.getValue().get("quantity"),
                            mapEntry.getValue().get("finalPrice"))));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

